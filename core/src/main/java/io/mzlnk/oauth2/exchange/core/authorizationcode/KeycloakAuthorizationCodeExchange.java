package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.KeycloakAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class KeycloakAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<KeycloakAuthorizationCodeExchangeResponse> {

    private KeycloakAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                              @NotNull KeycloakAuthorizationCodeExchangeClient exchangeClient,
                                              @NotNull KeycloakAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
    }

    @Override
    public KeycloakAuthorizationCodeExchangeResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

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

    public static class Builder {

        private OkHttpClient httpClient;
        private KeycloakAuthorizationCodeExchangeClient exchangeClient;
        private KeycloakAuthorizationCodeExchangeResponseHandler responseHandler;

        private static Supplier<KeycloakAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new KeycloakAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(KeycloakAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder responseHandler(KeycloakAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public KeycloakAuthorizationCodeExchange build() {
            return new KeycloakAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

    }

}
