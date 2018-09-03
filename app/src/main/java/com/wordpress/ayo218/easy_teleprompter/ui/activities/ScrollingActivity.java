package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.CameraFragment;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.TextScrollingFragment;

public class ScrollingActivity extends AppCompatActivity{

    private static final String TAG = "ScrollingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        if (intent.getStringExtra("fragments") != null){
            Log.e(TAG, "onCreate: Camera and scrolling" );
            initCameraFragment();
        } else{
            Log.e(TAG, "onCreate: Just scrolling");
            initScrollingFragment();
        }

    }

    private void initScrollingFragment(){
        TextScrollingFragment fragment = new TextScrollingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.text_scrolling_fragment, fragment);
        transaction.commit();
    }

    // FIXME: 9/2/18 Need help
    private void initCameraFragment(){
            getFragmentManager().beginTransaction()
                    .replace(R.id.camera_fragment, CameraFragment.newInstance())
                    .commit();

        TextScrollingFragment fragment = new TextScrollingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.text_scrolling_fragment, fragment);
        transaction.commit();

    }
}
