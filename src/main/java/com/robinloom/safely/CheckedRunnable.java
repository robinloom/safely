package com.robinloom.safely;

@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;
}
