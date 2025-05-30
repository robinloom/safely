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

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A container object that may either contain a successful result or a {@link Throwable} indicating failure.
 * <p>
 * This class provides a functional-style way of handling computations that may fail,
 * without throwing exceptions directly. Use {@link #isSuccess()} and {@link #isFailure()}
 * to check the outcome, and methods like {@link #map(Function)} to transform results.
 *
 * @param <T> the type of the successful result
 */
public class Result<T> {

    private final T value;
    private final Throwable error;

    private Result(T value, Throwable error) {
        this.value = value;
        this.error = error;
    }

    /**
     * Creates a successful {@code Result} wrapping the given value.
     *
     * @param value the value to wrap
     * @param <T>   the type of the value
     * @return a successful {@code Result}
     */
    static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    /**
     * Creates a failed {@code Result} wrapping the given {@link Throwable}.
     *
     * @param error the error to wrap
     * @param <T>   the type of the expected value
     * @return a failed {@code Result}
     */
    static <T> Result<T> failure(Throwable error) {
        return new Result<>(null, error);
    }

    /**
     * Returns {@code true} if the result is successful.
     *
     * @return {@code true} if no error is present
     */
    public boolean isSuccess() {
        return error == null;
    }

    /**
     * Returns {@code true} if the result is a failure.
     *
     * @return {@code true} if an error is present
     */
    public boolean isFailure() {
        return error != null;
    }

    /**
     * Returns the contained value if the result is successful, otherwise {@code null}.
     *
     * @return the value, or {@code null} if failed
     */
    public T get() {
        return value;
    }

    /**
     * Returns the value if successful, or a fallback value if not.
     *
     * @param other the fallback value
     * @return the contained value or {@code other}
     */
    public T orElse(T other) {
        return isSuccess() ? value : other;
    }

    /**
     * Transforms the contained value using the given mapper if successful;
     * otherwise propagates the failure.
     *
     * @param mapper a function to apply to the value if successful
     * @param <R>    the result type of the mapping function
     * @return a new {@code Result} with the mapped value or original failure
     */
    public <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        return isSuccess() ? success(mapper.apply(value)) : failure(error);
    }

    /**
     * Returns the {@link Throwable} that caused this result to be a failure, if any.
     *
     * @return the throwable associated with this result if it represents a failure;
     *         {@code null} if the result was successful.
     */
    public Throwable getError() {
        return error;
    }

    /**
     * Executes the given action if this result represents a successful computation.
     *
     * @param action the action to perform on the successful result value
     * @return this {@code Result} instance, allowing for method chaining
     */
    public Result<T> onSuccess(Consumer<T> action) {
        if (isSuccess()) {
            action.accept(value);
        }
        return this;
    }

    /**
     * Executes the given action if this result represents a failure.
     *
     * @param action the action to perform on the throwable causing the failure
     * @return this {@code Result} instance, allowing for method chaining
     */
    public Result<T> onFailure(Consumer<Throwable> action) {
        if (isFailure()) {
            action.accept(error);
        }
        return this;
    }
}
