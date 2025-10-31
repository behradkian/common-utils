package ir.radman.common.util.date;

import ir.radman.common.general.enumeration.PersianMonthType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class JalaliCalendar extends Calendar {

    private static final long serialVersionUID = 0x8dddd79bbf54f43L;
    private static final int TSN_NATURAL = 0;
    private static final int TSN_KABISE = 1;
    private static final int Khayyam_Table[] = {
            5, 9, 13, 17, 21, 25, 29, 34, 38, 42,
            46, 50, 54, 58, 62, 67, 71, 75, 79, 83,
            87, 91, 95, 100, 104, 108, 112, 116, 120, 124
    };
    private static final TimeZone GMT_TIME_ZONE = TimeZone.getDefault();
    private static final byte gregorianDaysInMonth[] = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31
    };
    private static final byte jalaliDaysInMonth[] = {
            31, 31, 31, 31, 31, 31, 30, 30, 30, 30,
            30, 29
    };
    private TimeZone desTimeZone;
    private TimeZone eraTimeZone;
    private boolean calcOverflow;
    private boolean computeDesTimeZone;
    private boolean isCalledConstructor;
    private boolean isDateTime;

    public JalaliCalendar() {
        this(Calendar.getInstance(GMT_TIME_ZONE));
    }

    public JalaliCalendar(DateTime jalaliDate) {
        this(jalaliDate, jalaliDate.getTimeZone(), TimeZone.getDefault());
    }

    public JalaliCalendar(DateTime jalaliDate, TimeZone desTimeZone) {
        this(jalaliDate, jalaliDate.getTimeZone(), desTimeZone);
    }

    private JalaliCalendar(DateTime jalaliDate, TimeZone eraTimeZone, TimeZone desTimeZone) {
        isCalledConstructor = false;
        isDateTime = false;
        isDateTime = true;
        fields[1] = jalaliDate.getYear();
        fields[2] = jalaliDate.getMonth();
        fields[5] = jalaliDate.getDay();
        fields[11] = jalaliDate.getHour();
        fields[12] = jalaliDate.getMinute();
        fields[13] = jalaliDate.getSecond();
        fields[14] = jalaliDate.getMillisecond();
        this.eraTimeZone = eraTimeZone;
        if (desTimeZone == null || desTimeZone.equals(""))
            this.desTimeZone = TimeZone.getDefault();
        else
            this.desTimeZone = desTimeZone;
        if (fields[13] > 59) {
            int remainedSecond = fields[13] - 59;
            fields[13] = 59;
            add(13, remainedSecond);
        }
        if (fields[12] > 59) {
            int remainedMinute = fields[12] - 59;
            fields[12] = 59;
            add(12, remainedMinute);
        }
        if (fields[11] > 24) {
            int remainedHour = fields[11] - 24;
            fields[11] = 24;
            add(11, remainedHour);
        }
        int baseDay = fields[5];
        if (fields[2] > 12) {
            int remainedMonth = fields[2] - 12;
            fields[2] = 12;
            add(2, remainedMonth);
        }
        if (fields[2] >= 1 && fields[2] <= 6) {
            if (baseDay > 31)
                add(5, baseDay - 31);
        } else if (fields[2] > 6 && fields[2] <= 11) {
            if (baseDay > 30)
                add(5, baseDay - 30);
        } else if (fields[2] == 12)
            if (shamsiKabise(fields[1]) == 1) {
                if (baseDay > 30)
                    add(5, baseDay - 30);
            } else if (baseDay > 29)
                add(5, baseDay - 29);
        fields[10] = fields[11];
        isTimeSet = false;
        computeDesTimeZone = true;
    }

    public JalaliCalendar(Date date) {
        this(date, TimeZone.getDefault());
    }

    public JalaliCalendar(Date date, TimeZone desTimeZone) {
        this(date, TimeZone.getDefault(), desTimeZone);
    }

    public JalaliCalendar(Date date, TimeZone eraTimeZone, TimeZone desTimeZone) {
        isCalledConstructor = false;
        isDateTime = false;
        isDateTime = false;
        isCalledConstructor = true;
        if (eraTimeZone == null || eraTimeZone.equals(""))
            this.eraTimeZone = TimeZone.getDefault();
        else
            this.eraTimeZone = eraTimeZone;
        if (desTimeZone == null || desTimeZone.equals(""))
            this.desTimeZone = TimeZone.getDefault();
        else
            this.desTimeZone = desTimeZone;
        setTime(date);
    }

    public JalaliCalendar(Calendar calendar) {
        this(calendar.getTime());
    }

    public JalaliCalendar(Calendar calendar, TimeZone desTimeZone) {
        this(calendar.getTime(), desTimeZone);
    }

    private void getDateTime() {
        if (!isDateTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(fields[1], fields[2], fields[5], fields[11], fields[12], fields[13]);
            Calendar resultCalendar;
            if (computeDesTimeZone)
                resultCalendar = Calendar.getInstance();
            else
                resultCalendar = Calendar.getInstance(desTimeZone);
            resultCalendar.setTime(calendar.getTime());
            convert(resultCalendar);
            isDateTime = true;
            setTimeZone(desTimeZone);
        }
    }

    private Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        if (fields[1] == 0 || fields[2] == 0 || fields[5] == 0) {
            if (fields[1] == 0)
                fields[1] = getMinimum(1);
            if (fields[2] == 0)
                fields[2] = 1;
            if (fields[5] == 0)
                fields[5] = 1;
        }
        int result[] = jalaliToGregorian(new int[]{
                fields[1], fields[2], fields[5]
        });
        calendar.set(result[0], result[1] - 1, result[2], fields[11], fields[12], fields[13]);
        calendar.setTimeZone(eraTimeZone);
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd KK:mm:ss a zzz yyyy");
        TimeZone tz = desTimeZone;
        format.setTimeZone(tz);
        format.format(calendar.getTime());
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.set(format.getCalendar().get(1), format.getCalendar().get(2), format.getCalendar().get(5), format.getCalendar().get(11), format.getCalendar().get(12), format.getCalendar().get(13));
        Calendar correctTZCalendar = Calendar.getInstance(desTimeZone);
        correctTZCalendar.setTime(resultCalendar.getTime());
        if (fields[9] == 0)
            correctTZCalendar.clear(9);
        if (fields[10] == 0)
            correctTZCalendar.clear(10);
        if (fields[11] == 0)
            correctTZCalendar.clear(11);
        if (fields[12] == 0)
            correctTZCalendar.clear(12);
        if (fields[13] == 0)
            correctTZCalendar.clear(13);
        if (fields[14] == 0)
            correctTZCalendar.clear(14);
        computeDesTimeZone = true;
        return correctTZCalendar;
    }

    private void convert(Calendar calendar) {
        int jalali[] = gregorianToJalali(new int[]{
                calendar.get(1), calendar.get(2) + 1, calendar.get(5)
        });
        fields[1] = jalali[0];
        fields[2] = jalali[1];
        fields[5] = jalali[2];
        fields[9] = calendar.get(9);
        fields[10] = calendar.get(10);
        fields[11] = calendar.get(11);
        fields[12] = calendar.get(12);
        fields[13] = calendar.get(13);
        if (fields[14] != 0)
            fields[14] = calendar.get(14);
    }

    private int[] gregorianToJalali(int g[]) {
        int gy = g[0] - 1600;
        int gm = g[1] - 1;
        int gd = g[2] - 1;
        int gDays = ((365 * gy + (gy + 3) / 4) - (gy + 99) / 100) + (gy + 399) / 400;
        int i;
        for (i = 0; i < gm; i++)
            gDays += gregorianDaysInMonth[i];

        if (gm > 1 && (gy % 4 == 0 && gy % 100 != 0 || gy % 400 == 0))
            gDays++;
        gDays += gd;
        int jDays = gDays - 79;
        int jNp = jDays / 12053;
        jDays %= 12053;
        int jy = 979 + 33 * jNp + 4 * (jDays / 1461);
        jDays %= 1461;
        if (jDays >= 366) {
            jy += (jDays - 1) / 365;
            jDays = (jDays - 1) % 365;
        }
        fields[6] = jDays + 1;
        for (i = 0; i < 11 && jDays >= jalaliDaysInMonth[i]; i++)
            jDays -= jalaliDaysInMonth[i];

        int jm = i + 1;
        int jd = jDays + 1;
        return (new int[]{
                jy, jm, jd
        });
    }

    private int[] jalaliToGregorian(int j[]) {
        int jy = j[0] - 979;
        int jm = j[1] - 1;
        int jd = j[2] - 1;
        int jDays = 365 * jy + (jy / 33) * 8 + (jy % 33 + 3) / 4;
        int i;
        for (i = 0; i < jm; i++)
            jDays += jalaliDaysInMonth[i];

        jDays += jd;
        int gDays = jDays + 79;
        int gy = 1600 + 400 * (gDays / 0x23ab1);
        gDays %= 0x23ab1;
        int leap = 1;
        if (gDays >= 36525) {
            gDays--;
            gy += 100 * (gDays / 36524);
            gDays %= 36524;
            if (gDays >= 365)
                gDays++;
            else
                leap = 0;
        }
        gy += 4 * (gDays / 1461);
        gDays %= 1461;
        if (gDays >= 366) {
            leap = 0;
            gDays--;
            gy += gDays / 365;
            gDays %= 365;
        }
        for (i = 0; gDays >= gregorianDaysInMonth[i] + boolToInt(i == 1 && leap == 1); i++)
            gDays -= gregorianDaysInMonth[i] + boolToInt(i == 1 && leap == 1);

        int gm = i + 1;
        int gd = gDays + 1;
        return (new int[]{
                gy, gm, gd
        });
    }

    private int boolToInt(boolean b) {
        return !b ? 0 : 1;
    }

    private void increment(int iYear, int iMonth, int iDay, boolean calcOverflow) {
        if (!isDateTime) {
            getDateTime();
            isDateTime = true;
        }
        fields[1] += iYear;
        fields[2] += iMonth;
        fields[1] += fields[2] % 12 != 0 ? fields[2] / 12 : fields[2] / 12 - 1;
        fields[2] = fields[2] % 12 != 0 ? fields[2] % 12 : fields[2] % 12 + 12;
        if (!calcOverflow && fields[5] > daysOfMonth(fields[1], fields[2]))
            fields[5] = daysOfMonth(fields[1], fields[2]);
        fields[5] += iDay;
        do {
            if (fields[5] <= daysOfMonth(fields[1], fields[2]))
                break;
            fields[5] -= daysOfMonth(fields[1], fields[2]);
            fields[2]++;
            if (fields[2] > 12) {
                fields[1]++;
                fields[2] = 1;
            }
        } while (true);
    }

    private void decrement(int dYear, int dMonth, int dDay) {
        if (!isDateTime) {
            getDateTime();
            isDateTime = true;
        }
        boolean bMnthLastDay = false;
        if (fields[5] == daysOfMonth(fields[1], fields[2]))
            bMnthLastDay = true;
        fields[1] -= dYear;
        fields[2] -= dMonth;
        fields[1] -= fields[2] % 12 >= 1 ? -(fields[2] / 12) : -(fields[2] / 12) + 1;
        fields[2] = fields[2] % 12 >= 1 ? fields[2] % 12 : fields[2] % 12 + 12;
        if (fields[5] > daysOfMonth(fields[1], fields[2]) || bMnthLastDay)
            fields[5] = daysOfMonth(fields[1], fields[2]);
        for (; dDay >= fields[5]; fields[5] = daysOfMonth(fields[1], fields[2])) {
            dDay -= fields[5];
            if (--fields[2] < 1) {
                fields[1]--;
                fields[2] = 12;
            }
        }

        fields[5] -= dDay;
    }

    private int daysOfMonth(int aYear, int aMonth) {
        if (0 >= aMonth || aMonth >= 13)
            throw new RuntimeException("ERROR INVALID MONTH");
        else
            return aMonth > 6 ? aMonth >= 12 ? ((byte) (shamsiKabise(aYear) != 0 ? 30 : 29)) : 30 : 31;
    }

    private int shamsiKabise(int i_iyear) {
        int dd;
        if (i_iyear >= 474) {
            dd = (i_iyear - 474) % 128;
            if (dd == 0)
                return 1;
        } else {
            dd = i_iyear < 342 ? 128 - (374 - i_iyear) % 128 : i_iyear - 342;
        }
        for (int i = 0; i < 30; i++)
            if (Khayyam_Table[i] == dd)
                return 1;

        return 0;
    }

    public int get(int field) {
        setTimeZone(desTimeZone);
        if (!isDateTime)
            getDateTime();
        complete();
        switch (field) {
            case 1: // '\001'
                return fields[1];

            case 2: // '\002'
                return fields[2] - 1;

            case 5: // '\005'
                return fields[5];

            case 6: // '\006'
                return fields[6];

            case 7: // '\007'
                return fields[7];

            case 10: // '\n'
                return fields[11] != 0 ? fields[11] != 12 ? fields[11] <= 12 ? fields[11] : fields[11] % 12 : 0 : 0;

            case 11: // '\013'
                return fields[11] != 0 ? fields[11] != 24 ? fields[11] <= 24 ? fields[11] : fields[11] % 24 : 0 : 0;

            case 12: // '\f'
                return fields[12];

            case 13: // '\r'
                return fields[13];

            case 14: // '\016'
                return fields[14];

            case 9: // '\t'
                if (fields[11] >= 0 && fields[11] < 12)
                    return fields[9] != 0 ? 1 : 0;
                if (fields[11] >= 12 && fields[11] <= 23)
                    return 1;
                // fall through

            case 15: // '\017'
                return fields[15];

            case 16: // '\020'
                return fields[16];

            case 3: // '\003'
            case 4: // '\004'
            case 8: // '\b'
            default:
                return 0;
        }
    }

    public void set(int field, int value) {
        setTimeZone(desTimeZone);
        switch (field) {
            case 1: // '\001'
                fields[1] = value;
                break;

            case 2: // '\002'
                fields[2] = value + 1;
                break;

            case 5: // '\005'
                fields[5] = value;
                break;

            case 10: // '\n'
                fields[10] = value != 0 ? value != 24 ? value <= 24 ? value : value % 24 : 0 : 0;
                fields[11] = value != 0 ? value != 24 ? value <= 24 ? value : value % 24 : 0 : 0;
                break;

            case 11: // '\013'
                fields[10] = value != 0 ? value != 24 ? value <= 24 ? value : value % 24 : 0 : 0;
                fields[11] = value != 0 ? value != 24 ? value <= 24 ? value : value % 24 : 0 : 0;
                break;

            case 12: // '\f'
                fields[12] = value;
                break;

            case 13: // '\r'
                fields[13] = value;
                fields[13] = value;
                break;

            case 14: // '\016'
                fields[14] = value;
                break;

            case 9: // '\t'
                fields[9] = value != 0 ? 1 : 0;
                break;
        }
        isTimeSet = false;
    }

    public void add(int field, int amount) {
        boolean calcOverflow = false;
        if (isCalcOverflow())
            calcOverflow = true;
        switch (field) {
            case 3: // '\003'
            case 4: // '\004'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
            case 9: // '\t'
            default:
                break;

            case 1: // '\001'
                if (amount >= 0)
                    increment(amount, 0, 0, calcOverflow);
                else
                    decrement(-amount, 0, 0);
                break;

            case 2: // '\002'
                if (amount >= 0)
                    increment(0, amount, 0, calcOverflow);
                else
                    decrement(0, -amount, 0);
                break;

            case 5: // '\005'
                if (amount >= 0)
                    increment(0, 0, amount, calcOverflow);
                else
                    decrement(0, 0, -amount);
                break;

            case 10: // '\n'
                fields[11] += amount;
                fields[10] += amount;
                break;

            case 11: // '\013'
                fields[11] += amount;
                fields[10] += amount;
                break;

            case 12: // '\f'
                fields[12] += amount;
                break;

            case 13: // '\r'
                fields[13] += amount;
                break;
        }
        isTimeSet = false;
    }

    protected void computeFields() {
        Calendar calendar = Calendar.getInstance(eraTimeZone);
        calendar.setTime(new Date(time));
        if (isCalledConstructor) {
            fields[1] = calendar.get(1);
            fields[2] = calendar.get(2);
            fields[5] = calendar.get(5);
            fields[6] = calendar.get(6);
            fields[7] = calendar.get(7);
            fields[9] = calendar.get(9);
            fields[11] = calendar.get(11);
            fields[12] = calendar.get(12);
            fields[13] = calendar.get(13);
            fields[14] = calendar.get(14);
            fields[15] = calendar.get(15);
            fields[16] = calendar.get(16);
            isCalledConstructor = false;
        } else {
            fields[1] = fields[1] != 0 ? calendar.get(1) : getMinimum(1);
            fields[2] = fields[2] != 0 ? calendar.get(2) : 0;
            fields[5] = fields[5] != 0 ? calendar.get(5) : 0;
            fields[6] = calendar.get(6);
            fields[7] = calendar.get(7);
            fields[9] = calendar.get(9);
            fields[11] = calendar.get(11);
            fields[12] = calendar.get(12);
            fields[13] = calendar.get(13);
            fields[14] = calendar.get(14);
            fields[15] = calendar.get(15);
            fields[16] = calendar.get(16);
        }
        isDateTime = false;
        getDateTime();
    }

    protected void computeTime() {
        time = getCalendar().getTime().getTime();
    }

    public int getGreatestMinimum(int field) {
        return getMinimum(field);
    }

    public int getLeastMaximum(int field) {
        return getMaximum(field);
    }

    public int getMaximum(int field) {
        if (!isDateTime)
            getDateTime();
        switch (field) {
            case 1: // '\001'
                return 1468;
        }
        return Calendar.getInstance().getMaximum(field);
    }

    public int getMinimum(int field) {
        if (!isDateTime)
            getDateTime();
        switch (field) {
            case 1: // '\001'
                return 1304;
        }
        return Calendar.getInstance().getMinimum(field);
    }

    public void roll(int field, boolean up) {
        if (!isDateTime) {
            getDateTime();
            isDateTime = true;
        }
        switch (field) {
            case 3: // '\003'
            case 4: // '\004'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
            case 9: // '\t'
            default:
                break;

            case 1: // '\001'
            {
                fields[1] += up ? 1 : -1;
                int kabisse = shamsiKabise(fields[1]);
                if (kabisse == 0 && fields[2] == 12 && fields[5] >= 30)
                    fields[5] = 29;
                if (kabisse == 1 && fields[2] == 12 && fields[5] >= 30)
                    fields[5] = 30;
                break;
            }

            case 2: // '\002'
            {
                fields[2] += up ? 1 : -1;
                if (fields[2] > 12)
                    fields[2] = 1;
                else if (fields[2] < 1)
                    fields[2] = 12;
                int kabisse = shamsiKabise(fields[1]);
                if (fields[2] <= 6 || fields[2] > 12)
                    break;
                if (fields[2] == 12) {
                    if (kabisse == 0) {
                        if (fields[5] > 29)
                            fields[5] = 29;
                        break;
                    }
                    if (kabisse == 1 && fields[5] > 30)
                        fields[5] = 30;
                    break;
                }
                if (fields[5] > 30)
                    fields[5] = 30;
                break;
            }

            case 5: // '\005'
            {
                fields[5] += up ? 1 : -1;
                int kabisse = shamsiKabise(fields[1]);
                if (fields[2] >= 1 && fields[2] <= 6) {
                    if (fields[5] < 1) {
                        fields[5] = 31;
                        break;
                    }
                    if (fields[5] > 31)
                        fields[5] = 1;
                    break;
                }
                if (fields[2] <= 6 || fields[2] > 12)
                    break;
                if (fields[2] == 12) {
                    if (kabisse == 0) {
                        if (fields[5] > 29) {
                            fields[5] = 1;
                            break;
                        }
                        if (fields[5] < 1)
                            fields[5] = 29;
                        break;
                    }
                    if (kabisse != 1)
                        break;
                    if (fields[5] > 30) {
                        fields[5] = 1;
                        break;
                    }
                    if (fields[5] < 1)
                        fields[5] = 30;
                    break;
                }
                if (fields[5] > 30) {
                    fields[5] = 1;
                    break;
                }
                if (fields[5] < 1)
                    fields[5] = 30;
                break;
            }

            case 10: // '\n'
            {
                fields[11] += up ? 1 : -1;
                if (fields[11] > 23) {
                    fields[11] = 0;
                    break;
                }
                if (fields[11] < 0)
                    fields[11] = 23;
                break;
            }

            case 11: // '\013'
            {
                fields[11] += up ? 1 : -1;
                if (fields[11] > 23) {
                    fields[11] = 0;
                    break;
                }
                if (fields[11] < 0)
                    fields[11] = 23;
                break;
            }

            case 12: // '\f'
            {
                fields[12] += up ? 1 : -1;
                if (fields[12] == 60) {
                    fields[12] = 0;
                    break;
                }
                if (fields[12] == -1)
                    fields[12] = 59;
                break;
            }

            case 13: // '\r'
            {
                fields[13] += up ? 1 : -1;
                if (fields[13] == 60) {
                    fields[13] = 0;
                    break;
                }
                if (fields[13] == -1)
                    fields[13] = 59;
                break;
            }
        }
        fields[10] = fields[11];
    }

    public TimeZone getTimeZone() {
        if (computeDesTimeZone)
            return desTimeZone;
        else
            return super.getTimeZone();
    }

    public boolean isCalcOverflow() {
        return calcOverflow;
    }

    public void setCalcOverflow(boolean calcOverflow) {
        this.calcOverflow = calcOverflow;
    }

    public int getFirstDayOfWeek() {
        return 7;
    }

    public int getDay(){
        return CalendarUtil.getDateTime(this).getDay();
    }

    public int getMonth(){
        return CalendarUtil.getDateTime(this).getMonth();
    }

    public String getMonthName(){
        return PersianMonthType.getById(CalendarUtil.getDateTime(this).getMonth()).getValue();
    }

    public int getYear(){
        return CalendarUtil.getDateTime(this).getYear();
    }

}
