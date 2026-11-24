package br.com.zyntra.prontia.service;
import br.com.zyntra.prontia.AI.MensagemIa;
import br.com.zyntra.prontia.AI.OllamaClient;
import br.com.zyntra.prontia.AI.PromptBuilder;
import br.com.zyntra.prontia.DTO.MensagemRequest;
import br.com.zyntra.prontia.DTO.MensagemResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {
  private static final String CONTEXTO_FICTICIO = """
 Nome: Barbearia Modelo
            Endereço: Rua das Flores, 123 - Centro
            Funcionamento: terça a sábado, das 9h às 19h. Fechado domingo e segunda.
            Serviços:
            - Corte masculino: R$ 40 (30 min)
            - Barba: R$ 30 (20 min)
            - Corte + barba: R$ 65 (50 min)
            """; 
            private final OllamaClient ollama;
            private final PromptBuilder promptBuilder;

            public AtendimentoService(OllamaClient ollama, PromptBuilder promptBuilder){
                this.ollama = ollama;
                this.promptBuilder = promptBuilder;
            }

            public MensagemResponse responder(MensagemRequest request){
                List<MensagemIa> conversa =List.of(MensagemIa.system(promptBuilder.montarSistema(CONTEXTO_FICTICIO)),MensagemIa.user(request.texto()));
                return new MensagemResponse(ollama.conversar(conversa));
            }
}
