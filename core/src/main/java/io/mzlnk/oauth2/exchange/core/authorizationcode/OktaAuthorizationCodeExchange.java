package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OktaAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class OktaAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<OktaAuthorizationCodeExchangeResponse> {

    private OktaAuthorizationCodeExchange(OkHttpClient httpClient,
                                          OktaAuthorizationCodeExchangeClient exchangeClient) {
        super(httpClient, exchangeClient);
    }

    @Override
    public OktaAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var requestBody = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.exchangeClient.getRedirectUri())
                .build();

        var url = "%s/v1/token".formatted(this.exchangeClient.getClientBaseUrl());

        var request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return this.makeHttpCall(request);
    }

    @Override
    protected OktaAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return OktaAuthorizationCodeExchangeResponse.from(values);
    }

    public static class Builder {

        private OkHttpClient httpClient = new OkHttpClient();
        private OktaAuthorizationCodeExchangeClient exchangeClient;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(OktaAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public OktaAuthorizationCodeExchange build() {
            Preconditions.checkNotNull(this.exchangeClient, "Okta client cannot be null.");

            return new OktaAuthorizationCodeExchange(
                    this.httpClient,
                    this.exchangeClient
            );
        }
    }

}
