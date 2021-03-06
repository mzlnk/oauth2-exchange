package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.GitHubAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GitHubOAuth2TokenResponseHandler;
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
        prefix = "oauth2.exchange.providers.github",
        properties = {"client-id", "client-secret", "redirect-uri"}
)
public class GitHubAuthorizationCodeExchangeDefaultConfiguration {

    private final Logger log = LoggerFactory.getLogger(GitHubAuthorizationCodeExchangeDefaultConfiguration.class);

    @Bean(name = "defaultGitHubOAuth2Client")
    public GitHubOAuth2Client gitHubOAuth2Client(@Value("${oauth2.exchange.providers.github.client-id}") String clientId,
                                                 @Value("${oauth2.exchange.providers.github.client-secret}") String clientSecret,
                                                 @Value("${oauth2.exchange.providers.github.redirect-uri}") String redirectUri) {
        return new GitHubOAuth2Client(clientId, clientSecret, redirectUri);
    }

    @Bean(name = "defaultGitHubTokenResponseHandler")
    public GitHubOAuth2TokenResponseHandler gitHubOAuth2TokenResponseHandler(ObjectMapper objectMapper) {
        return new GitHubOAuth2TokenResponseHandler(objectMapper);
    }

    @Bean(name = "defaultGitHubExchange")
    public GitHubAuthorizationCodeExchange gitHubAuthorizationCodeExchange(OkHttpClient httpClient,
                                                                           @Qualifier("defaultGitHubOAuth2Client") GitHubOAuth2Client exchangeClient,
                                                                           @Qualifier("defaultGitHubTokenResponseHandler") GitHubOAuth2TokenResponseHandler responseHandler) {
        log.debug("Creating default OAuth2 authorization code exchange for GitHub auth provider");
        return GitHubAuthorizationCodeExchange.builder()
                .httpClient(httpClient)
                .oAuth2Client(exchangeClient)
                .responseHandler(responseHandler)
                .build();
    }

}
