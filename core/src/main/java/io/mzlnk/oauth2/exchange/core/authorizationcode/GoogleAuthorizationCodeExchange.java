package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.core.type.TypeReference;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GoogleAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleAuthorizationCodeExchangeResponse;
import io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class GoogleAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GoogleAuthorizationCodeExchangeResponse> {

    private GoogleAuthorizationCodeExchange(OkHttpClient client,
                                            GoogleAuthorizationCodeExchangeClient exchangeClient) {
        super(client, exchangeClient);
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

    @Override
    protected GoogleAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return GoogleAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected void handleErrorResponse(Response errorResponse) throws IOException {
        String responseBody = Optional
                .ofNullable(errorResponse.body())
                .map(OkHttpUtils::convertResponseBodyToString).orElse("");

        if (errorResponse.code() == 400) {
            handleBadRequestHttpResponse(errorResponse, responseBody);
        }

        handleNonBadRequestHttpResponse(errorResponse, responseBody);
    }

    private void handleBadRequestHttpResponse(Response errorResponse, String responseBody) throws IOException {
        var typeRef = new TypeReference<Map<String, Object>>() {};
        var values = this.objectMapper.readValue(responseBody, typeRef);

        var message = "Exchange failed. Cause: %s, %s".formatted(values.get("error"), values.get("error_description"));
        throw new ExchangeException(message, responseBody);
    }

    private void handleNonBadRequestHttpResponse(Response errorResponse, String responseBody) {
        var message = "Exchange failed. Cause: %s".formatted(errorResponse.message());
        throw new ExchangeException(message, responseBody);
    }

    public static class Builder {

        private OkHttpClient httpClient = new OkHttpClient();
        private GoogleAuthorizationCodeExchangeClient exchangeClient;

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(GoogleAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public GoogleAuthorizationCodeExchange build() {
            return new GoogleAuthorizationCodeExchange(
                    this.httpClient,
                    this.exchangeClient
            );
        }
    }

}
