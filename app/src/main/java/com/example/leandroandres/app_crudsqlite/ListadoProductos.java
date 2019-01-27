package com.example.leandroandres.app_crudsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListadoProductos extends AppCompatActivity {

    private ListView lvProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);

        //https://stackoverflow.com/questions/45446452/send-arraylist-by-intent
        List<Producto> productos = (List<Producto>) getIntent().getSerializableExtra("productos");

        lvProductos = (ListView) findViewById(R.id.lvProductos);

        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this,R.layout.activity_productos__opcion,productos);

        lvProductos.setAdapter(adapter);
    }

    public void volver(View view)
    {
        Intent inte = new Intent(this, MainActivity.class);
        startActivity(inte);
    }
}
