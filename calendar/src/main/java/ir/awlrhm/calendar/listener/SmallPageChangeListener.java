package ir.awlrhm.calendar.listener;


import androidx.viewpager.widget.ViewPager;



public abstract class SmallPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            scrollEnd();
        } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            scrollStart();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // IGNORE
    }

    @Override
    public void onPageSelected(int position) {
        // IGNORE
    }

    public abstract void scrollStart();

    public abstract void scrollEnd();
}
