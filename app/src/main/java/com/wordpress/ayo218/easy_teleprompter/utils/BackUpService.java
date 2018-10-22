package com.wordpress.ayo218.easy_teleprompter.utils;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class BackUpService extends JobService{
    private static final String TAG = "BackUpService";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.e(TAG, "onStarJob: Here");
        // TODO: 10/22/2018 This service will be updating the database in the future
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
