package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import java.util.Map;

public class GitHubAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    public static GitHubAuthorizationCodeExchangeResponse from(Map<String, Object> values) {
        return new GitHubAuthorizationCodeExchangeResponse(values);
    }

    private GitHubAuthorizationCodeExchangeResponse(Map<String, Object> values) {
        super(values);
    }

    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    public String getScope() {
        return (String) this.get("scope");
    }

    public String getTokenType() {
        return (String) this.get("token_type");
    }

}