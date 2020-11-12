package com.example.nexus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class phome extends AppCompatActivity {
    Button myprofile, aboutus, app, finddoc;
    TextView name;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fstore;
    String pid;
    Button logoutbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phome);
        this.setTitle("Patient Homepage");

        myprofile = findViewById(R.id.button13);
        aboutus = findViewById(R.id.button14);
        app = findViewById(R.id.button8);
        finddoc = findViewById(R.id.button12);
        name=findViewById(R.id.textView15);
        logoutbtn= findViewById(R.id.button15);

        mFirebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance() ;
        pid=mFirebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Patients").document(pid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                name.setText("Hey "+value.getString( "FullName")+"!");


                myprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), showpatprofile.class);
                        startActivity(i);
                    }
                });
                aboutus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Aboutus.class);
                        startActivity(i);
                    }
                });
                app.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), myAppts.class);
                        startActivity(i);
                    }
                });
                finddoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Finddoc.class);
                        startActivity(i);
                    }
                });


            }
        });



    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Toast.makeText(this, "You're logged out!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),patientLogin.class));

    }
}