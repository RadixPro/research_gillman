package nl.nvwoa.gillman.model;

public class MatchMember {
    private final String familyId;
    private final String familyRole;
    private final String id;
    private final PlanetarySequence sequence;

    public MatchMember(final String id, final String familyId, final String familyRole, final PlanetarySequence sequence) {
        this.id = id;
        this.familyId = familyId;
        this.familyRole = familyRole;
        this.sequence = sequence;
    }

    public String getFamilyId() {
        return familyId;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public String getId() {
        return id;
    }

    public PlanetarySequence getSequence() {
        return sequence;
    }
}
