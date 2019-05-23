package com.example.prestamo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.prestamo.Recycler.RecyclerAdapCliente;
import com.example.prestamo.clases.Datos;

public class ListCliente extends AppCompatActivity {

    private RecyclerAdapCliente adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cliente);
        final Intent[] intent = new Intent[1];
        RecyclerView rvCliente = findViewById(R.id.rvClientes);
        RecyclerAdapCliente.OnItemClickListener onItemClickListener = new RecyclerAdapCliente.OnItemClickListener() {
            @Override
            public void OnItemClick(final int posicion, long id) {
                if(id==R.id.ibEliminar){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListCliente.this);
                    builder.setTitle("Eliminando");
                    builder.setMessage("Seguro que desea eliminarlo"+ Datos.clientes.get(posicion).getNombre());
                    builder.setNegativeButton("NO", null);
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Datos.clientes.remove(posicion);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else if(id==R.id.ibEditar){
                    intent[0] =new Intent(ListCliente.this, MainActivity.class);
                    intent[0].putExtra("indice", String.valueOf(posicion));
                    startActivityForResult(intent[0], 1111);
                }else{
                    intent[0] =new Intent(ListCliente.this, VerCliente.class);
                    intent[0].putExtra("indice", String.valueOf(posicion));
                    startActivity(intent[0]);
                }
            }
        };
        adapter=new RecyclerAdapCliente(Datos.clientes, onItemClickListener);
        GridLayoutManager manager = new GridLayoutManager(this,1);
        rvCliente.setLayoutManager(manager);
        rvCliente.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1111){
            if(resultCode==RESULT_OK){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
