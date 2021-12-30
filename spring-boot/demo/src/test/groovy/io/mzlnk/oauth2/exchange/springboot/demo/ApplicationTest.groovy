package io.mzlnk.oauth2.exchange.springboot.demo

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest {

    @Test
    void "Should run application"() {
        assert true
    }

    @Test
    @Disabled
    void "Should not execute"() {
        assert false
    }

}
