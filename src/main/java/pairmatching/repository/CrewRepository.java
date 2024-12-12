package pairmatching.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;

public class CrewRepository {
    private static final List<Crew> CREWS = new ArrayList<>();

    public static void addCrew(Crew crew) {
        CREWS.add(crew);
    }

    public static List<Crew> findAll() {
        return CREWS;
    }

    public static Crew find(Course course, String name) {
        List<Crew> target = CREWS.stream().filter(c ->
                c.getCourse() == course && Objects.equals(c.getName(), name)).toList();
        if (target.isEmpty()) {
            return null;
        }
        return target.getFirst();
    }
}
