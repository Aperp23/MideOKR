package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tareas extends AppCompatActivity {
    private String key, keyRecib, usuarioExtra;
    private int flag;
    private String tarea;
    private String nombreTarea, ptosHistoria;
    private EditText etNombreTarea;

    private ArrayList<String> arrTareas = new ArrayList<String>();
    private Spinner spSeleccionTarea;

    private EditText etResultadoPtosHistoria;
    private TextView tvNombreProyecto;
    private Button btnGuardar;

    private Button btnBorrar;
    private Button btnEditar;
    private Button btnSiguiente;

    private DatabaseReference mDatabaseReference, mDatabaseReference2, mDatabaseReference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        Bundle extras = getIntent().getExtras();
        keyRecib = extras.getString("keyProyecto");
        usuarioExtra = extras.getString("usr");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib);
        mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib).child("TAREAS");
        mDatabaseReference3 = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib).child("TAREAS");

        etNombreTarea = (EditText) findViewById(R.id.etNombreTarea);
        etResultadoPtosHistoria = (EditText) findViewById(R.id.etResultadoPtosHistoria);
        tvNombreProyecto = (TextView) findViewById(R.id.tvNombreProyecto);
        spSeleccionTarea = (Spinner) findViewById(R.id.spSeleccionTarea);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        cargarProyecto();




        cargarTareas();


        ArrayAdapter adpTareas = new ArrayAdapter(Tareas.this, android.R.layout.simple_spinner_dropdown_item, arrTareas );
        spSeleccionTarea.setAdapter(adpTareas);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 1) {
                    //TODO EDITAR EN FIREBASE

                    flag=0;
                }else if(flag == 0){

                nombreTarea = etNombreTarea.getText().toString().trim();
                ptosHistoria = etResultadoPtosHistoria.getText().toString().trim();

                TareasModel tm = new TareasModel(nombreTarea, ptosHistoria);
                key = mDatabaseReference.push().getKey();
                mDatabaseReference.child("TAREAS").child(key).setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Tareas.this, "Tarea creada...", Toast.LENGTH_SHORT).show();

                            etNombreTarea.setText("");
                            etResultadoPtosHistoria.setText("");
                            arrTareas.clear();
                            cargarTareas();
                        }

                    }
                });
            }

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=1;

                btnGuardar.setEnabled(true);
                etNombreTarea.setEnabled(true);
                etResultadoPtosHistoria.setEnabled(true);

                btnBorrar.setEnabled(false);
                btnEditar.setEnabled(false);
                btnSiguiente.setEnabled(false);

            }
        });

        spSeleccionTarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tarea = (String) spSeleccionTarea.getAdapter().getItem(i);

                mDatabaseReference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {

                        for(DataSnapshot dataSnapshot2 : snapshot2.getChildren()){
                            TareasModel tm2;
                            tm2 = dataSnapshot2.getValue(TareasModel.class);
                            if(tm2.getNombre().equals(tarea)){
                                etNombreTarea.setText(tm2.getNombre());
                                etResultadoPtosHistoria.setText(tm2.getPtosHistoria());
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }

    private void cargarTareas() {


        arrTareas.add("Seleccione una Tarea");

        mDatabaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    TareasModel tm;
                    tm = dataSnapshot1.getValue(TareasModel.class);
                    arrTareas.add(tm.getNombre());

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void cargarProyecto() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProyectoModel pm = snapshot.getValue(ProyectoModel.class);

                tvNombreProyecto.setText(pm.getNombreProyecto());

                btnGuardar.setEnabled(true);
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
                btnSiguiente.setEnabled(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}