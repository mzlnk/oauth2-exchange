package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.GoogleAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GoogleAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertiesExist(
        prefix = "oauth2.exchange.providers.google",
        properties = {"client-id", "client-secret", "redirect-uri"}
)
public class GoogleAuthorizationCodeExchangeDefaultConfiguration {

    @Bean
    @Qualifier("defaultGoogleExchangeClient")
    public GoogleAuthorizationCodeExchangeClient googleAuthorizationCodeExchangeClient(@Value("${oauth2.exchange.providers.google.client-id}") String clientId,
                                                                                       @Value("${oauth2.exchange.providers.google.client-secret}") String clientSecret,
                                                                                       @Value("${oauth2.exchange.providers.google.redirect-uri}") String redirectUri) {
        return new GoogleAuthorizationCodeExchangeClient(clientId, clientSecret, redirectUri);
    }

    @Bean
    @Qualifier("defaultGoogleResponseHandler")
    public GoogleAuthorizationCodeExchangeResponseHandler googleAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new GoogleAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean
    @Qualifier("defaultGoogleExchange")
    public GoogleAuthorizationCodeExchange googleAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultGoogleExchangeClient") GoogleAuthorizationCodeExchangeClient exchangeClient,
                                                                           @Qualifier("defaultGoogleResponseHandler") GoogleAuthorizationCodeExchangeResponseHandler responseHandler) {
        return new GoogleAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
