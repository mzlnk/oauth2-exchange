package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.GitHubAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GitHubAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertiesExist(
        prefix = "oauth2.exchange.providers.github",
        properties = {"client-id", "client-secret", "redirect-uri"}
)
public class GitHubAuthorizationCodeExchangeDefaultConfiguration {

    @Bean("defaultGitHubExchangeClient")
    public GitHubAuthorizationCodeExchangeClient gitHubAuthorizationCodeExchangeClient(@Value("${oauth2.exchange.providers.github.client-id}") String clientId,
                                                                                       @Value("${oauth2.exchange.providers.github.client-secret}") String clientSecret,
                                                                                       @Value("${oauth2.exchange.providers.github.redirect-uri}") String redirectUri) {
        return new GitHubAuthorizationCodeExchangeClient(clientId, clientSecret, redirectUri);
    }

    @Bean("defaultGitHubResponseHandler")
    public GitHubAuthorizationCodeExchangeResponseHandler gitHubAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new GitHubAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean(name = "defaultGitHubExchange")
    public GitHubAuthorizationCodeExchange gitHubAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultGitHubExchangeClient") GitHubAuthorizationCodeExchangeClient exchangeClient,
                                                                           @Qualifier("defaultGitHubResponseHandler") GitHubAuthorizationCodeExchangeResponseHandler responseHandler) {
        return new GitHubAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
