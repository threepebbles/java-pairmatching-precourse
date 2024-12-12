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
            PairMatchingResult result = solvePairMatchingResultWithLimit(request, 3);
            PairMatchingResultRepository.put(result);
            OutputView.printPairMatchingResult(result);
            return;
        }
    }

    public boolean keepMatchingOrNot(Course course, Level level, Mission mission) {
        if (PairMatchingResultRepository.find(course, level, mission)
                != null) {
            return InputView.scanRematching();
        }
        return true;
    }

    public PairMatchingResult solvePairMatchingResultWithLimit(MatchingTargetRequest request, int count) {
        return (PairMatchingResult) RetryHandler.retryUntilSuccessWithReturn(count, () -> {
            return PairMatchingService.matchingPairs(request.getCourse(), request.getLevel(), request.getMission());
        });
    }

    public void retrieveMatching() {
        OutputView.printMissionList();
        RetryHandler.retryUntilSuccess(() -> {
            RetrieveMatchingRequest request = InputView.scanRetrieveMatching();
            PairMatchingResult result = PairMatchingResultRepository.find(request.getCourse(), request.getLevel(),
                    request.getMission());
            if (result == null) {
                throw new IllegalArgumentException("[ERROR] 매칭 이력이 없습니다.");
            }
            OutputView.printPairMatchingResult(result);
        });
    }

    public void clearMatching() {
        PairMatchingResultRepository.dropAll();
    }
}
