package com.wordpress.ayo218.easy_teleprompter.Viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wordpress.ayo218.easy_teleprompter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.script_title)
    public TextView script_title;
    @BindView(R.id.script_content)
    public TextView script_content;
    @BindView(R.id.script_creation_date)
    public TextView script_creation_date;

    public ScriptsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
