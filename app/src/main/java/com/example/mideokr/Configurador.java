package com.example.mideokr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Configurador extends AppCompatActivity {

    private Spinner spSeleccionProyecto;
    private Spinner spSprints;
    private Spinner spSemanas;
    private Spinner spHoras;
    private Spinner spTrabajadores;

    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnSiguiente;

    private EditText etNombreProyecto;

    private String valor;

    private ArrayList<String> arrProyectos = new ArrayList<String>();
    private ArrayList<String> arrConfiguracion = new ArrayList<String>();

    private ProyectoModel pm;
    private UsuarioModel um;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String sprint, semanas, horas, trabajadores, nombreProyecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurador);
        user = firebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(user.getUid());


        arrConfiguracion.add(0, "Selecciona un valor de la lista");
        for(int i = 1; i<=10; i++){
            valor = Integer.toString(i);
            arrConfiguracion.add(valor);
        }



        etNombreProyecto = (EditText) findViewById(R.id.etNombreProyecto);

        spSeleccionProyecto = (Spinner) findViewById(R.id.spSeleccionProyecto);
        spSprints = (Spinner) findViewById(R.id.spSprints);
        spSemanas = (Spinner) findViewById(R.id.spSemanas);
        spHoras = (Spinner) findViewById(R.id.spHoras);
        spTrabajadores = (Spinner) findViewById(R.id.spTrabajadores);


        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        deshabilitar();

        etNombreProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardarDatos();
                //TODO GUARDAR LA BBDD
                deshabilitar();

            }
        });


        ArrayAdapter adpSprints = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spSprints.setAdapter(adpSprints);

        spSprints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO configurar
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter adpSemanas = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spSemanas.setAdapter(adpSemanas);
        spSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO configurar
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adpHoras = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spHoras.setAdapter(adpHoras);
        spHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO configurar
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adpTrabajadores = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spTrabajadores.setAdapter(adpTrabajadores);
        spTrabajadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                trabajadores = (String) spTrabajadores.getAdapter().getItem( i );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*ArrayAdapter adpSprints = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spSprints.setAdapter(adpSprints);
        spSprints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO configurar
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


    }

    private void guardarDatos() {

        um = new UsuarioModel(pm);

        pm = new ProyectoModel();//TODO METER LOS STRINGS QUE SAQUEMOS DE LOS SPINNER

        mDatabaseReference.setValue(pm);

    }

    private void habilitar() {
        etNombreProyecto.setEnabled(true);
        spSprints.setEnabled(true);
        spSemanas.setEnabled(true);
        spHoras.setEnabled(true);
        spTrabajadores.setEnabled(true);
    }

    private void deshabilitar() {


        spSprints.setEnabled(false);
        spSemanas.setEnabled(false);
        spHoras.setEnabled(false);
        spTrabajadores.setEnabled(false);


    }


}