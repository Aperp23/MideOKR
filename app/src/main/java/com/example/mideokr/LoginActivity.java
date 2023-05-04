package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvRegister;
    private Button btnEntrar;
    private Button btnRegistro;
    private String email, password;
    private FirebaseAuth auth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(LoginActivity.this, Registro.class);
               startActivity(i);
               finish();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Olvido.class);
                startActivity(i);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatos();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent i = new Intent(LoginActivity.this, Home.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_LONG).show();
                            etPassword.setText("");
                        }
                    }
                });
            }
        });
    }

    private void obtenerDatos() {

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

    }


}