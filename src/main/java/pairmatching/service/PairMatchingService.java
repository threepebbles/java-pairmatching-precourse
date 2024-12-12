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

public class PairMatchingService {

    public static PairMatchingResult matchingPairs(Course course, Level level, Mission mission) {
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

    public static void connectCrews(Level level, List<Crew> crews) {
        for (Crew crew : crews) {
            List<Crew> friends = crews.stream()
                    .filter(c -> !Objects.equals(c.getName(), crew.getName()))
                    .toList();
            crew.saveHistory(level, friends);
        }
    }
}
