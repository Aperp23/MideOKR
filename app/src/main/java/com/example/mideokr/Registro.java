package com.example.mideokr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

public class Registro extends Activity {
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etPassword;
    private EditText etDni;
    private EditText etEmail;
    private Button btnRegistro;
    private Button btnAtras;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private String nombre, apellidos, password, dni, email;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellidos = (EditText) findViewById(R.id.etApellidos);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etDni = (EditText) findViewById(R.id.etDni);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnAtras = (Button) findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registro.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recogerDatos();
                //TODO Cuadro pregunta, estás seguro?

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        user = firebaseAuth.getCurrentUser();
                        if(task.isSuccessful()){
                            user = firebaseAuth.getCurrentUser();

                            key = mDatabaseReference.push().getKey();

                            UsuarioModel um = new UsuarioModel(nombre,
                                    apellidos,
                                    email,
                                    password,
                                    dni);

                            mDatabaseReference.child(user.getUid()).setValue(um);

                            Intent i = new Intent(Registro.this, LoginActivity.class);
                            startActivity(i);
                            Toast.makeText(Registro.this, "Usuario: "+nombre+ " registrado Correctamente", Toast.LENGTH_SHORT).show();
                            //FIXME Si fuera necesario, finish();
                            //TODO PROGRESS DIALOG
                        }else if(!task.isSuccessful()){//TODO CONTROLAR si no hay conex o usuario ya registrado
                            Toast.makeText(Registro.this, "Usuario ya Registrado", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Registro.this, "Intentelo de nuevo más tarde. . .", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void recogerDatos() {//TODO VALIDACIONES
        nombre = etNombre.getText().toString().trim();
        apellidos = etApellidos.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        dni = etDni.getText().toString().trim();
        email = etEmail.getText().toString().trim();
    }
}
