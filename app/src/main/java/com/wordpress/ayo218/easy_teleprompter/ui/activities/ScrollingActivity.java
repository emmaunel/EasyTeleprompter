package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.CameraFragment;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.TextScrollingFragment;

public class ScrollingActivity extends AppCompatActivity implements TextScrollingFragment.OnPauseListener{

    private static final String TAG = "ScrollingActivity";
    public static final String DOUBLE_FRAGMENT = "fragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        if (intent.getStringExtra(DOUBLE_FRAGMENT) != null) {
            Log.e(TAG, "onCreate: Camera and scrolling");
            initCameraFragment();
        } else {
            Log.e(TAG, "onCreate: Just scrolling");
            initScrollingFragment();
        }

    }

    private void initScrollingFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.text_scrolling_fragment, TextScrollingFragment.newInstance())
                .commit();
    }

    private void initCameraFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_fragment, CameraFragment.newInstance())
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.text_scrolling_fragment, TextScrollingFragment.newInstance())
                .commit();

    }

    @Override
    public void onClicked(boolean clicked) {
        // FIXME: 9/27/18 Trying to control viedo record with record button from textdcrolling
        Log.e(TAG, "onClicked: " + clicked);
    }
}
