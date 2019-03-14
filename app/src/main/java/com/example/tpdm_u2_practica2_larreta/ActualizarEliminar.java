package com.example.tpdm_u2_practica2_larreta;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActualizarEliminar extends AppCompatActivity {
    String telefono;
    EditText nombre, domicilio, fecha;
    Button   actualizar, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_eliminar);

        nombre = findViewById(R.id.nombrepropietario);
        domicilio = findViewById(R.id.dompropietario);
        fecha = findViewById(R.id.fechapropietario);
        actualizar = findViewById(R.id.actualizarpropietario);
        eliminar = findViewById(R.id.eliminarpropietario);

        Bundle parametros = getIntent().getExtras();
        nombre.setText(parametros.getString("nombre"));
        domicilio.setText(parametros.getString("domicilio"));
        fecha.setText(parametros.getString("fecha"));
        telefono = parametros.getString("telefono");

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

    }

    private void actualizar(){
        String mensaje = "";
        Propietario propietario = new Propietario(this);
        if(vacios()){
            mensaje = "Verifique los datos ingresados, hay datos en blanco o vacíos";
        }else{
            boolean respuesta = propietario.actualizar(new Propietario(telefono,nombre.getText().toString(),
                    domicilio.getText().toString(), fecha.getText().toString()));
            if(respuesta){
                mensaje = "Se actualizó correctamente el Propietario: "+ nombre.getText().toString();
            }else{
                mensaje = "Error algo salió mal: " + propietario.error;
            }
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
    }

    private void eliminar(){
        String mensaje ="";
        Propietario propietario = new Propietario(this);
        boolean respuesta = propietario.eliminar(telefono);

        if(respuesta){
            mensaje = "Se Eliminó exitosamente el Propietario " + nombre.getText().toString();
        }else{
            mensaje = "Error! algo salió mal: \n" + propietario.error;
        }

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();

        nombre.setText("");
        domicilio.setText("");
        fecha.setText("");
    }

    private boolean vacios(){
        if(nombre.getText().toString().isEmpty()) return true;
        if(domicilio.getText().toString().isEmpty()) return true;
        if(fecha.getText().toString().isEmpty()) return true;

        return false;
    }
}
