package br.com.zyntra.prontia.AI;

import br.com.zyntra.prontia.config.OllamaPropiedades;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class OllamaClient {

    private static final Logger log = LoggerFactory.getLogger(OllamaClient.class);

    // Modelos Qwen3 podem devolver o raciocínio dentro de <think>...</think>
    private static final Pattern THINK = Pattern.compile("(?s)<think>.*?</think>");

    private final RestClient restClient;
    private final OllamaPropiedades props;

    public OllamaClient(RestClient ollamaRestClient, OllamaPropiedades props) {
        this.restClient = ollamaRestClient;
        this.props = props;
    }

    public String conversar(List<MensagemIa> mensagens) {
        var corpo = Map.of(
                "model", props.modelo(),
                "messages", mensagens,
                "stream", false,
                "think", false,
                "options", Map.of("temperature", props.temperatura())
        );

        try {
            OllamaChatResponse resposta = restClient.post()
                    .uri(props.baseUrl() + "/api/chat")
                    .body(corpo)
                    .retrieve()
                    .body(OllamaChatResponse.class);

            if (resposta == null || resposta.message() == null
                    || resposta.message().content() == null) {
                throw new IllegalStateException("Resposta vazia ou inválida do Ollama.");
            }
            return THINK.matcher(resposta.message().content()).replaceAll("").trim();

        } catch (RestClientException e) {
            log.error("Falha ao chamar o Ollama.", e);
            throw new IllegalStateException("Não foi possível falar com a IA.", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record OllamaChatResponse(MensagemIa message) {
    }
}