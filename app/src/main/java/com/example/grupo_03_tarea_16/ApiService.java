package com.example.grupo_03_tarea_16;

import okhttp3.Callback;

public interface ApiService {
    void getDataFromTable(String tableName, Callback callback);
}
