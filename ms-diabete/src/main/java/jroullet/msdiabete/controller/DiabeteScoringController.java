package jroullet.msdiabete.controller;

import jroullet.msdiabete.model.enums.RiskLevel;
import jroullet.msdiabete.service.DiabeteScoringService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiabeteScoringController {

    private final DiabeteScoringService diabeteScoringService;
    private final static Logger logger = LoggerFactory.getLogger(DiabeteScoringController.class);


    @GetMapping("/diabete/value/{patientId}")
    public ResponseEntity<RiskLevel> getRiskLevel(@PathVariable Long patientId) {
        try {
            logger.info("Retrieving risk level for patient {}", patientId);
            RiskLevel riskLevel = diabeteScoringService.determineRiskLevel(patientId);
            return ResponseEntity.ok(riskLevel);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
