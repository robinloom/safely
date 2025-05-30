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
 * Represents an action that may throw a checked exception.
 * <p>
 * This is a functional interface whose functional method is {@link #run()}.
 * It mirrors {@link Runnable} but allows the {@code run()} method to throw
 * a {@link Exception}, making it compatible with {@link Safely#run}.
 */
@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;
}
