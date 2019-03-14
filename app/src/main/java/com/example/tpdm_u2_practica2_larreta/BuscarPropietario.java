package com.example.tpdm_u2_practica2_larreta;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BuscarPropietario extends AppCompatActivity {

    TextView informacion;
    EditText filtro;
    Button buscar, regresar;
    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_propietario);

        informacion = findViewById(R.id.informacion);
        filtro = findViewById(R.id.filtro);
        buscar = findViewById(R.id.consultar);
        regresar = findViewById(R.id.regresarPropietario);
        lista = findViewById(R.id.listaseguros);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void buscar(){

        String mensaje ="";
        Propietario[] resultado =null;
        Propietario propietario = new Propietario(this);
        Seguro[] resultado2 = null;
        Seguro seguro = new Seguro(this);

        if(filtro.getText().toString().isEmpty()){
            mensaje ="Porfavor ingresa datos para realizar la búsqueda";
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
            return;
        }
        try{
            int tel = Integer.parseInt((filtro.getText().toString().substring(0,1)));
            resultado = propietario.consultar("TELEFONO",filtro.getText().toString());
            resultado2 = seguro.consultar("TELEFONO",filtro.getText().toString());

            llenarseguros(resultado2);
            //Log.d("asdd",filtro.getText().toString());
        }catch (NumberFormatException e){
            resultado = propietario.consultar("NOMBRE",filtro.getText().toString());
        }
        if(resultado == null){
            mensaje ="No hay coincidencias";
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
            return;
        }
        informacion.setText("Propietario: "+ resultado[0].getNombre()+"\n" +
                "Teléfono: "+ resultado[0].getTelefono()+"\n"+"Domicilio: "+resultado[0].getDomicilio()+"\n" +
                "Fecha: "+resultado[0].getFecha() + "\n"+
                "Seguro(s): ");

        filtro.setText("");
    }

    private void llenarseguros(Seguro[] resultado){
        String[] seg = null;
        seg = new String[resultado.length];
        for(int i= 0; i<resultado.length; i++){
            Seguro temp = resultado[i];
            seg[i] = temp.getTiposeguro().toString();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, seg);
        lista.setAdapter(adaptador);
    }
}
