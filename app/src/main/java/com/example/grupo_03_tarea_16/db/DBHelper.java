package com.example.grupo_03_tarea_16.db;

import android.content.Context;

public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context) {dbAdapter = new DBAdapter(context); }

}
