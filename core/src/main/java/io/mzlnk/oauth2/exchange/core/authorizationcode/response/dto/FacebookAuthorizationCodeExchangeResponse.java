package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FacebookAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    public static FacebookAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new FacebookAuthorizationCodeExchangeResponse(values);
    }

    private FacebookAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    public String getTokenType() {
        return (String) this.get("token_type");
    }

    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

}
