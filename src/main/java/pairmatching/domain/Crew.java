package pairmatching.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private Course course;
    private String name;

    private Map<Level, List<Crew>> history = new HashMap<>();

    public Crew(Course course, String name) {
        this.course = course;
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public Map<Level, List<Crew>> getHistory() {
        return history;
    }

    public void saveHistory(Level level, List<Crew> crews) {
        List<Crew> toBeUpdated = new ArrayList<>();
        if (history.containsKey(level)) {
            toBeUpdated.addAll(history.get(level));
        }
        for (Crew old : toBeUpdated) {
            if (crews.stream().anyMatch(c -> c.equals(old))) {
                throw new IllegalArgumentException("[ERROR] " + old + ": 같은 레벨에서 이미 페어로 만난 적이 있는 크루입니다.");
            }
        }
        toBeUpdated.addAll(crews);
        history.put(level, toBeUpdated);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crew) {
            return ((Crew) obj).getCourse() == course && Objects.equals(((Crew) obj).getName(), name);
        }
        return false;
    }
}
