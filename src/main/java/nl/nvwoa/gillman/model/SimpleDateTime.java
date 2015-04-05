package nl.nvwoa.gillman.model;

public class SimpleDateTime {
    private Calendar calendar;
    private int day;
    private int month;
    private double ut;
    private int year;

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }

    public int getDay() {
        return day;
    }

    public void setDay(final int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public double getUt() {
        return ut;
    }

    public void setUt(final double ut) {
        this.ut = ut;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }
}