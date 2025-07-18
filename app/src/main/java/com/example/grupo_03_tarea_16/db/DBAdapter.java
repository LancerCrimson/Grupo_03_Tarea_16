package com.example.grupo_03_tarea_16.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_Grupo_03_Tarea_16";







    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Context context;

    public DBAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public DBAdapter open() throws SQLiteException {
        try {
            db = databaseHelper.getWritableDatabase();
        }catch (SQLiteException ex){
            Toast.makeText(context, "Error al Abrir Base de Datos", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public boolean isOpen(){
        if(db == null){
            return false;
        }
        return db.isOpen();
    }

    public void close(){
        databaseHelper.close();
        db.close();
    }


}
