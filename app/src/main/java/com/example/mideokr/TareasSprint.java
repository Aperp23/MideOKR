package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TareasSprint extends AppCompatActivity implements OnRadioButtonSelectedListener{
    private String keyRecib, usuarioExtra, nombrePry, sprints;
    private int sprintsInt, sprintsIntObt;
    private ProyectoModel pm;
    private TextView tvNombreProyecto;
    private RecyclerView rvSprint;
    private RecyclerView rvTarea;
    private ItemClickListener itemClickListener;
    private TareasTrabajadoresAdapter adapter;
    private TareasSprintAdapter adapter2;
    private DatabaseReference mDatabaseReferenceSp, mDatabaseReferenceTa;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();
    private ArrayList<TareasModel> arrTareasEnteras = new ArrayList<TareasModel>();
    private ArrayList<String> arrTareas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_sprint);
        Bundle extras = getIntent().getExtras();
        keyRecib = extras.getString("keyProyecto");
        usuarioExtra = extras.getString("usr");
        nombrePry = extras.getString("nombrePry");

        rvSprint = findViewById(R.id.rvSprint);
        rvTarea = findViewById(R.id.rvTarea);

        tvNombreProyecto = (TextView) findViewById(R.id.tvNombreProyecto);
        tvNombreProyecto.setText(nombrePry);

        mDatabaseReferenceTa = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib).child("TAREAS");;
        mDatabaseReferenceSp = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioExtra).child("PROYECTOS").child(keyRecib);

        // Set layout manager
        rvSprint.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        adapter = new TareasTrabajadoresAdapter(arrayList, itemClickListener);

        obtenerSprints();
//  TODO ADAPTER DE TAREAS AQUÍ DEBAJO
       // rvSprint.setLayoutManager(new LinearLayoutManager(this));

    //adapter2 = new TareasSprintAdapter(arrayList2);


    }

    private void obtenerSprints() {

        mDatabaseReferenceSp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                        sprints = snapshot.child("numSprints").getValue(String.class);

                        for (int i = 1; i <= Integer.parseInt(sprints); i++) {
                            arrayList.add("sprint " + i);
                        }

                }
                /*itemClickListener = new ItemClickListener() {
                    @Override public void onClick(String s)
                    {
                        // Notify adapter
                        rvSprint.post(new Runnable() {
                            @Override public void run()
                            {
                                adapter.notifyDataSetChanged();
                            }
                        });
                        // Display toast
                        Toast.makeText(getApplicationContext(),"Selected : " + s,Toast.LENGTH_LONG).show();
                    }
                };*/

                rvSprint.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void obtenerTareas(){

        mDatabaseReferenceTa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    TareasModel tm;

                    tm=dataSnapshot1.getValue(TareasModel.class);

                    arrTareasEnteras.add(tm);

                }
                for (TareasModel tm2 : arrTareasEnteras){

                    arrTareas.add(tm2.getNombre());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onRadioButtonSelected(int position) {
        String selectedRadioButtonName = arrayList.get(position);
        Toast.makeText(this, "RadioButton seleccionado: " + selectedRadioButtonName, Toast.LENGTH_SHORT).show();
    }
    }

