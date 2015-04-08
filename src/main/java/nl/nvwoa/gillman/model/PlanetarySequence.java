package nl.nvwoa.gillman.model;

public class PlanetarySequence {
    private Bodies[] bodiesInSequence;
    private String familyGroupId;
    private String id;

    public Bodies[] getBodiesInSequence() {
        return bodiesInSequence.clone();
    }

    public void setBodiesInSequence(final Bodies[] bodiesInSequence) {
        this.bodiesInSequence = bodiesInSequence.clone();
    }

    public String getFamilyGroupId() {
        return familyGroupId;
    }

    public void setFamilyGroupId(final String familyGroupId) {
        this.familyGroupId = familyGroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
