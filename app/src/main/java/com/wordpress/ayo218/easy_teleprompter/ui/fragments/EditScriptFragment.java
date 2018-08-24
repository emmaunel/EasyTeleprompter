package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModel;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.EditScriptViewModelFactory;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wordpress.ayo218.easy_teleprompter.ui.fragments.ScriptFragment.UID;

public class EditScriptFragment extends Fragment {
    private static final String TAG = "EditScriptFragment";

    public static final String DATE_EXTRA = "date_creation";
    private static final int DEFAULT_ID = -1;
    private int scriptId = DEFAULT_ID;

    @BindView(R.id.script_title_txt)
    EditText script_title;
    @BindView(R.id.script_content_txt)
    EditText script_content;
    @BindView(R.id.script_play_button)
    ImageButton play_btn;
    @BindView(R.id.script_modification_date)
            TextView script_modification_date;

    TextView title_txt;

    String creationDate, title, content, updateDate;
    //title from new script
    String intent_title;

    private AppDatabase database;
    private LiveData<Scripts> scripts;

    private EditScriptViewModelFactory factory;
    private EditScriptViewModel viewModel;
    public EditScriptFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_script, container, false);
        ButterKnife.bind(this, view);
        database = AppDatabase.getsInstance(getContext());
        title_txt = getActivity().findViewById(R.id.script_title);

        //Trying something new
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(UID)){
            if (scriptId == DEFAULT_ID) {
                scriptId = intent.getIntExtra(UID, DEFAULT_ID);
                factory = new EditScriptViewModelFactory(database, scriptId);
                viewModel = ViewModelProviders.of(getActivity(), factory).get(EditScriptViewModel.class);
                viewModel.getScriptsLiveData().observe(getActivity(), new Observer<Scripts>() {
                    @Override
                    public void onChanged(@Nullable Scripts script) {
                        viewModel.getScriptsLiveData().removeObserver(this);
                        Log.d(TAG, "Receiving Data from database");
                        populateUI(script);
                    }
                });
            }
        } else {
            if (intent.hasExtra(Intent.EXTRA_TEXT)){
                intent_title = intent.getStringExtra(Intent.EXTRA_TEXT);
                title_txt.setText(intent_title);
            }

            if (intent.hasExtra(DATE_EXTRA)) {
                creationDate = intent.getStringExtra(DATE_EXTRA);
                script_modification_date.setText("Created : " + creationDate);
            }
        }


        ImageView done_img = getActivity().findViewById(R.id.done_btn);
        done_img.setOnClickListener(v -> {
            if (scriptId == DEFAULT_ID){
                title = intent_title;
            }else{
                viewModel.getScriptsLiveData().observe(getActivity(), scripts -> title = scripts.getTitle());

            }

           content = script_content.getText().toString();
           Scripts scripts = new Scripts(title, content, creationDate, updateDate);
            if (scriptId == DEFAULT_ID) {
                //Insert a new script
                database.scriptDao().insertScript(scripts);
            }else{
                //update script
                scripts.setUid(scriptId);
                database.scriptDao().updateScript(scripts);
            }
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });

        return view;
    }

    private void populateUI(Scripts scripts) {
        if (scripts == null){
            return;
        }
        title_txt.setText(scripts.getTitle());
        script_content.setText(scripts.getContent());

        updateDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        script_modification_date.setText("Last update: " + updateDate);
    }
}
