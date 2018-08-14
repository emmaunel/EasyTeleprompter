package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.widget.TextView;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.EditScriptFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditScriptActivity extends AppCompatActivity {
    private static final String TAG = "EditScriptActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.script_title)
    TextView title_txt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        ButterKnife.bind(this);

        //start transition
        Transition transition = buildEnterTransition();
        getWindow().setEnterTransition(transition);

        //set title
        String title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        title_txt.setText(title);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());

        //replace fragment
        EditScriptFragment fragment = new EditScriptFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_fragment, fragment);
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Visibility buildEnterTransition(){
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.RIGHT);
        return slide;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditScriptActivity.this, MainActivity.class));
        finish();
    }
}
