package pairmatching.domain;

import java.util.Objects;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private String name;

    Course(String name) {
        this.name = name;
    }

    public static Boolean isExist(String name) {
        for (Course c : Course.values()) {
            if (Objects.equals(c.name, name)) {
                return true;
            }
        }
        return false;
    }

    public static Course value(String name) {
        for (Course c : Course.values()) {
            if (Objects.equals(c.name, name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 코스입니다.");
    }
}
