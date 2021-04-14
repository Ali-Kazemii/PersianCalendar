package ir.awlrhm.reminder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.calendar.PersianHorizontalCalendar
import ir.awlrhm.calendar.enums.PersianCustomMarks
import ir.awlrhm.calendar.enums.PersianViewPagerType
import ir.awlrhm.calendar.model.CustomGradientDrawable
import kotlinx.android.synthetic.main.contain_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.joda.time.Chronology
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam

class MainFragment(
    private val callback: () -> Unit
) : Fragment() {

    private var isRemove: Boolean = true
    private val perChr: Chronology =
        PersianChronologyKhayyam.getInstance(/*DateTimeZone.forID("Asia/Tehran")*/ /*DateTimeZone.forID("Asia/Tehran")*/
            DateTimeZone.getDefault()
        )

    private val now = DateTime(perChr)
    private val monthResource = intArrayOf(
        R.drawable.bkg_01_jan,
        R.drawable.bkg_02_feb,
        R.drawable.bkg_03_mar,
        R.drawable.bkg_04_apr,
        R.drawable.bkg_05_may,
        R.drawable.bkg_06_jun,
        R.drawable.bkg_07_jul,
        R.drawable.bkg_08_aug,
        R.drawable.bkg_09_sep,
        R.drawable.bkg_10_oct,
        R.drawable.bkg_11_nov,
        R.drawable.bkg_12_dec,
    )

    @SuppressLint("SetTextI18n")
    private fun setup() {
        val activity = activity ?: return

        imgMonth.background = ContextCompat.getDrawable(activity, monthResource[getMonthIndex(now)])
        txtToday.text = now.dayOfMonth.toString()
        txtTitle.text =
            "${now.dayOfMonth} فروردین  ${now.year}"

        rclReminder.layoutManager = LinearLayoutManager(context)
        rclReminder.setHasFixedSize(true)
        rclReminder.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return

        setup()
        initReminderItems()
        nestedScroll.parent.requestChildFocus(nestedScroll, nestedScroll)

        layoutTitle.setOnClickListener {
            if (persianCalendar.isExpand) {
                ObjectAnimator.ofFloat(imgArrow, View.ROTATION, 180f, 0f).setDuration(300).start();
                persianCalendar.collapse()
                imgMonth.isVisible = true
                nestedScroll.parent.requestChildFocus(nestedScroll, nestedScroll)

            } else {
                ObjectAnimator.ofFloat(imgArrow, View.ROTATION, 0f, 180f).setDuration(300).start();
                persianCalendar.expand()
                imgMonth.isVisible = false
            }
        }

        persianCalendar
            .setPersianHorizontalExpCalListener(object :
                PersianHorizontalCalendar.PersianHorizontalExpCalListener {
                @SuppressLint("SetTextI18n")
                override fun onCalendarScroll(dateTime: DateTime) {
                    txtTitle.text =
                        "${dateTime.dayOfMonth} فروردین  ${dateTime.year}"
                    imgMonth.background = ContextCompat.getDrawable(
                        activity,
                        monthResource[getMonthIndex(dateTime)]
                    )
                }

                @SuppressLint("SetTextI18n")
                override fun onDateSelected(dateTime: DateTime) {
                    txtTitle.text =
                        "${dateTime.dayOfMonth} فروردین  ${dateTime.year}"
                    imgMonth.background = ContextCompat.getDrawable(
                        activity,
                        monthResource[getMonthIndex(dateTime)]
                    )
                    if (persianCalendar.hasMarkDay(dateTime))
                        callback.invoke()
                }

                override fun onChangeViewPager(persianViewPagerType: PersianViewPagerType) {}
            })

        persianCalendar
            .markDate(
                DateTime(1400, 1, 15, 0, 0, perChr),
//                PersianCustomMarks.SmallOval_Bottom,
//                Color.RED,
                CustomGradientDrawable(GradientDrawable.OVAL, Color.RED)
                    .setViewLayoutSize(24, 24)
                    .setViewLayoutGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
                    .setTextColor(Color.BLACK)
            )
            /* .markDate(
                 DateTime(1400, 1, 15, 0, 0, perChr),
                 CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.GREEN)
                     .setViewLayoutSize(ViewGroup.LayoutParams.MATCH_PARENT, 14)
                     .setViewLayoutGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
                     .setcornerRadius(5)
                     .setTextColor(Color.BLACK)
             )*/
            .markDate(
                DateTime(1400, 1, 12, 0, 0, perChr),
                PersianCustomMarks.SmallOval_Bottom,
                Color.RED
            )

        layoutCalendar.setOnClickListener {
            scrollToToday()
        }

        btnShowEvents.setOnClickListener {
            btnShowEvents.isVisible = false
            rclReminder.isVisible = true
        }
        btnAdd.setOnClickListener {
//            callback.invoke()
            if(isRemove) {
                isRemove = false
                persianCalendar.markDate(
                    now,
                    PersianCustomMarks.SmallOval_Bottom,
                    Color.RED
                )
            }else {
                isRemove = true
                persianCalendar.removeMarkAt(now)
            }
        }
    }

    private fun initReminderItems() {
        rclReminder.adapter = Adapter(
            mutableListOf<Model>().apply {
                add(
                    Model("15", "دوشنبه", "فروردین", "جلسه با شرکت نیک آفرینگان")
                )
                add(
                    Model("24", "دوشنبه", "فروردین", "جلسه کارگاه آموزشی")
                )
                add(
                    Model("1", "دوشنبه", "اردیبهشت", "جلسه اداره برق منطقه ای")
                )
                add(
                    Model("12", "دوشنبه", "اردیبهشت", "جلسه با دکتر علاقه مندان")
                )
                add(
                    Model("5", "یکشنبه", "خرداد", "جلسه اداره برق منطقه ای")
                )
            }
        ) {
            callback.invoke()
        }
    }

    private fun clearMarks() {
        persianCalendar
            .clearMarks()
            .markToday()
            .updateMarks()
        scrollToToday()
    }

    private fun scrollToToday() {
        persianCalendar
            .scrollToDate(now)
    }

    private fun getMonthIndex(now: DateTime): Int {
        val current = now.monthOfYear
        return if (current < 10)
            current + 2
        else
            current - 10
    }

    companion object {
        val TAG = "Reminder: ${MainFragment::class.java.simpleName}"
    }
}