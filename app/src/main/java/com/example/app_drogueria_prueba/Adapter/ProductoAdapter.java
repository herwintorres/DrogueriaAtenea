package com.example.app_drogueria_prueba.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_drogueria_prueba.Dao.DaoProducto;
import com.example.app_drogueria_prueba.Models.Producto;
import com.example.app_drogueria_prueba.R;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    ArrayList<Producto> lista;
    DaoProducto dao;
    Producto p;
    Activity a;

    public ProductoAdapter(Activity a, ArrayList<Producto> lista, DaoProducto dao){
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        p = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        p = lista.get(i);
        return p.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        p = lista.get(position);
        TextView nombre = (TextView) v.findViewById(R.id.t_nombre);
        TextView descripcion = (TextView) v.findViewById(R.id.t_descripcion);
        TextView precio = (TextView) v.findViewById(R.id.t_precio);
        Button editar = (Button) v.findViewById(R.id.editar);
        Button eliminar = (Button) v.findViewById(R.id.eliminar);
        nombre.setText(p.getNombre());
        descripcion.setText(p.getDescripcion());
        precio.setText(""+String.valueOf(p.getPrecio()));
        editar.setTag(position);
        eliminar.setTag(position);
        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Diagolo de editar dialogo.xml
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo para confirmar si o no
            }
        });


        return v;
    }
}
