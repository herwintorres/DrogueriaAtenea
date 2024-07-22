package com.example.app_drogueria_prueba.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_drogueria_prueba.Dao.DaoProducto;
import com.example.app_drogueria_prueba.MainActivity;
import com.example.app_drogueria_prueba.Models.Producto;
import com.example.app_drogueria_prueba.R;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    ArrayList<Producto> lista;
    DaoProducto dao;
    Producto p;
    Activity a;
    int id = 0;

    public ProductoAdapter(Activity a, ArrayList<Producto> lista, DaoProducto dao){
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                int pos=Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.nombre);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.descripcion);
                final EditText precio = (EditText) dialogo.findViewById(R.id.precio);
                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                p = lista.get(pos);
                setId(p.getId());
                nombre.setText(p.getNombre());
                descripcion.setText(p.getDescripcion());
                precio.setText(""+String.valueOf(p.getPrecio()));
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            p = new Producto(getId(), nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    Double.parseDouble(precio.getText().toString()));
                            dao.editar(p);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
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
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo para confirmar si o no
                int pos=Integer.parseInt(v.getTag().toString());
                p=lista.get(pos);
                setId(p.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("Esta seguro de eliminar producto?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });


        return v;
    }
}
