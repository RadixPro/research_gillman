package nl.nvwoa.gillman.model;

public class CalculatedPosition {
    private Bodies body;
    private double declination;
    private double latitude;
    private double longitude;
    private double rightAscension;
    private double risingTime;


    public Bodies getBody() {
        return body;
    }


    public double getDeclination() {
        return declination;
    }

    public void setDeclination(final double declination) {
        this.declination = declination;
    }


    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public double getRightAscension() {
        return rightAscension;
    }

    public void setRightAscension(final double rightAscension) {
        this.rightAscension = rightAscension;
    }

    public double getRisingTime() {
        return risingTime;
    }

    public void setRisingTime(final double risingTime) {
        this.risingTime = risingTime;
    }

    public void setBodies(final Bodies body) {
        this.body = body;
    }
}
