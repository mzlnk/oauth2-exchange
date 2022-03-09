package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a response returned during authorization code for token exchange in OAuth2 authorization code flow
 * for Keycloak identity provider.
 * <br />
 * The example response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "expires_in": 300,
 *   "refresh_expires_in": 1800,
 *   "refresh_token": "some-refresh-token",
 *   "token_type": "bearer",
 *   "not-before-policy": 0,
 *   "session_state": "some-session-state",
 *   "scope": "some-scope"
 * }
 * </pre>
 * The structure of the response and all its details are created and documented based on the information posted on the official
 * <a href="https://www.keycloak.org/docs/latest/authorization_services/">documentation site</a>.
 */
public final class KeycloakOAuth2TokenResponse extends AbstractOAuth2TokenResponse {

    private KeycloakOAuth2TokenResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    /**
     * Returns an access token which has been returned in a HTTP response under <code>access_token</code> parameter
     *
     * @return string representation of an access token
     */
    @Override
    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    /**
     * Returns a token type which has been returned in a HTTP response under <code>token_type</code> parameter
     *
     * @return string representation of a token type
     */
    @Override
    public String getTokenType() {
        return (String) this.get("token_type");
    }

    /**
     * Returns an access token expiration time which has been returned in a HTTP response under <code>expires_in</code> parameter
     *
     * @return number representation of an access token expiration time
     */
    @Override
    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

    /**
     * Returns a refresh token which has been returned in a HTTP response under <code>refresh_token</code> parameter
     *
     * @return string representation of an refresh token
     */
    @Override
    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    /**
     * Returns a scope which has been returned in a HTTP response under <code>scope</code> parameter
     *
     * @return string representation of a scope
     */
    @Override
    public String getScope() {
        return (String) this.get("scope");
    }

    /**
     * Returns a refresh token expiration time which has been returned in a HTTP response under <code>refresh_expires_in</code> parameter
     *
     * @return number representation of a refresh token expiration time
     */
    public Integer getRefreshExpiresIn() {
        return (Integer) this.get("refresh_expires_in");
    }

    /**
     * Returns a not-before-policy related value which has been returned in a HTTP response under <code>not-before-policy</code> parameter
     *
     * @return number representation of a token type
     */
    public Integer getNotBeforePolicy() {
        return (Integer) this.get("not-before-policy");
    }

    /**
     * Returns a session state which has been returned in a HTTP response under <code>session_state</code> parameter
     *
     * @return string representation of a session state
     */
    public String getSessionState() {
        return (String) this.get("session_state");
    }

    /**
     * Represents factory to create OAuth2 token response received from Keycloak authorization provider.
     */
    public static final class Factory implements OAuth2TokenResponse.Factory {

        /**
         * Construct an response from a given map of values, usually returned as a JSON HTTP body response from HTTP request
         * issued for exchanging authorization code for a token.
         *
         * @param values non-null map of values (representation of a JSON HTTP response body)
         * @return instance of a response based on given values
         * @throws NullPointerException if given map of values is null
         */
        @Override
        public OAuth2TokenResponse create(@NotNull Map<String, Object> values) {
            return new KeycloakOAuth2TokenResponse(values);
        }

    }

}
