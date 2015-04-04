package nl.nvwoa.gillman.model;

public class PlanetarySequence {
    private Bodies[] bodiesInSequence;
    private String familyGroupId;
    private String id;

    public Bodies[] getBodiesInSequence() {
        return bodiesInSequence;
    }

    public String getFamilyGroupId() {
        return familyGroupId;
    }

    public String getId() {
        return id;
    }

    public void setBodiesInSequence(final Bodies[] bodiesInSequence) {
        this.bodiesInSequence = bodiesInSequence;
    }

    public void setFamilyGroupId(final String familyGroupId) {
        this.familyGroupId = familyGroupId;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
