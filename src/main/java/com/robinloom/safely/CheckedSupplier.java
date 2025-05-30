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
 * Represents a supplier of results that may throw a checked exception.
 * <p>
 * This is a functional interface whose functional method is {@link #get()}.
 * It mirrors {@link java.util.function.Supplier} but allows the {@code get()}
 * method to throw a {@link Exception}, enabling safe wrapping via the
 * {@link Safely} utility.
 *
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

    T get() throws Exception;
}
