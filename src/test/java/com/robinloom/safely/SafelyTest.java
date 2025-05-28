package com.robinloom.safely;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SafelyTest {

    @Test
    void testSafelyRun_noExceptions() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.run(() -> {
                throw new NullPointerException();
            });
        });
    }

    @Test
    void testSafelyCall_noExceptions() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.call(() -> {
                throw new NullPointerException();
            });
        });
    }
}
