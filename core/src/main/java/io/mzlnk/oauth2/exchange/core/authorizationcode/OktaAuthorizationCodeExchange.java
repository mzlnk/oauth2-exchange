package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OktaAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OktaAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class OktaAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<OktaAuthorizationCodeExchangeResponse> {

    private OktaAuthorizationCodeExchange(OkHttpClient httpClient,
                                          OktaAuthorizationCodeExchangeClient exchangeClient,
                                          OktaAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
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

    public static class Builder {

        private OkHttpClient httpClient;
        private OktaAuthorizationCodeExchangeClient exchangeClient;
        private OktaAuthorizationCodeExchangeResponseHandler responseHandler;

        private static Supplier<OktaAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new OktaAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(OktaAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder responseHandler(OktaAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public OktaAuthorizationCodeExchange build() {
            Preconditions.checkNotNull(this.exchangeClient, "Okta client cannot be null.");

            return new OktaAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

    }

}
