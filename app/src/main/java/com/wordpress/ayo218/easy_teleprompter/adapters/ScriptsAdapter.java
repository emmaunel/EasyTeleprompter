package com.wordpress.ayo218.easy_teleprompter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.Viewholders.ScriptsViewHolder;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.utils.listener.OnItemClickListener;
import com.wordpress.ayo218.easy_teleprompter.utils.listener.OnLongItemPressed;

import java.util.List;

/**
 * Adapter for Script Fragment
 * @author ayo
 */
public class ScriptsAdapter extends RecyclerView.Adapter<ScriptsViewHolder> {
    private static final String TAG = "ScriptsAdapter";
    private List<Scripts> scriptsList;
    private ViewGroup viewGroup;

    private OnItemClickListener listener;

    public ScriptsAdapter(List<Scripts> scriptsList, OnItemClickListener listener) {
        this.scriptsList = scriptsList;
        this.listener = listener;
    }

    /**
     * It creates individual item for the recyclerview. And with them, it adds a custom
     * on click listener that tells the index it is in and the script.
     * 'Thinking about this, it might a little ineffictive but I will fix it later.'
     * @param parent
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ScriptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.script_item, null);

        ScriptsViewHolder holder = new ScriptsViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(scriptsList.get(i),holder.getAdapterPosition()));
        view.setOnLongClickListener(view1 -> {
            editTitle(scriptsList.get(holder.getAdapterPosition()).getTitle(), parent.getContext());
            return true;
        });
        viewGroup = parent;
        return holder;
    }

    /**
     * Responsible for updating the view based on the user's input.
     * @param holder
     * @param i
     */
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

    /**
     * Returns the size of the script list
     * @return script list
     */
    @Override
    public int getItemCount() {
        if (scriptsList != null) {
            return scriptsList.size();
        }
        return 0;
    }

    /**
     * This is used in the Script Fragment when is given the
     * initialed list of scripts
     * @param list of scripts
     */
    public void setScripts(List<Scripts> list) {
        scriptsList = list;
        notifyDataSetChanged();
    }

    /**
     * Returns the list of scripts
     * @return
     */
    public List<Scripts> getScriptsList() {
        return scriptsList;
    }

    /**
     * When the user long press an item, a dialog popped up to
     * edit the title which is updated in real-time in the
     * database.
     * @param title the script's title
     * @param context just needed because I didn't set a global context
     */
    private void editTitle(String title, Context context){
        Log.e(TAG, "My title is : " + title);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_script_editor_dialog,
                null);
        EditText newTitle = view.findViewById(R.id.script_title_txt);
        newTitle.setHint(title);
        Button ok = view.findViewById(R.id.edit_btn);

        // TODO: 4/4/19 Figure out to update the title in the database
        ok.setOnClickListener(e -> Toast.makeText(context, "Done", Toast.LENGTH_LONG).show());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
