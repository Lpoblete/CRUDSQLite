package com.example.leandroandres.app_crudsqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etCodigo, etDescripcion, etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = (EditText) findViewById(R.id.txtCodigo);
        etDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        etPrecio = (EditText) findViewById(R.id.txtPrecio);
    }

    public void registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        try {
            if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("descripcion", descripcion);
                registro.put("precio", precio);

                //bd.insert("articulos", null, registro);
                bd.insertWithOnConflict("articulos", null, registro, SQLiteDatabase.CONFLICT_FAIL);
                bd.close();

                etCodigo.setText("");
                etDescripcion.setText("");
                etPrecio.setText("");

                Toast.makeText(this, "El registro del producto fue exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "debe llenar todas las casillas para poder registrar producto", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = bd.rawQuery
                    ("select descripcion, precio from articulos where codigo=" + codigo, null);

            if (fila.getCount() > 0) {
                fila.moveToFirst();
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "el producto no existe", Toast.LENGTH_SHORT).show();
            }

            bd.close();
        } else {
            Toast.makeText(this, "debes ingresar el codigo", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()) {

            int cantidad = bd.delete("articulos", "codigo=" + codigo, null);
            bd.close();

            etCodigo.setText("");
            etDescripcion.setText("");
            etPrecio.setText("");

            if (cantidad > 0) {
                Toast.makeText(this, "el producto ha sido eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "el producto no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "debes ingresar el codigo", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            //int cantidad = bd.update("articulos", registro, "codigo=" + codigo, null);
            int cantidad = bd.updateWithOnConflict("articulos", registro, "codigo=" + codigo, null, SQLiteDatabase.CONFLICT_FAIL);

            bd.close();

            if (cantidad > 0) {
                Toast.makeText(this, "producto modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "el producto no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void listar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        ArrayList<Producto> productos = new ArrayList<>();

        Cursor fila = bd.rawQuery("select * from articulos", null);

        while (fila.moveToNext()) {
            Producto prod = new Producto();
            prod.setCodigo(fila.getString(0));
            prod.setDescripcion(fila.getString(1));
            prod.setPrecio(fila.getString(2));

            productos.add(prod);
            prod = new Producto();
        }

        System.out.println("TODOS LOS PRODUCTOS: " + productos);

        Intent inte = new Intent(this, ListadoProductos.class);
        //inte.putParcelableArrayListExtra("productos", productos);
        inte.putExtra("productos", productos);
        startActivity(inte);
    }
}
