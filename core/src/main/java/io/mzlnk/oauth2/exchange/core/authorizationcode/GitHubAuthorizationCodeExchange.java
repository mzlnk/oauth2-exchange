package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class GitHubAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GitHubAuthorizationCodeExchangeResponse> {

    private GitHubAuthorizationCodeExchange(OkHttpClient client,
                                            GitHubAuthorizationCodeExchangeClient exchangeClient) {
        super(client, exchangeClient);
    }

    @Override
    public GitHubAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
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

    @Override
    protected GitHubAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return GitHubAuthorizationCodeExchangeResponse.from(values);
    }

    public static class Builder {

        private OkHttpClient httpClient = new OkHttpClient();
        private GitHubAuthorizationCodeExchangeClient exchangeClient;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(GitHubAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public GitHubAuthorizationCodeExchange build() {
            return new GitHubAuthorizationCodeExchange(
                    this.httpClient,
                    this.exchangeClient
            );
        }
    }

}
