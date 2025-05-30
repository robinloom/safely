/*
 * Copyright (C) 2025 Robin Kösters
 * mail[at]robinloom[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robinloom.safely;

/**
 * Utility class for executing operations that may throw exceptions,
 * wrapping the outcome in a {@link Result} object for fluent error handling.
 * <p>
 * The {@code Safely} class provides static methods to run code blocks—both
 * returning and non-returning—in a safe and expressive manner. Instead of
 * propagating exceptions, these methods return a {@code Result} instance
 * that encapsulates success or failure. This approach simplifies error
 * handling and encourages a more functional style of programming.
 * </p>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * Result<String> result = Safely.call(() -> someOperation());
 * if (result.isSuccess()) {
 *     System.out.println("Success: " + result.get());
 * } else {
 *     result.getError().printStackTrace();
 * }
 * }</pre>
 *
 * <p>This class is not meant to be instantiated.</p>
 */
public final class Safely {

    private Safely() {}

    /**
     * Executes the given {@link CheckedSupplier} and returns a {@link Result}
     * that either contains the value or the exception that was thrown.
     *
     * <p>This method is useful for capturing checked exceptions in a functional
     * style without propagating them. The result can then be handled in a
     * structured and expressive way.</p>
     *
     * @param <T> the type of the result
     * @param supplier a supplier that produces a result and may throw an exception
     * @return a {@code Result} containing either the successful value or the thrown exception
     * @throws NullPointerException if {@code supplier} is {@code null}
     */
    public static <T> Result<T> call(CheckedSupplier<T> supplier) {
        try {
            return Result.success(supplier.get());
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    /**
     * Executes the given {@link CheckedSupplier} and returns a {@link Result}
     * containing the result only if the thrown exception is an instance of the specified type.
     * <p>
     * If the {@code supplier} throws a {@link Throwable} that is not an instance of the given {@code clazz},
     * the exception will be rethrown.
     * </p>
     *
     * @param <T> the type of the result
     * @param supplier a supplier that produces a result and may throw an exception
     * @param clazz the type of exception to catch; all others will be propagated
     * @return a {@code Result} containing the value or the expected exception
     * @throws NullPointerException if {@code supplier} or {@code clazz} is {@code null}
     * @throws RuntimeException if the thrown exception is not an instance of {@code clazz}
     */
    public static <T> Result<T> call(CheckedSupplier<T> supplier, Class<? extends Throwable> clazz) {
        try {
            return Result.success(supplier.get());
        } catch (Throwable t) {
            if (clazz.isAssignableFrom(t.getClass())) {
                return Result.failure(t);
            } else {
                throw new RuntimeException(t);
            }
        }
    }

    /**
     * Executes the given {@link CheckedRunnable} and returns a {@link Result}
     * indicating whether the operation completed successfully or threw an exception.
     * <p>
     * If the runnable completes normally, the result will be marked as successful.
     * If an exception is thrown, it will be captured in the result.
     * </p>
     *
     * @param runnable a runnable that may throw an exception
     * @return a {@code Result<Void>} representing the outcome of the operation
     * @throws NullPointerException if {@code runnable} is {@code null}
     */
    public static Result<Void> run(CheckedRunnable runnable) {
        try {
            runnable.run();
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    /**
     * Executes the given {@link CheckedRunnable} and returns a {@link Result}
     * indicating the outcome, but only catches exceptions that are instances of
     * the specified {@code clazz} (or its subclasses).
     * <p>
     * If the runnable completes normally, the result will be successful. If it throws
     * an exception of the specified type, it will be captured in the result. Other exceptions
     * will propagate.
     * </p>
     *
     * @param runnable a runnable that may throw an exception
     * @param clazz the class of the exception to catch
     * @return a {@code Result<Void>} representing the outcome
     * @throws NullPointerException if {@code runnable} or {@code clazz} is {@code null}
     * @throws RuntimeException if the thrown exception is not an instance of {@code clazz}
     */
    public static Result<Void> run(CheckedRunnable runnable, Class<? extends Throwable> clazz) {
        try {
            runnable.run();
            return Result.success(null);
        } catch (Throwable t) {
            if (clazz.isAssignableFrom(t.getClass())) {
                return Result.failure(t);
            } else {
                throw new RuntimeException(t);
            }
        }
    }
}
