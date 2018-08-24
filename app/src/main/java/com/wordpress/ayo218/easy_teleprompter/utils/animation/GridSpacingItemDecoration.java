package com.wordpress.ayo218.easy_teleprompter.utils.animation;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public GridSpacingItemDecoration(int space){ this.space = space;}

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view) == 0){
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
