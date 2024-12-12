package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.util.RetryHandler;

public class InputView {
    private static final List<String> OPTIONS = List.of("1", "2", "3", "Q");
    private static final List<String> YES_OR_NO = List.of("네", "아니오");
    private static final String SELECT_OPTIONS_SCREEN = """
            기능을 선택하세요.
            1. 페어 매칭
            2. 페어 조회
            3. 페어 초기화
            Q. 종료
            """;

    private static final String ENTER_MISSION_INFO_SCREEN = """
            과정, 레벨, 미션을 선택하세요.
            ex) 백엔드, 레벨1, 자동차경주
            """;

    private static final String REMATCHING_SCREEN = """
            매칭 정보가 있습니다. 다시 매칭하시겠습니까?
            네 | 아니오
            """;

    public static String scanOption() {
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
        throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다. 다시 입력해 주세요.");
    }

    public static MatchingTargetRequest scanMatchingTarget() {
        return (MatchingTargetRequest) RetryHandler.retryUntilSuccessWithReturn(() -> {
            System.out.print(ENTER_MISSION_INFO_SCREEN);
            String inp = Console.readLine();
            validateCourseLevelMission(inp);
            List<String> parsed = Arrays.stream(inp.split(",", -1))
                    .map(String::strip).toList();
            return new MatchingTargetRequest(Course.value(parsed.get(0)), Level.value(parsed.get(1)),
                    Mission.value(Level.value(parsed.get(1)), parsed.get(2)));
        });
    }

    private static void validateCourseLevelMission(String inp) {
        List<String> parsed = Arrays.stream(inp.split(",", -1))
                .map(String::strip).toList();
        if (parsed.size() != 3) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 포맷입니다. 다시 입력해 주세요.");
        }
        if (!Course.isExist(parsed.get(0))
                || !Level.isExist(parsed.get(1))) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 과정, 레벨명입니다. 다시 입력해 주세요.");
        }
        if (!Mission.isExist(
                Level.value(parsed.get(1)),
                parsed.get(2))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 레벨<->미션명 관계입니다. 다시 입력해 주세요.");
        }
    }

    public static Boolean scanRematching() {
        return (Boolean) RetryHandler.retryUntilSuccessWithReturn(() -> {
            System.out.print(REMATCHING_SCREEN);
            String inp = Console.readLine();
            validateYesOrNo(inp);
            return convertYesOrNo(inp);
        });
    }

    private static void validateYesOrNo(String inp) {
        if (!YES_OR_NO.contains(inp)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다. 다시 입력해 주세요.");
        }
    }

    private static Boolean convertYesOrNo(String inp) {
        if (Objects.equals(inp, "네")) {
            return true;
        } else if (Objects.equals(inp, "아니오")) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다.");
    }

    public static RetrieveMatchingRequest scanRetrieveMatching() {
        return (RetrieveMatchingRequest) RetryHandler.retryUntilSuccessWithReturn(() -> {
            System.out.print(ENTER_MISSION_INFO_SCREEN);
            String inp = Console.readLine();
            validateCourseLevelMission(inp);
            List<String> parsed = Arrays.stream(inp.split(",", -1))
                    .map(String::strip).toList();
            return new RetrieveMatchingRequest(Course.value(parsed.get(0)), Level.value(parsed.get(1)),
                    Mission.value(Level.value(parsed.get(1)), parsed.get(2)));
        });
    }
}
