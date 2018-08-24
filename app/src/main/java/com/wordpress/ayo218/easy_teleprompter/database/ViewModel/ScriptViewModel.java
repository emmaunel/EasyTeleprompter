package com.wordpress.ayo218.easy_teleprompter.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

import java.util.List;

public class ScriptViewModel extends AndroidViewModel{

    private static final String TAG = "ScriptViewModel";
    private LiveData<List<Scripts>> scripts;

    public ScriptViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the database");
        scripts = database.scriptDao().loadAllScripts();
    }

    public LiveData<List<Scripts>> getScripts() {
        return scripts;
    }
}
