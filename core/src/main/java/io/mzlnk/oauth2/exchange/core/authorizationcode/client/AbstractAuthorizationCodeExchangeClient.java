package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAuthorizationCodeExchangeClient implements AuthorizationCodeExchangeClient {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    protected AbstractAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                      @NotNull String clientSecret,
                                                      @NotNull String redirectUri) {

        Preconditions.checkNotNull(clientId, "Parameter `clientId` cannot be null.");
        Preconditions.checkNotNull(clientSecret, "Parameter `clientSecret` cannot be null.");
        Preconditions.checkNotNull(redirectUri, "Parameter `redirectUri` cannot be null.");

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @NotNull
    @Override
    public String getClientId() {
        return this.clientId;
    }

    @NotNull
    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @NotNull
    @Override
    public String getRedirectUri() {
        return this.redirectUri;
    }

}
