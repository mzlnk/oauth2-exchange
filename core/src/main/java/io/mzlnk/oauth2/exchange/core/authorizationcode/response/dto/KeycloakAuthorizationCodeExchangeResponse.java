package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class KeycloakAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    public static KeycloakAuthorizationCodeExchangeResponse from(@NotNull Map<String, Object> values) {
        return new KeycloakAuthorizationCodeExchangeResponse(values);
    }

    private KeycloakAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

    public Integer getRefreshExpiresIn() {
        return (Integer) this.get("refresh_expires_in");
    }

    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    public String getTokenType() {
        return (String) this.get("token_type");
    }

    public Integer getNotBeforePolicy() {
        return (Integer) this.get("not-before-policy");
    }

    public String getSessionState() {
        return (String) this.get("session_state");
    }

    public String getScope() {
        return (String) this.get("scope");
    }

}
