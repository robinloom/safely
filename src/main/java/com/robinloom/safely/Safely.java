package com.robinloom.safely;

public final class Safely {

    private Safely() {}

    public static <T> Result<T> call(CheckedSupplier<T> supplier) {
        try {
            return Result.success(supplier.get());
        } catch (Throwable t) {
            return Result.failure(t);
        }
    }

    public static Result<Void> run(CheckedRunnable runnable) {
        try {
            runnable.run();
            return Result.success(null);
        } catch (Throwable t) {
            return Result.failure(t);
        }
    }
}
