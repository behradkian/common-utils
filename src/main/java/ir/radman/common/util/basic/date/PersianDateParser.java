package ir.radman.common.util.basic.date;

public class PersianDateParser {
    private String dateString;
    private String delimiter;

    public PersianDateParser(String dateString) {
        this.delimiter = "/";
        this.dateString = dateString;
    }

    public PersianDateParser(String dateString, String delimiter) {
        this(dateString);
        this.delimiter = delimiter;
    }

    public PersianCalendar getPersianDate() {
        this.checkDateStringInitialValidation();
        String[] tokens = this.splitDateString(this.normalizeDateString(this.dateString));
        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);
        this.checkPersianDateValidation(year, month, day);
        PersianCalendar pCal = new PersianCalendar();
        pCal.setPersianDate(year, month, day);
        return pCal;
    }

    private void checkPersianDateValidation(int year, int month, int day) {
        if (year < 1) {
            throw new RuntimeException("year is not valid");
        } else if (month >= 1 && month <= 12) {
            if (day >= 1 && day <= 31) {
                if (month > 6 && day == 31) {
                    throw new RuntimeException("day is not valid");
                } else if (month == 12 && day == 30 && !PersianCalendarUtils.isPersianLeapYear(year)) {
                    throw new RuntimeException("day is not valid " + year + " is not a leap year");
                }
            } else {
                throw new RuntimeException("day is not valid");
            }
        } else {
            throw new RuntimeException("month is not valid");
        }
    }

    private String normalizeDateString(String dateString) {
        return dateString;
    }

    private String[] splitDateString(String dateString) {
        String[] tokens = dateString.split(this.delimiter);
        if (tokens.length != 3) {
            throw new RuntimeException("wrong date:" + dateString + " is not a Persian Date or can not be parsed");
        } else {
            return tokens;
        }
    }

    private void checkDateStringInitialValidation() {
        if (this.dateString == null) {
            throw new RuntimeException("input didn't assing please use setDateString()");
        }
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
