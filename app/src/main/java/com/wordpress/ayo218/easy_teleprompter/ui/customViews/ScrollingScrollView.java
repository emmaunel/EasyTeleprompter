package com.wordpress.ayo218.easy_teleprompter.ui.customViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.wordpress.ayo218.easy_teleprompter.utils.listener.ScrollViewListerner;

public class ScrollingScrollView extends ScrollView {

    private ScrollViewListerner listerner;
    private boolean isScrollable;

    public ScrollingScrollView(Context context) {
        super(context);
    }

    public ScrollingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollingScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setListerner(ScrollViewListerner listerner) {
        this.listerner = listerner;
    }

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listerner != null) {
            listerner.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollable && super.onTouchEvent(ev);
    }
}
