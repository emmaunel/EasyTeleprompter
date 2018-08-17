package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditScriptFragment extends Fragment {

    private static final String TAG = "EditScriptFragment";
    public static final String DATE_EXTRA = "date_creation";

    @BindView(R.id.script_content_txt)
    EditText script_content;
    @BindView(R.id.script_play_button)
    ImageButton play_btn;

    String creationDate, title, content, updateDate;
    private AppDatabase database;

    public EditScriptFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_script, container, false);
        ButterKnife.bind(this, view);

        database = AppDatabase.getsInstance(getContext());
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                saveDb();
            }
        });
        return view;
    }

    private void saveDb() {
        title = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        content = script_content.getText().toString();
        creationDate = getActivity().getIntent().getStringExtra(DATE_EXTRA);
        updateDate = getActivity().getIntent().getStringExtra(DATE_EXTRA);

        Scripts scripts = new Scripts(title, content, creationDate, updateDate);
        database.scriptDao().insertScript(scripts);

        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }


}
