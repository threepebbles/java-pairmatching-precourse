package pairmatching.repository;

import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.Crew;

public class CrewRepository {
    private static final List<Crew> CREWS = new ArrayList<>();

    public static void addCrew(Crew crew) {
        CREWS.add(crew);
    }

    public static List<Crew> findAll() {
        return CREWS;
    }
}
