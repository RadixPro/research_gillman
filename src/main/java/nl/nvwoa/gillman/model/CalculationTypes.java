package nl.nvwoa.gillman.model;


public enum CalculationTypes {
    LONGITUDE("long"),
    RIGHT_ASCENSION("ra"),
    ALTITUDE_BODY("altb"),
    ALTITUDE_ECLIPTICAL_POS("altp")  ;

    private final String abbreviation;

    CalculationTypes (final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static CalculationTypes getCalculationTypeForAbbreviation(final String abbreviation) {
        for (CalculationTypes actCalculationType : CalculationTypes.values()) {
            if (actCalculationType.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return actCalculationType;
            }
        }
        return null;
    }

    public String getAbbreviation() {
        return abbreviation;
    }


}
