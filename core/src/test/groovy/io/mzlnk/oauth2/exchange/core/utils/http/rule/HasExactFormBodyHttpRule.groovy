package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.FormBody
import okhttp3.Request

class HasExactFormBodyHttpRule implements HttpRule {

    private final Map<String, String> expectedFields

    HasExactFormBodyHttpRule(Map<String, String> expectedFields) {
        this.expectedFields = expectedFields
    }

    @Override
    boolean matches(Request httpRequest) {
        if (httpRequest.body().class != FormBody) {
            return false
        }
        def formBody = httpRequest.body() as FormBody
        def fields = toMap(formBody)

        return fields.size() == expectedFields.size() && expectedFields.every {field ->
            fields[field.key].matches(field.value)
        }
    }

    private static def toMap(FormBody formBody) {
        def map = [:] as Map<String, String>
        for( i in 0..<formBody.size()) {
            map[formBody.name(i)] = formBody.value(i)
        }
        return map
    }

}
