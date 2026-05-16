package finalProject.analysis.controller;

import finalProject.analysis.dto.request.AnalysisCreateRequestDto;
import finalProject.analysis.dto.response.AnalysisResultResponseDto;
import finalProject.analysis.service.AnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnalysisResultResponseDto analyzeTheory(@Valid @RequestBody AnalysisCreateRequestDto dto) {
        return analysisService.analyzeTheory(dto);
    }

    @GetMapping("/{analysisId}")
    public AnalysisResultResponseDto getAnalysisById(@PathVariable UUID analysisId) {
        return analysisService.getAnalysisById(analysisId);
    }

    @GetMapping("/theory/{theoryId}")
    public AnalysisResultResponseDto getAnalysisByTheoryId(@PathVariable UUID theoryId) {
        return analysisService.getAnalysisByTheoryId(theoryId);
    }
}
