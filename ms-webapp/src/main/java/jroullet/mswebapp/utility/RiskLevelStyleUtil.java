package jroullet.mswebapp.utility;

import jroullet.mswebapp.model.RiskLevel;

import java.util.EnumMap;
import java.util.Map;

public class RiskLevelStyleUtil {

    private static final Map<RiskLevel, String> RISK_CSS_CLASS_MAP = new EnumMap<>(RiskLevel.class);

    // Map a RiskLevel to a String  for each field
    // Useful for CSS mapping (ie: "risk-none" isPresent in styles.css)
    static {
        RISK_CSS_CLASS_MAP.put(RiskLevel.NONE, "risk-none");
        RISK_CSS_CLASS_MAP.put(RiskLevel.BORDERLINE, "risk-borderline");
        RISK_CSS_CLASS_MAP.put(RiskLevel.IN_DANGER, "risk-danger");
        RISK_CSS_CLASS_MAP.put(RiskLevel.EARLY_ONSET, "risk-early-onset");
    }

    public static String getCssClass(RiskLevel level) {
        return RISK_CSS_CLASS_MAP.getOrDefault(level, "risk-none");
    }
}

