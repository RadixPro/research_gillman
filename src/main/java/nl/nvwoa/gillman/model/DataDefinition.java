package nl.nvwoa.gillman.model;

public class DataDefinition {
    private String ignoreLine;
    private int indexOfCategory = -1;
    private int indexOfDay = -1;
    private int indexOfGeoLatDegree = -1;
    private int indexOfGeoLatFull = -1;
    private int indexOfGeoLatMinute = -1;
    private int indexOfGeoLatSecond = -1;
    private int indexOfGeoLatSign = -1;
    private int indexOfGeoLongDegree = -1;
    private int indexOfGeoLongFull = -1;
    private int indexOfGeoLongMinute = -1;
    private int indexOfGeoLongSecond = -1;
    private int indexOfGeoLongSign = -1;
    private int indexOfHour = -1;
    private int indexOfId = -1;
    private int indexOfMinute = -1;
    private int indexOfMonth = -1;
    private int indexOfName = -1;
    private int indexOfSecond = -1;
    private int indexOfTimeZone = -1;
    private int indexOfAdditionalLocation = -1;
    private int indexOfYear = -1;
    private int nrOfHeaderLinesToSkip;
    private String separator;
    private String geoLatFullFormat;
    private String geoLongFullFormat;

    public int getIndexOfName() {
        return indexOfName;
    }

    public void setIndexOfName(int indexOfName) {
        this.indexOfName = indexOfName;
    }

    public String getGeoLongFullFormat() {
        return geoLongFullFormat;
    }

    public void setGeoLongFullFormat(String geoLongFullFormat) {
        this.geoLongFullFormat = geoLongFullFormat;
    }

    public String getGeoLatFullFormat() {
        return geoLatFullFormat;
    }

    public void setGeoLatFullFormat(String geoLatFullFormat) {
        this.geoLatFullFormat = geoLatFullFormat;
    }

    public int getIndexOfAdditionalLocation() {
        return indexOfAdditionalLocation;
    }

    public void setIndexOfAdditionalLocation(int indexOfAdditionalLocation) {
        this.indexOfAdditionalLocation = indexOfAdditionalLocation;
    }

    public int getIndexOfTimeZone() {
        return indexOfTimeZone;
    }

    public void setIndexOfTimeZone(int indexOfTimeZone) {
        this.indexOfTimeZone = indexOfTimeZone;
    }

    public int getIndexOfCategory() {
        return indexOfCategory;
    }

    public void setIndexOfCategory(final int indexOfCategory) {
        this.indexOfCategory = indexOfCategory;
    }

    public int getIndexOfDay() {
        return indexOfDay;
    }

    public void setIndexOfDay(final int indexOfDay) {
        this.indexOfDay = indexOfDay;
    }


    public int getIndexOfGeoLatFull() {
        return indexOfGeoLatFull;
    }

    public void setIndexOfGeoLatFull(final int indexOfGeoLatFull) {
        this.indexOfGeoLatFull = indexOfGeoLatFull;
    }

    public int getIndexOfGeoLongFull() {
        return indexOfGeoLongFull;
    }

    public void setIndexOfGeoLongFull(final int indexOfGeoLongFull) {
        this.indexOfGeoLongFull = indexOfGeoLongFull;
    }

    public int getIndexOfHour() {
        return indexOfHour;
    }

    public void setIndexOfHour(final int indexOfHour) {
        this.indexOfHour = indexOfHour;
    }

    public int getIndexOfId() {
        return indexOfId;
    }

    public void setIndexOfId(final int id) {
        indexOfId = id;
    }

    public int getIndexOfMinute() {
        return indexOfMinute;
    }

    public void setIndexOfMinute(final int indexOfMinute) {
        this.indexOfMinute = indexOfMinute;
    }

    public int getIndexOfMonth() {
        return indexOfMonth;
    }

    public void setIndexOfMonth(final int indexOfMonth) {
        this.indexOfMonth = indexOfMonth;
    }


    public int getIndexOfSecond() {
        return indexOfSecond;
    }

    public void setIndexOfSecond(final int indexOfSecond) {
        this.indexOfSecond = indexOfSecond;
    }


    public int getIndexOfYear() {
        return indexOfYear;
    }

    public void setIndexOfYear(final int indexOfYear) {
        this.indexOfYear = indexOfYear;
    }

    public int getNrOfHeaderLinesToSkip() {
        return nrOfHeaderLinesToSkip;
    }

    public void setNrOfHeaderLinesToSkip(final int nrOfHeaderLinesToSkip) {
        this.nrOfHeaderLinesToSkip = nrOfHeaderLinesToSkip;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(final String separator) {
        this.separator = separator;
    }
}
