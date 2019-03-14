package com.example.tpdm_u2_practica2_larreta;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Seguro{

    private BaseDatos base;
    private String idseguro, descripcion, fecha, tiposeguro, telefono;
    protected String error;
    Seguro[] seg = null;
    Seguro s;
    Cursor c;

    public Seguro(Activity activity){
        base = new BaseDatos(activity, "aseguradora",null,1);
    }

    public Seguro(String id, String desc,  String fec, String tipo, String tel){
        idseguro = id;
        descripcion = desc;
        fecha = fec;
        tiposeguro = tipo;
        telefono = tel;
    }

    public String getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(String idseguro) {
        this.idseguro = idseguro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTiposeguro() {
        return tiposeguro;
    }

    public void setTiposeguro(String tiposeguro) {
        this.tiposeguro = tiposeguro;
    }

    public boolean insertar(Seguro aseg){
        //String mensaje="";
        try{
            SQLiteDatabase insertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("IDSEGURO",aseg.getIdseguro());
            datos.put("DESCRIPCION",aseg.getDescripcion());
            datos.put("FECHA",aseg.getFecha());
            datos.put("TIPO",aseg.getTiposeguro());
            datos.put("TELEFONO", aseg.getTelefono());

            long resultado = insertar.insert("SEGURO","IDSEGURO",datos);

            if(resultado == -1){
                return false;
            }
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public Seguro[] consultar(){
        try{
            seg = null;
            SQLiteDatabase busqueda = base.getReadableDatabase();
            String sql = "SELECT * FROM SEGURO";
            c = busqueda.rawQuery(sql,null);

            if(c.moveToFirst()){
                seg  = new Seguro[c.getCount()];
                int pos = 0;
                do{
                    seg[pos] = new Seguro(c.getString(0), c.getString(1),
                            c.getString(2), c.getString(3),c.getString(4));
                }while (c.moveToNext());
            }
            return seg;

        }catch(SQLiteException e){
            return null;
        }
    }

    public Seguro[] consultar(String columna, String clave){
        String sql="";
        SQLiteDatabase busqueda = base.getReadableDatabase();
        Cursor c = null;
        Seguro[] s= null;
        try{
            sql = "SELECT * FROM SEGURO WHERE " + columna + "=" + "'"+ clave +"'";
            c = busqueda.rawQuery(sql,null);

            if(c.moveToFirst()){
                s = new Seguro[c.getCount()];
                int pos = 0;
                do{
                    s[pos]=new Seguro(c.getString(0), c.getString(1),
                            c.getString(2), c.getString(3),c.getString(4));
                    pos++;
                }while(c.moveToNext());
            }
            return s;
        }catch (SQLiteException e){
            return null;
        }
    }

    public boolean actualizar(){
        return true;
    }

    public boolean eliminar(String id){
        int resultado;
        try{
            SQLiteDatabase eliminar = base.getWritableDatabase();
            String[] claves = {id+""};
            resultado = eliminar.delete("SEGURO","TELEFONO=?",claves);
            eliminar.close();

        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado > 0;
    }


}
