package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.FacebookAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookOAuth2TokenResponseHandler;
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
        prefix = "oauth2.exchange.providers.facebook",
        properties = {"client-id", "client-secret", "redirect-uri"}
)
public class FacebookAuthorizationCodeExchangeDefaultConfiguration {

    private final Logger log = LoggerFactory.getLogger(FacebookAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean("defaultFacebookExchangeClient")
    public FacebookOAuth2Client facebookAuthorizationCodeExchangeClient(@Value("${oauth2.exchange.providers.facebook.client-id}") String clientId,
                                                                        @Value("${oauth2.exchange.providers.facebook.client-secret}") String clientSecret,
                                                                        @Value("${oauth2.exchange.providers.facebook.redirect-uri}") String redirectUri) {
        return new FacebookOAuth2Client(clientId, clientSecret, redirectUri);
    }

    @Bean("defaultFacebookResponseHandler")
    public FacebookOAuth2TokenResponseHandler facebookAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new FacebookOAuth2TokenResponseHandler(objectMapper);
    }

    @Bean(name = "defaultFacebookExchange")
    public FacebookAuthorizationCodeExchange facebookAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultFacebookExchangeClient") FacebookOAuth2Client exchangeClient,
                                                                           @Qualifier("defaultFacebookResponseHandler") FacebookOAuth2TokenResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for Facebook auth provider");
        return FacebookAuthorizationCodeExchange.builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
