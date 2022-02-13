package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.MicrosoftAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

public class MicrosoftAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<MicrosoftAuthorizationCodeExchangeResponse> {

    private final String scope;
    private final String codeVerifier;
    private final String clientAssertionType;
    private final String clientAssertion;

    private MicrosoftAuthorizationCodeExchange(OkHttpClient client,
                                               MicrosoftAuthorizationCodeExchangeClient exchangeClient,
                                               MicrosoftAuthorizationCodeExchangeResponseHandler responseHandler,
                                               String scope,
                                               String codeVerifier,
                                               String clientAssertionType,
                                               String clientAssertion) {
        super(client, exchangeClient, responseHandler);
        this.scope = scope;
        this.codeVerifier = codeVerifier;
        this.clientAssertionType = clientAssertionType;
        this.clientAssertion = clientAssertion;
    }

    @Override
    public MicrosoftAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var builder = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.exchangeClient.getRedirectUri());

        addFormFieldIfExists(builder, "scope", this.scope);
        addFormFieldIfExists(builder, "code_verifier", this.codeVerifier);
        addFormFieldIfExists(builder, "client_assertion_type", this.clientAssertionType);
        addFormFieldIfExists(builder, "client_assertion", this.clientAssertion);

        var url = "%s/oauth2/v2.0/token".formatted(this.exchangeClient.getClientBaseUrl());

        var request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    private void addFormFieldIfExists(FormBody.Builder builder, String fieldName, String value) {
        if (value != null) {
            builder.add(fieldName, value);
        }
    }

    public static class Builder {

        private OkHttpClient httpClient;
        private MicrosoftAuthorizationCodeExchangeClient exchangeClient;
        private MicrosoftAuthorizationCodeExchangeResponseHandler responseHandler;

        private String scope;
        private String codeVerifier;
        private String clientAssertionType;
        private String clientAssertion;

        private static Supplier<MicrosoftAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new MicrosoftAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder exchangeClient(MicrosoftAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder codeVerifier(String codeVerifier) {
            this.codeVerifier = codeVerifier;
            return this;
        }

        public Builder clientAssertionType(String clientAssertionType) {
            this.clientAssertionType = clientAssertionType;
            return this;
        }

        public Builder clientAssertion(String clientAssertion) {
            this.clientAssertion = clientAssertion;
            return this;
        }

        public MicrosoftAuthorizationCodeExchange build() {
            return new MicrosoftAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler()),
                    this.scope,
                    this.codeVerifier,
                    this.clientAssertionType,
                    this.clientAssertion
            );
        }
    }

}
