package ir.awlrhm.calendar.listener;

import ir.awlrhm.calendar.PersianHorizontalCalendar;

import org.joda.time.DateTime;

/**
 * Created by Tasnim on 11/12/2017.
 */

public interface PageViewListener {
    PersianHorizontalCalendar onDayClick(DateTime dateTime);
}
