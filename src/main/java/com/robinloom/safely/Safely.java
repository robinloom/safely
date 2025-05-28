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

    public static Result<Void> run(CheckedRunnable runnable) {
        try {
            runnable.run();
            return Result.success(null);
        } catch (Throwable t) {
            return Result.failure(t);
        }
    }

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
