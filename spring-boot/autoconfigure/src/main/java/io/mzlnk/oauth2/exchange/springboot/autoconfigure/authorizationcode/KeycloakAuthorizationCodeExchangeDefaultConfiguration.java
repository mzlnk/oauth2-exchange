package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.KeycloakAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertiesExist(
        prefix = "oauth2.exchange.providers.keycloak",
        properties = {"client-id", "client-secret", "redirect-uri", "host", "realm"}
)
public class KeycloakAuthorizationCodeExchangeDefaultConfiguration {

    private final Logger log = LoggerFactory.getLogger(KeycloakAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean("defaultKeycloakExchangeClient")
    public KeycloakAuthorizationCodeExchangeClient keycloakAuthorizationCodeExchangeClient(@Value("${oauth2.exchange.providers.keycloak.client-id}") String clientId,
                                                                                           @Value("${oauth2.exchange.providers.keycloak.client-secret}") String clientSecret,
                                                                                           @Value("${oauth2.exchange.providers.keycloak.redirect-uri}") String redirectUri,
                                                                                           @Value("${oauth2.exchange.providers.keycloak.host}") String host,
                                                                                           @Value("${oauth2.exchange.providers.keycloak.realm}") String realm) {
        return new KeycloakAuthorizationCodeExchangeClient(clientId, clientSecret, redirectUri, host, realm);
    }

    @Bean("defaultKeycloakResponseHandler")
    public KeycloakAuthorizationCodeExchangeResponseHandler keycloakAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new KeycloakAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean(name = "defaultKeycloakExchange")
    public KeycloakAuthorizationCodeExchange keycloakAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                               @Qualifier("defaultKeycloakExchangeClient") KeycloakAuthorizationCodeExchangeClient exchangeClient,
                                                                               @Qualifier("defaultKeycloakResponseHandler") KeycloakAuthorizationCodeExchangeResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for Keycloak auth provider");
        return new KeycloakAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }


}
