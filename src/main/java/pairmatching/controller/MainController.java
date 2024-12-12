package pairmatching.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pairmatching.service.PairMatchingService;
import pairmatching.view.InputView;

public class MainController {
    private static final String END_OPTION = "Q";
    private static final Map<String, Runnable> OPTIONS = new HashMap<>();
    private final PairMatchingService pairMatchingService;

    public MainController(PairMatchingService pairMatchingService) {
        this.pairMatchingService = pairMatchingService;
        initializeCrews();
        initializeOptions();
    }

    private void initializeCrews() {
        ResourceLoader.loadBackendCrews();
        ResourceLoader.loadFrontendCrews();
    }

    private void initializeOptions() {
        OPTIONS.put("1", pairMatchingService::pairMatching);
        OPTIONS.put("2", pairMatchingService::retrieveMatching);
        OPTIONS.put("3", pairMatchingService::clearMatching);
    }

    public void run() {
        while (true) {
            String option = InputView.scanOption();
            if (Objects.equals(option, END_OPTION)) {
                return;
            }
            OPTIONS.get(option).run();
        }
    }
}
