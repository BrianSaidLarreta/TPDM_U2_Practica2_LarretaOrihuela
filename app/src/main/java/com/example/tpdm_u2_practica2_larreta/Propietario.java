package com.example.tpdm_u2_practica2_larreta;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {
    private BaseDatos base;
    private String telefono, nombre, domicilio, fecha;
    protected String error;
    Propietario[] aseguradora = null;
    Propietario as;
    Cursor c;

    public Propietario(Activity activity){
        base = new BaseDatos(activity, "aseguradora",null,1);
    }

    public Propietario(String telefono, String nombre,  String domicilio, String fec){
        this.telefono = telefono;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.fecha = fec;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean insertar(Propietario aseg){
        //String mensaje="";
        try{
            SQLiteDatabase insertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO",aseg.getTelefono());
            datos.put("NOMBRE",aseg.getNombre());
            datos.put("DOMICILIO",aseg.getDomicilio());
            datos.put("FECHA",aseg.getFecha());

            long resultado = insertar.insert("PROPIETARIO","TELEFONO",datos);

            if(resultado == -1){
                return false;
            }
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public Propietario[] consultar(){
        try{
            Propietario [] as = null;
            SQLiteDatabase busqueda = base.getReadableDatabase();
            String sql = "SELECT * FROM PROPIETARIO";
            c = busqueda.rawQuery(sql,null);

            if(c.moveToFirst()){
                as  = new Propietario[c.getCount()];
                int pos = 0;
                do{
                    as[pos] = new Propietario(c.getString(0), c.getString(1),
                            c.getString(2), c.getString(3));
                }while (c.moveToNext());
            }
            return as;

        }catch(SQLiteException e){
            return null;
        }
    }

    public Propietario[] consultar(String columna, String clave){
        String sql="";
        SQLiteDatabase busqueda = base.getReadableDatabase();
        Cursor c = null;
        try{
            Propietario[] a = null;
            if(columna == "TELEFONO"){
                sql = "SELECT * FROM PROPIETARIO WHERE " + columna + "=" + "'"+ clave +"'";
                c = busqueda.rawQuery(sql,null);
            }else{
                sql = "SELECT * FROM PROPIETARIO WHERE " + columna + "="+ "'" + clave + "'";
                c = busqueda.rawQuery(sql,null);
            }
            if(c.moveToFirst()){
                a = new Propietario[c.getCount()];
                int pos = 0;
                do{
                    a[pos]=new Propietario(c.getString(0), c.getString(1),
                            c.getString(2),c.getString(3));
                    pos++;
                }while(c.moveToNext());
            }
            return a;
        }catch (SQLiteException e){
            return null;
        }
    }

    public boolean actualizar(Propietario prop){
        try{
            SQLiteDatabase actualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("NOMBRE",prop.getNombre());
            datos.put("DOMICILIO",prop.getDomicilio());
            datos.put("FECHA",prop.getFecha());

            long resultado = actualizar.update("PROPIETARIO",datos,"TELEFONO=?",new String[]{prop.getTelefono()});
            actualizar.close();

            if(resultado == -1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return  false;
        }
        return  true;
    }

    public boolean eliminar(String id){
        int resultado;
        try{
            SQLiteDatabase eliminar = base.getWritableDatabase();
            String[] claves = {id+""};
            resultado = eliminar.delete("SEGURO","TELEFONO=?",claves);
            resultado = eliminar.delete("PROPIETARIO","TELEFONO=?",claves);
            eliminar.close();

        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado > 0;
    }

}
