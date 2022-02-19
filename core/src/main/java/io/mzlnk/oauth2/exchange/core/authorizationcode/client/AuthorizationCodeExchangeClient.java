package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

public interface AuthorizationCodeExchangeClient {

    @NotNull
    String getClientId();

    @NotNull
    String getClientSecret();

    @NotNull
    String getRedirectUri();

    @NotNull
    String getClientBaseUrl();

}
