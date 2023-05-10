package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Trabajadores extends AppCompatActivity {

    private String key, keyRecib, usuarioExtra, nombreProyecto;
    private String trabajador, keyTrabajador, tipoTrabajdor, resultadoWIP, tipoTrabajdorMod, resultadoWIPMod;
    private TrabajadoresModel tmMod;
    private int flag;
    private TextView tvNombreProyecto;
    private EditText etTipoTrabajador;
    private EditText etResultadoWip;
    private Spinner spSeleccionTrabajador;
    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnEditar;
    private Button btnSiguiente;

    private ArrayList<String> arrTrabajadores = new ArrayList<String>();

    private DatabaseReference mDatabaseReference, mDatabaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);

        Bundle extras = getIntent().getExtras();
        keyRecib = extras.getString("keyProyecto");
        usuarioExtra = extras.getString("usr");


        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        tvNombreProyecto = (TextView) findViewById(R.id.tvNombreProyecto);

        etTipoTrabajador = (EditText) findViewById(R.id.etTipoTrabajador);
        etResultadoWip = (EditText) findViewById(R.id.etResultadoWip);

        spSeleccionTrabajador = (Spinner) findViewById(R.id.spSeleccionTrabajador);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib);
        mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib).child("TRABAJADORES");

        cargarProyecto();

        cargarTrabajadores();

        ArrayAdapter adpTareas = new ArrayAdapter(Trabajadores.this, android.R.layout.simple_spinner_dropdown_item, arrTrabajadores );
        spSeleccionTrabajador.setAdapter(adpTareas);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=1;
                spSeleccionTrabajador.setEnabled(false);

                btnGuardar.setEnabled(true);
                etResultadoWip.setEnabled(true);
                etTipoTrabajador.setEnabled(true);

                btnBorrar.setEnabled(false);
                btnEditar.setEnabled(false);
                btnSiguiente.setEnabled(false);


            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(Trabajadores.this)
                        .setTitle("Se borrará el trabajador seleccionado")
                        .setMessage("¿Está seguro?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                borrarTrabajador();
                                btnEditar.setEnabled(false);
                                btnBorrar.setEnabled(false);
                                btnSiguiente.setEnabled(false);

                                btnGuardar.setEnabled(true);
                                etResultadoWip.setEnabled(true);
                                etTipoTrabajador.setEnabled(true);
                                spSeleccionTrabajador.setEnabled(true);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Toast.makeText(Trabajadores.this, "Acción cancelada", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO *
                // CUANDO LE DES A SIGUIENTE, SE DEBE COMPROBAR SI EXISTEN TAREAS Y TRABAJADORES DENTRO DE PROYECTO
                // SI FALTARA CUALQUIERA DE ELLAS, SE LE ENVIARÍA A LA VISTA CONFIGURADOR
                // PARA QUE CREARA LAS TAREAS Y TRABAJADORES NECESARIOS
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 1) {

                    actualizarTrabajador();
                    spSeleccionTrabajador.setEnabled(true);

                    flag=0;
                }else if(flag == 0){
                    if(etTipoTrabajador.getText().toString().trim().equals("") || etResultadoWip.getText().toString().trim().equals("")) {
                        Toast.makeText(Trabajadores.this, "Debes rellenar los campos con *", Toast.LENGTH_LONG).show();
                    }else{

                        tipoTrabajdor = etTipoTrabajador.getText().toString().trim();
                        resultadoWIP = etResultadoWip.getText().toString().trim();
                        key = mDatabaseReference.push().getKey();

                        TrabajadoresModel tm = new TrabajadoresModel(tipoTrabajdor, resultadoWIP, key);

                        mDatabaseReference.child("TRABAJADORES").child(key).setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Trabajadores.this, "Trabajdor creado...", Toast.LENGTH_LONG).show();

                                    etTipoTrabajador.setText("");
                                    etResultadoWip.setText("");
                                    arrTrabajadores.clear();
                                    cargarTrabajadores();
                                }

                            }
                        });

                    }
                }

            }
        });



        spSeleccionTrabajador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trabajador = (String) spSeleccionTrabajador.getAdapter().getItem(i);

                mDatabaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {

                        for(DataSnapshot dataSnapshot2 : snapshot2.getChildren()){
                            TrabajadoresModel tm2;
                            tm2 = dataSnapshot2.getValue(TrabajadoresModel.class);
                            if(tm2.getTipo().equals(trabajador)){
                                etTipoTrabajador.setText(tm2.getTipo());
                                etResultadoWip.setText(tm2.getWip());
                                keyTrabajador = tm2.getKeyTrabajadores();
                                btnBorrar.setEnabled(true);
                                btnEditar.setEnabled(true);
                                btnGuardar.setEnabled(false);
                                etResultadoWip.setEnabled(false);
                                etTipoTrabajador.setEnabled(false);
                                btnSiguiente.setEnabled(true);
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
    private void actualizarTrabajador() {
        btnSiguiente.setEnabled(true);
        if (etResultadoWip.getText().toString().trim().equals("") || etTipoTrabajador.getText().toString().trim().equals("")){
            Toast.makeText(this, "Debes rellenar todos los campos con (*)", Toast.LENGTH_LONG).show();
        } else{

            tipoTrabajdorMod = etTipoTrabajador.getText().toString().trim();
            resultadoWIPMod = etResultadoWip.getText().toString().trim();
            tmMod = new TrabajadoresModel(tipoTrabajdorMod, resultadoWIPMod, keyTrabajador);
            //pmAct = new ProyectoModel(nombreProyecto, sprint, semanas, horas, trabajadores, String.valueOf(ptosHistoria), keyObt);
            mDatabaseReference2.child(keyTrabajador).setValue(tmMod).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Trabajadores.this, "Trabajador Editado. . .", Toast.LENGTH_LONG).show();
                        spSeleccionTrabajador.setSelection(0);
                        etTipoTrabajador.setText("");
                        etResultadoWip.setText("");
                        arrTrabajadores.clear();
                        cargarTrabajadores();
                    } else {
                        Toast.makeText(Trabajadores.this, "Intentelo de nuevo más tarde", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }


    private void borrarTrabajador(){
        mDatabaseReference2.child(keyTrabajador).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    spSeleccionTrabajador.setSelection(0);
                    etResultadoWip.setText("");
                    etTipoTrabajador.setText("");
                    arrTrabajadores.clear();
                    cargarTrabajadores();

                }else{
                    Toast.makeText(Trabajadores.this, "No se ha podido borrar. . .", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cargarTrabajadores() {


        arrTrabajadores.add("Seleccione un Trabajador");

        mDatabaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    TrabajadoresModel tm;
                    tm = dataSnapshot1.getValue(TrabajadoresModel.class);
                    arrTrabajadores.add(tm.getTipo());

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
                btnSiguiente.setEnabled(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}