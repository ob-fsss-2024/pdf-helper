package com.example.outbrain.openai.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class GptApiClientConfiguration {
    //@Bean
//    public GptApiClient apiClient(@Value("${gpt.api.url}") final String gptApiUrl) {
//        final RestClient restClient = RestClient.builder().baseUrl(gptApiUrl).build();
//        final RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        final HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        return factory.createClient(GptApiClient.class);
//    }
}