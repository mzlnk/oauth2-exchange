package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.MicrosoftAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.MicrosoftAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition.ConditionalOnPropertiesExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertiesExist(
        prefix = "oauth2.exchange.providers.microsoft",
        properties = {"client-id", "client-secret", "redirect-uri", "client-type"}
)
public class MicrosoftAuthorizationCodeExchangeDefaultConfiguration {

    private final Logger log = LoggerFactory.getLogger(MicrosoftAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean(name = "defaultMicrosoftExchangeClient")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.microsoft", name = "client-type", havingValue = "COMMON")
    public MicrosoftAuthorizationCodeExchangeClient microsoftAuthorizationCodeExchangeCommonClient(@Value("${oauth2.exchange.providers.microsoft.client-id}") String clientId,
                                                                                                   @Value("${oauth2.exchange.providers.microsoft.client-secret}") String clientSecret,
                                                                                                   @Value("${oauth2.exchange.providers.microsoft.redirect-uri}") String redirectUri) {
        return new MicrosoftAuthorizationCodeExchangeClient.MicrosoftCommonClient(clientId, clientSecret, redirectUri);
    }

    @Bean(name = "defaultMicrosoftExchangeClient")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.microsoft", name = "client-type", havingValue = "CONSUMER")
    public MicrosoftAuthorizationCodeExchangeClient microsoftAuthorizationCodeExchangeConsumerClient(@Value("${oauth2.exchange.providers.microsoft.client-id}") String clientId,
                                                                                                     @Value("${oauth2.exchange.providers.microsoft.client-secret}") String clientSecret,
                                                                                                     @Value("${oauth2.exchange.providers.microsoft.redirect-uri}") String redirectUri) {
        return new MicrosoftAuthorizationCodeExchangeClient.MicrosoftConsumerClient(clientId, clientSecret, redirectUri);
    }

    @Bean(name = "defaultMicrosoftExchangeClient")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.microsoft", name = "client-type", havingValue = "ORGANIZATION")
    public MicrosoftAuthorizationCodeExchangeClient microsoftAuthorizationCodeExchangeOrganizationClient(@Value("${oauth2.exchange.providers.microsoft.client-id}") String clientId,
                                                                                                         @Value("${oauth2.exchange.providers.microsoft.client-secret}") String clientSecret,
                                                                                                         @Value("${oauth2.exchange.providers.microsoft.redirect-uri}") String redirectUri) {
        return new MicrosoftAuthorizationCodeExchangeClient.MicrosoftOrganizationClient(clientId, clientSecret, redirectUri);
    }

    @Bean(name = "defaultMicrosoftExchangeClient")
    @ConditionalOnPropertiesExist(prefix = "oauth2.exchange.providers.microsoft", properties = "azure-ad-id")
    @ConditionalOnProperty(prefix = "oauth2.exchange.providers.microsoft", name = "client-type", havingValue = "AZURE")
    public MicrosoftAuthorizationCodeExchangeClient microsoftAuthorizationCodeExchangeAzureADClient(@Value("${oauth2.exchange.providers.microsoft.client-id}") String clientId,
                                                                                                    @Value("${oauth2.exchange.providers.microsoft.client-secret}") String clientSecret,
                                                                                                    @Value("${oauth2.exchange.providers.microsoft.redirect-uri}") String redirectUri,
                                                                                                    @Value("${oauth2.exchange.providers.microsoft.azure-ad-id}") String azureAdId) {
        return new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(clientId, clientSecret, redirectUri, azureAdId);
    }

    @Bean(name = "defaultMicrosoftResponseHandler")
    public MicrosoftAuthorizationCodeExchangeResponseHandler microsoftAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new MicrosoftAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean(name = "defaultMicrosoftExchange")
    public MicrosoftAuthorizationCodeExchange microsoftAuthorizationCodeExchange(@Qualifier("defaultMicrosoftExchangeClient") MicrosoftAuthorizationCodeExchangeClient exchangeClient,
                                                                                 @Qualifier("defaultMicrosoftResponseHandler") MicrosoftAuthorizationCodeExchangeResponseHandler responseHandler,
                                                                                 @Value("${oauth2.exchange.providers.microsoft.scope:#{null}}") String scope,
                                                                                 @Value("${oauth2.exchange.providers.microsoft.code-verifier:#{null}}") String codeVerifier,
                                                                                 @Value("${oauth2.exchange.providers.microsoft.client-assertion-type:#{null}}") String clientAssertionType,
                                                                                 @Value("${oauth2.exchange.providers.microsoft.client-assertion:#{null}}") String clientAssertion) {
        log.debug("Creating default OAuth2 authorization code exchange for Microsoft auth provider");
        return new MicrosoftAuthorizationCodeExchange.Builder()
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .scope(scope)
                .codeVerifier(codeVerifier)
                .clientAssertionType(clientAssertionType)
                .clientAssertion(clientAssertion)
                .build();
    }

}
