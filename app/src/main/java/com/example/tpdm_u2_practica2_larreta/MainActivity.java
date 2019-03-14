package com.example.tpdm_u2_practica2_larreta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    Propietario propietario[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.listapropietarios);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlerta(position);
            }
        });

    }

    public void mostrarAlerta(final int  pos){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage("Deseas modificar / editar el propietario: " + propietario[pos].getNombre())
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invocarEliminarActualizar(pos);
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    private void invocarEliminarActualizar(int pos){
        Intent eliminactualiza = new Intent(this, ActualizarEliminar.class);
        eliminactualiza.putExtra("telefono",propietario[pos].getTelefono());
        eliminactualiza.putExtra("nombre",propietario[pos].getNombre());
        eliminactualiza.putExtra("domicilio",propietario[pos].getDomicilio());
        eliminactualiza.putExtra("fecha",propietario[pos].getFecha());

        startActivity(eliminactualiza);
    }

    protected void onStart() {
        Propietario p = new Propietario(this);
        propietario = p.consultar();
        String[] prop = null;

        if(propietario == null){
            prop = new String[1];
            prop[0] = "No hay Propietarios aún";
        }else {
            prop = new String[propietario.length];
            for(int i= 0; i<propietario.length; i++){
                Propietario temp = propietario[i];
                prop[i] = temp.getNombre()+"\n"+temp.getTelefono();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, prop);
        lista.setAdapter(adaptador);


        super.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.nuevoPropietario:
                Intent nuevoproyecto = new Intent(this,Registro.class);
                startActivity(nuevoproyecto);
                break;
            case R.id.buscarPropietario:
                Intent buscarproyecto = new Intent(this, BuscarPropietario.class);
                startActivity(buscarproyecto);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
