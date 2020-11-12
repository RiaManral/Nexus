package com.example.nexus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class showdocprofile extends AppCompatActivity {
    private static final String TAG = "TAG";
    TextView haddress,fullname, phone, emailId,speciality,edu,exp,fee;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fstore;
    String docid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdocprofile);
        this.setTitle("My Profile");
        fullname=findViewById(R.id.textView29);
        haddress=findViewById(R.id.textView11);

        speciality=findViewById(R.id.textView35);
        phone=findViewById(R.id.textView31);
        emailId=findViewById(R.id.textView33);

        edu=findViewById(R.id.textView9);
        exp=findViewById(R.id.textView12);

        fee=findViewById(R.id.textView13);


        mFirebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance() ;
        docid=mFirebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Doctors").document(docid);
         documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fullname.setText(value.getString("FullName"));

                phone.setText(value.getString("PhoneNumber"));
                emailId.setText(value.getString("Email"));
                speciality.setText(value.getString("Speciality"));
                edu.setText(value.getString("Education"));

                exp.setText(value.getString("Experience"));
                fee.setText(value.getString("ConsultationFee"));

                haddress.setText(value.getString("HospitalClinicAddress"));
            }
        });
        /*fstore.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/


    }
}