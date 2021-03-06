package com.ct.fitorto.utils;

import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Filippo-TheAppExpert on 6/26/2015.
 */
public class DateTimeUtils implements Runnable {

    private TextView mPostDate;
    private String mGivenDate;

    public DateTimeUtils(TextView postDate, String givenDate) {
        mPostDate = postDate;
        mGivenDate = givenDate;
        new Handler().post(this);
    }

    @Override
    public void run() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy");
            Date now = new Date();
            Date date = dateFormat.parse(mGivenDate);

            long dateTimeDifference = now.getTime() - date.getTime();

            long elapsedYears = dateTimeDifference / Format.YEAR.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.YEAR.getDateTime();
            if (elapsedYears > 0) {
                mPostDate.setText(elapsedYears + "y");
                return ;
            }

            long elapsedMonths = dateTimeDifference / Format.MONTH.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.MONTH.getDateTime();
            if (elapsedMonths > 0) {
                mPostDate.setText(elapsedMonths + "m");
                return ;
            }

            long elapsedWeeks = dateTimeDifference / Format.WEEK.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.WEEK.getDateTime();
            if (elapsedWeeks > 0) {
                mPostDate.setText(elapsedWeeks + "w");
                return ;
            }

            long elapsedDays = dateTimeDifference / Format.DAY.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.DAY.getDateTime();
            if (elapsedDays > 0) {
                mPostDate.setText(elapsedDays + "d");
                return ;
            }

            long elapsedHours = dateTimeDifference / Format.HOUR.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.HOUR.getDateTime();
            if (elapsedHours > 0) {
                mPostDate.setText(elapsedHours + "h");
                return ;
            }

            long elapsedMinutes = dateTimeDifference / Format.MINUTE.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.MINUTE.getDateTime();
            if (elapsedMinutes > 0) {
                mPostDate.setText(elapsedMinutes + "m");
                return ;
            }

            long elapsedSeconds = dateTimeDifference / Format.SECOND.getDateTime();
            mPostDate.setText(elapsedSeconds + "s");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public enum Format {

        YEAR(Format.YEARS_MILLI), MONTH(Format.MONTHS_MILLI),
        WEEK(Format.WEEKS_MILLI), DAY(Format.DAYS_MILLI),
        HOUR(Format.HOURS_MILLI), MINUTE(Format.MINUTES_MILLI),
        SECOND(Format.SECONDS_MILLI);

        private static final long SECONDS_MILLI = 1000;
        private static final long MINUTES_MILLI = SECONDS_MILLI * 60;
        private static final long HOURS_MILLI = MINUTES_MILLI * 60;
        private static final long DAYS_MILLI = HOURS_MILLI * 24;
        private static final long WEEKS_MILLI = DAYS_MILLI * 7;
        private static final long MONTHS_MILLI = WEEKS_MILLI * 4;
        private static final long YEARS_MILLI = MONTHS_MILLI * 12;

        private long dateTime;

        Format(long dt) {
            dateTime = dt;
        }

        public long getDateTime() {
            return dateTime;
        }
    }


    public static String formatToYesterdayOrToday(Context context, String date) throws ParseException {
        Date dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        Date now = new Date();
        //String time=DateUtils.getRelativeTimeSpanString(dateTime.getTime(), now, DateUtils.DAY_IN_MILLIS).toString();
        String str = DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                now.getTime(),
                0,
                DateUtils.FORMAT_ABBREV_ALL).toString();
        return str;
    }

    public static String formateDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM yyyy");
        String formatedDate = format.format(newDate);
        return formatedDate;
    }


    public static String formatTime(String time){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("hh:mm a");
        String formatedDate = format.format(newDate);
        return formatedDate;
    }

    public static String formateAgeDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM, yyyy");
        String formatedDate = format.format(newDate);
        return formatedDate;
    }

}
