package nl.nvwoa.gillman.util;

import nl.nvwoa.gillman.model.Calendar;
import nl.nvwoa.gillman.model.SimpleDateTime;
import org.json.simple.JSONObject;
import swisseph.SweDate;

public class Conversions {
    private static final double MINUTES_PER_HOUR_DEGREE = 60.0;
    private static final double SECONDS_PER_HOUR_DEGREE = 3600.0;

    /**
     * Constructs SimpleDateTimeObject from JSONObject with content like the following:
     * <p>
     * <pre>
     * "simpleDateTime" : {
     * "calendar" : "AUTOMATIC",
     * "day" : 7,
     * "month" : 9,
     * "ut" : 14.844444444444445,
     * "year" : 1905
     * </pre>
     *
     * @param jsonSimpleDateTime
     * @return
     */
    public static SimpleDateTime constructSimpleDateTime(final JSONObject jsonSimpleDateTime) {
        final SimpleDateTime simpleDateTime = new SimpleDateTime();
        simpleDateTime.setYear((int) ((long) jsonSimpleDateTime.get("year")));
        simpleDateTime.setMonth((int) ((long) jsonSimpleDateTime.get("month")));
        simpleDateTime.setDay((int) ((long) jsonSimpleDateTime.get("day")));
        simpleDateTime.setUt((Double) jsonSimpleDateTime.get("ut"));
        simpleDateTime.setCalendar(Calendar.AUTOMATIC);
        return simpleDateTime;
    }

    public static double convertGeoLat(final String geoLatTxt) {
        final String geoLatUpCase = geoLatTxt.toUpperCase().trim().replace(" ", "0");
        int posNeg = 1;
        int pos = geoLatUpCase.indexOf("N");
        if (pos < 0) {
            pos = geoLatUpCase.indexOf("S");
            posNeg = -1;
        }
        return constructDoubleFromLatLong(geoLatUpCase, pos, posNeg);
    }

    public static double convertGeoLong(final String geoLongTxt) {
        final String geoLongUpCase = geoLongTxt.toUpperCase().trim().replace(" ", "0");
        int posNeg = 1;
        int pos = geoLongUpCase.indexOf("E");
        if (pos < 0) {
            pos = geoLongUpCase.indexOf("W");
            posNeg = -1;
        }
        return constructDoubleFromLatLong(geoLongUpCase, pos, posNeg);
    }


    public static SweDate simpleDateTime2SweDate(final SimpleDateTime simpleDateTime) {
        final int year = simpleDateTime.getYear();
        final int month = simpleDateTime.getMonth();
        final int day = simpleDateTime.getDay();
        final double ut = simpleDateTime.getUt();
        return new SweDate(year, month, day, ut);
    }

    private static double constructDoubleFromLatLong(final String inText, final int pos, final int posNeg) {
        final String degreeTxt = inText.substring(0, pos);
        final String minuteTxt = inText.substring(pos + 1);
        final int degrees = Integer.parseInt(degreeTxt);
        final int minutes = Integer.parseInt(minuteTxt);
        return (degrees + (minutes / MINUTES_PER_HOUR_DEGREE)) * posNeg;
    }
}
