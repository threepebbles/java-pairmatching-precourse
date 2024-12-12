package pairmatching.domain;

import java.util.Objects;

public enum Mission {
    CAR_RACING(Level.LEVEL1, "자동차경주"),
    LOTTO(Level.LEVEL1, "로또"),
    BASEBALL_GAME(Level.LEVEL1, "숫자야구게임"),
    SHOPPING_CART(Level.LEVEL2, "장바구니"),
    PAYMENT(Level.LEVEL2, "결제"),
    SUBWAY(Level.LEVEL2, "지하철노선도"),
    IMPROVE_PERFORMANCE(Level.LEVEL4, "성능개선"),
    DISTRIBUTION(Level.LEVEL4, "배포"),
    NOTHING(null, null);

    private final Level level;
    private final String name;

    Mission(Level level, String name) {
        this.level = level;
        this.name = name;
    }

    public static Boolean isExist(Level level, String name) {
        for (Mission m : Mission.values()) {
            if (m.level == level && Objects.equals(m.name, name)) {
                return true;
            }
        }
        return false;
    }

    public static Mission value(Level level, String name) {
        for (Mission m : Mission.values()) {
            if (m.level == level && Objects.equals(m.name, name)) {
                return m;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 미션입니다.");
    }
}
