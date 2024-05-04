package ir.baarmaan.utility.date;

import ir.baarmaan.utility.primitive.StringUtility;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_ZONE = "IRST";

    private DateConverter() {
    }

    private static SimpleDateFormat getSimpleDate(){
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    }

    public static SimpleDateFormat getSimpleDate(String formatDate) {
        if(StringUtility.isBlank(formatDate))
            return getSimpleDate();

        return new SimpleDateFormat(formatDate);
    }

    public static String convertDateToPersian(Date date, String formatDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = getSimpleDate(formatDate);
        String formattedDate = simpleDateFormat.format(date);
        String persianDateString = convertDateToPersian(formattedDate,formatDate);
        return convertToJsonDate(persianDateString);
    }

    public static String convertDateToPersian(Date date) throws ParseException {
        return convertDateToPersian(date, DEFAULT_DATE_FORMAT);
    }

    public static String convertDateToPersian(String date, String formatDate) throws ParseException {
        SimpleDateFormat simpleDate = getSimpleDate(formatDate);
        simpleDate.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        Date dateObject = simpleDate.parse(date);
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setDelimiter("-");
        persianCalendar.setTime(dateObject);
        return persianCalendar.getPersianShortDate();
    }

    public static String convertDateToPersian(String date) throws ParseException {
        return convertDateToPersian(date, DEFAULT_DATE_FORMAT);
    }

    private static String convertToJsonDate(String s) {
        String[] split = s.split("-");
        String year = StringUtils.leftPad(split[0], 4, "0");
        String month = StringUtils.leftPad(split[1], 2, "0");
        String day = StringUtils.leftPad(split[2], 2, "0");
        return year + "-" + month + "-" + day;
    }


    public static Date milliSecondsToGregorianDate(int milliseconds) {
        return new Date(milliseconds);
    }

    public static Date jalaliDateStringWithoutFormatToGregorian(String jalaliDateString) {
        return new Date();
    }

}
