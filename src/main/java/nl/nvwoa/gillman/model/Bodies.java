package nl.nvwoa.gillman.model;

/**
 * Enum for Sun Moon and planets.<br/>
 * The keys and values for the enum correspond to the values as used in the Swiss Ephemeris.
 * However, the prefix <i>SE_</i> is removed from the keys.<br/>
 * Not all possibilities are implemented yet.
 */
public enum Bodies {
    SUN(0),
    MOON(1),
    MERCURY(2),
    VENUS(3),
    MARS(4),
    JUPITER(5),
    SATURN(6),
    URANUS(7),
    NEPTUNE(8),
    PLUTO(9);

    private int id;

    private Bodies(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}