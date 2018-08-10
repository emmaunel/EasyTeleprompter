package com.wordpress.ayo218.easy_teleprompter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ScriptsAdapter extends RecyclerView.Adapter<ScriptsViewHolder> {
    private Context context;
    private List<Scripts> scriptsList;

    public ScriptsAdapter(Context context, List<Scripts> scriptsList) {
        this.context = context;
        this.scriptsList = scriptsList;
    }

    @NonNull
    @Override
    public ScriptsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.script_item, viewGroup, false);
        return new ScriptsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScriptsViewHolder holder, int i) {
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.script_name.setText(scriptsList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return scriptsList.size();
    }
}
