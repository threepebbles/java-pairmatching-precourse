package pairmatching.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.PairMatchingResult;
import pairmatching.repository.PairMatchingResultRepository;
import pairmatching.service.PairMatchingService;
import pairmatching.util.RetryHandler;
import pairmatching.view.InputView;
import pairmatching.view.MatchingTargetRequest;
import pairmatching.view.OutputView;
import pairmatching.view.RetrieveMatchingRequest;

public class MainController {

    private static final String END_OPTION = "Q";
    private static final Map<String, Runnable> OPTIONS = new HashMap<>();
    private static final int PAIR_MATCHING_RETRY_LIMIT = 3;

    public MainController() {
        initializeCrews();
        initializeOptions();
    }

    private void initializeCrews() {
        ResourceLoader.loadBackendCrews();
        ResourceLoader.loadFrontendCrews();
    }

    private void initializeOptions() {
        OPTIONS.put("1", this::pairMatching);
        OPTIONS.put("2", this::retrieveMatching);
        OPTIONS.put("3", this::clearMatching);
    }

    public void run() {
        while (true) {
            String option = InputView.scanOption();
            if (Objects.equals(option, END_OPTION)) {
                return;
            }
            OPTIONS.get(option).run();
        }
    }

    public void pairMatching() {
        OutputView.printMissionList();
        while (true) {
            MatchingTargetRequest request = InputView.scanMatchingTarget();
            if (!keepMatchingOrNot(request.getCourse(), request.getLevel(), request.getMission())) {
                continue;
            }
            PairMatchingResult result = solvePairMatchingResultWithLimit(request, PAIR_MATCHING_RETRY_LIMIT);
            if (result == null) {
                break;
            }
            PairMatchingResultRepository.put(result);
            OutputView.printPairMatchingResult(result);
            return;
        }
    }

    private boolean keepMatchingOrNot(Course course, Level level, Mission mission) {
        if (PairMatchingResultRepository.find(course, level, mission)
                != null) {
            return InputView.scanRematching();
        }
        return true;
    }

    private PairMatchingResult solvePairMatchingResultWithLimit(MatchingTargetRequest request, int count) {
        try {
            return (PairMatchingResult) RetryHandler.retryUntilSuccessWithReturn(count,
                    () -> PairMatchingService.matchingPairs(request.getCourse(), request.getLevel(),
                            request.getMission())
            );
        } catch (RuntimeException e) {
            System.out.println("[ERROR] 페어 매칭 재시도 횟수 " + count + "번을 초과하여 매칭에 실패했습니다.");
            return null;
        }
    }

    public void retrieveMatching() {
        OutputView.printMissionList();
        RetrieveMatchingRequest request = InputView.scanRetrieveMatching();
        PairMatchingResult result = PairMatchingResultRepository.find(request.getCourse(), request.getLevel(),
                request.getMission());
        if (result == null) {
            System.out.println("[ERROR] 매칭 이력이 없습니다.");
            return;
        }
        OutputView.printPairMatchingResult(result);
    }

    public void clearMatching() {
        PairMatchingResultRepository.dropAll();
        System.out.println("초기화 되었습니다.");
    }
}
