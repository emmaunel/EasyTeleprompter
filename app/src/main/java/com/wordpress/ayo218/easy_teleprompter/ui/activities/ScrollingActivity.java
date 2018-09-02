package com.wordpress.ayo218.easy_teleprompter.ui.activities;

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
        initFragment();

    }

//    private void initFragment(){
//        CameraFragment fragment = new CameraFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.camera_fragment, fragment);
//        transaction.commit();
//    }

    private void initFragment(){
            getFragmentManager().beginTransaction()
                    .replace(R.id.camera_fragment, CameraFragment.newInstance())
                    .commit();

    }
}
