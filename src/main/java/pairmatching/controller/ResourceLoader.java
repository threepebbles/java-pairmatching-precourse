package pairmatching.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.repository.CrewRepository;

public class ResourceLoader {
    private static final String BACKEND_CREWS_FILE_PATH = "src/main/resources/backend-crew.md";
    private static final String FRONTEND_CREWS_FILE_PATH = "src/main/resources/frontend-crew.md";

    public static void loadBackendCrews() {
        try {
            FileReader fr = new FileReader(BACKEND_CREWS_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String inp;
            while ((inp = br.readLine()) != null) {
                CrewRepository.addCrew(new Crew(Course.BACKEND, inp));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadFrontendCrews() {
        try {
            FileReader fr = new FileReader(FRONTEND_CREWS_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String inp;
            while ((inp = br.readLine()) != null) {
                CrewRepository.addCrew(new Crew(Course.FRONTEND, inp));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}