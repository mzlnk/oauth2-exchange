package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an exchange which is responsible for exchanging the authorization code from incoming HTTP request for a token
 * in OAuth2 authorization code flow.
 */
public interface AuthorizationCodeExchange {

    /**
     * Exchanges authorization code obtained from incoming HTTP response for token response
     * by making proper HTTP request to the authorization provider.
     *
     * @param code authorization code obtained from incoming HTTP response
     * @return {@link OAuth2TokenResponse} token response which consists of a response data
     */
    OAuth2TokenResponse exchangeAuthorizationCode(@NotNull String code);

}
