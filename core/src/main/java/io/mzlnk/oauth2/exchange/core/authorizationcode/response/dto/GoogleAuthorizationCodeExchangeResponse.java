package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GoogleAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    public static GoogleAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new GoogleAuthorizationCodeExchangeResponse(values);
    }

    private GoogleAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    public String getScope() {
        return (String) this.get("scope");
    }

    public String getTokenType() {
        return (String) this.get("token_type");
    }

}
