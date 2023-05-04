package com.example.mideokr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mideokr.R;
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

import java.io.Serializable;

public class Home extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;
    private TextView tvNombre, tvEmail;

    private Button btnPrincipal, btnPerfil, btnProyectos, btnControl, btnFeedback, btnSesion;

    private String emaiLPersona;

    private final UsuarioModel[] usr = new UsuarioModel[1];

    private UsuarioModel um2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        btnPrincipal = (Button) findViewById(R.id.btnPrincipal);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnProyectos = (Button) findViewById(R.id.btnProyectos);
        btnControl = (Button) findViewById(R.id.btnControl);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnSesion = (Button) findViewById(R.id.btnSesion);

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        emaiLPersona = usuario.getEmail();

        cargarDatos();

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Perfil.class);
                startActivity(i);
                finish();
            }
        });

        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Configurador.class);
                i.putExtra("uid", usuario.getUid().toString());
                i.putExtra("todo", (Serializable) um2);
                startActivity(i);


            }
        });


        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
            }
        });
    }



    private void cargarDatos() {

        Query q = mDatabaseRef.orderByChild("email").equalTo(emaiLPersona);
        q.addListenerForSingleValueEvent(new ValueEventListener() { //un escuchador por cada uno de los Uid
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    usr[0] = snapshot.getValue(UsuarioModel.class);
                }

                if(emaiLPersona.equals(usr[0].getEmail())){
                    um2= new UsuarioModel(usr[0].getNombre(),usr[0].getApellidos(),usr[0].getEmail(),usr[0].getDni());
                    tvEmail.setText(usr[0].getEmail().toString());
                    tvNombre.setText(usr[0].getNombre().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}