package com.example.tpdm_u2_practica2_larreta;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Registro extends AppCompatActivity {

    EditText nombre, telefono, domicilio, fecha, id, descripcion;
    Spinner tiposeguro;
    Button registrar, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.nombrepropietario);
        telefono = findViewById(R.id.telpropietario);
        domicilio = findViewById(R.id.dompropietario);
        fecha = findViewById(R.id.fechapropietario);
        id = findViewById(R.id.idseguro);
        descripcion = findViewById(R.id.descSeguro);
        tiposeguro = findViewById(R.id.seguros);
        registrar = findViewById(R.id.registrarPropietario);
        regresar = findViewById(R.id.regresarRegistro);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void registrar() {
        String mensaje = "";
        if (vacios())
            mensaje = "Verifique los datos ingresados, hay datos en blanco o vacíos";
        else {
            Propietario p = new Propietario(this);
            boolean respuesta = p.insertar(new Propietario(telefono.getText().toString(), nombre.getText().toString(),
                    domicilio.getText().toString(), fecha.getText().toString()));

            Seguro s = new Seguro(this);
            boolean respuesta2 = s.insertar(new Seguro(id.getText().toString(),descripcion.getText().toString(),
                    fecha.getText().toString(),tiposeguro.getSelectedItem().toString().toUpperCase(),
                    telefono.getText().toString()));
            if (respuesta && respuesta2) {
                mensaje = "Propietario" + nombre.getText().toString() + "Registrado con Éxito";
            } else {
                mensaje = "Error! No se ha podido registrar el Propietario: " + nombre.getText() + "\n Respuesta de SQLITE: " + p.error;
            }
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK", null).show();
    }

    private boolean vacios(){
        if(nombre.getText().toString().isEmpty()) return true;
        if(domicilio.getText().toString().isEmpty()) return true;
        if(fecha.getText().toString().isEmpty()) return true;
        if(telefono.getText().toString().isEmpty()) return true;
        if(id.getText().toString().isEmpty()) return true;
        if(descripcion.getText().toString().isEmpty()) return true;

        return false;
    }
}
