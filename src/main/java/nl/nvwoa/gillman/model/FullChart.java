package nl.nvwoa.gillman.model;

import java.util.List;

public class FullChart {
    private List<CalculatedPosition> calculatedPositions;
    private double epsilon;
    private String familyId;
    private String familyRole;
    private String id;
    private double longitudeAsc;
    private double longitudeMC;
    private List<Bodies> bodiesOrderedByRisingTime;
    private PlanetarySequence planetarySequence;
    private double raAsc;
    private double raMc;


    public List<Bodies> getBodiesOrderedByRisingTime() {
        return bodiesOrderedByRisingTime;
    }

    public List<CalculatedPosition> getCalculatedPositions() {
        return calculatedPositions;
    }

    public void setCalculatedPositions(final List<CalculatedPosition> calculatedPositions) {
        this.calculatedPositions = calculatedPositions;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(final double epsilon) {
        this.epsilon = epsilon;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(final String familyId) {
        this.familyId = familyId;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(final String familyRole) {
        this.familyRole = familyRole;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public double getLongitudeAsc() {
        return longitudeAsc;
    }

    public void setLongitudeAsc(final double longitudeAsc) {
        this.longitudeAsc = longitudeAsc;
    }

    public double getLongitudeMC() {
        return longitudeMC;
    }

    public void setLongitudeMC(final double longitudeMC) {
        this.longitudeMC = longitudeMC;
    }

    public PlanetarySequence getPlanetarySequence() {
        return planetarySequence;
    }

    public void setPlanetarySequence(final PlanetarySequence planetarySequence) {
        this.planetarySequence = planetarySequence;
    }

    public double getRaAsc() {
        return raAsc;
    }

    public void setRaAsc(final double raAsc) {
        this.raAsc = raAsc;
    }

    public void setBodiesByRisingTime(final List<Bodies> bodiesOrdered) {
        bodiesOrderedByRisingTime = bodiesOrdered;
    }

    public double getRAMC() {
        return raMc;
    }

    public void setRaMc(final double ramc) {
        raMc = ramc;
    }
}
