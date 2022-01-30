package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookAuthorizationCodeExchangeResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class FacebookAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<FacebookAuthorizationCodeExchangeResponse> {

    private FacebookAuthorizationCodeExchange(OkHttpClient client,
                                              FacebookAuthorizationCodeExchangeClient exchangeClient) {
        super(client, exchangeClient);
    }

    @Override
    public FacebookAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var url = HttpUrl.parse("%s/v12.0/oauth/access_token".formatted(this.exchangeClient.getClientBaseUrl()))
                .newBuilder()
                .addQueryParameter("client_id", this.exchangeClient.getClientId())
                .addQueryParameter("client_secret", this.exchangeClient.getClientSecret())
                .addQueryParameter("redirect_uri", this.exchangeClient.getClientSecret())
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

        private OkHttpClient httpClient = new OkHttpClient();
        private FacebookAuthorizationCodeExchangeClient exchangeClient;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(FacebookAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public FacebookAuthorizationCodeExchange build() {
            return new FacebookAuthorizationCodeExchange(
                    this.httpClient,
                    this.exchangeClient
            );
        }

    }

}
