package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.FacebookAuthorizationCodeExchangeResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class FacebookAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<FacebookAuthorizationCodeExchangeResponse> {

    private FacebookAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                              @NotNull FacebookAuthorizationCodeExchangeClient exchangeClient,
                                              @NotNull FacebookAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
    }

    @Override
    public FacebookAuthorizationCodeExchangeResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

        var url = HttpUrl.parse("%s/v12.0/oauth/access_token".formatted(this.exchangeClient.getClientBaseUrl()))
                .newBuilder()
                .addQueryParameter("client_id", this.exchangeClient.getClientId())
                .addQueryParameter("client_secret", this.exchangeClient.getClientSecret())
                .addQueryParameter("redirect_uri", this.exchangeClient.getRedirectUri())
                .addQueryParameter("code", code)
                .build();

        var request = new Request.Builder()
                .url(url)
                .build();

        return this.makeHttpCall(request);
    }

    public static class Builder {

        private OkHttpClient httpClient;
        private FacebookAuthorizationCodeExchangeClient exchangeClient;
        private FacebookAuthorizationCodeExchangeResponseHandler responseHandler;

        private static Supplier<FacebookAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new FacebookAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(FacebookAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder responseHandler(FacebookAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public FacebookAuthorizationCodeExchange build() {
            return new FacebookAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

    }

}
