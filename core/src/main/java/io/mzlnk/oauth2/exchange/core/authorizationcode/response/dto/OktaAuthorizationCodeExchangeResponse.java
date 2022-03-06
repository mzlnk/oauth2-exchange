package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a response returned during authorization code for token exchange in OAuth2 authorization code flow
 * for Okta identity provider.
 * <br />
 * The example response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "token_type": "Bearer",
 *   "expires_in": 3600,
 *   "scope": "some-scope",
 *   "refresh_token": "some-refresh-token",
 *   "id_token": "some-id-token"
 * }
 * </pre>
 * The structure of the response and all its details are created and documented based on the information posted on the official
 * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
 */
public final class OktaAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    /**
     * Construct an response from a given map of values, usually returned as a JSON HTTP body response from HTTP request
     * issued for exchanging authorization code for a token.
     *
     * @param values non-null map of values (representation of a JSON HTTP response body)
     * @return instance of a response based on given values
     * @throws NullPointerException if given map of values is null
     */
    public static OktaAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new OktaAuthorizationCodeExchangeResponse(values);
    }

    private OktaAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    /**
     * Returns an access token which has been returned in a HTTP response under <code>access_token</code> parameter
     *
     * @return string representation of an access token
     */
    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    /**
     * Returns a token type which has been returned in a HTTP response under <code>token_type</code> parameter
     *
     * @return string representation of a token type
     */
    public String getTokenType() {
        return (String) this.get("token_type");
    }

    /**
     * Returns a token expiration time which has been returned in a HTTP response under <code>expires_in</code> parameter
     *
     * @return number representation of an token expiration time
     */
    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

    /**
     * Returns a scope which has been returned in a HTTP response under <code>scope</code> parameter
     *
     * @return string representation of a scope
     */
    public String getScope() {
        return (String) this.get("scope");
    }

    /**
     * Returns a refresh token which has been returned in a HTTP response under <code>refresh_token</code> parameter
     *
     * @return string representation of a refresh token
     */
    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    /**
     * Returns an ID token which has been returned in a HTTP response under <code>id_token</code> parameter
     *
     * @return string representation of an ID token
     */
    public String getIdToken() {
        return (String) this.get("id_token");
    }

}
