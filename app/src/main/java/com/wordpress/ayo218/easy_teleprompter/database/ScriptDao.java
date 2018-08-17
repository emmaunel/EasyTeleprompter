package com.wordpress.ayo218.easy_teleprompter.database;

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
    List<Scripts> loadAllScripts();

    @Insert
    void insertScript(Scripts scripts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateScript(Scripts scripts);

    @Delete
    void deleteTask(Scripts scripts);
}
