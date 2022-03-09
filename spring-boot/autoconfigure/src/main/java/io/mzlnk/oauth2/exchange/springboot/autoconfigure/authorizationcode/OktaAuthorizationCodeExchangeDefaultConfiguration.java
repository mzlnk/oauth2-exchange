package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.OktaAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2AuthorizationServerClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2SingleSignOnClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OktaOAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(OktaAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean(name = "defaultOktaExchangeClient")
    @ConditionalOnPropertiesExist(prefix = "oauth2.exchange.providers.okta", properties = "okta-domain")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.okta", name = "client-type", havingValue = "SINGLE_SIGN_ON")
    public OktaOAuth2Client oktaAuthorizationCodeExchangeSingleSignOnClient(@Value("${oauth2.exchange.providers.okta.client-id}") String clientId,
                                                                            @Value("${oauth2.exchange.providers.okta.client-secret}") String clientSecret,
                                                                            @Value("${oauth2.exchange.providers.okta.redirect-uri}") String redirectUri,
                                                                            @Value("${oauth2.exchange.providers.okta.okta-domain}") String oktaDomain) {
        return new OktaOAuth2SingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain);
    }

    @Bean(name = "defaultOktaExchangeClient")
    @ConditionalOnPropertiesExist(prefix = "oauth2.exchange.providers.okta", properties = {"okta-domain", "okta-authorization-server-id"})
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.okta", name = "client-type", havingValue = "AUTHORIZATION_SERVER")
    public OktaOAuth2Client oktaAuthorizationCodeExchangeAuthorizationServerClient(@Value("${oauth2.exchange.providers.okta.client-id}") String clientId,
                                                                                   @Value("${oauth2.exchange.providers.okta.client-secret}") String clientSecret,
                                                                                   @Value("${oauth2.exchange.providers.okta.redirect-uri}") String redirectUri,
                                                                                   @Value("${oauth2.exchange.providers.okta.okta-domain}") String oktaDomain,
                                                                                   @Value("${oauth2.exchange.providers.okta.okta-authorization-server-id}") String oktaAuthorizationServerId) {
        return new OktaOAuth2AuthorizationServerClient(clientId, clientSecret, redirectUri, oktaDomain, oktaAuthorizationServerId);
    }

    @Bean(name = "defaultOktaResponseHandler")
    public OktaOAuth2TokenResponseHandler oktaAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new OktaOAuth2TokenResponseHandler(objectMapper);
    }

    @Bean(name = "defaultOktaExchange")
    public OktaAuthorizationCodeExchange oktaAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                       @Qualifier("defaultOktaExchangeClient") OktaOAuth2Client exchangeClient,
                                                                       @Qualifier("defaultOktaResponseHandler") OktaOAuth2TokenResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for Okta auth provider");
        return OktaAuthorizationCodeExchange.builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
