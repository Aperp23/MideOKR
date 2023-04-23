package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Perfil extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef2;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;
    private EditText etNombre, etApellidos, etPassword, etDni;
    private Button btnGuardar, btnAtras;
    private String nombre, apellidos, password, dni, email;
    private String key;
    private final UsuarioModel[] usr = new UsuarioModel[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("Usuarios");


        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellidos = (EditText) findViewById(R.id.etApellidos);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etDni = (EditText) findViewById(R.id.etDni);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAtras = (Button) findViewById(R.id.btnAtras);

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        email = usuario.getEmail();

        cargarDatos();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Perfil.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = firebaseAuth.getCurrentUser();

                nombre = etNombre.getText().toString().trim();
                apellidos = etApellidos.getText().toString().trim();
                dni =  etDni.getText().toString().trim();
                password = etPassword.getText().toString().trim();


                UsuarioModel um = new UsuarioModel(nombre,
                        apellidos,
                        email,
                        password,
                        dni);

                mDatabaseRef2.child(usuario.getUid()).setValue(um).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Perfil.this, "Datos cambiados correctamente", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Perfil.this, "No se pudo realizar el cambio", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


    }

    private void cargarDatos() {
        Query q = mDatabaseRef.orderByChild("email").equalTo(email);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    usr[0] = snapshot.getValue(UsuarioModel.class);
                }

                if(email.equals(usr[0].getEmail())){
                    etNombre.setText(usr[0].getNombre().toString());
                    etApellidos.setText(usr[0].getApellidos().toString());
                    etPassword.setText(usr[0].getPassword().toString());
                    etDni.setText(usr[0].getDni().toString());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}