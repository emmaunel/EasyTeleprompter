package com.wordpress.ayo218.easy_teleprompter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.script_placeholder)
    ImageView imageView;
    @BindView(R.id.script_name)
    TextView script_name;

    public ScriptsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
