package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.AuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.AuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GitHubAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class GitHubAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GitHubAuthorizationCodeExchangeResponse> {

    private GitHubAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                            @NotNull AuthorizationCodeExchangeClient exchangeClient,
                                            @NotNull AuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
    }

    @Override
    public GitHubAuthorizationCodeExchangeResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

        var requestBody = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("redirect_uri", this.exchangeClient.getRedirectUri())
                .build();

        var request = new Request.Builder()
                .url("%s/login/oauth/access_token".formatted(this.exchangeClient.getClientBaseUrl()))
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .build();

        return this.makeHttpCall(request);
    }

    public static class Builder {

        private OkHttpClient httpClient;
        private AuthorizationCodeExchangeClient exchangeClient;
        private AuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> responseHandler;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(AuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder responseHandler(AuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public GitHubAuthorizationCodeExchange build() {
            return new GitHubAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<GitHubAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new GitHubAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

    }

}
