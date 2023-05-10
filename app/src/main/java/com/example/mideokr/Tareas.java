package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private String keyTarea, key, keyRecib, usuarioExtra;
    private int flag;
    private String tarea;
    private String nombreProyecto, nombreTarea, ptosHistoria, tareaMod, ptosHistoriaMod;
    private EditText etNombreTarea;

    private ArrayList<String> arrTareas = new ArrayList<String>();
    private Spinner spSeleccionTarea;

    private EditText etResultadoPtosHistoria;
    private TextView tvNombreProyecto;
    private Button btnGuardar;

    private Button btnBorrar;
    private Button btnEditar;
    private Button btnSiguiente;

    private TareasModel tmMod;

    private DatabaseReference mDatabaseReference, mDatabaseReference2, mDatabaseReference3, mDatabaseReference4;

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
        mDatabaseReference4 = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib).child("TAREAS");

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

                    actualizarTarea();
                    spSeleccionTarea.setEnabled(true);
                    flag=0;
                }else if(flag == 0){
                if(etResultadoPtosHistoria.getText().toString().trim().equals("") || etNombreTarea.getText().toString().trim().equals("")) {
                    Toast.makeText(Tareas.this, "Debes rellenar los campos con *", Toast.LENGTH_LONG).show();
                }else{

                    nombreTarea = etNombreTarea.getText().toString().trim();
                    ptosHistoria = etResultadoPtosHistoria.getText().toString().trim();
                    key = mDatabaseReference.push().getKey();
                    TareasModel tm = new TareasModel(nombreTarea, ptosHistoria, key);

                    mDatabaseReference.child("TAREAS").child(key).setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(Tareas.this, "Tarea creada...", Toast.LENGTH_LONG).show();

                                etNombreTarea.setText("");
                                etResultadoPtosHistoria.setText("");

                                arrTareas.clear();
                                cargarTareas();
                            }

                        }
                    });

                }
            }

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=1;
                spSeleccionTarea.setEnabled(false);

                btnGuardar.setEnabled(true);
                etNombreTarea.setEnabled(true);
                etResultadoPtosHistoria.setEnabled(true);

                btnBorrar.setEnabled(false);
                btnEditar.setEnabled(false);
                btnSiguiente.setEnabled(false);


            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tareas.this, Trabajadores.class);
                i.putExtra("keyProyecto", keyRecib);
                i.putExtra("usr", usuarioExtra);
                i.putExtra("nombrePry", nombreProyecto);
                startActivity(i);
            }


        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(Tareas.this)
                        .setTitle("Se borrará la tarea seleccionada")
                        .setMessage("¿Está seguro?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                borrarTarea();
                                btnEditar.setEnabled(false);
                                btnBorrar.setEnabled(false);
                                btnSiguiente.setEnabled(false);

                                btnGuardar.setEnabled(true);
                                etNombreTarea.setEnabled(true);
                                etResultadoPtosHistoria.setEnabled(true);
                                spSeleccionTarea.setEnabled(true);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Toast.makeText(Tareas.this, "Acción cancelada", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

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
                                keyTarea = tm2.getKeyTarea();
                                btnBorrar.setEnabled(true);
                                btnEditar.setEnabled(true);
                                btnGuardar.setEnabled(false);
                                etNombreTarea.setEnabled(false);
                                btnSiguiente.setEnabled(true);
                                etResultadoPtosHistoria.setEnabled(false);
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

    private void actualizarTarea() {
        btnSiguiente.setEnabled(true);
            if (etNombreTarea.getText().toString().trim().equals("") || etResultadoPtosHistoria.getText().toString().trim().equals("")){
                Toast.makeText(this, "Debes rellenar todos los campos con (*)", Toast.LENGTH_SHORT).show();
            } else{

                tareaMod = etNombreTarea.getText().toString().trim();
                ptosHistoriaMod = etResultadoPtosHistoria.getText().toString().trim();
                tmMod = new TareasModel(tareaMod, ptosHistoriaMod, keyTarea);
                //pmAct = new ProyectoModel(nombreProyecto, sprint, semanas, horas, trabajadores, String.valueOf(ptosHistoria), keyObt);
                mDatabaseReference4.child(keyTarea).setValue(tmMod).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Tareas.this, "Tarea Editado. . .", Toast.LENGTH_LONG).show();
                            spSeleccionTarea.setSelection(0);
                            etNombreTarea.setText("");
                            etResultadoPtosHistoria.setText("");
                            arrTareas.clear();
                            cargarTareas();
                        } else {
                            Toast.makeText(Tareas.this, "Intentelo de nuevo más tarde", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        }
    }

    private void borrarTarea(){
        mDatabaseReference4.child(keyTarea).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    spSeleccionTarea.setSelection(0);
                    etNombreTarea.setText("");
                    etResultadoPtosHistoria.setText("");
                    arrTareas.clear();
                    cargarTareas();

                }else{
                    Toast.makeText(Tareas.this, "No se ha podido borrar. . .", Toast.LENGTH_LONG).show();
                }
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
                nombreProyecto = pm.getNombreProyecto();
                tvNombreProyecto.setText(nombreProyecto);

                btnGuardar.setEnabled(true);
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}