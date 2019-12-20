package com.tanushree.bestreads.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "books";
    private static final Object LOCK = new Object();
    private static final String TAG = AppDatabase.class.getSimpleName();

    private static AppDatabase sAppDatabase;

    // Singleton Pattern (to make sure only one instance of this class is created).

    public static AppDatabase getInstance(Context context) {
        if (sAppDatabase == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sAppDatabase;
    }

    public abstract BookDao bookDao();
}
