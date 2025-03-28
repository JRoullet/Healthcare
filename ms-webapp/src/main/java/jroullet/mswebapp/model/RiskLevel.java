package jroullet.mswebapp.model;

import lombok.Getter;

@Getter
public enum RiskLevel {

    NONE("Risque très faible"),
    BORDERLINE("Risque apparent"),
    IN_DANGER("Danger"),
    EARLY_ONSET("Early and Onset"),;

    private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
