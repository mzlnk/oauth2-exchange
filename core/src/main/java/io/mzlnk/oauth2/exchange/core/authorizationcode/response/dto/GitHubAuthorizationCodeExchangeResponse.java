package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a response returned during authorization code for token exchange in OAuth2 authorization code flow
 * for GitHub identity provider.
 * <br />
 * The example response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "scope": "some-scope",
 *   "token_type": "bearer"
 * }
 * </pre>
 * The structure of the response and all its details are created and documented based on the information posted on the official
 * <a href="https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps">documentation site</a>.
 */
public final class GitHubAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    /**
     * Construct an response from a given map of values, usually returned as a JSON HTTP body response from HTTP request
     * issued for exchanging authorization code for a token.
     *
     * @param values non-null map of values (representation of a JSON HTTP response body)
     * @return instance of a response based on given values
     * @throws NullPointerException if given map of values is null
     */
    public static GitHubAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new GitHubAuthorizationCodeExchangeResponse(values);
    }

    private GitHubAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
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
     * Returns a scope which has been returned in a HTTP response under <code>scope</code> parameter
     *
     * @return string representation of a scope
     */
    public String getScope() {
        return (String) this.get("scope");
    }

    /**
     * Returns a token type which has been returned in a HTTP response under <code>token_type</code> parameter
     *
     * @return string representation of a token type
     */
    public String getTokenType() {
        return (String) this.get("token_type");
    }

}
