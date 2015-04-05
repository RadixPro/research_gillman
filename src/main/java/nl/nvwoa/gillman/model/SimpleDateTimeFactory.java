package nl.nvwoa.gillman.model;

public class SimpleDateTimeFactory {
    private static final double MINUTES_PER_HOUR = 60.0;
    private static final double SECONDS_PER_HOUR = 3600.0;

    /**
     * Always uses a Gregorian calendar (no other calendars in the Gillman research).
     */
    public static SimpleDateTime getSimpleDateTime(final String year, final String month, final String day, final String hour, final String minute, final String second) {
        SimpleDateTime simpleDateTime = new SimpleDateTime();
        simpleDateTime.setCalendar(Calendar.GREGORIAN);
        simpleDateTime.setYear(Integer.parseInt(year));
        simpleDateTime.setMonth(Integer.parseInt(month));
        simpleDateTime.setDay(Integer.parseInt(day));
        double ut = Double.parseDouble(hour) +
                (Double.parseDouble(minute) / MINUTES_PER_HOUR) +
                (Double.parseDouble(second) / SECONDS_PER_HOUR);
        simpleDateTime.setUt(ut);
        return simpleDateTime;
    }
}
