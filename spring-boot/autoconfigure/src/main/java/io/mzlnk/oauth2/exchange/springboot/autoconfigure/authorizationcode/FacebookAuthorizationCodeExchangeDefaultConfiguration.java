package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.FacebookAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookAuthorizationCodeExchangeResponseHandler;
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
    public FacebookAuthorizationCodeExchangeClient facebookAuthorizationCodeExchangeClient(@Value("${oauth2.exchange.providers.facebook.client-id}") String clientId,
                                                                                       @Value("${oauth2.exchange.providers.facebook.client-secret}") String clientSecret,
                                                                                       @Value("${oauth2.exchange.providers.facebook.redirect-uri}") String redirectUri) {
        return new FacebookAuthorizationCodeExchangeClient(clientId, clientSecret, redirectUri);
    }

    @Bean("defaultFacebookResponseHandler")
    public FacebookAuthorizationCodeExchangeResponseHandler facebookAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        return new FacebookAuthorizationCodeExchangeResponseHandler(objectMapper);
    }

    @Bean(name = "defaultFacebookExchange")
    public FacebookAuthorizationCodeExchange facebookAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultFacebookExchangeClient") FacebookAuthorizationCodeExchangeClient exchangeClient,
                                                                           @Qualifier("defaultFacebookResponseHandler") FacebookAuthorizationCodeExchangeResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for Facebook auth provider");
        return new FacebookAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
