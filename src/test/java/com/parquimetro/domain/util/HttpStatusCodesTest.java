package com.parquimetro.domain.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HttpStatusCodesTest {

    @Test
    void testHttpStatusCodes() {
        assertEquals("200", HttpStatusCodes.OK);
        assertEquals("400", HttpStatusCodes.NOT_FOUND);
    }
}
