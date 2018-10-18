package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.ScrollingActivity;
import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModel;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModelFactory;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.MainActivity;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.template.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

import static com.wordpress.ayo218.easy_teleprompter.ui.activities.ScrollingActivity.DOUBLE_FRAGMENT;
import static com.wordpress.ayo218.easy_teleprompter.ui.fragments.TextScrollingFragment.SCRIPT_SCROLLING;
import static com.wordpress.ayo218.easy_teleprompter.ui.fragments.ScriptFragment.UID;

public class EditScriptFragment extends BaseFragment {
    private static final String TAG = "EditScriptFragment";

    public static final String DATE_EXTRA = "date_creation";
    private static final int DEFAULT_ID = -1;
    private int scriptId = DEFAULT_ID;

    @BindView(R.id.script_content_txt)
    EditText script_content;
    @BindView(R.id.script_play_button)
    ImageButton play_btn;
    @BindView(R.id.record_script_button)
    ImageButton record_btn;
    @BindView(R.id.relative_layout)
    RelativeLayout layout;
    @BindView(R.id.script_speed_seekbar)
    SeekBar speed_Bar;

    ActionBar toolbar;
    TextView title_txt;

    private String creationDate, title, content, updateDate;
    private int textColor;
    private int backgroundColor;
    private int scrollSpeed;
    private int fontSize;
    private boolean textMirroed;

    //title from new script
    String intent_title;

    private AppDatabase database;

    private EditScriptViewModelFactory factory;
    private EditScriptViewModel viewModel;

    //SavedInstanceState Constant
    private static final String BUNDLE_SCRIPT_CONTENT = "content";

    String saved_cont;

    public EditScriptFragment() {
    }


    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_script, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null){
            String saved_con = savedInstanceState.getString(BUNDLE_SCRIPT_CONTENT);
            Log.i(TAG, "onCreateView: " + saved_con);
        }

        database = AppDatabase.getsInstance(getContext());
        title_txt = getActivity().findViewById(R.id.script_title);
        toolbar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        //Trying something new
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(UID)) {
            if (scriptId == DEFAULT_ID) {
                scriptId = intent.getIntExtra(UID, DEFAULT_ID);
                factory = new EditScriptViewModelFactory(database, scriptId);
                viewModel = ViewModelProviders.of(getActivity(), factory).get(EditScriptViewModel.class);
                viewModel.getScriptsLiveData().observe(getActivity(), new Observer<Scripts>() {
                    @Override
                    public void onChanged(@Nullable Scripts script) {
                        viewModel.getScriptsLiveData().removeObserver(this);
                        populateUI(script);
                    }
                });
            }
        } else {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                intent_title = intent.getStringExtra(Intent.EXTRA_TEXT);
                title_txt.setText(intent_title);
            }

            if (intent.hasExtra(DATE_EXTRA)) {
                creationDate = intent.getStringExtra(DATE_EXTRA);
            }
        }


//        ImageView done_img = getActivity().findViewById(R.id.done_btn);
//        done_img.setOnClickListener(v -> {
//            if (scriptId == DEFAULT_ID) {
//                title = intent_title;
//            } else {
//                viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());
//
//            }
//
//            content = script_content.getText().toString();
//            // TODO: 8/22/2018 Change this later
//            Scripts scripts = new Scripts(title, content, creationDate, updateDate);
//
//            if (scriptId == DEFAULT_ID) {
//                //Insert a new script
//                database.scriptDao().insertScript(scripts);
//            } else {
//                //update script
//                scripts.setUid(scriptId);
//                database.scriptDao().updateScript(scripts);
//            }
//            startActivity(new Intent(getContext(), MainActivity.class));
//            getActivity().finish();
//
//
//        });


        //Open the text_scrolling fragment
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scriptId == DEFAULT_ID) {
                    title = intent_title;
                } else {
                    viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());
                }

                content = script_content.getText().toString();

                // FIXME: 9/17/18 Also was here
                Scripts scripts = new Scripts(title, content, 1, 2, android.R.color.holo_blue_dark, R.color.white);
                Intent intent = new Intent(getContext(), ScrollingActivity.class);
                intent.putExtra(SCRIPT_SCROLLING, scripts);
                startActivity(intent);
            }
        });


        //Open camera and text_scrolling fragments
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scriptId == DEFAULT_ID) {
                    title = intent_title;
                } else {
                    viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());
                }

                content = script_content.getText().toString();

                Scripts scripts = new Scripts(title, content);
                Intent intent = new Intent(getContext(), ScrollingActivity.class);
                intent.putExtra(DOUBLE_FRAGMENT, "hi");
                intent.putExtra(SCRIPT_SCROLLING, scripts);
                startActivity(intent);
            }
        });

        return view;
    }

    private int setSpeedNumber(int scroll) {
        scrollSpeed = scroll;
        return scrollSpeed;
    }

    private void populateUI(Scripts scripts) {
        if (scripts == null) {
            return;
        }
        title_txt.setText(scripts.getTitle());
//        script_title.setText(scripts.getTitle());
        script_content.setText(scripts.getContent());

        updateDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
    }
    
    @OnClick(R.id.settings_script_button)
    public void colorPicker(){
        ColorPicker colorPicker = new ColorPicker(getActivity());
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#303030");
        colors.add("#E91E63");
        colors.add("#9C27B0");
        colors.add("#2196F3");
        colors.add("#00BCD4");
        colors.add("#009688");
        colors.add("#4CAF50");
        colors.add("#FFC107");
        colors.add("#FF5722");


        colorPicker.setDefaultColorButton(Color.parseColor("#303030"))
                .setColors(colors)
                .setColumns(3)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChooseColor(int position, int color) {
                        backgroundColor = color;
                        layout.setBackgroundColor(color);
                        toolbar.setBackgroundDrawable(new ColorDrawable(color));
                        getActivity().getWindow().setStatusBarColor(darkenBackgroundColor(color, 0.7f));
                    }

                    @Override
                    public void onCancel() {

                    }
                })
                .show();
    }

    private static int darkenBackgroundColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);

        return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String saved_cont = script_content.getText().toString();
        outState.putString(BUNDLE_SCRIPT_CONTENT, saved_cont);
    }


    //    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState == null) {
//            Log.e(TAG, "onViewStateRestored: NULLLLLL" );
//        }else{
//            String saved = savedInstanceState.getString(BUNDLE_SCRIPT_CONTENT);
//            Log.i(TAG, "onViewStateRestored: " + saved);
//            script_content.setText(saved);
//        }
//    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String saved_cont = script_content.getText().toString();
        Log.i(TAG, "onViewStateRestored: " + saved_cont);
        script_content.setText(saved_cont);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (scriptId == DEFAULT_ID) {
            title = intent_title;
        } else {
            viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());

        }

        content = script_content.getText().toString();
        // TODO: 8/22/2018 Change this later
        Scripts scripts = new Scripts(title, content, creationDate, updateDate);

        if (scriptId == DEFAULT_ID) {
            //Insert a new script
            database.scriptDao().insertScript(scripts);
        } else {
            //update script
            scripts.setUid(scriptId);
            database.scriptDao().updateScript(scripts);
        }
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}
