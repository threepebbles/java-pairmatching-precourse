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

    public static <T> Object retryUntilSuccessWithReturn(int limitCount, Supplier<T> supplier) {
        while (limitCount > 0) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                limitCount--;
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("[ERROR] 지정된 재시도 횟수를 초과했습니다.");
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
