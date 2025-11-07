package ir.radman.common.util.basic.date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class DateUtil {
    private static final int Khayyam_Table[] = {
            5, 9, 13, 17, 21, 25, 29, 34, 38, 42,
            46, 50, 54, 58, 62, 67, 71, 75, 79, 83,
            87, 91, 95, 100, 104, 108, 112, 116, 120, 124
    };

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static Date getDateFromString(String inputDate, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(inputDate);
    }

    public static Calendar getCalenderFromString(String date, String pattern) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        calendar.setTime(dateFormat.parse(date));
        return calendar;
    }

    public static String calendarToString(Calendar calendar, String pattern) {
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Calendar getSysDateInCalender() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
    }

    public static Date getSysDateInDate() {
        return getSysDateInCalender().getTime();
    }

    public static String getSysDateInString(String pattern) {
        DateFormat date = new SimpleDateFormat(pattern);
        return date.format(getSysDateInDate());
    }

    public static java.sql.Date convertFromJAVADateToSQLDate(Date utilDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utilDate);
        java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
        return sqlDate;
    }

    public static Date convertFromSQLDateToJAVADate(java.sql.Date sqlDate) {
        Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return javaDate;
    }

    public static Timestamp convertFromJAVADateToSQLTimeStamp(Date utilDate) {
        return new Timestamp(utilDate.getTime());
    }

    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xmlCalendar) {
        return xmlCalendar.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date inputDate) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(inputDate);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static int findYearsBetweenDates(Date newerDate, Date olderDate) {
        Calendar fCal = Calendar.getInstance();
        Calendar sCal = Calendar.getInstance();
        fCal.setTime(olderDate);
        sCal.setTime(newerDate);
        LocalDate fDate = LocalDate.of(fCal.get(Calendar.YEAR), fCal.get(Calendar.MONTH) + 1, fCal.get(Calendar.DAY_OF_MONTH));
        LocalDate sDate = LocalDate.of(sCal.get(Calendar.YEAR), sCal.get(Calendar.MONTH) + 1, sCal.get(Calendar.DAY_OF_MONTH));
        return Period.between(fDate, sDate).getYears();
    }
    /**
     * Returns True if Start date is before End Date.
     * <p>
     * This method always returns immediately, whether or not the
     * 0 if the argument Date is equal to this Date;
     * and a value greater than 0 if this Date is after the Date argument.
     * finally convert to boolean
     *
     * @param  startDate  start Date
     * @param  endDate end Date
     * @return      the result of comparing two dates
     * @see         Date
     */
    public static boolean IsStartBeforeEnd(Date startDate, Date endDate){
        return (startDate.compareTo(endDate) < 0 ? true : false);
    }

}
