package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public final class KeycloakAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    private final String host;
    private final String realm;

    public KeycloakAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                   @NotNull String clientSecret,
                                                   @NotNull String redirectUri,
                                                   @NotNull String host,
                                                   @NotNull String realm) {
        super(clientId, clientSecret, redirectUri);

        Preconditions.checkNotNull(host, "Parameter `host` cannot be null.");
        Preconditions.checkNotNull(realm, "Parameter `realm` cannot be null.");

        this.host = host;
        this.realm = realm;
    }

    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "%s/auth/realms/%s".formatted(this.host, this.realm);
    }

}
