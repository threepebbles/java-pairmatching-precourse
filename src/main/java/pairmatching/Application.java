package pairmatching;

import pairmatching.controller.MainController;
import pairmatching.service.PairMatchingService;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new MainController(new PairMatchingService());
        mainController.run();
    }
}
