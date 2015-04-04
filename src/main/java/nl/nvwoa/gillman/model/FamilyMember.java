package nl.nvwoa.gillman.model;


public enum FamilyMember {
    DAUGHTER("D"), FATHER("F"), MOTHER("M"), SON("S");

    private String abbreviation;

    FamilyMember(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static FamilyMember getFamilyMemberForAbbreviation(final String abbreviation) {
        for (FamilyMember actMember : FamilyMember.values()) {
            if (actMember.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return actMember;
            }
        }
        return null;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}