package io.mzlnk.oauth2.exchange.core.authorizationcode;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents an exchange which is responsible for exchanging the authorization code from incoming HTTP request for a token
 * in OAuth2 authorization code flow.
 *
 * @param <R> token response type which extends {@link Map} with {@link String}-{@link Object} key-value pairs
 */
public interface AuthorizationCodeExchange<R extends Map<String, Object>> {

    /**
     * Exchanges authorization code obtained from incoming HTTP response for token response
     * by making proper HTTP request to the authorization provider.
     *
     * @param code authorization code obtained from incoming HTTP response
     * @return token response which consists of response data in {@link Map} form
     */
    R exchangeAuthorizationCode(@NotNull String code);

}
