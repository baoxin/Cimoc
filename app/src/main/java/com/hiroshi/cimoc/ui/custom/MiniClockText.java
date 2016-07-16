package com.hiroshi.cimoc.ui.custom;

import android.content.Context;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Hiroshi on 2016/7/16.
 */
public class MiniClockText extends TextView {

    public static final CharSequence FORMAT_24_HOUR = "H:mm";

    private Calendar mCalendar;
    private boolean mAttached = false;

    private Runnable mTicker = new Runnable() {
        @Override
        public void run() {
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            setText(DateFormat.format(FORMAT_24_HOUR, mCalendar));

            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            getHandler().postAtTime(mTicker, next);
        }
    };

    public MiniClockText(Context context) {
        super(context);
        initClock();
    }

    public MiniClockText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock();
    }

    public MiniClockText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initClock();
    }

    private void initClock() {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;
            mTicker.run();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            getHandler().removeCallbacks(mTicker);
            mAttached = false;
        }
    }

}
