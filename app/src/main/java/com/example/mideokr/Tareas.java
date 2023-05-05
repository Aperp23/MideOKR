package com.example.mideokr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Tareas extends AppCompatActivity {
    private String keyRecib;
    private EditText etNombreTarea;

    private Spinner spSeleccionTarea;//todo tareas ya creadas, if exist

    private EditText etResultadoPtosHistoria;
    private TextView tvNombreProyecto;

    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnEditar;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        Bundle extras = getIntent().getExtras();
        keyRecib = extras.getString("keyProyecto");

        etNombreTarea = (EditText) findViewById(R.id.etNombreTarea);
        etResultadoPtosHistoria = (EditText) findViewById(R.id.etResultadoPtosHistoria);
        tvNombreProyecto = (TextView) findViewById(R.id.tvNombreProyecto);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnEditar = (Button) findViewById(R.id.btnEditar);



    }
}