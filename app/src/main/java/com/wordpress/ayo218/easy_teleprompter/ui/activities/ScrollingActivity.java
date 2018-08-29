package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.TextScrollingFragment;

public class ScrollingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initFragment();
    }

    private void initFragment(){
        TextScrollingFragment fragment = new TextScrollingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.text_scrolling_fragment, fragment);
        transaction.commit();
    }
}
