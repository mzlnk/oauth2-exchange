package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.MediaType
import okhttp3.Request

class HasExactContentTypeHttpRule implements HttpRule {

    private final MediaType expectedContentType

    HasExactContentTypeHttpRule(MediaType expectedContentType) {
        this.expectedContentType = expectedContentType
    }

    @Override
    boolean matches(Request httpRequest) {
        return httpRequest.body().contentType() == expectedContentType
    }
}
