package com.wordpress.ayo218.easy_teleprompter.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wordpress.ayo218.easy_teleprompter.models.Scripts;

@Database(entities = {Scripts.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_TABLE = "ScriptDB";
    private static AppDatabase database;

    public static AppDatabase getsInstance(Context context) {
        if (database == null) {
            synchronized (LOCK) {
                database = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_TABLE)
                        // TODO: 8/14/2018 Remove this later on
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return  database;
    }

    public abstract ScriptDao scriptDao();
}
