package com.example.tpdm_u2_practica2_larreta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROPIETARIO(" +
                "TELEFONO VARCHAR(20) PRIMARY KEY," +
                "NOMBRE VARCHAR(200) NOT NULL," +
                "DOMICILIO VARCHAR(200)," +
                "FECHA DATE)");
        db.execSQL("CREATE TABLE SEGURO(" +
                "IDSEGURO VARCHAR(20) PRIMARY KEY," +
                "DESCRIPCION VARCHAR(200) NOT NULL," +
                "FECHA DATE," +
                "TIPO VARCHAR(10)," +
                "TELEFONO VARCHAR(20),"+
                "FOREIGN KEY (TELEFONO) REFERENCES PROPIETARIO(TELEFONO))");

        //select * from PROPIETARIO CROSS JOIN SEGURO WHERE SEGURO.TELEFONO = "3111428070"
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
