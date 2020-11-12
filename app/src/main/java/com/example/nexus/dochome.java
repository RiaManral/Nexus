package com.example.nexus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class dochome extends AppCompatActivity {
    Button myprofile, aboutus, app, payment;
    TextView name;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fstore;
    String docid;
    Button logoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dochome);
        this.setTitle("Doctor Homepage");

        myprofile=findViewById(R.id.button9);
        aboutus=findViewById(R.id.button10);
        app=findViewById(R.id.button7);
        payment =findViewById(R.id.button6);
        name=findViewById(R.id.textView17);
        logoutbtn=findViewById(R.id.button6);


        mFirebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance() ;
        docid=mFirebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Doctors").document(docid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText("Hey "+value.getString( "FullName")+"!");


                myprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), showdocprofile.class);
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
                        Intent i = new Intent(getApplicationContext(), docAppt.class);
                        startActivity(i);
                    }
                });
                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), docpayment.class);
                        startActivity(i);
                    }
                });




            }
        });
    }

    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Toast.makeText(this, "You're logged out!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),loginactivity.class));

    }




}

