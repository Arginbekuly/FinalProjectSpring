package finalProject.contradiction.service;

import finalProject.theory.entity.Theory;
import org.springframework.stereotype.Service;

@Service
public class ContradictionCalculateService {

    public int calculateSeverity(Theory theoryA, Theory theoryB, String reason) {
        int severity = 1;

        if (reason != null && reason.length() > 150) {
            severity++;
        }

        if (theoryA.getCredibilityScore() > 60 && theoryB.getCredibilityScore() > 60) {
            severity++;
        }

        if (hasSharedImportantWords(theoryA.getTitle(), theoryB.getTitle())) {
            severity++;
        }

        return Math.min(severity, 5);
    }

    private boolean hasSharedImportantWords(String titleA, String titleB) {
        if (titleA == null || titleB == null) {
            return false;
        }

        String[] wordsA = titleA.toLowerCase().split("\\W+");
        String[] wordsB = titleB.toLowerCase().split("\\W+");

        for (String wordA : wordsA) {
            if (wordA.length() < 4) {
                continue;
            }

            for (String wordB : wordsB) {
                if (wordA.equals(wordB)) {
                    return true;
                }
            }
        }

        return false;
    }
}