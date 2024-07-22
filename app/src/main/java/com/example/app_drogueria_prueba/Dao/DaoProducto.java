package com.example.app_drogueria_prueba.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_drogueria_prueba.Models.Producto;

import java.util.ArrayList;

public class DaoProducto {
    SQLiteDatabase cx;
    ArrayList<Producto> lista = new ArrayList<Producto>();
    Producto p;
    Context ct;
    String nombreBD = "BDrogueria";
    String tabla = "create table if not exists producto(id integer primary key autoincrement, nombre text, descripcion text, precio real)";

    public DaoProducto(Context c){
        this.ct=c;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Producto p){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre",p.getNombre());
        contenedor.put("descripcion",p.getDescripcion());
        contenedor.put("precio",p.getPrecio());
        return (cx.insert("producto",null,contenedor))>0;
    }

    public boolean eliminar(int id){
        return (cx.delete("producto","id="+id,null))>0;
    }

    public boolean editar(Producto p){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre",p.getNombre());
        contenedor.put("descripcion",p.getDescripcion());
        contenedor.put("precio",p.getPrecio());
        return (cx.update("producto",contenedor,"id="+p.getId(),null))>0;
    }
    public ArrayList<Producto> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from producto", null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Producto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3)));
            }while(cursor.moveToNext());
        }
        return lista;
    }

    public Producto verUno(int posicion){
        Cursor cursor = cx.rawQuery("select * from producto", null);
        cursor.moveToPosition(posicion);
        p = new Producto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getFloat(3));
        return p;
    }
}
