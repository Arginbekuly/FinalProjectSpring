package finalProject.theory.service;

import finalProject.contradiction.entity.ContradictionStatus;
import finalProject.contradiction.repository.ContradictionRepository;
import finalProject.evidence.entity.Evidence;
import finalProject.evidence.entity.EvidencePosition;
import finalProject.evidence.repository.EvidenceRepository;
import finalProject.theory.entity.DebateVerdict;
import finalProject.theory.entity.InvestigationStatus;
import finalProject.theory.entity.Theory;
import finalProject.theory.repository.TheoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TheoryInvestigationService {

    private final TheoryRepository theoryRepository;
    private final EvidenceRepository evidenceRepository;
    private final ContradictionRepository contradictionRepository;

    public void recalculate(UUID theoryId) {
        Theory theory = theoryRepository.findById(theoryId).orElseThrow();

        var evidences = evidenceRepository.findAllByTheoryId(theoryId);
        var contradictions = contradictionRepository.findAllByTheoryIdAOrTheoryIdB(theoryId, theoryId);

        float supportScore = calculateAverage(evidences, EvidencePosition.SUPPORTING);
        float oppositionScore = calculateAverage(evidences, EvidencePosition.OPPOSING);

        long activeContradictions = contradictions.stream()
                .filter(c -> c.getStatus() != ContradictionStatus.REJECTED)
                .count();

        float controversyScore = Math.min(100f, oppositionScore + activeContradictions * 15f);

        theory.setSupportScore(supportScore);
        theory.setOppositionScore(oppositionScore);
        theory.setControversyScore(controversyScore);
        theory.setInvestigationStatus(resolveInvestigationStatus(evidences.size(), supportScore, oppositionScore, activeContradictions));
        theory.setDebateVerdict(resolveDebateVerdict(evidences.size(), supportScore, oppositionScore));

        theoryRepository.save(theory);
    }

    private float calculateAverage(Iterable<Evidence> evidences, EvidencePosition position) {
        int count = 0;
        int total = 0;

        for (Evidence evidence : evidences) {
            if (evidence.getPosition() == position) {
                total += evidence.getReliabilityScore();
                count++;
            }
        }

        return count == 0 ? 0f : (float) total / count;
    }

    private InvestigationStatus resolveInvestigationStatus(int evidenceCount, float supportScore, float oppositionScore, long contradictions) {
        if (evidenceCount == 0) {
            return InvestigationStatus.SUBMITTED;
        }

        if (evidenceCount < 2) {
            return InvestigationStatus.COLLECTING_EVIDENCE;
        }

        if (supportScore >= 70 && oppositionScore < 40 && contradictions == 0) {
            return InvestigationStatus.VERIFIED;
        }

        if (oppositionScore >= 70 || contradictions >= 2) {
            return InvestigationStatus.DEBUNKED;
        }

        if (Math.abs(supportScore - oppositionScore) <= 15) {
            return InvestigationStatus.INCONCLUSIVE;
        }

        return InvestigationStatus.UNDER_REVIEW;
    }

    private DebateVerdict resolveDebateVerdict(int evidenceCount, float supportScore, float oppositionScore) {
        if (evidenceCount == 0) {
            return DebateVerdict.NOT_ENOUGH_DATA;
        }

        if (supportScore >= 70 && oppositionScore < 40) {
            return DebateVerdict.MOSTLY_SUPPORTED;
        }

        if (oppositionScore >= 70 && supportScore < 40) {
            return DebateVerdict.MOSTLY_OPPOSED;
        }

        if (Math.abs(supportScore - oppositionScore) <= 15) {
            return DebateVerdict.DISPUTED;
        }

        return DebateVerdict.INCONCLUSIVE;
    }
}