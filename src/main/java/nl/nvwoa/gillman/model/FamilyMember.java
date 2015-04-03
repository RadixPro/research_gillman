package nl.nvwoa.gillman.model;


public enum FamilyMember {
    DAUGHTER("D"), FATHER("F"), MOTHER("M"), SON("S");
    public static FamilyMember getFamilyMemberForAbreviation(final String abbreviation) {
        for (FamilyMember actMember : FamilyMember.values()) {
            if (actMember.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return actMember;
            }
        }
        return null;
    }

    private String abbreviation;

    FamilyMember(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}