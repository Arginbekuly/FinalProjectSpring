package finalProject.evidence.service;


import finalProject.evidence.dto.request.EvidenceCreateRequestDto;
import finalProject.evidence.entity.EvidenceType;
import org.springframework.stereotype.Service;

@Service

public class EvidenceCalculateService {
    public int calculateReliability(EvidenceCreateRequestDto dto) {
        int score = 40;

        if (dto.sourceUrl() != null && dto.sourceUrl().startsWith("https://")) {
            score += 10;
        }

        if (dto.sourceName() != null && !dto.sourceName().isBlank()) {
            score += 10;
        }

        if (dto.content() != null && dto.content().length() >= 100) {
            score += 15;
        }

        if (dto.type() == EvidenceType.DOCUMENT_REFERENCE) {
            score += 15;
        }

        if (dto.sourceUrl() != null &&
                (dto.sourceUrl().contains("kff.kz")
                        || dto.sourceUrl().contains("uefa.com")
                        || dto.sourceUrl().contains("fifa.com"))) {
            score += 20;
        }

        return Math.min(score, 100);
    }
}
