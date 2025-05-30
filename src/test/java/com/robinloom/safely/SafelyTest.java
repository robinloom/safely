package com.robinloom.safely;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

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
    void testSafelyRunCustomThrowable_caught() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.run(() -> {
                throw new IllegalArgumentException("on purpose");
            }, Throwable.class);
        });
    }

    @Test
    void testSafelyRunCustomThrowable_notCaught() {
        Assertions.assertThrows(Exception.class,
                () -> Safely.run(() -> {
                    throw new Exception("on purpose");
                }, IllegalArgumentException.class).isSuccess());
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
    void testSafelyCallCustomThrowable_caught() {
        Assertions.assertDoesNotThrow(() -> {
            Safely.call(() -> {
                throw new IllegalArgumentException("on purpose");
            }, Throwable.class);
        });
    }

    @Test
    void testSafelyCallCustomThrowable_notCaught() {
        Assertions.assertThrows(Exception.class,
                () -> Safely.call(() -> {
                    throw new Exception("on purpose");
                }, IllegalArgumentException.class).isSuccess());
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

    @Test
    void testMap_onSuccess() {
        Result<Integer> result = Result.success(5);
        Result<String> mapped = result.map(i -> "Number: " + i);
        Assertions.assertTrue(mapped.isSuccess());
        Assertions.assertEquals("Number: 5", mapped.get());
    }

    @Test
    void testMap_onFailure() {
        Throwable error = new RuntimeException("fail");
        Result<Integer> result = Result.failure(error);
        Result<String> mapped = result.map(i -> "Number: " + i);
        Assertions.assertTrue(mapped.isFailure());
        Assertions.assertEquals(error, mapped.getError());
    }

    @Test
    void testOrElse_onSuccess() {
        Result<String> result = Result.success("Hello");
        Assertions.assertEquals("Hello", result.orElse("Fallback"));
    }

    @Test
    void testOrElse_onFailure() {
        Result<String> result = Result.failure(new RuntimeException("fail"));
        Assertions.assertEquals("Fallback", result.orElse("Fallback"));
    }

    @Test
    void testGetError_onSuccess() {
        Result<String> result = Result.success("Hi");
        Assertions.assertNull(result.getError());
    }

    @Test
    void testGetError_onFailure() {
        Throwable error = new IllegalArgumentException("error");
        Result<String> result = Result.failure(error);
        Assertions.assertEquals(error, result.getError());
    }

    @Test
    void testOnSuccess_runsConsumer_onSuccess() {
        Result<String> result = Result.success("Hello");
        AtomicBoolean ran = new AtomicBoolean(false);

        result.onSuccess(value -> {
            Assertions.assertEquals("Hello", value);
            ran.set(true);
        });

        Assertions.assertTrue(ran.get());
    }

    @Test
    void testOnSuccess_doesNotRunConsumer_onFailure() {
        Result<String> result = Result.failure(new RuntimeException("fail"));
        AtomicBoolean ran = new AtomicBoolean(false);

        result.onSuccess(value -> ran.set(true));

        Assertions.assertFalse(ran.get());
    }

    @Test
    void testOnFailure_runsConsumer_onFailure() {
        Throwable error = new RuntimeException("fail");
        Result<String> result = Result.failure(error);
        AtomicBoolean ran = new AtomicBoolean(false);

        result.onFailure(err -> {
            Assertions.assertEquals(error, err);
            ran.set(true);
        });

        Assertions.assertTrue(ran.get());
    }

    @Test
    void testOnFailure_doesNotRunConsumer_onSuccess() {
        Result<String> result = Result.success("Hello");
        AtomicBoolean ran = new AtomicBoolean(false);

        result.onFailure(err -> ran.set(true));

        Assertions.assertFalse(ran.get());
    }

}
