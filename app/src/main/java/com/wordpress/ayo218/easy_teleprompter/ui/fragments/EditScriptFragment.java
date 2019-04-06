package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModel;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModelFactory;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.MainActivity;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.ScrollingActivity;
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
import static com.wordpress.ayo218.easy_teleprompter.ui.fragments.ScriptFragment.UID;
import static com.wordpress.ayo218.easy_teleprompter.ui.fragments.TextScrollingFragment.SCRIPT_SCROLLING;

/**
 * Fragment for the editing screen
 * @author ayo
 */
public class EditScriptFragment extends BaseFragment {
    private static final String TAG = "EditScriptFragment";
    private final String SAVED_CONTENT = "savedContent";
    private final String  CONTENT_SPEED = "speed";
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
    private int backgroundColor;


    //title from new script
    private String intent_title;

    private AppDatabase database;

    private EditScriptViewModelFactory factory;
    private EditScriptViewModel viewModel;

    public EditScriptFragment() {}

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_script, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            content = savedInstanceState.getString(SAVED_CONTENT);
            script_content.setText(content);
        }

        database = AppDatabase.getsInstance(getContext());
        title_txt = getActivity().findViewById(R.id.script_title);
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

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


        play_btn.setOnClickListener(v -> {
            if (scriptId == DEFAULT_ID) {
                title = intent_title;
            } else {
                viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());
            }

            content = script_content.getText().toString();

            Scripts scripts = new Scripts(title, content);
            Intent scolling = new Intent(getContext(), ScrollingActivity.class);
            scolling.putExtra(SCRIPT_SCROLLING, scripts);
            startActivity(scolling);
        });


        //Open camera and text_scrolling fragments
        record_btn.setOnClickListener(view1 -> {
            if (scriptId == DEFAULT_ID) {
                title = intent_title;
            } else {
                viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());
            }

            content = script_content.getText().toString();

            Scripts scripts = new Scripts(title, content);
            Intent scroll_camera = new Intent(getContext(), ScrollingActivity.class);
            scroll_camera.putExtra(DOUBLE_FRAGMENT, "hi");
            scroll_camera.putExtra(SCRIPT_SCROLLING, scripts);
            startActivity(scroll_camera);
        });

        return view;
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

    /**
     * Display a color picker so the user has the chance to change
     * the editor background.
     */
    @OnClick(R.id.settings_script_button)
    public void colorPicker() {
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


        colorPicker.setDefaultColorButton(Color.parseColor("#303030")).setColors(colors).setColumns(3).
                setRoundColorButton(true).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
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
        }).show();
    }

    /**
     * Fancy method to change make the toolbar slightly darker than the editor
     * badckground. It doesn't work. Sad boy
     * @param color
     * @param factor
     * @return
     */
    private static int darkenBackgroundColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);

        return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
    }

    /**
     * Because I wanted to have a similar functionality as google notes, there is no save
     * button but when you press the back button, it is automatically saved.
     */
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

    /**
     * To prevent user's text to magically disappering during a rotation,
     *  this method will save an instant of the text so users don't kill me.
     * @param outState
     */
    @Override
    public void onSaveInstanceState (@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CONTENT, script_content.getText().toString());
    }
}
