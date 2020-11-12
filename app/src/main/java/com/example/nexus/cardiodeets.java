package com.example.nexus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class cardiodeets extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "TAG";
    TextView name,sp,exp,edu,fee,phone, datepicker;
    RadioButton radioButton;
    RadioGroup radioGroup;
    Button conf;
    String pid;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardiodeets);
        this.setTitle("Book Appointment");
        name = findViewById(R.id.name);
        sp = findViewById(R.id.sp);
         exp=findViewById(R.id.exp);
         edu=findViewById(R.id.edu);
         fee=findViewById(R.id.fee);
         phone=findViewById(R.id.phone);
        datepicker=findViewById(R.id.datepicker);
        radioGroup=findViewById(R.id.radioGroup);
        conf=findViewById(R.id.button17);


        String Name = getIntent().getStringExtra("FullName");
        String Speciality = getIntent().getStringExtra("Speciality");
         String Experience = getIntent().getStringExtra("Experience");
         String Education = getIntent().getStringExtra("Education");
         String Fee = getIntent().getStringExtra("ConsultationFee");
         String Phone = getIntent().getStringExtra("PhoneNumber");
        name.setText(Name);
        sp.setText(Speciality);
         edu.setText(Education);
         exp.setText(Experience+" experience overall");
         phone.setText("Contact: "+Phone);
         fee.setText("Fee: "+Fee);



        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.nexus.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String time=radioButton.getText().toString();
                    final String date=datepicker.getText().toString();
                    String docname=name.getText().toString();
                    final String spec=sp.getText().toString();
                    final String phn=phone.getText().toString();
                    db=FirebaseFirestore.getInstance();
                    mFirebaseAuth = FirebaseAuth.getInstance();
                    pid = mFirebaseAuth.getCurrentUser().getUid();


                    Map<String, Object> user = new HashMap<>();
                    user.put("Doctor", docname);
                    user.put("Speciality", spec);
                    user.put("Date", date);
                    user.put("Time", time);
                    user.put("Contact", phn);

                    documentReference=db.collection("Patients").document(pid).collection("My Appointments").document();
                    documentReference.set(user);
                    Log.d(TAG, "onSuccess: doc id created for:"+CardioAdapter.docid);


                 documentReference = db.collection("Patients").document(pid);

                 documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                         if (documentSnapshot.exists()) {
                             String name = documentSnapshot.getString("FullName");
                             String phn = documentSnapshot.getString("PhoneNumber");
                             Map<String, Object> d_appt = new HashMap<>();
                             d_appt.put("Date", date);
                             d_appt.put("Time", time);
                             d_appt.put("Patient", name);
                             d_appt.put("Contact", phn);
                             documentReference = db.collection("Doctors").document(CardioAdapter.docid).collection("My Appointments").document();
                             documentReference.set(d_appt);
                         } else {
                             Toast.makeText(cardiodeets.this, "document doesnot exist", Toast.LENGTH_SHORT).show();
                         }
                     }
                 })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(cardiodeets.this, "error", Toast.LENGTH_SHORT).show();

                             }
                         });
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentdatestring= DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.getTime());
        datepicker.setText(currentdatestring);



    }
    public void checkButton(View v){
         int radioId=radioGroup.getCheckedRadioButtonId();
         radioButton=(RadioButton)findViewById(radioId);
         Toast.makeText(cardiodeets.this,
                radioButton.getText()+" selected", Toast.LENGTH_SHORT).show();



    }

}


