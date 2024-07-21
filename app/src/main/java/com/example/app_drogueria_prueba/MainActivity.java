package com.example.app_drogueria_prueba;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_drogueria_prueba.Adapter.ProductoAdapter;
import com.example.app_drogueria_prueba.Dao.DaoProducto;
import com.example.app_drogueria_prueba.Models.Producto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DaoProducto dao;
    ProductoAdapter adapter;
    ArrayList<Producto> lista;
    Producto p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dao = new DaoProducto(this);
        lista = dao.verTodos();
        adapter = new ProductoAdapter(this,lista,dao);
        ListView list = (ListView)findViewById(R.id.lista);
        Button agregar = (Button)findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialogo para ver lista previa del registro vista.xml
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo de agregar dialogo.xml
                Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.nombre);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.descripcion);
                final EditText precio = (EditText) dialogo.findViewById(R.id.precio);
                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            p = new Producto(nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    Double.parseDouble(precio.getText().toString()));
                            dao.insertar(p);
                            lista = dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });

        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //return insets;
        //});
    }
}