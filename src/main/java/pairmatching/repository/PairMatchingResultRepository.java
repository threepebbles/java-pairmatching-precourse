package pairmatching.repository;

import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.PairMatchingResult;

public class PairMatchingResultRepository {
    private static final List<PairMatchingResult> RESULTS = new ArrayList<>();

    public static void put(PairMatchingResult result) {
        RESULTS.removeIf(r -> r.getCourse() == result.getCourse()
                && r.getLevel() == result.getLevel()
                && r.getMission() == result.getMission());
        RESULTS.add(result);
    }

    public static PairMatchingResult find(Course c, Level l, Mission m) {
        List<PairMatchingResult> target = RESULTS.stream().filter(r -> r.getCourse() == c
                && r.getLevel() == l
                && r.getMission() == m).toList();
        if (target.isEmpty()) {
            return null;
        }
        return target.getFirst();
    }

    public static void dropAll() {
        RESULTS.clear();
    }
}
