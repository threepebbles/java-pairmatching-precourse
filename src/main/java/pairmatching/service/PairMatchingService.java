package pairmatching.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.domain.PairMatchingResult;
import pairmatching.repository.CrewRepository;
import pairmatching.repository.PairMatchingResultRepository;
import pairmatching.util.RetryHandler;
import pairmatching.view.InputView;
import pairmatching.view.MatchingTargetRequest;
import pairmatching.view.OutputView;
import pairmatching.view.RetrieveMatchingRequest;

public class PairMatchingService {
    private static final int PAIR_MATCHING_RETRY_LIMIT = 3;

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
                    () -> matchingPairs(request.getCourse(), request.getLevel(),
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


    private PairMatchingResult matchingPairs(Course course, Level level, Mission mission) {
        List<Pair> pairs = new ArrayList<>();
        List<String> crewNames = CrewRepository.findAll().stream().filter(c -> c.getCourse() == course)
                .map(Crew::getName).toList();
        List<String> shuffled = Randoms.shuffle(crewNames); // 섞인 크루 이름 목록
        for (int i = 1; i < shuffled.size(); i += 2) {
            List<Crew> pair = new ArrayList<>();
            pair.add(CrewRepository.find(course, shuffled.get(i - 1)));
            pair.add(CrewRepository.find(course, shuffled.get(i)));
            if (i == shuffled.size() - 2 && shuffled.size() % 2 != 0) {
                pair.add(CrewRepository.find(course, shuffled.getLast()));
            }
            connectCrews(level, pair);
            pairs.add(new Pair(pair));
        }
        return new PairMatchingResult(course, level, mission, pairs);
    }

    private void connectCrews(Level level, List<Crew> crews) {
        for (Crew crew : crews) {
            List<Crew> friends = crews.stream()
                    .filter(c -> !Objects.equals(c.getName(), crew.getName()))
                    .toList();
            crew.saveHistory(level, friends);
        }
    }
}
