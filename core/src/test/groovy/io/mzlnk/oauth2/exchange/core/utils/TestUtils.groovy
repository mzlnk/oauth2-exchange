package io.mzlnk.oauth2.exchange.core.utils

import com.google.common.io.Resources

import java.nio.charset.StandardCharsets

class TestUtils {

    static def loadResourceAsString(String resource) {
        return Resources.toString(Resources.getResource(resource), StandardCharsets.UTF_8)
    }

}