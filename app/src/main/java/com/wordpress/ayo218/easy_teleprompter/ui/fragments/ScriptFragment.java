package com.wordpress.ayo218.easy_teleprompter.ui.fragments;

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

import com.wordpress.ayo218.easy_teleprompter.GridSpacingItemDecoration;
import com.wordpress.ayo218.easy_teleprompter.R;
import com.wordpress.ayo218.easy_teleprompter.adapters.ScriptsAdapter;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptFragment extends Fragment {
    @BindView(R.id.script_view)
    RecyclerView recyclerView;
    List<Scripts> scriptsList = new ArrayList<>();
    ScriptsAdapter adapter;

    public ScriptFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        scriptsList.add(new Scripts("App presentation", "Cat cat cat cat", "August 20, 2018"));
        scriptsList.add(new Scripts("Default", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        scriptsList.add(new Scripts("Movie","Length length length length"));
        scriptsList.add(new Scripts("School", "String string string string"));
        scriptsList.add(new Scripts("", "Content content content content"));
        scriptsList.add(new Scripts("", "Size size size size size size size size size size size"));
        scriptsList.add(new Scripts("", "Char char char char char char char"));
        scriptsList.add(new Scripts("", "Dog dog dog dog dog dog"));
        scriptsList.add(new Scripts("TEst", "R"));
        scriptsList.add(new Scripts("", "Int hold dog cat hold hold hold cat hold hold dog hold int hold hold hold hold hold"));


        adapter = new ScriptsAdapter(scriptsList);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing));

        ItemTouchHelper.Callback itemCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Collections.swap(scriptsList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


}
