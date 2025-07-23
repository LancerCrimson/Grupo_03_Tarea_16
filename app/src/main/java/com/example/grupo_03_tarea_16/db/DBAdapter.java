package com.example.grupo_03_tarea_16.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Acta;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.example.grupo_03_tarea_16.modelo.Infraccion;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Usuario;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.example.grupo_03_tarea_16.modelo.Zona;

import java.util.ArrayList;

public class DBAdapter {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "db_tarea_16";

    //NOMBRES DE LAS TABLAS
    private static final String TABLE_USUARIO = "usuario";
    private static final String TABLE_PROPIETARIO = "propietario";
    private static final String TABLE_ZONA = "zona";
    private static final String TABLE_AUDIENCIA = "audiencia";
    private static final String TABLE_NORMASDET = "normasdet";
    private static final String TABLE_VEHICULO = "vehiculo";
    private static final String TABLE_PUESDECONTROL = "puesdecontrol";
    private static final String TABLE_AGENTE = "agente";
    private static final String TABLE_INFRACCION = "infraccion";
    private static final String TABLE_OFICINAGOB = "oficinagob";
    private static final String TABLE_ACCIDENTE = "accidente";
    private static final String TABLE_ACTA = "acta";

    //ID PARA LAS TABLAS
    private static final String KEY_ID_USUARIO = "id_usuario";
    private static final String KEY_CEDULAP = "cedulap";
    private static final String KEY_ID_ZONA = "id_zona";
    private static final String KEY_ID_AUDIENCIA = "id_audiencia";
    private static final String KEY_ID_NORMA = "id_norma";
    private static final String KEY_NUMPLACA = "numplaca";
    private static final String KEY_ID_PUESDECONTROL = "id_puesdecontrol";
    private static final String KEY_ID_AGENTE = "id_agente";
    private static final String KEY_ID_INFRACCION = "id_infraccion";
    private static final String KEY_ID_OFICINAGOB = "id_oficinagob";
    private static final String KEY_ID_ACCIDENTE = "id_accidente";
    private static final String KEY_ID_ACTA = "id_acta";

    //ATRIBUTOS PARA LAS TABLAS
    private static final String KEY_USUARIO_NOMBRES = "nombres";
    private static final String KEY_USUARIO_APELLIDOS = "apellidos";
    private static final String KEY_USUARIO_CORREO = "correo";
    private static final String KEY_USUARIO_PASSWORD = "password";

    private static final String KEY_PROPIETARIO_NOMBRE = "nombre";
    private static final String KEY_PROPIETARIO_CIUDAD = "ciudad";

    private static final String KEY_ZONA_UBICACION = "ubicacion";

    private static final String KEY_AUDIENCIA_LUGAR = "lugar";
    private static final String KEY_AUDIENCIA_FECHA = "fecha";
    private static final String KEY_AUDIENCIA_HORA = "hora";

    private static final String KEY_NORMASDET_NUMNORMA = "numnorma";
    private static final String KEY_NORMASDET_DESCRIPCION = "descripcion";

    private static final String KEY_VEHICULO_MARCA = "marca";
    private static final String KEY_VEHICULO_MODELO = "modelo";
    private static final String KEY_VEHICULO_MOTOR = "motor";
    private static final String KEY_VEHICULO_YEAR = "year";
    private static final String KEY_VEHICULO_MEDIA = "media";
    private static final String KEY_VEHICULO_CEDULAP = "cedulap";

    private static final String KEY_PUESDECONTROL_ID_ZONA = "id_zona";
    private static final String KEY_PUESDECONTROL_UBICACION = "ubicacion";

    private static final String KEY_AGENTE_CEDULAA = "cedulaa";
    private static final String KEY_AGENTE_NOMBRE = "nombre";
    private static final String KEY_AGENTE_ID_PUESDECONTROL = "id_puesdecontrol";
    private static final String KEY_AGENTE_RANGO = "rango";

    private static final String KEY_INFRACCION_ID_AGENTE = "id_agente";
    private static final String KEY_INFRACCION_NUMPLACA = "numplaca";
    private static final String KEY_INFRACCION_VALORMULTA = "valormulta";
    private static final String KEY_INFRACCION_FECHA = "fecha";
    private static final String KEY_INFRACCION_ID_NORMA = "id_norma";
    private static final String KEY_INFRACCION_HORA = "hora";

    private static final String KEY_OFICINAGOB_VALORVEHICULO = "valorvehiculo";
    private static final String KEY_OFICINAGOB_NPOLIZA = "npoliza";
    private static final String KEY_OFICINAGOB_NUMPLACA = "numplaca";
    private static final String KEY_OFICINAGOB_UBICACION = "ubicacion";

    private static final String KEY_ACCIDENTE_NUMPLACA = "numplaca";
    private static final String KEY_ACCIDENTE_ID_AGENTE = "id_agente";
    private static final String KEY_ACCIDENTE_HORA = "hora";
    private static final String KEY_ACCIDENTE_FECHA = "fecha";
    private static final String KEY_ACCIDENTE_DESCRIPCION = "descripcion";
    private static final String KEY_ACCIDENTE_LATITUD = "latitud";
    private static final String KEY_ACCIDENTE_LONGITUD = "longitud";
    private static final String KEY_ACCIDENTE_MEDIA = "media";

