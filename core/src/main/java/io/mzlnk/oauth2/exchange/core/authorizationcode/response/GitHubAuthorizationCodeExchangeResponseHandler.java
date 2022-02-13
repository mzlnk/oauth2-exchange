package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class GitHubAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> {

    public GitHubAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected GitHubAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return GitHubAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    public GitHubAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return null;
    }

}
