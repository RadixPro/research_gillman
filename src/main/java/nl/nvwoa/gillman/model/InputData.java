package nl.nvwoa.gillman.model;

public class InputData {
    private String description;
    private double geographicLatitude;
    private double geographicLongitude;
    private String groupId;
    private String groupMemberType;
    private String id;
    private String name;
    private String originalText;
    private SimpleDateTime simpleDateTime;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public double getGeographicLatitude() {
        return geographicLatitude;
    }

    public void setGeographicLatitude(final double latitude) {
        geographicLatitude = latitude;
    }

    public double getGeographicLongitude() {
        return geographicLongitude;
    }

    public void setGeographicLongitude(final double longitude) {
        geographicLongitude = longitude;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getGroupMemberType() {
        return groupMemberType;
    }

    public void setGroupMemberType(final String memberType) {
        groupMemberType = memberType;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(final String text) {
        originalText = text;
    }

    public SimpleDateTime getSimpleDateTime() {
        return simpleDateTime;
    }

    public void setSimpleDateTime(final SimpleDateTime simpleDateTime) {
        this.simpleDateTime = simpleDateTime;
    }
}
