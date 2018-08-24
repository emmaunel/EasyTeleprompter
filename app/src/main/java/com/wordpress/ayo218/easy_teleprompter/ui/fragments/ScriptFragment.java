package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.adapters.ScriptsAdapter;
import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.database.AppExecutors;
import com.wordpress.ayo218.easy_teleprompter.database.ViewModel.ScriptViewModel;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;
import com.wordpress.ayo218.easy_teleprompter.ui.activities.EditScriptActivity;
import com.wordpress.ayo218.easy_teleprompter.utils.animation.GridSpacingItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptFragment extends Fragment {
    private static final String TAG = "ScriptFragment";

    public static final String UID = "id";

    @BindView(R.id.script_view)
    RecyclerView recyclerView;

    ScriptsAdapter adapter;

    private AppDatabase database;

    public ScriptFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        database = AppDatabase.getsInstance(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing));

        /**
         * Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         * An ItemTouchHelper enables touch behaviour (like swipe and move) on each viewHolder,
         * and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder holder) {
                int source = viewHolder.getAdapterPosition();
                int target = holder.getAdapterPosition();
                adapter.notifyItemMoved(source, target);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // TODO: 8/17/2018 Maybe use viewmodel or something
                AppExecutors.getsInstance().diskIO().execute(() -> {
                    int position = viewHolder.getAdapterPosition();
                    List<Scripts> scripts = adapter.getScriptsList();
                    database.scriptDao().deleteTask(scripts.get(position));
                });
            }
        }).attachToRecyclerView(recyclerView);

        setupViewModel();
    }


    private void setupViewModel() {
        ScriptViewModel viewModel = ViewModelProviders.of(getActivity()).get(ScriptViewModel.class);
        viewModel.getScripts().observe(getActivity(), scriptsList -> {
            adapter = new ScriptsAdapter(scriptsList, (scripts1, position) -> {
                int id = scriptsList.get(position).getUid();
                Intent intent = new Intent(getContext(), EditScriptActivity.class);
                intent.putExtra(UID, id);
                startActivity(intent);
            });

            adapter.setScripts(scriptsList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        });
    }
}
