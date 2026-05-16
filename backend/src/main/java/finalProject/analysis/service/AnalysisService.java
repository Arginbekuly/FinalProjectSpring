package finalProject.analysis.service;

import finalProject.analysis.dto.request.AnalysisCreateRequestDto;
import finalProject.analysis.dto.response.AnalysisResultResponseDto;
import finalProject.analysis.entity.AnalysisResult;
import finalProject.analysis.mapper.AnalysisMapper;
import finalProject.analysis.repository.AnalysisResultRepository;
import finalProject.common.exception.NotFoundException;
import finalProject.contradiction.entity.ContradictionStatus;
import finalProject.contradiction.repository.ContradictionRepository;
import finalProject.evidence.entity.Evidence;
import finalProject.evidence.repository.EvidenceRepository;
import finalProject.theory.entity.Theory;
import finalProject.theory.entity.TheoryStatus;
import finalProject.theory.repository.TheoryRepository;
import finalProject.user.entity.User;
import finalProject.user.entity.UserRole;
import finalProject.user.entity.UserStatus;
import finalProject.vote.entity.VoteType;
import finalProject.vote.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisResultRepository analysisResultRepository;
    private final AnalysisMapper analysisMapper;
    private final TheoryRepository theoryRepository;
    private final EvidenceRepository evidenceRepository;
    private final VoteRepository voteRepository;
    private final ContradictionRepository contradictionRepository;

    public AnalysisResultResponseDto analyzeTheory(AnalysisCreateRequestDto dto) {
        Theory theory = getTheoryOrThrow(dto.theoryId());
        List<Evidence> evidences = evidenceRepository.findAllByTheoryId(dto.theoryId());
        long upvotes = voteRepository.countByTheory_IdAndType(dto.theoryId(), VoteType.UPVOTE);
        long downvotes = voteRepository.countByTheory_IdAndType(dto.theoryId(), VoteType.DOWNVOTE);
        var contradictions = contradictionRepository.findAllByTheoryIdAOrTheoryIdB(dto.theoryId(), dto.theoryId()).stream()
                .filter(contradiction -> contradiction.getStatus() != ContradictionStatus.REJECTED)
                .toList();

        User author = theory.getUser();
        float userReputation = calculateUserReputation(author);

        float rawEvidenceScore = calculateEvidenceScore(evidences);

        float evidenceScore = roundToTwoDecimals(
                Math.min(100f, rawEvidenceScore * (userReputation >= 1.0f ? 1.05f : 0.7f))
        );

        float consistencyScore = calculateConsistencyScore(
                contradictions.size(),
                contradictions.stream()
                        .map(contradiction -> contradiction.getSeverity() == null ? 0 : contradiction.getSeverity())
                        .toList()
        );

        if (contradictions.size() >= 3) {
            consistencyScore = roundToTwoDecimals(consistencyScore * 0.6f);
        }

        float communityScore = calculateCommunityScore(upvotes, downvotes);

        float finalCredibilityScore = roundToTwoDecimals(
                evidenceScore * 0.45f + consistencyScore * 0.35f + communityScore * 0.20f
        );

        String summary = buildSummary(evidenceScore, consistencyScore, communityScore, finalCredibilityScore, userReputation, contradictions.size(), evidences.size());

        AnalysisResult analysisResult = analysisResultRepository.findByTheoryId(dto.theoryId())
                .orElseGet(() -> analysisMapper.toEntity(dto));
        analysisResult.setTheoryId(dto.theoryId());
        analysisResult.setEvidenceScore(evidenceScore);
        analysisResult.setConsistencyScore(consistencyScore);
        analysisResult.setCommunityScore(communityScore);
        analysisResult.setFinalCredibilityScore(finalCredibilityScore);
        analysisResult.setSummary(summary);

        AnalysisResult savedAnalysisResult = analysisResultRepository.save(analysisResult);
        updateTheoryScores(theory, finalCredibilityScore, upvotes, downvotes);
        return analysisMapper.toDto(savedAnalysisResult);
    }


    public AnalysisResultResponseDto getAnalysisById(UUID analysisId) {
        AnalysisResult analysisResult = analysisResultRepository.findById(analysisId)
                .orElseThrow(() -> new NotFoundException("Analysis not found with id: " + analysisId));
        return analysisMapper.toDto(analysisResult);
    }


    public AnalysisResultResponseDto getAnalysisByTheoryId(UUID theoryId) {
        getTheoryOrThrow(theoryId);
        AnalysisResult analysisResult = analysisResultRepository.findByTheoryId(theoryId)
                .orElseThrow(() -> new NotFoundException("Analysis not found for theory id: " + theoryId));
        return analysisMapper.toDto(analysisResult);
    }


    private Theory getTheoryOrThrow(UUID theoryId) {
        return theoryRepository.findById(theoryId)
                .orElseThrow(() -> new NotFoundException("Theory not found with id: " + theoryId));
    }


    private float calculateUserReputation(User user) {
        if (user == null)  return 0.5f;
        if (user.getStatus() == UserStatus.BLOCKED) return 0f;
        if (user.getRole() == UserRole.ADMIN) return 2f;

        float baseReputation = 1f;
        UUID userId = user.getId();

        List <Evidence> userEvidences = evidenceRepository.findAllByUserId(userId);
        if (!userEvidences.isEmpty()){
            float totalReliability = 0;
            for (Evidence evidence : userEvidences) {
                totalReliability +=evidence.getReliabilityScore();
            }
            float avgReliability = totalReliability / userEvidences.size();

            if (avgReliability > 70f) baseReputation += 0.3f;

            if(avgReliability < 40f) baseReputation -= 0.4f;

        }

        List<Theory> userTheories = theoryRepository.findByUserId(userId);
        for (Theory t : userTheories) {
            if (t.getStatus() == TheoryStatus.FLAGGED || t.getStatus() == TheoryStatus.REJECTED) {
                baseReputation -= 0.2f;
            }
            if (t.getStatus() == TheoryStatus.PUBLISHED) {
                baseReputation += 0.1f;
            }
        }

        return Math.max(0.1f, Math.min(2.5f, baseReputation));

    }

    private float calculateEvidenceScore(List<Evidence> evidences) {
        if (evidences.isEmpty()) {
            return 0f;
        }

        float totalWeight = 0f;
        float weightedSum = 0f;

        for (Evidence ev : evidences) {
            if (ev.getReliabilityScore() == null) continue;

            float typeWeight = switch (ev.getType()) {
                case DOCUMENT_REFERENCE -> 1.5f;
                case VIDEO_REFERENCE -> 1.3f;
                case IMAGE_REFERENCE -> 1.1f;
                case LINK -> 1.0f;
                case TEXT -> 0.6f;
            };

            weightedSum += (ev.getReliabilityScore() * typeWeight);
            totalWeight += typeWeight;
        }

        if (totalWeight == 0f) return 0f;
        return roundToTwoDecimals(weightedSum / totalWeight);
    }


    private float calculateConsistencyScore(int contradictionCount, List<Integer> severities) {
        if (contradictionCount == 0 || severities.isEmpty()) {
            return 100f;
        }

        double averageSeverity = severities.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        double penalty = averageSeverity * 15 + Math.max(0, contradictionCount - 1) * 10;
        return roundToTwoDecimals((float) Math.max(0, 100 - penalty));
    }


    private float calculateCommunityScore(long upvotes, long downvotes) {
        long totalVotes = upvotes + downvotes;
        if (totalVotes == 0) {
            return 50f;
        }

        return roundToTwoDecimals(((float) upvotes / totalVotes) * 100f);
    }


    private void updateTheoryScores(Theory theory, float credibilityScore, long upvotes, long downvotes) {
        theory.setCredibilityScore(credibilityScore);
        theory.setPopularityScore(calculatePopularityScore(upvotes, downvotes));
        theoryRepository.save(theory);
    }


    private float calculatePopularityScore(long upvotes, long downvotes) {
        long totalVotes = upvotes + downvotes;
        return roundToTwoDecimals(Math.min(100f, totalVotes * 10f));
    }


    private String buildSummary(float evidenceScore, float consistencyScore, float communityScore, float finalScore, float reputation, int contradictionCount, int evidenceCount) {
        String evidencePart;
        if (evidenceScore >= 70f) {
            evidencePart = "strong evidence support";
        } else if (evidenceScore >= 40f) {
            evidencePart = "moderate evidence support";
        } else {
            evidencePart = "limited evidence support";
        }

        String consistencyPart;
        if (consistencyScore >= 70f) {
            consistencyPart = "few active contradictions";
        } else if (consistencyScore >= 40f) {
            consistencyPart = "some notable contradictions";
        } else {
            consistencyPart = "significant contradiction pressure";
        }

        String communityPart;
        if (communityScore >= 60f) {
            communityPart = "positive community feedback";
        } else if (communityScore >= 40f) {
            communityPart = "mixed community feedback";
        } else {
            communityPart = "mostly negative community feedback";
        }

        String overallPart;
        if (finalScore >= 70f) {
            overallPart = "Overall credibility looks solid.";
        } else if (finalScore >= 40f) {
            overallPart = "Overall credibility is mixed.";
        } else {
            overallPart = "Overall credibility is weak.";
        }

        String systemMetricsPart = String.format(
                " [System Metrics -> Author Reputation Index: %.2f | Total Sources: %d | Active Contradictions: %d]",
                reputation, evidenceCount, contradictionCount
        );

        return evidencePart + ", " + consistencyPart + ", " + communityPart + ". " + overallPart + systemMetricsPart;
    }


    private float roundToTwoDecimals(float value) {
        return Math.round(value * 100f) / 100f;
    }
}
