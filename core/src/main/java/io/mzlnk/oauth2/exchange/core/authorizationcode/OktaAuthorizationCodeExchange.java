package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OktaAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class OktaAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<OktaAuthorizationCodeExchangeResponse> {

    private final OktaClient oktaClient;

    private OktaAuthorizationCodeExchange(OkHttpClient httpClient,
                                         OktaClient oktaClient,
                                         String clientId,
                                         String clientSecret,
                                         String redirectUri) {
        super(httpClient, clientId, clientSecret, redirectUri);
        this.oktaClient = oktaClient;
    }

    @Override
    public OktaAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var requestBody = new FormBody.Builder()
                .add("client_id", this.clientId)
                .add("client_secret", this.clientSecret)
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.redirectUri)
                .build();

        var request = new Request.Builder()
                .url("%s/v1/token".formatted(this.oktaClient.getClientBaseUrl()))
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
        private OktaClient oktaClient;
        private String clientId = "";
        private String clientSecret = "";
        private String redirectUri = "";

        public OktaAuthorizationCodeExchange.Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public OktaAuthorizationCodeExchange.Builder oktaClient(OktaClient oktaClient) {
            this.oktaClient = oktaClient;
            return this;
        }

        public OktaAuthorizationCodeExchange.Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public OktaAuthorizationCodeExchange.Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public OktaAuthorizationCodeExchange.Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public OktaAuthorizationCodeExchange build() {
            Preconditions.checkNotNull(this.oktaClient, "Okta client cannot be null.");

            return new OktaAuthorizationCodeExchange(
                    this.httpClient,
                    this.oktaClient,
                    this.clientId,
                    this.clientSecret,
                    this.redirectUri
            );
        }
    }

}
