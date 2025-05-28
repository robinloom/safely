package com.robinloom.safely;

@FunctionalInterface
public interface CheckedSupplier<T> {

    T get() throws Exception;
}
