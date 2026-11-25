package test.java.br.com.zyntra.prontia.service;

import br.com.zyntra.prontia.entity.Estabelecimento;
import br.com.zyntra.prontia.entity.Servico;
import br.com.zyntra.prontia.repository.EstabelecimentoRepository;
import br.com.zyntra.prontia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private static final Locale PT_BR = Locale.of("pt", "BR");

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ServicoRepository servicoRepository;

    @Transactional(readOnly = true)
    public String gerarContexto(String slug) {
        Estabelecimento e = estabelecimentoRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalStateException(
                        "Estabelecimento não encontrado: " + slug));
        List<Servico> servicos = servicoRepository.findByEstabelecimentoIdOrderByNome(e.getId());

        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(e.getNome()).append("\n");
        if (e.getEndereco() != null) {
            sb.append("Endereço: ").append(e.getEndereco()).append("\n");
        }
        sb.append("Funcionamento: ").append(formatarDias(e.getDiasFuncionamento()))
                .append(", das ").append(hora(e.getAbertura()))
                .append(" às ").append(hora(e.getFechamento())).append("\n");
        sb.append("Serviços:\n");
        for (Servico s : servicos) {
            sb.append("- ").append(s.getNome()).append(": ")
                    .append(moeda(s)).append(" (")
                    .append(s.getDuracaoMinutos()).append(" min)\n");
        }
        return sb.toString();
    }

    private String moeda(Servico s) {
        return NumberFormat.getCurrencyInstance(PT_BR).format(s.getPreco())
                .replace('\u00A0', ' ');
    }

    private String hora(LocalTime t) {
        return t.getMinute() == 0
                ? "%dh".formatted(t.getHour())
                : "%dh%02d".formatted(t.getHour(), t.getMinute());
    }

    private String formatarDias(Set<DayOfWeek> dias) {
        List<DayOfWeek> ordenados = dias.stream().sorted().toList();
        if (ordenados.isEmpty()) {
            return "não informado";
        }
        DayOfWeek primeiro = ordenados.getFirst();
        DayOfWeek ultimo = ordenados.getLast();
        boolean continuo = ordenados.size() > 2
                && ultimo.getValue() - primeiro.getValue() == ordenados.size() - 1;
        if (continuo) {
            return nomeDia(primeiro) + " a " + nomeDia(ultimo);
        }
        return ordenados.stream().map(this::nomeDia).collect(Collectors.joining(", "));
    }

    private String nomeDia(DayOfWeek d) {
        return d.getDisplayName(TextStyle.FULL, PT_BR);
    }
}