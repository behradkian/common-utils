package ir.radman.common.util.date;

public class PersianCalendarUtils {
    public PersianCalendarUtils() {
    }

    public static long persianToJulian(long year, int month, int day) {
        return 365L * (ceil((double)(year - 474L), 2820.0D) + 474L - 1L) + (long)Math.floor((double)(682L * (ceil((double)(year - 474L), 2820.0D) + 474L) - 110L) / 2816.0D) + 1948320L + 1029983L * (long)Math.floor((double)(year - 474L) / 2820.0D) + (long)(month < 7 ? 31 * month : 30 * month + 6) + (long)day;
    }

    public static boolean isPersianLeapYear(int persianYear) {
        return ceil((38.0D + (double)(ceil((double)((long)persianYear - 474L), 2820.0D) + 474L)) * 682.0D, 2816.0D) < 682L;
    }

    public static long julianToPersian(long julianDate) {
        long persianEpochInJulian = julianDate - persianToJulian(475L, 0, 1);
        long cyear = ceil((double)persianEpochInJulian, 1029983.0D);
        long ycycle = cyear != 1029982L ? (long)Math.floor((2816.0D * (double)cyear + 1031337.0D) / 1028522.0D) : 2820L;
        long year = 474L + 2820L * (long)Math.floor((double)persianEpochInJulian / 1029983.0D) + ycycle;
        long aux = 1L + julianDate - persianToJulian(year, 0, 1);
        int month = (int)(aux > 186L ? Math.ceil((double)(aux - 6L) / 30.0D) - 1.0D : Math.ceil((double)aux / 31.0D) - 1.0D);
        int day = (int)(julianDate - (persianToJulian(year, month, 1) - 1L));
        return year << 16 | ((long) month << 8) | (long)day;
    }

    public static long ceil(double double1, double double2) {
        return (long)(double1 - double2 * Math.floor(double1 / double2));
    }
}
