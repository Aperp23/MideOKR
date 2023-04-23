package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Olvido extends AppCompatActivity {

    private EditText etEmail;
    private Button btnAtras, btnEnviar;

    //private ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);

        mAuth = FirebaseAuth.getInstance();
        //mDialog = new ProgressDialog(Olvido.this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = etEmail.getText().toString().trim();
                if(!mail.isEmpty()){
                    enviarCorreo(mail);
                }else{
                    Toast.makeText(Olvido.this, "Debe Ingresar un Email", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void enviarCorreo(String email) {

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    /*mDialog.setMessage("Espere un momento. . .");
                    mDialog.setCanceledOnTouchOutside(false);*/
                    Toast.makeText(Olvido.this, "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                }else{
                    Toast.makeText(Olvido.this, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show();
                }

               // mDialog.dismiss();

            }
        });


    }
}