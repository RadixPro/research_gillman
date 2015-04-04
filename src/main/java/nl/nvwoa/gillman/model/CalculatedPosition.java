package nl.nvwoa.gillman.model;

public class CalculatedPosition {
    Bodies body;
    double declination;
    double latitude;
    double longitude;
    double rightAscension;
    double risingTime;

    
    public Bodies getBody() {
        return body;
    }

    
    public double getDeclination() {
        return declination;
    }

    
    public double getLatitude() {
        return latitude;
    }

    
    public double getLongitude() {
        return longitude;
    }

    
    public double getRightAscension() {
        return rightAscension;
    }

    
    public double getRisingTime() {
        return risingTime;
    }

    
    public void setBodies(final Bodies body) {
        this.body = body;
    }

    
    public void setDeclination(final double declination) {
        this.declination = declination;
    }

    
    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    
    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    
    public void setRightAscension(final double rightAscension) {
        this.rightAscension = rightAscension;
    }

    
    public void setRisingTime(final double risingTime) {
        this.risingTime = risingTime;
    }
}
