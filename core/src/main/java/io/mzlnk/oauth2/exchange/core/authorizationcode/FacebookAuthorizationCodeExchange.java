package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookAuthorizationCodeExchangeResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class FacebookAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<FacebookAuthorizationCodeExchangeResponse> {

    private FacebookAuthorizationCodeExchange(OkHttpClient client,
                                              String clientId,
                                              String clientSecret,
                                              String redirectUri) {
        super(client, clientId, clientSecret, redirectUri);
    }

    @Override
    public FacebookAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var url = HttpUrl.parse("https://graph.facebook.com/v12.0/oauth/access_token")
                .newBuilder()
                .addQueryParameter("client_id", this.clientId)
                .addQueryParameter("client_secret", this.clientSecret)
                .addQueryParameter("redirect_uri", this.redirectUri)
                .addQueryParameter("code", code)
                .build();

        var request = new Request.Builder()
                .url(url)
                .build();

        return this.makeHttpCall(request);
    }

    @Override
    protected FacebookAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return FacebookAuthorizationCodeExchangeResponse.from(values);
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

        public FacebookAuthorizationCodeExchange build() {
            return new FacebookAuthorizationCodeExchange(
                    this.client,
                    this.clientId,
                    this.clientSecret,
                    this.redirectUri
            );
        }
    }

}
