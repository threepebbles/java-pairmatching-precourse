package pairmatching.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pairmatching.view.InputView;

public class MainController {

    private static final String END_OPTION = "Q";
    private static final Map<String, Runnable> OPTIONS = new HashMap<>();

    public MainController() {
        initializeCrews();
        initializeOptions();
    }

    private void initializeCrews() {
        ResourceLoader.loadBackendCrews();
        ResourceLoader.loadFrontendCrews();
    }

    private void initializeOptions() {
        OPTIONS.put("1", this::pairMatching);
        OPTIONS.put("2", this::retrieveMatching);
        OPTIONS.put("3", this::clearMatching);
    }

    public void run() {
        while (true) {
            String option = InputView.requestOption();
            if (Objects.equals(option, END_OPTION)) {
                return;
            }
            OPTIONS.get(option).run();
        }
    }

    public void pairMatching() {
        System.out.println("페어매칭 할거다");
    }

    public void retrieveMatching() {
        System.out.println("조회할거다");
    }

    public void clearMatching() {
        System.out.println("초기화 할거다");
    }
}
