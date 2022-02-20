package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.OktaAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OktaAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertiesExist(
        prefix = "oauth2.exchange.providers.okta",
        properties = {"client-id", "client-secret", "redirect-uri", "client-type"}
)
public class OktaAuthorizationCodeExchangeDefaultConfiguration {

    @Bean(name = "defaultOktaExchangeClient")
    @ConditionalOnPropertiesExist(prefix = "oauth2.exchange.providers.okta", properties = "okta-domain")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.okta", name = "client-type", havingValue = "SINGLE_SIGN_ON")
    public OktaAuthorizationCodeExchangeClient oktaAuthorizationCodeExchangeSingleSignOnClient(@Value("${oauth2.exchange.providers.okta.client-id}") String clientId,
                                                                                               @Value("${oauth2.exchange.providers.okta.client-secret}") String clientSecret,
                                                                                               @Value("${oauth2.exchange.providers.okta.redirect-uri}") String redirectUri,
                                                                                               @Value("${oauth2.exchange.providers.okta.okta-domain}") String oktaDomain) {
        return new OktaAuthorizationCodeExchangeClient.OktaSingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain);
    }

    @Bean(name = "defaultOktaExchangeClient")
    @ConditionalOnPropertiesExist(prefix = "oauth2.exchange.providers.okta", properties = {"okta-domain", "okta-authorization-server-id"})
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.okta", name = "client-type", havingValue = "AUTHORIZATION_SERVER")
    public OktaAuthorizationCodeExchangeClient oktaAuthorizationCodeExchangeAuthorizationServerClient(@Value("${oauth2.exchange.providers.okta.client-id}") String clientId,
                                                                                                      @Value("${oauth2.exchange.providers.okta.client-secret}") String clientSecret,
                                                                                                      @Value("${oauth2.exchange.providers.okta.redirect-uri}") String redirectUri,
                                                                                                      @Value("${oauth2.exchange.providers.okta.okta-domain}") String oktaDomain,
                                                                                                      @Value("${oauth2.exchange.providers.okta.okta-authorization-server-id}") String oktaAuthorizationServerId) {
        return new OktaAuthorizationCodeExchangeClient.OktaAuthorizationServerClient(clientId, clientSecret, redirectUri, oktaDomain, oktaAuthorizationServerId);
    }

    @Bean(name = "defaultOktaResponseHandler")
    public OktaAuthorizationCodeExchangeResponseHandler oktaAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new OktaAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean(name = "defaultOktaExchange")
    public OktaAuthorizationCodeExchange oktaAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                       @Qualifier("defaultOktaExchangeClient") OktaAuthorizationCodeExchangeClient exchangeClient,
                                                                       @Qualifier("defaultOktaResponseHandler") OktaAuthorizationCodeExchangeResponseHandler responseHandler) {
        return new OktaAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
