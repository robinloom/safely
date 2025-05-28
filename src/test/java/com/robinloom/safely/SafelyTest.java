package com.robinloom.safely;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SafelyTest {

    @Test
    void testSafelyRun_noExceptions() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.run(() -> {
                throw new Exception("on purpose");
            });
        });
    }

    @Test
    void testSafelyRun_isSuccess() {
        Result<Void> result = Safely.run(() -> {});
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertFalse(result.isFailure());
    }

    @Test
    void testSafelyRun_isFailure() {
        Result<Void> result = Safely.run(() -> {
            throw new Exception("on purpose");
        });
        Assertions.assertTrue(result.isFailure());
        Assertions.assertFalse(result.isSuccess());
    }

    @Test
    void testSafelyCall_noExceptions() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.call(() -> {
                throw new Exception("on purpose");
            });
        });
    }

    @Test
    void testSafelyCall_isSuccess() {
        Result<Integer> result = Safely.call(() -> 42);
        Assertions.assertEquals(42, result.get());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testSafelyCall_isFailure() {
        Result<Integer> result = Safely.call(() -> {
            if (true) {
                throw new Exception("on purpose");
            }
            return 42;
        });
        Assertions.assertNull(result.get());
    }
}
