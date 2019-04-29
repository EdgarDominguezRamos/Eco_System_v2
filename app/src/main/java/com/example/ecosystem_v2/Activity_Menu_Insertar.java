package com.example.ecosystem_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_Menu_Insertar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu__insertar);
    }
    //categorias
    public void carton(View view){
        Intent intent = new Intent(getApplicationContext(), Activity_Insert_Carton.class);
        startActivity(intent);
    }
    public void aluminio(View view) {
        Intent intent = new Intent(getApplicationContext(), Activity_Insert_Aluminio.class);
        startActivity(intent);
    }
    public void pet(View view) {
        Intent intent = new Intent(getApplicationContext(), Activity_Insert_Pet.class);
        startActivity(intent);
    }
}
