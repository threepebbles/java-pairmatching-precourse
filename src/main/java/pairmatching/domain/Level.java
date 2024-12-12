package pairmatching.domain;

import java.util.Objects;

public enum Level {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private final String name;

    Level(String name) {
        this.name = name;
    }

    public static Boolean isExist(String name) {
        for (Level l : Level.values()) {
            if (Objects.equals(l.name, name)) {
                return true;
            }
        }
        return false;
    }

    public static Level value(String name) {
        for (Level l : Level.values()) {
            if (Objects.equals(l.name, name)) {
                return l;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 레벨입니다.");
    }
}
