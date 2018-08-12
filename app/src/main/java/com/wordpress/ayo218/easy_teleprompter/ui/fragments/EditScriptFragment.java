package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wordpress.ayo218.easy_teleprompter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditScriptFragment extends Fragment {

    @BindView(R.id.script_title_txt)
    EditText script_title;
    @BindView(R.id.script_content_txt)
    EditText script_content;

    public EditScriptFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_script, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


}
