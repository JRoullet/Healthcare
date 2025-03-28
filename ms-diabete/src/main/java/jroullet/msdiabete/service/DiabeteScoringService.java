package jroullet.msdiabete.service;

import jroullet.msdiabete.client.NoteFeignClient;
import jroullet.msdiabete.client.PatientFeignClient;
import jroullet.msdiabete.model.Note;
import jroullet.msdiabete.model.Patient;
import jroullet.msdiabete.model.enums.Gender;
import jroullet.msdiabete.model.enums.RiskLevel;
import jroullet.msdiabete.model.enums.TriggeringTerm;
import jroullet.msdiabete.utils.GenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class DiabeteScoringService {

    private final NoteFeignClient noteFeignClient;

    private final PatientFeignClient patientFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(DiabeteScoringService.class);

    public DiabeteScoringService(NoteFeignClient noteFeignClient, PatientFeignClient patientFeignClient) {
        this.noteFeignClient = noteFeignClient;
        this.patientFeignClient = patientFeignClient;
    }

    public RiskLevel determineRiskLevel(Long patientId){

        Patient patient = patientFeignClient.getPatientById(patientId);

        if (patient == null) {
            throw new IllegalStateException("Patient not found.");
        }

        logger.info("Patient found: {}", patient);

        List<Note> notes = noteFeignClient.getNotesByPatientId(patientId).getBody();
        if (notes == null || notes.isEmpty()) {
            throw new IllegalStateException("No notes found for this patient.");
        }
        logger.info("Notes: {}", notes);

        int patientAge = calculateAge(patient);
        logger.info("Patient age: {}", patientAge);

        Gender gender = GenderUtils.resolve(patient.getGender());

        Set<TriggeringTerm> detectedTerms = findTriggeringTerms(notes);
        int numberOfTerms = detectedTerms.size();

        return evaluateRiskLevel(gender, patientAge, numberOfTerms);

    }

    // Age calculation
    private int calculateAge(Patient patient) {
        return Period.between(patient.getBirthday(), LocalDate.now()).getYears();
    }

    // Set is a List that avoids twice the same value
    private Set<TriggeringTerm> findTriggeringTerms (List<Note> notes) {
        //Define a Set (no pairs involved)
        Set<TriggeringTerm> triggeringTerms = new HashSet<>();
        //For each :  iterate on each note
        for(Note note : notes) {
            // normalize each note content (strings) using our own method normalizeText
            String normalizedContent = normalizeText(note.getContent());
            // For each : iterate on each triggering term that takes triggeringTerms.values as value
            for(TriggeringTerm term : TriggeringTerm.values()) {
                if(normalizedContent.contains(term.getNormalizedTerm())){
                    triggeringTerms.add(term);
                }
            }
        }
        return triggeringTerms;
    }

    // Normalize all the triggering terms to avoid missing them, using included JDK libraries (Normalizer and Pattern)
    private String normalizeText(String text) {
        // To lowercase
        String textToLowerCase = text.toLowerCase();
        // Decompose chars with accents in  1 char without accent + 1 another char for the accent (ie: à = a + ` )
        String normalized = Normalizer.normalize(textToLowerCase, Normalizer.Form.NFD);
        // Create a pattern combining all the now separated accents
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+"); // index on all accents (' ` ")
        // match those characters with normalized text and deletes them
        String noAccent = pattern.matcher(normalized).replaceAll("");

        // delete all chars except min, number, space, delete dots and comas,
        // delete and replace multiple spaces, tabs and returns with 1 space
        // trim deletes spaces at beginning and end of the chain
        return noAccent.replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+","").trim();
    }

    private RiskLevel evaluateRiskLevel(Gender gender, int patientAge, int numberOfTerms) {
        if(numberOfTerms < 2){
            return RiskLevel.NONE;
        }
        if(patientAge > 30){
            if(numberOfTerms > 2 && numberOfTerms < 6)
            {
                return RiskLevel.BORDERLINE;
            }
            if(numberOfTerms >= 6 && numberOfTerms < 8){
                return RiskLevel.IN_DANGER;
            }
            if(numberOfTerms >= 8)
            {
                return RiskLevel.EARLY_ONSET;
            }
        }
        if(patientAge < 30){
            if(gender == Gender.MALE) {
                if (numberOfTerms >= 3 && numberOfTerms < 5 ) {
                    return RiskLevel.IN_DANGER;
                }
                if (numberOfTerms >= 5) {
                    return RiskLevel.EARLY_ONSET;
                }
            }
            if(gender == Gender.FEMALE) {
                if (numberOfTerms >= 4 && numberOfTerms < 7 ) {
                    return RiskLevel.IN_DANGER;
                }
                if (numberOfTerms >= 7) {
                    return RiskLevel.EARLY_ONSET;
                }
            }
        }
        return RiskLevel.NONE;
    }

}
