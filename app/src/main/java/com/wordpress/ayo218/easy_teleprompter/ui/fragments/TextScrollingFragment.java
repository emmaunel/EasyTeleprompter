package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.customViews.ScrollingScrollView;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.template.BaseFragment;
import com.wordpress.ayo218.easy_teleprompter.utils.listener.ScrollViewListerner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextScrollingFragment extends BaseFragment
        implements ScrollViewListerner{

    private static final String TAG = "TextScrollingFragment";

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY = 3000;
    private static final int SCROLL_DELAY = 6000;

    private static final int UI_ANIMATION_DELAY = 300;

    public static final String SCRIPT_SCROLLING = "scrolling";

    OnPauseListener listener;

    private final Handler uiRemover = new Handler();

    @BindView(R.id.fullscreen_content_control)
    View controlsView;

    private boolean visible;

    @BindView(R.id.custom_slideshow)
    ScrollingScrollView scrollingScrollView;
    @BindView(R.id.fullscreen_script)
    TextView content_txt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.countdown_view)
    FrameLayout countdownView;
    @BindView(R.id.countdown_txt)
    TextView countdownTxt;
    @BindView(R.id.scroll_controller)
    LinearLayout scroll_controller;
    @BindView(R.id.btn_play)
    Button play_button;
    @BindView(R.id.btn_pause)
    Button pause_button;

    private String content;
    private boolean isPlaying;

    private boolean isClicked;

    private int animationDelay;
    private int scrollOffset;

    private AnimatorSet animatorSet;
    private Handler animationHandler;
    private AnimationRunnable animationRunnable;
    private boolean isFirsttime = false;

    private Scripts scripts = null;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.text_scrolling_fragment, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            isFirsttime = true;
        }

        //Getting intent
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(SCRIPT_SCROLLING)) {
            scripts = intent.getParcelableExtra(SCRIPT_SCROLLING);
        }


        if (scripts != null) {
            content_txt.setText(scripts.getContent());
            // FIXME: 9/17/18 I was here
            setAnimationSpeed(3);

        }

        visible = true;

        content_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        scroll_controller.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toggle();
                return true;
            }
        });

        play_button.setOnTouchListener(delayListener);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScrollAnimation();
            }
        });

        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopScrollAnimation();
            }
        });

        scrollingScrollView.setListerner(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        delayHide(100);
    }

    @Override
    public void onResume() {
        super.onResume();
        startCountdown();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (animationHandler != null) {
            animationHandler.removeCallbacks(animationRunnable);
        }
    }

    private void toggle() {
        if (visible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        controlsView.setVisibility(View.GONE);
        visible = false;

        uiRemover.removeCallbacks(runnable2);
        uiRemover.postDelayed(runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        content_txt.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        visible = true;

        // Schedule a runnable to display UI elements after a delay
        uiRemover.removeCallbacks(runnable);
        uiRemover.postDelayed(runnable2, UI_ANIMATION_DELAY);
    }

    private void delayHide(int delayMills) {
        uiRemover.removeCallbacks(hideRunnable);
        uiRemover.postDelayed(hideRunnable, delayMills);
    }

    private void startCountdown(){
        showCountdown();
        new Handler().postDelayed(new CountDownRunnable(), 2000);
    }

    private void startScrollAnimation() {
        int y = scrollingScrollView.getScrollY();
        scrollingScrollView.setScrollable(false);
        animationHandler = new Handler();
        animationRunnable = new AnimationRunnable(y);
        animationHandler.postDelayed(animationRunnable, SCROLL_DELAY);
        showPauseBtn();
    }

    private void stopScrollAnimation() {
        animationHandler.removeCallbacks(animationRunnable);
        scrollingScrollView.setScrollable(true);
        showPlayBtn();
        // FIXME: 9/21/18 
//        isClicked = true;
//        listener.onClicked(isClicked);
    }


    @Override
    public void onScrollChanged(ScrollingScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (difference <= 40) {
            if (animationHandler != null) {
                stopScrollAnimation();
            }
        }
    }

    private void showCountdown() {
        countdownView.setVisibility(View.VISIBLE);
    }

    private void hideCountDown() {
        countdownView.setVisibility(View.GONE);
    }

    private void showPauseBtn() {
        play_button.setVisibility(View.GONE);
        pause_button.setVisibility(View.VISIBLE);
        isPlaying = true;
    }

    private void showPlayBtn() {
        play_button.setVisibility(View.VISIBLE);
        pause_button.setVisibility(View.GONE);
        isPlaying = false;
    }

    // TODO: 8/23/2018 Find a better name 
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            content_txt.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            controlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener delayListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (AUTO_HIDE) {
                delayHide(AUTO_HIDE_DELAY);
            }

            return false;
        }
    };

    private final View.OnTouchListener delayListener2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (AUTO_HIDE) {
                delayHide(AUTO_HIDE_DELAY);
            }
            animatorSet.start();
            return false;
        }
    };

    private class AnimationRunnable implements Runnable{
        private int scrollTo;

        public AnimationRunnable(int scrollTo) {
            this.scrollTo= scrollTo;
        }

        @Override
        public void run() {
            scrollingScrollView.smoothScrollTo(0, scrollTo);
            animationHandler = new Handler();
            animationRunnable = new AnimationRunnable(scrollTo + scrollOffset);
            animationHandler.postDelayed(animationRunnable, animationDelay);
        }
    }

    private class CountDownRunnable implements Runnable{

        @Override
        public void run() {
            int count = Integer.parseInt(countdownTxt.getText().toString());
            if(count>1){
                count--;
                countdownTxt.setText(Integer.toString(count));
                new Handler().postDelayed(new CountDownRunnable(),1000);
            }else {
                countdownTxt.setText("Start");
                hideCountDown();
                startScrollAnimation();
            }
        }
    }

    private void setAnimationSpeed(int scrollSpeed) {
        switch (scrollSpeed) {
            case 0:
                animationDelay = 25;
                scrollOffset = 1;
                break;
            case 1:
                animationDelay = 30;
                scrollOffset = 2;
                break;
            case 2:
                animationDelay = 30;
                scrollOffset = 3;
                break;
            case 3:
                animationDelay = 25;
                scrollOffset = 3;
                break;
            case 4:
                animationDelay = 30;
                scrollOffset = 4;
                break;
        }
    }

    public interface OnPauseListener{
        void onClicked(boolean clicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPauseListener) context;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}

