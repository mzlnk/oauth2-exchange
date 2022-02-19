package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class OktaAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    private OktaAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    public static OktaAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new OktaAuthorizationCodeExchangeResponse(values);
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

    public String getScope() {
        return (String) this.get("scope");
    }

    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    public String getIdToken() {
        return (String) this.get("id_token");
    }

}
