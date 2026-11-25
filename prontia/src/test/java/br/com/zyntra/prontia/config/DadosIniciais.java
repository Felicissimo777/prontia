package test.java.br.com.zyntra.prontia.config;

import br.com.zyntra.prontia.entity.Estabelecimento;
import br.com.zyntra.prontia.entity.Servico;
import br.com.zyntra.prontia.repository.EstabelecimentoRepository;
import br.com.zyntra.prontia.repository.ServicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;

@Configuration
public class DadosIniciais {

    @Bean
    CommandLineRunner popularBanco(EstabelecimentoRepository estabelecimentos,
                                   ServicoRepository servicos) {
        return args -> {
            if (estabelecimentos.findBySlug("barbearia-prime").isPresent()) {
                return;
            }

            Estabelecimento prime = new Estabelecimento();
            prime.setSlug("barbearia-prime");
            prime.setNome("Barbearia Prime");
            prime.setAbertura(LocalTime.of(9, 0));
            prime.setFechamento(LocalTime.of(19, 0));
            prime.setDiasFuncionamento(EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.SATURDAY));
            estabelecimentos.save(prime);

            Servico corte = new Servico();
            corte.setNome("Corte masculino");
            corte.setPreco(new BigDecimal("35.00"));
            corte.setDuracaoMinutos(40);
            corte.setEstabelecimento(prime);
            servicos.save(corte);
        };
    }
}