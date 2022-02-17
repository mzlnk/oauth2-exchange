package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class GitHubAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> {

    public GitHubAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    /*
     * This bypass is essential to handle error response properly as GitHub returns
     * HTTP 400 Bad Request related responses with HTTP 200 OK status.
     */
    @Override
    public GitHubAuthorizationCodeExchangeResponse handleResponse(Response response) {
        if(!response.isSuccessful()) {
            return super.handleErrorResponse(response);
        }

        var jsonResponse = this.readJsonBody(response);
        boolean errorFieldExists = jsonResponse.containsKey("error");

        return errorFieldExists
                ? this.handleBadRequestResponse(response)
                : super.handleSuccessfulResponse(response);
    }

    @Override
    protected GitHubAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return GitHubAuthorizationCodeExchangeResponse.from(values);
    }

    private GitHubAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.getOrDefault("error_description", ""));
        throw new ExchangeException(message, response);
    }

}
