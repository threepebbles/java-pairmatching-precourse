package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import pairmatching.util.RetryHandler;

public class InputView {
    private static final List<String> OPTIONS = List.of("1", "2", "3", "Q");
    private static final String SELECT_OPTIONS_SCREEN = """
            기능을 선택하세요.
            1. 페어 매칭
            2. 페어 조회
            3. 페어 초기화
            Q. 종료
            """;

    public static String requestOption() {
        return (String) RetryHandler.retryUntilSuccessWithReturn(() -> {
            System.out.print(SELECT_OPTIONS_SCREEN);
            String inp = Console.readLine();
            validateOption(inp);
            return inp;
        });
    }

    private static void validateOption(String inp) {
        if (OPTIONS.contains(inp)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다. 재입력 해주세요.");
    }
}
