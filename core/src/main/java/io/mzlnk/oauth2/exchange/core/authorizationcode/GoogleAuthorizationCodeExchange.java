package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GoogleAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GoogleAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class GoogleAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GoogleAuthorizationCodeExchangeResponse> {

    private GoogleAuthorizationCodeExchange(OkHttpClient client,
                                            GoogleAuthorizationCodeExchangeClient exchangeClient,
                                            GoogleAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(client, exchangeClient, responseHandler);
    }

    @Override
    public GoogleAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var requestBody = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.exchangeClient.getRedirectUri())
                .build();

        var url = "%s/token".formatted(this.exchangeClient.getClientBaseUrl());

        var request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return this.makeHttpCall(request);
    }

    public static class Builder {

        private OkHttpClient httpClient;
        private GoogleAuthorizationCodeExchangeClient exchangeClient;
        private GoogleAuthorizationCodeExchangeResponseHandler responseHandler;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(GoogleAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder responseHandler(GoogleAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public GoogleAuthorizationCodeExchange build() {
            return new GoogleAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<GoogleAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new GoogleAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

    }

}
