package jroullet.msdiabete.model.enums;

import lombok.Getter;

@Getter
public enum TriggeringTerm {

    HEMOGLOBINE_A1C("hemoglobine a1c"),
    MICRO_ALBUMINE("micro albumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholesterol"),
    VERTIGE("vertige"),
    RECHUTE("rechute"),
    REACTION("reaction"),
    ANTICORPS("anticorps");

    private final String normalizedTerm;

    TriggeringTerm(String normalizedTerm) {
        this.normalizedTerm = normalizedTerm;

    }
}
