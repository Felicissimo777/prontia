package br.com.zyntra.prontia.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.beans.BeanProperty;
import java.net.http.HttpClient;

@Configuration
public class AiConfigCliente {

    @Bean
    RestClient ollamaRestClient(OllamaPropiedades ollamaPropiedades){
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(ollamaPropiedades.connectTimeout())
                .build();
        return RestClient.builder().requestFactory(new JdkClientHttpRequestFactory(httpClient)).build();
    }
    
}
