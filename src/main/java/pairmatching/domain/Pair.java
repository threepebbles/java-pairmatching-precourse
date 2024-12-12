package pairmatching.domain;

import java.util.List;

public class Pair {
    private List<Crew> crew;

    public Pair(List<Crew> crew) {
        this.crew = crew;
    }

    public List<Crew> getCrew() {
        return crew;
    }
}
