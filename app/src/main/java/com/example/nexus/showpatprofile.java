package com.example.nexus;


import androidx.annotation.Nullable;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class showpatprofile extends AppCompatActivity {
    TextView name, phn, email;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fstore;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpatprofile);
        this.setTitle("My Profile");
        name = findViewById(R.id.textView38);
        phn = findViewById(R.id.textView40);
        email = findViewById(R.id.textView42);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        pid = mFirebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Patients").document(pid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("FullName"));
                phn.setText(value.getString("PhoneNumber"));
                email.setText(value.getString("Email"));
            }
        });
    }
}
