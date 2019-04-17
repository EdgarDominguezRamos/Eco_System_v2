package com.example.ecosystem_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Activity_Editar_Perfil extends AppCompatActivity {

    EditText et_nombre_ai;
    EditText et_apellido_paterno_ai;
    EditText et_apellido_materno_ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__editar__perfil);
    }
}
