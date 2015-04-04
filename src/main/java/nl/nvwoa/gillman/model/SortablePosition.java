package nl.nvwoa.gillman.model;

public class SortablePosition {
    private final Bodies body;
    private final double position;
    private final double risingTime;
    private double relativePosition;

    public SortablePosition(final Bodies body, final double position, final double risingTime) {
        this.body = body;
        this.position = position;
        this.risingTime = risingTime;
        relativePosition = 0.0;
    }


    public Bodies getBody() {
        return body;
    }


    public double getPosition() {
        return position;
    }


    public double getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(final double relativePosition) {
        this.relativePosition = relativePosition;
    }

    public double getRisingTime() {
        return risingTime;
    }
}
