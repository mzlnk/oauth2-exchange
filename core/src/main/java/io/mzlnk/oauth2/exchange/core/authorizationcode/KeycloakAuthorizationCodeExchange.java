package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class KeycloakAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<KeycloakAuthorizationCodeExchangeResponse> {

    private KeycloakAuthorizationCodeExchange(OkHttpClient httpClient,
                                              KeycloakAuthorizationCodeExchangeClient exchangeClient) {
        super(httpClient, exchangeClient);
    }

    @Override
    public KeycloakAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var builder = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.exchangeClient.getRedirectUri());

        var url = "%s/protocol/openid-connect/token".formatted(this.exchangeClient.getClientBaseUrl());

        var request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    @Override
    protected KeycloakAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return KeycloakAuthorizationCodeExchangeResponse.from(values);
    }

    public static class Builder {

        private OkHttpClient httpClient = new OkHttpClient();
        private KeycloakAuthorizationCodeExchangeClient exchangeClient;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(KeycloakAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public KeycloakAuthorizationCodeExchange build() {
            return new KeycloakAuthorizationCodeExchange(
                    this.httpClient,
                    this.exchangeClient
            );
        }
    }

}
