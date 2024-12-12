package pairmatching.util;

import java.util.function.Supplier;

public class RetryHandler {
    public static <T> Object retryUntilSuccessWithReturn(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void retryUntilSuccess(Runnable toRun) {
        while (true) {
            try {
                toRun.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
