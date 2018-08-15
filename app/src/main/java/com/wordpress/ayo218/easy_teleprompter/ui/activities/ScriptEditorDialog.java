package com.wordpress.ayo218.easy_teleprompter.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.ui.fragments.EditScriptFragment;
import com.wordpress.ayo218.easy_teleprompter.utils.FabDialogMorphSetup;
import com.wordpress.ayo218.easy_teleprompter.utils.TransitionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScriptEditorDialog extends AppCompatActivity {

    private static final String EXTRA = "slide_transition";
    public static final String SLIDE = "slide";
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.edit_btn)
    Button editBtn;
    @BindView(R.id.script_title_txt)
    EditText scriptTitleTxt;

    String creationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_editor_dialog);
        ButterKnife.bind(this);

        creationDate = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault()).format(new Date());
        //Begin fab <-> Dialog and Dialog <-> fab animation
        FabDialogMorphSetup.setupSharedElementTransitions(this, container, getResources().getDimensionPixelSize(R.dimen.dialog_corners));
    }


    public void dismiss(View view) {
        ActivityCompat.finishAfterTransition(this);
    }

    //I don't know if this looks good but it is a slide transition to the edit activity
    @OnClick(R.id.edit_btn)
    public void onViewClicked() {
        String inputTitle = scriptTitleTxt.getText().toString();
        if (inputTitle.isEmpty()) {
            inputTitle = "Untitled";
        }

        Intent intent = new Intent(this, EditScriptActivity.class);
        intent.putExtra(EXTRA, SLIDE);
        intent.putExtra(Intent.EXTRA_TEXT, inputTitle);
        intent.putExtra(EditScriptFragment.DATE_EXTRA, creationDate);
        Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, optionsCompat.toBundle());
        }
    }
}
