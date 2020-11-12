package com.example.nexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class docprofile extends AppCompatActivity {


    public static final String TAG ="TAG";
    EditText speciality,edu,exp,haddress,fee;
    Button save;
    FirebaseFirestore fstore;
    FirebaseAuth mFirebaseAuth;
    String docid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docprofile);
        this.setTitle("Set up a Doctor Profile!");
        speciality=findViewById(R.id.editText8);
        edu=findViewById(R.id.editText9);
        exp=findViewById(R.id.editText10);
        haddress=findViewById(R.id.editText11);
        fee=findViewById(R.id.editText12);
        save=findViewById(R.id.button5);
        fstore= FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String sp = speciality.getText().toString();
                String educ = edu.getText().toString();
                String expe = exp.getText().toString();
                 String hosaddress = haddress.getText().toString();
                String fees = fee.getText().toString();


                if (sp.isEmpty() && educ.isEmpty() && expe.isEmpty() && hosaddress.isEmpty() && fees.isEmpty()) {
                    Toast.makeText(docprofile.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();

                }

                if (sp.isEmpty()) {
                    speciality.setError("Please enter Speciality");
                    speciality.requestFocus();

                }
                if (educ.isEmpty()) {
                    edu.setError("Please enter Education");
                    edu.requestFocus();
                }

                if (expe.isEmpty()) {
                    exp.setError("Please enter Experience");
                    exp.requestFocus();
                }
                if (hosaddress.isEmpty()) {
                    haddress.setError("Please enter Hospital Address");
                    haddress.requestFocus();
                }
                if (fees.isEmpty()) {
                    fee.setError("Please enter Consultation Fee");
                    fee.requestFocus();
                }

                if (!(sp.isEmpty() && educ.isEmpty() && expe.isEmpty() && hosaddress.isEmpty() && fees.isEmpty())) {

                    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                    if (mFirebaseUser != null) {

                        Toast.makeText(docprofile.this, "Welcome to NexusHealth", Toast.LENGTH_SHORT).show();
                        docid = mFirebaseAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("Doctors").document(docid);


                        Map<String, Object> user = new HashMap<>();
                        user.put("Speciality", sp);
                        user.put("Education", educ);
                        user.put("Experience", expe);
                        user.put("HospitalClinicAddress", hosaddress);
                        user.put("ConsultationFee", fees);

                        documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: doc id created for:" + docid);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure:" + e.toString());
                            }
                        });

                        Toast.makeText(docprofile.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), dochome.class));


                    }
                    else {

                        Toast.makeText(docprofile.this, "profile Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });







    }
}