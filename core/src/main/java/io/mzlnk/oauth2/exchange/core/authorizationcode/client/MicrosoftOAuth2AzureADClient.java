package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link OAuth2Client} for Microsoft OAuth2 authorization code flow.
 * This type of the client is dedicated for allowing only users with work/school accounts from a particular Azure AD tenant
 * to sign into the application. Either the friendly domain name of the Azure AD tenant or the tenant's GUID identifier can be used.
 * <br />
 * Implementation of the client is created based on information posted on official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
 */
public final class MicrosoftOAuth2AzureADClient extends MicrosoftOAuth2Client {

    private final String azureADId;

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID), redirection URI and Azure AD ID
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @param azureADId    non-null string representation of the Azure AD ID
     * @throws NullPointerException if any of the parameters is null
     */
    public MicrosoftOAuth2AzureADClient(@NotNull String clientId,
                                        @NotNull String clientSecret,
                                        @NotNull String redirectUri,
                                        @NotNull String azureADId) {
        super(clientId, clientSecret, redirectUri);

        Preconditions.checkNotNull(azureADId, "Parameter `azureADId` cannot be null.");
        this.azureADId = azureADId;
    }

    /**
     * Returns the token URL which the request for exchange authorization code for a token is sent to. For this type of
     * the Microsoft OAuth2 authorization code flow related client, the client base URL is <i>https://login.microsoftonline.com/{azureADId}</i>, where:
     * <ul>
     *     <li><b>azureAdId</b> - string representation of the Azure AD ID</li>
     * </ul>
     *
     * @return non-null string representation of the token URL
     */
    @NotNull
    @Override
    public String getTokenUrl() {
        return "https://login.microsoftonline.com/%s/oauth2/v2.0/token".formatted(this.azureADId);
    }

    /**
     * Returns the Azure AD ID which the client is pointing out to.
     *
     * @return non-null string representation of the Azure AD ID
     */
    public String getAzureADId() {
        return this.azureADId;
    }

}
