package com.wordpress.ayo218.easy_teleprompter.utils.listener;

import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

/**
 * Custom Inteface for Individual script on the main screen
 * @author ayo
 */
public interface OnItemClickListener {
    /**
     * Pass the script and its position
     * @param scripts
     * @param position
     */
    void onItemClick(Scripts scripts, int position);
}
