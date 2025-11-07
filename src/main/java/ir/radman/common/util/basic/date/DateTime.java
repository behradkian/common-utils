package ir.radman.common.util.basic.date;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.TimeZone;

@Getter
@Setter
public class DateTime implements Serializable, Cloneable, Comparable {

    @Serial
    private static final long serialVersionUID = 0x164276b556118bb5L;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;
    private TimeZone timeZone;

    public DateTime() {
    }

    public DateTime(int year, int month, int day) {
        this(year, month, day, 0, 0, 0, 0, TimeZone.getDefault());
    }

    public DateTime(int year, int month, int day, int hour, int minute) {
        this(year, month, day, hour, minute, 0, 0, TimeZone.getDefault());
    }

    public DateTime(int year, int month, int day, int hour, int minute, int second) {
        this(year, month, day, hour, minute, second, 0, TimeZone.getDefault());
    }

    public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this(year, month, day, hour, minute, second, millisecond, TimeZone.getDefault());
    }

    public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, TimeZone timeZone) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
        if (timeZone == null)
            this.timeZone = TimeZone.getDefault();
        else
            this.timeZone = timeZone;
    }


    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null || getClass() != otherObject.getClass())
            return false;
        DateTime that = (DateTime) otherObject;
        if (year != that.year)
            return false;
        if (month != that.month)
            return false;
        if (day != that.day)
            return false;
        if (hour != that.hour)
            return false;
        if (minute != that.minute)
            return false;
        if (second != that.second)
            return false;
        return timeZone == null || that.timeZone == null || timeZone.getID().equals(that.timeZone.getID());
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        result = 31 * result + second;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%1$04d/%2$02d/%3$02d %4$02d:%5$02d:%6$02d", year, month, day, hour, minute, second);
    }

    @Override
    public int compareTo(Object otherObject) {
        if (this.equals(otherObject))
            return 0;
        else {
            DateTime otherDateTime = (DateTime) otherObject;
            if (this.year > otherDateTime.getYear())
                return 1;
            else if (this.year < otherDateTime.getYear())
                return -1;
            else {
                if (this.month > otherDateTime.getMonth())
                    return 1;
                else if (this.month < otherDateTime.getMonth())
                    return -1;
                else {
                    if (this.day > otherDateTime.getDay())
                        return 1;
                    else if (this.day < otherDateTime.getDay())
                        return -1;
                    else {
                        if (this.hour > otherDateTime.getHour())
                            return 1;
                        else if (this.hour < otherDateTime.getHour())
                            return -1;
                        else {
                            if (this.minute > otherDateTime.getMinute())
                                return 1;
                            else if (this.minute < otherDateTime.getMinute())
                                return -1;
                            else {
                                if (this.second > otherDateTime.getSecond())
                                    return 1;
                                else if (this.second < otherDateTime.getSecond())
                                    return -1;
                                else {
                                    if (this.millisecond > otherDateTime.getMillisecond())
                                        return 1;
                                    else if (this.millisecond < otherDateTime.getMillisecond())
                                        return -1;
                                    else
                                        throw new RuntimeException("could not compare 2 timeZone");


                                }

                            }

                        }

                    }
                }
            }
        }
    }

}
