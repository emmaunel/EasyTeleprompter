package com.wordpress.ayo218.easy_teleprompter.database.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;
import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

public class EditScriptViewModel extends ViewModel{

    private LiveData<Scripts> scriptsLiveData;

    public EditScriptViewModel(AppDatabase database, int scriptId) {
        scriptsLiveData = database.scriptDao().loadScriptById(scriptId);
    }

    public LiveData<Scripts> getScriptsLiveData() {
        return scriptsLiveData;
    }
}
