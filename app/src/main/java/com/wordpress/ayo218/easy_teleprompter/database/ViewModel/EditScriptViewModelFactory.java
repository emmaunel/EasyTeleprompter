package com.wordpress.ayo218.easy_teleprompter.database.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.wordpress.ayo218.easy_teleprompter.database.AppDatabase;

public class EditScriptViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase database;
    private final int scriptId;

    public EditScriptViewModelFactory(AppDatabase database, int scriptId) {
        this.database = database;
        this.scriptId = scriptId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditScriptViewModel(database, scriptId);
    }
}
