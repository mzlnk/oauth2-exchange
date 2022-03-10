package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.GoogleAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GoogleOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleOAuth2TokenResponseHandler;
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
        prefix = "oauth2.exchange.providers.google",
        properties = {"client-id", "client-secret", "redirect-uri"}
)
public class GoogleAuthorizationCodeExchangeDefaultConfiguration {

    private final Logger log = LoggerFactory.getLogger(GoogleAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean(name = "defaultGoogleOAuth2Client")
    public GoogleOAuth2Client googleOAuth2Client(@Value("${oauth2.exchange.providers.google.client-id}") String clientId,
                                                 @Value("${oauth2.exchange.providers.google.client-secret}") String clientSecret,
                                                 @Value("${oauth2.exchange.providers.google.redirect-uri}") String redirectUri) {
        return new GoogleOAuth2Client(clientId, clientSecret, redirectUri);
    }

    @Bean(name = "defaultGoogleTokenResponseHandler")
    public GoogleOAuth2TokenResponseHandler googleOAuth2TokenResponseHandler(ObjectMapper objectMapper) {
        return new GoogleOAuth2TokenResponseHandler(objectMapper);
    }

    @Bean(name = "defaultGoogleExchange")
    public GoogleAuthorizationCodeExchange googleAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultGoogleOAuth2Client") GoogleOAuth2Client exchangeClient,
                                                                           @Qualifier("defaultGoogleTokenResponseHandler") GoogleOAuth2TokenResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for Google auth provider");
        return GoogleAuthorizationCodeExchange.builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
