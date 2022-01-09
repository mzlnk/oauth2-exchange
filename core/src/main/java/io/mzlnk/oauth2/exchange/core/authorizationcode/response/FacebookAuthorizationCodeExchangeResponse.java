package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import java.util.Map;

public class FacebookAuthorizationCodeExchangeResponse extends AbstractAuthorizationCodeExchangeResponse {

    public static FacebookAuthorizationCodeExchangeResponse from(Map<String, Object> values) {
        return new FacebookAuthorizationCodeExchangeResponse(values);
    }

    private FacebookAuthorizationCodeExchangeResponse(Map<String, Object> values) {
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
