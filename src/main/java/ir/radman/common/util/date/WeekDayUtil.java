package ir.radman.common.util.date;

import ir.radman.common.general.enumeration.DayOfWeek;
import ir.radman.common.util.primitive.StringUtility;

import java.time.LocalDate;

public class WeekDayUtil {

    private WeekDayUtil() {
    }

    public static DayOfWeek getDayOfWeek(int year, int month, int day) {
        java.time.DayOfWeek dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek();
        return DayOfWeek.getByValue(dayOfWeek.toString());
    }

    public static DayOfWeek getDayOfWeek() {
        DateTime dateTime = CalendarUtil.currentGeorgianDateTime();
        return getDayOfWeek(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
    }

    public static String getDayOfWeekToString(int year, int month, int day) {
        return StringUtility.toPascalCase(getDayOfWeek(year, month, day).getValue());
    }

    public static String getDayOfWeekToString() {
        return StringUtility.toPascalCase(getDayOfWeek().getValue());
    }

    public static String getDayOfWeekToPersian() {
        return getDayOfWeek().getPersian();
    }

    public static String getDayOfWeekToPersian(int year, int month, int day) {
        return getDayOfWeek(year, month, day).getPersian();
    }

}
