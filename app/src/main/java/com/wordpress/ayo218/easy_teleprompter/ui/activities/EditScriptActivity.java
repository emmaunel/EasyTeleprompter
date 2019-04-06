package com.wordpress.ayo218.easy_teleprompter.ui.activities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.EditScriptFragment;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.template.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity for Edit Script. It has the edit fragment in it
 * @author ayo
 */
public class EditScriptActivity extends AppCompatActivity{
    private static final String TAG = "EditScriptActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.script_title)
    TextView title_txt;

    //String title;


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
//        title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
//        title_txt.setText(title);

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
        slide.setSlideEdge(Gravity.END);
        return slide;
    }

    /**
     * I think this is bad but If I didn't do it, it doesn't to the main screen(long time since I
     * looked it). So I "forced" to do to the main activity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditScriptActivity.this, MainActivity.class));
        tellFragments();
        finish();
    }

    /**
     * I honestly don't remember what this does but it does something magical.
     */
    private void tellFragments() {
       List<Fragment> fragments = getSupportFragmentManager().getFragments();
       for (Fragment f : fragments){
           if (f instanceof BaseFragment){
               ((BaseFragment)f).onBackPressed();
           }
       }
    }
}
