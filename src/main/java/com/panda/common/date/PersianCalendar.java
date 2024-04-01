package com.panda.common.date;

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class PersianCalendar extends GregorianCalendar {

    private static final long serialVersionUID = 5541422440580682494L;
    private int persianYear;
    private int persianMonth;
    private int persianDay;
    private String delimiter = "/";

    private long convertToMilliSeconds(long julianDate) {
        return -210866803200000L + julianDate * 86400000L + PersianCalendarUtils.ceil((double)(this.getTimeInMillis() - -210866803200000L), 8.64E7D);
    }

    public PersianCalendar() {
        this.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    protected void calculatePersianDate() {
        long julianDate = (long)Math.floor((double)(this.getTimeInMillis() - -210866803200000L)) / 86400000L;
        long PersianRowDate = PersianCalendarUtils.julianToPersian(julianDate);
        long year = PersianRowDate >> 16;
        int month = (int)(PersianRowDate & 65280L) >> 8;
        int day = (int)(PersianRowDate & 255L);
        this.persianYear = (int)(year > 0L ? year : year - 1L);
        this.persianMonth = month;
        this.persianDay = day;
    }

    public boolean isPersianLeapYear() {
        return PersianCalendarUtils.isPersianLeapYear(this.persianYear);
    }

    public void setPersianDate(int persianYear, int persianMonth, int persianDay) {
        this.persianYear = persianYear;
        this.persianMonth = persianMonth;
        this.persianDay = persianDay;
        this.setTimeInMillis(this.convertToMilliSeconds(PersianCalendarUtils.persianToJulian((long)(this.persianYear > 0 ? this.persianYear : this.persianYear + 1), this.persianMonth - 1, this.persianDay)));
    }

    public int getPersianYear() {
        return this.persianYear;
    }

    public int getPersianMonth() {
        return this.persianMonth + 1;
    }

    public String getPersianMonthName() {
        return PersianCalendarConstants.persianMonthNames[this.persianMonth];
    }

    public int getPersianDay() {
        return this.persianDay;
    }

    public String getPersianWeekDayName() {
        switch(this.get(7)) {
            case 1:
                return PersianCalendarConstants.persianWeekDays[1];
            case 2:
                return PersianCalendarConstants.persianWeekDays[2];
            case 3:
                return PersianCalendarConstants.persianWeekDays[3];
            case 4:
                return PersianCalendarConstants.persianWeekDays[4];
            case 5:
                return PersianCalendarConstants.persianWeekDays[5];
            case 6:
            default:
                return PersianCalendarConstants.persianWeekDays[6];
            case 7:
                return PersianCalendarConstants.persianWeekDays[0];
        }
    }

    public String getPersianLongDate() {
        return this.getPersianWeekDayName() + "  " + this.formatToMilitary(this.persianDay) + "  " + this.getPersianMonthName() + "  " + this.persianYear;
    }

    public String getPersianShortDate() {
        return this.formatToMilitary(this.persianYear) + this.delimiter + this.formatToMilitary(this.getPersianMonth()) + this.delimiter + this.formatToMilitary(this.persianDay);
    }

    private String formatToMilitary(int i) {
        return i < 9 ? "0" + i : String.valueOf(i);
    }

    public void addPersianDate(int field, int amount) {
        if (amount != 0) {
            if (field >= 0 && field < 15) {
                if (field == 1) {
                    this.setPersianDate(this.persianYear + amount, this.getPersianMonth(), this.persianDay);
                } else if (field == 2) {
                    this.setPersianDate(this.persianYear + (this.getPersianMonth() + amount) / 12, (this.getPersianMonth() + amount) % 12, this.persianDay);
                } else {
                    this.add(field, amount);
                    this.calculatePersianDate();
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void parse(String dateString) {
        PersianCalendar p = (new PersianDateParser(dateString, this.delimiter)).getPersianDate();
        this.setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String toString() {
        String str = super.toString();
        return str.substring(0, str.length() - 1) + ",PersianDate=" + this.getPersianShortDate() + "]";
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void set(int field, int value) {
        super.set(field, value);
        this.calculatePersianDate();
    }

    public void setTimeInMillis(long millis) {
        super.setTimeInMillis(millis);
        this.calculatePersianDate();
    }

    public void setTimeZone(TimeZone zone) {
        super.setTimeZone(zone);
        this.calculatePersianDate();
    }
}
