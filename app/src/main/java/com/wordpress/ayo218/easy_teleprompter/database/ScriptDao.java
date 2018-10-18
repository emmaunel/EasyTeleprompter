package com.wordpress.ayo218.easy_teleprompter.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

import java.util.List;

@Dao
public interface ScriptDao {

    @Query("SELECT * FROM Scripts")
    LiveData<List<Scripts>> loadAllScripts();

    @Query("SELECT * FROM Scripts")
    List<Scripts> loadScripts();

    @Query("SELECT * FROM Scripts WHERE uid = :id")
    LiveData<Scripts> loadScriptById(int id);
    @Insert
    void insertScript(Scripts scripts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateScript(Scripts scripts);

    @Delete
    void deleteTask(Scripts scripts);
}
