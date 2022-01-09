package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class GoogleAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GoogleAuthorizationCodeExchangeResponse> {

    private GoogleAuthorizationCodeExchange(OkHttpClient client,
                                            String clientId,
                                            String clientSecret,
                                            String redirectUri) {
        super(client, clientId, clientSecret, redirectUri);
    }

    @Override
    public GoogleAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var requestBody = new FormBody.Builder()
                .add("client_id", this.clientId)
                .add("client_secret", this.clientSecret)
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.redirectUri)
                .build();

        var request = new Request.Builder()
                .url("https://auth.mzlnk.io")
                .post(requestBody)
                .build();

        return this.makeHttpCall(request);
    }

    @Override
    protected GoogleAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return GoogleAuthorizationCodeExchangeResponse.from(values);
    }

    public static class Builder {

        private OkHttpClient client = new OkHttpClient();
        private String clientId = "";
        private String clientSecret = "";
        private String redirectUri = "";

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public GoogleAuthorizationCodeExchange build() {
            return new GoogleAuthorizationCodeExchange(
                    this.client,
                    this.clientId,
                    this.clientSecret,
                    this.redirectUri
            );
        }
    }

}
