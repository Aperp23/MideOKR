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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Configurador extends AppCompatActivity {

    private Spinner spSeleccionProyecto;
    private Spinner spSprints;
    private Spinner spSemanas;
    private Spinner spHoras;
    private Spinner spTrabajadores;

    private TextView tvResultadoPtosHistoria;

    private Button btnGuardar;
    private Button btnBorrar;
    private Button btnSiguiente;

    private Button btnEditar;

    private EditText etNombreProyecto;

    private String valor;

    private ArrayList<String> arrProyectos = new ArrayList<String>();
    private ArrayList<String> arrConfiguracion = new ArrayList<String>();

    private ArrayList<ProyectoModel> arrProyectosEnteros = new ArrayList<ProyectoModel>();

    private ProyectoModel pm;
    private UsuarioModel um;

    private DatabaseReference mDatabaseReference, mDatabaseReference2, mDatabaseReference3;

    private String key;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    public String usuarioExtra;
    private String sprint, semanas, horas, trabajadores, nombreProyecto, proyecto;
    private String sprintObt, semanasObt, horasObt, trabajadoresObt, ptosHistoriaObt;

    private int flag=0;
    private String emaiLPersona;
    private int ptosHistoria;

    private final UsuarioModel[] usr = new UsuarioModel[1];

    private UsuarioModel usuarioM, uRecibido, userExtra;
    //TODO VALIDAR QUE TODOS LOS SPINNER TIENEN DATOS REALES
    //TODO CREAR UN BOTON DE EDITAR QUE HABILITE LOS CAMPOS DESHABILITADOS


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurador);
        Bundle extras = getIntent().getExtras();
        if(user!=null) {
            user = firebaseAuth.getCurrentUser();
            usuarioExtra = user.getUid();
        }else {
            usuarioExtra = extras.getString("uid");
            userExtra = (UsuarioModel) getIntent().getSerializableExtra("todo");
            emaiLPersona = userExtra.getEmail();

        }


        mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("Usuarios");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra);
        mDatabaseReference3 = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS");



        arrConfiguracion.add(0, "Selecciona un valor de la lista");
        for(int i = 1; i<=10; i++){
            valor = Integer.toString(i);
            arrConfiguracion.add(valor);
        }



        etNombreProyecto = (EditText) findViewById(R.id.etNombreProyecto);

        tvResultadoPtosHistoria = (TextView) findViewById(R.id.tvResultadoPtosHistoria);

        spSeleccionProyecto = (Spinner) findViewById(R.id.spSeleccionProyecto);
        spSprints = (Spinner) findViewById(R.id.spSprints);
        spSemanas = (Spinner) findViewById(R.id.spSemanas);
        spHoras = (Spinner) findViewById(R.id.spHoras);
        spTrabajadores = (Spinner) findViewById(R.id.spTrabajadores);


        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        btnSiguiente.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnEditar.setEnabled(false);

        cargarDatos();



        etNombreProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //habilitar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // cargarDatos();
                if(flag==0) {
                    //btnGuardar.setEnabled(false);
                    //btnSiguiente.setEnabled(true);

                    guardarDatos();
                    arrProyectos.clear();

                }else if(flag==1){
                    actualizarDatos();
                }



            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etNombreProyecto.setEnabled(true);
                btnGuardar.setEnabled(true);
                spSprints.setEnabled(true);
                spSemanas.setEnabled(true);
                spHoras.setEnabled(true);
                spTrabajadores.setEnabled(true);

                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
                btnSiguiente.setEnabled(false);

            }
        });


        ArrayAdapter adpSprints = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrConfiguracion );
        spSprints.setAdapter(adpSprints);

        spSprints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sprint = (String) spSprints.getAdapter().getItem( i );
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

                semanas = (String) spSemanas.getAdapter().getItem( i );
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

                horas = (String) spHoras.getAdapter().getItem( i );
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

        spSeleccionProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                proyecto = (String) spSeleccionProyecto.getAdapter().getItem( i );
                for(ProyectoModel pm5 : arrProyectosEnteros){
                    if(pm5.getNombreProyecto().equals(proyecto)){
                        sprintObt = pm5.getNumSprints();
                        semanasObt = pm5.getNumSemanas();
                        horasObt = pm5.getNumHoras();
                        trabajadoresObt = pm5.getNumTrabajadores();
                        ptosHistoriaObt = pm5.getPtosHistoria();

                        spSprints.setSelection(Integer.parseInt(sprintObt));
                        spSemanas.setSelection(Integer.parseInt(semanasObt));
                        spHoras.setSelection(Integer.parseInt(horasObt));
                        spTrabajadores.setSelection(Integer.parseInt(trabajadoresObt));
                        tvResultadoPtosHistoria.setText(ptosHistoriaObt);
                        etNombreProyecto.setText(proyecto);

                        etNombreProyecto.setEnabled(false);
                        btnGuardar.setEnabled(false);
                        spSprints.setEnabled(false);
                        spSemanas.setEnabled(false);
                        spHoras.setEnabled(false);
                        spTrabajadores.setEnabled(false);

                        btnSiguiente.setEnabled(true);
                        btnBorrar.setEnabled(true);
                        btnEditar.setEnabled(true);



                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }

    private void actualizarDatos() {


    }

    private void guardarDatos() {
        if(etNombreProyecto.getText().toString().trim().equals("")){
            Toast.makeText(this, "Debes Introducir nombre de proyecto", Toast.LENGTH_SHORT).show();
        }else {
            nombreProyecto = etNombreProyecto.getText().toString().trim();
            ptosHistoria = Integer.parseInt(trabajadores) * Integer.parseInt(horas) * Integer.parseInt(sprint);
            //um = new UsuarioModel(pm);

            key = mDatabaseReference.push().getKey();

            pm = new ProyectoModel(nombreProyecto, sprint, semanas, horas, trabajadores, String.valueOf(ptosHistoria), key);



            //uRecibido = new UsuarioModel(userExtra.getNombre(),userExtra.getApellidos(),userExtra.getEmail(), userExtra.getDni(), pm);


            /*System.out.println("BBBBBBBBBBBBBBBBBBBBBBBB");
            System.out.println(uRecibido.getEmail());*/

//TODO RECIBE EL OBJETO PREPARADO PARA SU INGRESO

            //FIXME AÑADIR IMPORTANTE UN LISTENER CUANDO TERMINE EL UPDATE CON EL PROYECTO GURADADO.
            mDatabaseReference.child("PROYECTOS").child(key).setValue(pm).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        spSprints.setSelection(0);
                        spSemanas.setSelection(0);
                        spHoras.setSelection(0);
                        spTrabajadores.setSelection(0);
                        etNombreProyecto.setText("");
                        cargarDatos();

                    }else{
                        Toast.makeText(Configurador.this, "Intentelo más tarde. . .", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    private void cargarDatos() {

        mDatabaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ProyectoModel pm2;

                    pm2=dataSnapshot1.getValue(ProyectoModel.class);

                    arrProyectosEnteros.add(pm2);


                }
                arrProyectos.clear();

                arrProyectos.add("Seleccione un proyecto");

                for(ProyectoModel pm3 : arrProyectosEnteros){
                   arrProyectos.add(pm3.getNombreProyecto());
                }

                ArrayAdapter adpProyectos = new ArrayAdapter(Configurador.this, android.R.layout.simple_spinner_dropdown_item, arrProyectos );
                spSeleccionProyecto.setAdapter(adpProyectos);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


}
}