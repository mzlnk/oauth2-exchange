package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.MediaType
import org.junit.runner.Request

class HttpRules {

    static HttpRule get(String url) {
        return new IsExactRequestHttpRule('GET', url)
    }

    static HttpRule post(String url) {
        return new IsExactRequestHttpRule('POST', url)
    }

    static HttpRule put(String url) {
        return new IsExactRequestHttpRule('PUT', url)
    }

    static HttpRule delete(String url) {
        return new IsExactRequestHttpRule('DELETE', url)
    }

    static HttpRule withContentType(MediaType contentType) {
        return new HasExactContentTypeHttpRule(contentType)
    }

    static HttpRule withExactFormBody(Map<String, String> fields) {
        return new HasExactFormBodyHttpRule(fields)
    }

    static HttpRule withQueryParameter(String key, String value) {
        return new HasQueryParameterHttpRule(key, value)
    }

    static HttpRule always() {
        return { request -> true }
    }

}
