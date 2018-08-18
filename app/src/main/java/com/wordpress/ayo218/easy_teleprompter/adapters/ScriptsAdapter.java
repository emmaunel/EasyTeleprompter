package com.wordpress.ayo218.easy_teleprompter.adapters;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.Viewholders.ScriptsViewHolder;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.utils.listener.OnItemClickListener;

import java.util.List;

public class ScriptsAdapter extends RecyclerView.Adapter<ScriptsViewHolder> {
    private List<Scripts> scriptsList;
    private ViewGroup viewGroup;

    private OnItemClickListener listener;
    public ScriptsAdapter(List<Scripts> scriptsList, OnItemClickListener listener) {
        this.scriptsList = scriptsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScriptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.script_item, null);

        ScriptsViewHolder holder = new ScriptsViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(scriptsList.get(i),holder.getAdapterPosition()));
        viewGroup = parent;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScriptsViewHolder holder, int i) {
        holder.script_title.setText(scriptsList.get(i).getTitle());
        if (holder.script_title.getText().toString().isEmpty()){
            holder.script_title.setHeight(0);
        }

        holder.script_content.setText(scriptsList.get(i).getContent());
        if (holder.script_content.getText().toString().isEmpty()) {
            holder.script_content.setHeight(0);
        }

        holder.script_creation_date.setText(scriptsList.get(i).getDate_created());

        /**
         * Change font size according to content size
         */
        if (holder.script_content.getText().toString().length() > 0 && holder.script_content.getText().toString().length() < 6){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_thin);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(70);
        }

        if (holder.script_content.getText().toString().length() >= 6 && holder.script_content.getText().toString().length() < 10){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(50);
        }

        if (holder.script_content.getText().toString().length() >= 10 && holder.script_content.getText().toString().length() < 13){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(36);
        }

        if (holder.script_content.getText().toString().length() >= 13 && holder.script_content.getText().toString().length() < 19){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_light);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(24);
        }

        if (holder.script_content.getText().toString().length() >= 19 && holder.script_content.getText().toString().length() < 24){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(18);
        }

        if (holder.script_content.getText().toString().length() >= 24){
            Typeface typeface = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = viewGroup.getContext().getResources().getFont(R.font.roboto_slab_regular);
            }
            holder.script_content.setTypeface(typeface);
            holder.script_content.setTextSize(16);
        }
    }

    @Override
    public int getItemCount() {
        if (scriptsList != null) {
            return scriptsList.size();
        }
        return 0;
    }

    public void setScripts(List<Scripts> list) {
        scriptsList = list;
        notifyDataSetChanged();
    }

    public List<Scripts> getScriptsList() {
        return scriptsList;
    }
}
