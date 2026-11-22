package br.com.zyntra.prontia.config;
import org.springframework.boot.context.properties. ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "prontia.ollama")
public record OllamaPropiedades(
        String baseUrl,
        String modelo,
        double temperatura,
        Duration connectTimeout,
        Duration readTimeout
) {
    
}
