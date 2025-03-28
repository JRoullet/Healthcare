package jroullet.msdiabete.utils;

import jroullet.msdiabete.model.enums.Gender;

public class GenderUtils {

    public static Gender resolve(String gender) {
        if(gender == null){
            return Gender.OTHER;
        }
        return switch (gender.toUpperCase()) {
            case "M", "MALE", "HOMME" -> Gender.MALE;
            case "F", "FEMALE" -> Gender.FEMALE;
            default -> Gender.OTHER;
        };
    }
}