    private static final String KEY_ACTA_ID_ACCIDENTE = "id_accidente";
    private static final String KEY_ACTA_ID_AUDIENCIA = "id_audiencia";
    private static final String KEY_ACTA_HORA = "hora";
    private static final String KEY_ACTA_ID_ZONA = "id_zona";
    private static final String KEY_ACTA_ID_AGENTE = "id_agente";
    private static final String KEY_ACTA_FECHA = "fecha";

    //CREACIÓN DE LAS TABLAS
    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE " + TABLE_USUARIO + " (" +
                    KEY_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USUARIO_NOMBRES + " TEXT NOT NULL, " +
                    KEY_USUARIO_APELLIDOS + " TEXT NOT NULL, " +
                    KEY_USUARIO_CORREO + " TEXT NOT NULL, " +
                    KEY_USUARIO_PASSWORD + " TEXT NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_PROPIETARIO =
            "CREATE TABLE " + TABLE_PROPIETARIO + " (" +
                    KEY_CEDULAP + " TEXT PRIMARY KEY, " +
                    KEY_PROPIETARIO_NOMBRE + " TEXT NOT NULL, " +
                    KEY_PROPIETARIO_CIUDAD + " TEXT NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_ZONA =
            "CREATE TABLE " + TABLE_ZONA + " (" +
                    KEY_ID_ZONA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ZONA_UBICACION + " TEXT NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_AUDIENCIA =
            "CREATE TABLE " + TABLE_AUDIENCIA + " (" +
                    KEY_ID_AUDIENCIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_AUDIENCIA_LUGAR + " TEXT NOT NULL, " +
                    KEY_AUDIENCIA_FECHA + " TEXT NOT NULL, " +
                    KEY_AUDIENCIA_HORA + " TEXT NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_NORMASDET =
            "CREATE TABLE " + TABLE_NORMASDET + " (" +
                    KEY_ID_NORMA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NORMASDET_NUMNORMA + " TEXT NOT NULL, " +
                    KEY_NORMASDET_DESCRIPCION + " TEXT NOT NULL" +
                    ")";

    private static final String CREATE_TABLE_VEHICULO =
            "CREATE TABLE " + TABLE_VEHICULO + " (" +
                    KEY_NUMPLACA + " TEXT PRIMARY KEY, " +
                    KEY_VEHICULO_MARCA + " TEXT NOT NULL, " +
                    KEY_VEHICULO_MODELO + " TEXT NOT NULL, " +
                    KEY_VEHICULO_MOTOR + " TEXT NOT NULL, " +
                    KEY_VEHICULO_YEAR + " INTEGER NOT NULL, " +
                    KEY_VEHICULO_MEDIA + " BLOB, " +
                    KEY_VEHICULO_CEDULAP + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_VEHICULO_CEDULAP + ") REFERENCES " +
                    TABLE_PROPIETARIO + "(" + KEY_CEDULAP + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_PUESDECONTROL =
            "CREATE TABLE " + TABLE_PUESDECONTROL + " (" +
                    KEY_ID_PUESDECONTROL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_PUESDECONTROL_ID_ZONA + " INTEGER NOT NULL, " +
                    KEY_PUESDECONTROL_UBICACION + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_PUESDECONTROL_ID_ZONA + ") REFERENCES " +
                    TABLE_ZONA + "(" + KEY_ID_ZONA + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_AGENTE =
            "CREATE TABLE " + TABLE_AGENTE + " (" +
                    KEY_ID_AGENTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_AGENTE_CEDULAA + " TEXT NOT NULL, " +
                    KEY_AGENTE_NOMBRE + " TEXT NOT NULL, " +
                    KEY_AGENTE_ID_PUESDECONTROL + " INTEGER NOT NULL, " +
                    KEY_AGENTE_RANGO + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_AGENTE_ID_PUESDECONTROL + ") REFERENCES " +
                    TABLE_PUESDECONTROL + "(" + KEY_ID_PUESDECONTROL + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_INFRACCION =
            "CREATE TABLE " + TABLE_INFRACCION + " (" +
                    KEY_ID_INFRACCION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_INFRACCION_ID_AGENTE + " INTEGER NOT NULL, " +
                    KEY_INFRACCION_NUMPLACA + " TEXT NOT NULL, " +
                    KEY_INFRACCION_VALORMULTA + " REAL NOT NULL, " +
                    KEY_INFRACCION_FECHA + " TEXT NOT NULL, " +
                    KEY_INFRACCION_ID_NORMA + " INTEGER NOT NULL, " +
                    KEY_INFRACCION_HORA + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_INFRACCION_ID_AGENTE + ") REFERENCES " +
                    TABLE_AGENTE + "(" + KEY_ID_AGENTE + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_INFRACCION_NUMPLACA + ") REFERENCES " +
                    TABLE_VEHICULO + "(" + KEY_NUMPLACA + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_INFRACCION_ID_NORMA + ") REFERENCES " +
                    TABLE_NORMASDET + "(" + KEY_ID_NORMA + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_OFICINAGOB =
            "CREATE TABLE " + TABLE_OFICINAGOB + " (" +
                    KEY_ID_OFICINAGOB + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_OFICINAGOB_VALORVEHICULO + " REAL NOT NULL, " +
                    KEY_OFICINAGOB_NPOLIZA + " TEXT NOT NULL, " +
                    KEY_OFICINAGOB_NUMPLACA + " TEXT NOT NULL, " +
                    KEY_OFICINAGOB_UBICACION + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_OFICINAGOB_NUMPLACA + ") REFERENCES " +
                    TABLE_VEHICULO + "(" + KEY_NUMPLACA + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_ACCIDENTE =
            "CREATE TABLE " + TABLE_ACCIDENTE + " (" +
                    KEY_ID_ACCIDENTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ACCIDENTE_NUMPLACA + " TEXT NOT NULL, " +
                    KEY_ACCIDENTE_ID_AGENTE + " INTEGER NOT NULL, " +
                    KEY_ACCIDENTE_HORA + " TEXT NOT NULL, " +
                    KEY_ACCIDENTE_FECHA + " TEXT NOT NULL, " +
                    KEY_ACCIDENTE_DESCRIPCION + " TEXT NOT NULL, " +
                    KEY_ACCIDENTE_LATITUD + " REAL NOT NULL, " +
                    KEY_ACCIDENTE_LONGITUD + " REAL NOT NULL, " +
                    KEY_ACCIDENTE_MEDIA + " BLOB, " +
                    "FOREIGN KEY (" + KEY_ACCIDENTE_NUMPLACA + ") REFERENCES " +
                    TABLE_VEHICULO + "(" + KEY_NUMPLACA + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_ACCIDENTE_ID_AGENTE + ") REFERENCES " +
                    TABLE_AGENTE + "(" + KEY_ID_AGENTE + ") ON DELETE CASCADE" +
                    ")";

    private static final String CREATE_TABLE_ACTA =
            "CREATE TABLE " + TABLE_ACTA + " (" +
                    KEY_ID_ACTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ACTA_ID_ACCIDENTE + " INTEGER NOT NULL, " +
                    KEY_ACTA_ID_AUDIENCIA + " INTEGER NOT NULL, " +
                    KEY_ACTA_HORA + " TEXT NOT NULL, " +
                    KEY_ACTA_ID_ZONA + " INTEGER NOT NULL, " +
                    KEY_ACTA_ID_AGENTE + " INTEGER NOT NULL, " +
                    KEY_ACTA_FECHA + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + KEY_ACTA_ID_ACCIDENTE + ") REFERENCES " +
                    TABLE_ACCIDENTE + "(" + KEY_ID_ACCIDENTE + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_ACTA_ID_AUDIENCIA + ") REFERENCES " +
                    TABLE_AUDIENCIA + "(" + KEY_ID_AUDIENCIA + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_ACTA_ID_ZONA + ") REFERENCES " +
                    TABLE_ZONA + "(" + KEY_ID_ZONA + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + KEY_ACTA_ID_AGENTE + ") REFERENCES " +
                    TABLE_AGENTE + "(" + KEY_ID_AGENTE + ") ON DELETE CASCADE" +
                    ")";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static Context context;

    public DBAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true); // Activar claves foráneas
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USUARIO);
            db.execSQL(CREATE_TABLE_PROPIETARIO);
            db.execSQL(CREATE_TABLE_ZONA);
            db.execSQL(CREATE_TABLE_AUDIENCIA);
            db.execSQL(CREATE_TABLE_NORMASDET);
            db.execSQL(CREATE_TABLE_VEHICULO);
            db.execSQL(CREATE_TABLE_PUESDECONTROL);
            db.execSQL(CREATE_TABLE_AGENTE);
            db.execSQL(CREATE_TABLE_INFRACCION);
            db.execSQL(CREATE_TABLE_OFICINAGOB);
            db.execSQL(CREATE_TABLE_ACCIDENTE);
            db.execSQL(CREATE_TABLE_ACTA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENTE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFICINAGOB);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFRACCION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENTE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUESDECONTROL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICULO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NORMASDET);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUDIENCIA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZONA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPIETARIO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLiteException {
        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            Toast.makeText(context, "Error al Abrir Base de Datos", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public boolean isOpen() {
        if (db == null) {
            return false;
        }
        return db.isOpen();
    }

    public void close() {
        databaseHelper.close();
        db.close();
    }

    // ========== MÉTODOS PARA USUARIO ==========

    public long InsertarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO_NOMBRES, usuario.getNombres());
        values.put(KEY_USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(KEY_USUARIO_CORREO, usuario.getCorreo());
        values.put(KEY_USUARIO_PASSWORD, usuario.getPassword());
        return db.insert(TABLE_USUARIO, null, values);
    }

    public void EditarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO_NOMBRES, usuario.getNombres());
        values.put(KEY_USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(KEY_USUARIO_CORREO, usuario.getCorreo());
        values.put(KEY_USUARIO_PASSWORD, usuario.getPassword());
        db.update(TABLE_USUARIO, values,
                KEY_ID_USUARIO + " = " + usuario.getIdUsuario(), null);
    }

    public void EliminarUsuario(Usuario usuario) {
        db.delete(TABLE_USUARIO,
                KEY_ID_USUARIO + " = " + usuario.getIdUsuario(), null);
    }

    public Usuario get_Usuario(int id) {
        Usuario usuario = null;
        try {
            String query = "SELECT * FROM " + TABLE_USUARIO + " WHERE " + KEY_ID_USUARIO + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getInt(0));
                usuario.setNombres(cursor.getString(1));
                usuario.setApellidos(cursor.getString(2));
                usuario.setCorreo(cursor.getString(3));
                usuario.setPassword(cursor.getString(4));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener usuario", e);
        }
        return usuario;
    }

    public ArrayList<Usuario> get_all_Usuario() {
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_USUARIO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(cursor.getInt(0));
                    usuario.setNombres(cursor.getString(1));
                    usuario.setApellidos(cursor.getString(2));
                    usuario.setCorreo(cursor.getString(3));
                    usuario.setPassword(cursor.getString(4));
                    lista.add(usuario);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar usuarios", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA PROPIETARIO ==========

    public long InsertarPropietario(Propietario propietario) {
        ContentValues values = new ContentValues();
        values.put(KEY_CEDULAP, propietario.getCedulaP());
        values.put(KEY_PROPIETARIO_NOMBRE, propietario.getNombre());
        values.put(KEY_PROPIETARIO_CIUDAD, propietario.getCiudad());
        return db.insert(TABLE_PROPIETARIO, null, values);
    }

    public void EditarPropietario(Propietario propietario) {
        ContentValues values = new ContentValues();
        values.put(KEY_PROPIETARIO_NOMBRE, propietario.getNombre());
        values.put(KEY_PROPIETARIO_CIUDAD, propietario.getCiudad());
        db.update(TABLE_PROPIETARIO, values,
                KEY_CEDULAP + " = '" + propietario.getCedulaP() + "'", null);
    }

    public void EliminarPropietario(Propietario propietario) {
        db.delete(TABLE_PROPIETARIO,
                KEY_CEDULAP + " = '" + propietario.getCedulaP() + "'", null);
    }

    public Propietario get_Propietario(String cedulaP) {
        Propietario propietario = null;
        try {
            String query = "SELECT * FROM " + TABLE_PROPIETARIO + " WHERE " + KEY_CEDULAP + " = '" + cedulaP + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                propietario = new Propietario();
                propietario.setCedulaP(cursor.getString(0));
                propietario.setNombre(cursor.getString(1));
                propietario.setCiudad(cursor.getString(2));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener propietario", e);
        }
        return propietario;
    }

    public ArrayList<Propietario> get_all_Propietario() {
        ArrayList<Propietario> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_PROPIETARIO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Propietario propietario = new Propietario();
                    propietario.setCedulaP(cursor.getString(0));
                    propietario.setNombre(cursor.getString(1));
                    propietario.setCiudad(cursor.getString(2));
                    lista.add(propietario);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar propietarios", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA ZONA ==========

    public long InsertarZona(Zona zona) {
        ContentValues values = new ContentValues();
        values.put(KEY_ZONA_UBICACION, zona.getUbicacion());
        return db.insert(TABLE_ZONA, null, values);
    }

    public void EditarZona(Zona zona) {
        ContentValues values = new ContentValues();
        values.put(KEY_ZONA_UBICACION, zona.getUbicacion());
        db.update(TABLE_ZONA, values,
                KEY_ID_ZONA + " = " + zona.getIdZona(), null);
    }

    public void EliminarZona(Zona zona) {
        db.delete(TABLE_ZONA,
                KEY_ID_ZONA + " = " + zona.getIdZona(), null);
    }

    public Zona get_Zona(int idZona) {
        Zona zona = null;
        try {
            String query = "SELECT * FROM " + TABLE_ZONA + " WHERE " + KEY_ID_ZONA + " = " + idZona;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                zona = new Zona();
                zona.setIdZona(cursor.getInt(0));
                zona.setUbicacion(cursor.getString(1));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener zona", e);
        }
        return zona;
    }

    public ArrayList<Zona> get_all_Zona() {
        ArrayList<Zona> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_ZONA;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Zona zona = new Zona();
                    zona.setIdZona(cursor.getInt(0));
                    zona.setUbicacion(cursor.getString(1));
                    lista.add(zona);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar zonas", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA AUDIENCIA ==========

    public long InsertarAudiencia(Audiencia audiencia) {
        ContentValues values = new ContentValues();
        values.put(KEY_AUDIENCIA_LUGAR, audiencia.getLugar());
        values.put(KEY_AUDIENCIA_FECHA, audiencia.getFecha());
        values.put(KEY_AUDIENCIA_HORA, audiencia.getHora());
        return db.insert(TABLE_AUDIENCIA, null, values);
    }

    public void EditarAudiencia(Audiencia audiencia) {
        ContentValues values = new ContentValues();
        values.put(KEY_AUDIENCIA_LUGAR, audiencia.getLugar());
        values.put(KEY_AUDIENCIA_FECHA, audiencia.getFecha());
        values.put(KEY_AUDIENCIA_HORA, audiencia.getHora());
        db.update(TABLE_AUDIENCIA, values,
                KEY_ID_AUDIENCIA + " = " + audiencia.getIdAudiencia(), null);
    }

    public void EliminarAudiencia(Audiencia audiencia) {
        db.delete(TABLE_AUDIENCIA,
                KEY_ID_AUDIENCIA + " = " + audiencia.getIdAudiencia(), null);
    }

    public Audiencia get_Audiencia(int id) {
        Audiencia audiencia = null;
        try {
            String query = "SELECT * FROM " + TABLE_AUDIENCIA + " WHERE " + KEY_ID_AUDIENCIA + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                audiencia = new Audiencia();
                audiencia.setIdAudiencia(cursor.getInt(0));
                audiencia.setLugar(cursor.getString(1));
                audiencia.setFecha(cursor.getString(2));
                audiencia.setHora(cursor.getString(3));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener audiencia", e);
        }
        return audiencia;
    }

    public ArrayList<Audiencia> get_all_Audiencia() {
        ArrayList<Audiencia> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_AUDIENCIA;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Audiencia audiencia = new Audiencia();
                    audiencia.setIdAudiencia(cursor.getInt(0));
                    audiencia.setLugar(cursor.getString(1));
                    audiencia.setFecha(cursor.getString(2));
                    audiencia.setHora(cursor.getString(3));
                    lista.add(audiencia);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar audiencias", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA NORMASDET ==========

    public long InsertarNorma(NormasDeT norma) {
        ContentValues values = new ContentValues();
        values.put(KEY_NORMASDET_NUMNORMA, norma.getNumNorma());
        values.put(KEY_NORMASDET_DESCRIPCION, norma.getDescripcion());
        return db.insert(TABLE_NORMASDET, null, values);
    }

    public void EditarNorma(NormasDeT norma) {
        ContentValues values = new ContentValues();
        values.put(KEY_NORMASDET_NUMNORMA, norma.getNumNorma());
        values.put(KEY_NORMASDET_DESCRIPCION, norma.getDescripcion());
        db.update(TABLE_NORMASDET, values,
                KEY_ID_NORMA + " = " + norma.getIdNorma(), null);
    }

    public void EliminarNorma(NormasDeT norma) {
        db.delete(TABLE_NORMASDET,
                KEY_ID_NORMA + " = " + norma.getIdNorma(), null);
    }

    public NormasDeT get_Norma(int id) {
        NormasDeT norma = null;
        try {
            String query = "SELECT * FROM " + TABLE_NORMASDET + " WHERE " + KEY_ID_NORMA + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                norma = new NormasDeT();
                norma.setIdNorma(cursor.getInt(0));
                norma.setNumNorma(cursor.getString(1));
                norma.setDescripcion(cursor.getString(2));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener norma", e);
        }
        return norma;
    }

    public ArrayList<NormasDeT> get_all_Norma() {
        ArrayList<NormasDeT> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_NORMASDET;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    NormasDeT norma = new NormasDeT();
                    norma.setIdNorma(cursor.getInt(0));
                    norma.setNumNorma(cursor.getString(1));
                    norma.setDescripcion(cursor.getString(2));
                    lista.add(norma);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar normas", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA VEHICULO ==========

    public long InsertarVehiculo(Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put(KEY_NUMPLACA, vehiculo.getNumPlaca());
        values.put(KEY_VEHICULO_MARCA, vehiculo.getMarca());
        values.put(KEY_VEHICULO_MODELO, vehiculo.getModelo());
        values.put(KEY_VEHICULO_MOTOR, vehiculo.getMotor());
        values.put(KEY_VEHICULO_YEAR, vehiculo.getYear());
        values.put(KEY_VEHICULO_MEDIA, vehiculo.getMedia());
        values.put(KEY_VEHICULO_CEDULAP, vehiculo.getCedulaP());
        return db.insert(TABLE_VEHICULO, null, values);
    }

    public void EditarVehiculo(Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put(KEY_VEHICULO_MARCA, vehiculo.getMarca());
        values.put(KEY_VEHICULO_MODELO, vehiculo.getModelo());
        values.put(KEY_VEHICULO_MOTOR, vehiculo.getMotor());
        values.put(KEY_VEHICULO_YEAR, vehiculo.getYear());
        values.put(KEY_VEHICULO_MEDIA, vehiculo.getMedia());
        values.put(KEY_VEHICULO_CEDULAP, vehiculo.getCedulaP());
        db.update(TABLE_VEHICULO, values,
                KEY_NUMPLACA + " = '" + vehiculo.getNumPlaca() + "'", null);
    }

    public void EliminarVehiculo(Vehiculo vehiculo) {
        db.delete(TABLE_VEHICULO,
                KEY_NUMPLACA + " = '" + vehiculo.getNumPlaca() + "'", null);
    }


    public Vehiculo get_Vehiculo(String numPlaca) {
        Vehiculo vehiculo = null;
        try {
            String query = "SELECT * FROM " + TABLE_VEHICULO + " WHERE " + KEY_NUMPLACA + " = '" + numPlaca + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                vehiculo = new Vehiculo();
                vehiculo.setNumPlaca(cursor.getString(0));
                vehiculo.setMarca(cursor.getString(1));
                vehiculo.setModelo(cursor.getString(2));
                vehiculo.setMotor(cursor.getString(3));
                vehiculo.setYear(cursor.getInt(4));
                vehiculo.setMedia(cursor.getBlob(5));
                vehiculo.setCedulaP(cursor.getString(6));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener vehículo", e);
        }
        return vehiculo;
    }

    public ArrayList<Vehiculo> get_all_Vehiculo() {
        ArrayList<Vehiculo> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_VEHICULO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setNumPlaca(cursor.getString(0));
                    vehiculo.setMarca(cursor.getString(1));
                    vehiculo.setModelo(cursor.getString(2));
                    vehiculo.setMotor(cursor.getString(3));
                    vehiculo.setYear(cursor.getInt(4));
                    vehiculo.setMedia(cursor.getBlob(5));
                    vehiculo.setCedulaP(cursor.getString(6));
                    lista.add(vehiculo);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar vehículos", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA PUESDECONTROL ==========

    public long InsertarPuestoDeControl(PuesDeControl pdc) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUESDECONTROL_ID_ZONA, pdc.getIdZona());
        values.put(KEY_PUESDECONTROL_UBICACION, pdc.getUbicacion());
        return db.insert(TABLE_PUESDECONTROL, null, values);
    }

    public void EditarPuesDeControl(PuesDeControl puesto) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUESDECONTROL_ID_ZONA, puesto.getIdZona());
        values.put(KEY_PUESDECONTROL_UBICACION, puesto.getUbicacion());
        db.update(TABLE_PUESDECONTROL, values,
                KEY_ID_PUESDECONTROL + " = " + puesto.getIdPuestoControl(), null);
    }

    public void EliminarPuesDeControl(PuesDeControl puesto) {
        db.delete(TABLE_PUESDECONTROL,
                KEY_ID_PUESDECONTROL + " = " + puesto.getIdPuestoControl(), null);
    }

    public PuesDeControl get_PuesDeControl(int id) {
        PuesDeControl puesto = null;
        try {
            String query = "SELECT * FROM " + TABLE_PUESDECONTROL + " WHERE " + KEY_ID_PUESDECONTROL + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                puesto = new PuesDeControl();
                puesto.setIdPuestoControl(cursor.getInt(0));
                puesto.setIdZona(cursor.getInt(1));
                puesto.setUbicacion(cursor.getString(2));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener puesto de control", e);
        }
        return puesto;
    }

    public ArrayList<PuesDeControl> get_all_PuesDeControl() {
        ArrayList<PuesDeControl> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_PUESDECONTROL;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    PuesDeControl puesto = new PuesDeControl();
                    puesto.setIdPuestoControl(cursor.getInt(0));
                    puesto.setIdZona(cursor.getInt(1));
                    puesto.setUbicacion(cursor.getString(2));
                    lista.add(puesto);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar puestos de control", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA AGENTE ==========

    public long InsertarAgente(Agente agente) {
        ContentValues values = new ContentValues();
        values.put(KEY_AGENTE_CEDULAA, agente.getCedulaA());
        values.put(KEY_AGENTE_NOMBRE, agente.getNombre());
        values.put(KEY_AGENTE_ID_PUESDECONTROL, agente.getIdPuestoControl());
        values.put(KEY_AGENTE_RANGO, agente.getRango());
        return db.insert(TABLE_AGENTE, null, values);
    }

    public void EditarAgente(Agente agente) {
        ContentValues values = new ContentValues();
        values.put(KEY_AGENTE_CEDULAA, agente.getCedulaA());
        values.put(KEY_AGENTE_NOMBRE, agente.getNombre());
        values.put(KEY_AGENTE_ID_PUESDECONTROL, agente.getIdPuestoControl());
        values.put(KEY_AGENTE_RANGO, agente.getRango());
        db.update(TABLE_AGENTE, values,
                KEY_ID_AGENTE + " = " + agente.getIdAgente(), null);
    }

    public void EliminarAgente(Agente agente) {
        db.delete(TABLE_AGENTE,
                KEY_ID_AGENTE + " = " + agente.getIdAgente(), null);
    }

    public Agente get_Agente(int id) {
        Agente agente = null;
        try {
            String query = "SELECT * FROM " + TABLE_AGENTE + " WHERE " + KEY_ID_AGENTE + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                agente = new Agente();
                agente.setIdAgente(cursor.getInt(0));
                agente.setCedulaA(cursor.getString(1));
                agente.setNombre(cursor.getString(2));
                agente.setIdPuestoControl(cursor.getInt(3));
                agente.setRango(cursor.getString(4));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener agente", e);
        }
        return agente;
    }

    public ArrayList<Agente> get_all_Agente() {
        ArrayList<Agente> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_AGENTE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Agente agente = new Agente();
                    agente.setIdAgente(cursor.getInt(0));
                    agente.setCedulaA(cursor.getString(1));
                    agente.setNombre(cursor.getString(2));
                    agente.setIdPuestoControl(cursor.getInt(3));
                    agente.setRango(cursor.getString(4));
                    lista.add(agente);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar agentes", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA INFRACCION ==========

    public long InsertarInfraccion(Infraccion infraccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_INFRACCION_ID_AGENTE, infraccion.getIdAgente());
        values.put(KEY_NUMPLACA, infraccion.getNumPlaca());
        values.put(KEY_INFRACCION_VALORMULTA, infraccion.getValorMulta());
        values.put(KEY_INFRACCION_FECHA, infraccion.getFecha());
        values.put(KEY_INFRACCION_ID_NORMA, infraccion.getIdNorma());
        values.put(KEY_INFRACCION_HORA, infraccion.getHora());
        return db.insert(TABLE_INFRACCION, null, values);
    }

    public void EditarInfraccion(Infraccion infraccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_INFRACCION_ID_AGENTE, infraccion.getIdAgente());
        values.put(KEY_INFRACCION_NUMPLACA, infraccion.getNumPlaca());
        values.put(KEY_INFRACCION_VALORMULTA, infraccion.getValorMulta());
        values.put(KEY_INFRACCION_FECHA, infraccion.getFecha());
        values.put(KEY_INFRACCION_ID_NORMA, infraccion.getIdNorma());
        values.put(KEY_INFRACCION_HORA, infraccion.getHora());
        db.update(TABLE_INFRACCION, values,
                KEY_ID_INFRACCION + " = " + infraccion.getIdInfraccion(), null);
    }

    public void EliminarInfraccion(Infraccion infraccion) {
        db.delete(TABLE_INFRACCION,
                KEY_ID_INFRACCION + " = " + infraccion.getIdInfraccion(), null);
    }

    public Infraccion get_Infraccion(int id) {
        Infraccion infraccion = null;
        try {
            String query = "SELECT * FROM " + TABLE_INFRACCION + " WHERE " + KEY_ID_INFRACCION + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                infraccion = new Infraccion();
                infraccion.setIdInfraccion(cursor.getInt(0));
                infraccion.setIdAgente(cursor.getInt(1));
                infraccion.setNumPlaca(cursor.getString(2));
                infraccion.setValorMulta(cursor.getDouble(3));
                infraccion.setFecha(cursor.getString(4));
                infraccion.setIdNorma(cursor.getInt(5));
                infraccion.setHora(cursor.getString(6));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener infracción", e);
        }
        return infraccion;
    }

    public ArrayList<Infraccion> get_all_Infraccion() {
        ArrayList<Infraccion> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_INFRACCION;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Infraccion infraccion = new Infraccion();
                    infraccion.setIdInfraccion(cursor.getInt(0));
                    infraccion.setIdAgente(cursor.getInt(1));
                    infraccion.setNumPlaca(cursor.getString(2));
                    infraccion.setValorMulta(cursor.getDouble(3));
                    infraccion.setFecha(cursor.getString(4));
                    infraccion.setIdNorma(cursor.getInt(5));
                    infraccion.setHora(cursor.getString(6));
                    lista.add(infraccion);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar infracciones", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA OFICINAGOB ==========

    public long InsertarOficinaGob(OficinaGob oficina) {
        ContentValues values = new ContentValues();
        values.put(KEY_OFICINAGOB_VALORVEHICULO, oficina.getValorVehiculo());
        values.put(KEY_OFICINAGOB_NPOLIZA, oficina.getnPoliza());
        values.put(KEY_OFICINAGOB_NUMPLACA, oficina.getNumPlaca());
        values.put(KEY_OFICINAGOB_UBICACION, oficina.getUbicacion());
        return db.insert(TABLE_OFICINAGOB, null, values);
    }

    public void EditarOficinaGob(OficinaGob oficina) {
        ContentValues values = new ContentValues();
        values.put(KEY_OFICINAGOB_VALORVEHICULO, oficina.getValorVehiculo());
        values.put(KEY_OFICINAGOB_NPOLIZA, oficina.getnPoliza());
        values.put(KEY_OFICINAGOB_NUMPLACA, oficina.getNumPlaca());
        values.put(KEY_OFICINAGOB_UBICACION, oficina.getUbicacion());
        db.update(TABLE_OFICINAGOB, values,
                KEY_ID_OFICINAGOB + " = " + oficina.getIdOficinaGob(), null);
    }

    public void EliminarOficinaGob(OficinaGob oficina) {
        db.delete(TABLE_OFICINAGOB,
                KEY_ID_OFICINAGOB + " = " + oficina.getIdOficinaGob(), null);
    }

    public OficinaGob get_OficinaGob(int id) {
        OficinaGob oficina = null;
        try {
            String query = "SELECT * FROM " + TABLE_OFICINAGOB + " WHERE " + KEY_ID_OFICINAGOB + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                oficina = new OficinaGob();
                oficina.setIdOficinaGob(cursor.getInt(0));
                oficina.setValorVehiculo(cursor.getDouble(1));
                oficina.setnPoliza(cursor.getString(2));
                oficina.setNumPlaca(cursor.getString(3));
                oficina.setUbicacion(cursor.getString(4));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener OficinaGob", e);
        }
        return oficina;
    }

    public ArrayList<OficinaGob> get_all_OficinaGob() {
        ArrayList<OficinaGob> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_OFICINAGOB;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    OficinaGob oficina = new OficinaGob();
                    oficina.setIdOficinaGob(cursor.getInt(0));
                    oficina.setValorVehiculo(cursor.getDouble(1));
                    oficina.setnPoliza(cursor.getString(2));
                    oficina.setNumPlaca(cursor.getString(3));
                    oficina.setUbicacion(cursor.getString(4));
                    lista.add(oficina);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar oficinas", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA ACCIDENTE ==========

    public long InsertarAccidente(Accidente accidente) {
        ContentValues values = new ContentValues();
        values.put(KEY_ACCIDENTE_NUMPLACA, accidente.getNumPlaca());
        values.put(KEY_ACCIDENTE_ID_AGENTE, accidente.getIdAgente());
        values.put(KEY_ACCIDENTE_HORA, accidente.getHora());
        values.put(KEY_ACCIDENTE_FECHA, accidente.getFecha());
        values.put(KEY_ACCIDENTE_DESCRIPCION, accidente.getDescripcion());
        values.put(KEY_ACCIDENTE_LATITUD, accidente.getLatitud());
        values.put(KEY_ACCIDENTE_LONGITUD, accidente.getLongitud());
        values.put(KEY_ACCIDENTE_MEDIA, accidente.getMedia());
        return db.insert(TABLE_ACCIDENTE, null, values);
    }

    public void EditarAccidente(Accidente accidente) {
        ContentValues values = new ContentValues();
        values.put(KEY_ACCIDENTE_NUMPLACA, accidente.getNumPlaca());
        values.put(KEY_ACCIDENTE_ID_AGENTE, accidente.getIdAgente());
        values.put(KEY_ACCIDENTE_HORA, accidente.getHora());
        values.put(KEY_ACCIDENTE_FECHA, accidente.getFecha());
        values.put(KEY_ACCIDENTE_DESCRIPCION, accidente.getDescripcion());
        values.put(KEY_ACCIDENTE_LATITUD, accidente.getLatitud());
        values.put(KEY_ACCIDENTE_LONGITUD, accidente.getLongitud());
        values.put(KEY_ACCIDENTE_MEDIA, accidente.getMedia());
        db.update(TABLE_ACCIDENTE, values,
                KEY_ID_ACCIDENTE + " = " + accidente.getIdAccidente(), null);
    }

    public void EliminarAccidente(Accidente accidente) {
        db.delete(TABLE_ACCIDENTE,
                KEY_ID_ACCIDENTE + " = " + accidente.getIdAccidente(), null);
    }

    public Accidente get_Accidente(int id) {
        Accidente accidente = null;
        try {
            String query = "SELECT * FROM " + TABLE_ACCIDENTE + " WHERE " + KEY_ID_ACCIDENTE + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                accidente = new Accidente();
                accidente.setIdAccidente(cursor.getInt(0));
                accidente.setNumPlaca(cursor.getString(1));
                accidente.setIdAgente(cursor.getInt(2));
                accidente.setHora(cursor.getString(3));
                accidente.setFecha(cursor.getString(4));
                accidente.setDescripcion(cursor.getString(5));
                accidente.setLatitud(cursor.getDouble(6));
                accidente.setLongitud(cursor.getDouble(7));
                accidente.setMedia(cursor.getBlob(8));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener accidente", e);
        }
        return accidente;
    }

    public ArrayList<Accidente> get_all_Accidente() {
        ArrayList<Accidente> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_ACCIDENTE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Accidente accidente = new Accidente();
                    accidente.setIdAccidente(cursor.getInt(0));
                    accidente.setNumPlaca(cursor.getString(1));
                    accidente.setIdAgente(cursor.getInt(2));
                    accidente.setHora(cursor.getString(3));
                    accidente.setFecha(cursor.getString(4));
                    accidente.setDescripcion(cursor.getString(5));
                    accidente.setLatitud(cursor.getDouble(6));
                    accidente.setLongitud(cursor.getDouble(7));
                    accidente.setMedia(cursor.getBlob(8));
                    lista.add(accidente);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar accidentes", e);
        }
        return lista;
    }

    // ========== MÉTODOS PARA ACTA ==========

    public long InsertarActa(Acta acta) {
        ContentValues values = new ContentValues();
        values.put(KEY_ACTA_ID_ACCIDENTE, acta.getIdAccidente());
        values.put(KEY_ACTA_ID_AUDIENCIA, acta.getIdAudiencia());
        values.put(KEY_ACTA_HORA, acta.getHora());
        values.put(KEY_ACTA_ID_ZONA, acta.getIdZona());
        values.put(KEY_ACTA_ID_AGENTE, acta.getIdAgente());
        values.put(KEY_ACTA_FECHA, acta.getFecha());
        return db.insert(TABLE_ACTA, null, values);
    }

    public void EditarActa(Acta acta) {
        ContentValues values = new ContentValues();
        values.put(KEY_ACTA_ID_ACCIDENTE, acta.getIdAccidente());
        values.put(KEY_ACTA_ID_AUDIENCIA, acta.getIdAudiencia());
        values.put(KEY_ACTA_HORA, acta.getHora());
        values.put(KEY_ACTA_ID_ZONA, acta.getIdZona());
        values.put(KEY_ACTA_ID_AGENTE, acta.getIdAgente());
        values.put(KEY_ACTA_FECHA, acta.getFecha());
        db.update(TABLE_ACTA, values,
                KEY_ID_ACTA + " = " + acta.getIdActa(), null);
    }

    public void EliminarActa(Acta acta) {
        db.delete(TABLE_ACTA,
                KEY_ID_ACTA + " = " + acta.getIdActa(), null);
    }

    public Acta get_Acta(int id) {
        Acta acta = null;
        try {
            String query = "SELECT * FROM " + TABLE_ACTA + " WHERE " + KEY_ID_ACTA + " = " + id;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                acta = new Acta();
                acta.setIdActa(cursor.getInt(0));
                acta.setIdAccidente(cursor.getInt(1));
                acta.setIdAudiencia(cursor.getInt(2));
                acta.setHora(cursor.getString(3));
                acta.setIdZona(cursor.getInt(4));
                acta.setIdAgente(cursor.getInt(5));
                acta.setFecha(cursor.getString(6));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al obtener acta", e);
        }
        return acta;
    }

    public ArrayList<Acta> get_all_Acta() {
        ArrayList<Acta> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_ACTA;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Acta acta = new Acta();
                    acta.setIdActa(cursor.getInt(0));
                    acta.setIdAccidente(cursor.getInt(1));
                    acta.setIdAudiencia(cursor.getInt(2));
                    acta.setHora(cursor.getString(3));
                    acta.setIdZona(cursor.getInt(4));
                    acta.setIdAgente(cursor.getInt(5));
                    acta.setFecha(cursor.getString(6));
                    lista.add(acta);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DB", "Error al listar actas", e);
        }
        return lista;
    }
}