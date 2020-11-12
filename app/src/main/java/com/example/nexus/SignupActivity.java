package com.example.nexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
     EditText fullname, phone, emailId;
     TextInputEditText password, confirmpassword;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    FirebaseFirestore fstore;
    String docid;
    ProgressDialog progressDialog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Doctor SignUp!");

        fullname=findViewById(R.id.editText3);
        phone=findViewById(R.id.editText4);
        emailId=findViewById(R.id.editText5);
        password=findViewById(R.id.editText6);
        confirmpassword=findViewById(R.id.editText7);
        btnSignUp=findViewById(R.id.button4);
        tvSignIn=findViewById(R.id.textView3);
        //progressBar=findViewById(R.id.progressBar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        if(mFirebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),docprofile.class));
            finish();
        }

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(SignupActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Intent i = new Intent(getApplicationContext(), loginactivity.class);
                startActivity(i);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = fullname.getText().toString();
                 final String phn = phone.getText().toString();
                String confp = confirmpassword.getText().toString();
                 final  String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()&& pwd.isEmpty()&&name.isEmpty()&&phn.isEmpty()&&confp.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Fields Are Empty!", Toast.LENGTH_SHORT).show();
                }

                    if (name.isEmpty()) {
                        fullname.setError("Please enter fullname");
                        fullname.requestFocus();
                        return;
                        //Toast.makeText(SignupActivity.this, "Name cannot be Empty!", Toast.LENGTH_SHORT).show();
                    }
                    if (phn.isEmpty()) {
                        phone.setError("Please enter phone number");
                        phone.requestFocus();
                        return;}
                        //Toast.makeText(SignupActivity.this, "Phone cannot be Empty!", Toast.LENGTH_SHORT).show();

                    if (email.isEmpty()) {
                        emailId.setError("Please enter email id");
                        emailId.requestFocus();
                        return;
                    }
                    if (pwd.isEmpty()) {
                        password.setError("Please enter your password");
                        password.requestFocus();
                        return;
                    }
                    if (confp.isEmpty()) {
                        confirmpassword.setError("Please enter password again");
                        confirmpassword.requestFocus();
                        return;
                    }



                if(password.length() < 6){
                    password.setError("Password Should be Greater than 6");
                    return;
                }
               /* if(!(password.getText().toString().equals(confirmpassword.getText().toString()))){
                    Toast.makeText(SignupActivity.this,"Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }*/




                if(password.getText().toString().equals(confirmpassword.getText().toString())){
                    //progressBar.setVisibility(View.VISIBLE);
                    progressDialog=new ProgressDialog(SignupActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"Your Account has been Created!",Toast.LENGTH_SHORT).show();
                                docid=mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fstore.collection("Doctors").document(docid);
                                Map<String,Object> user=new HashMap<>();
                                user.put("FullName",name);
                                user.put("PhoneNumber",phn);
                                user.put("Email",email);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: doc id created for:"+docid);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure:"+e.toString());
                                    }
                                });



                                startActivity(new Intent(getApplicationContext(),docprofile.class));


                            }
                            else {

                                Toast.makeText(SignupActivity.this,"Signup Unsuccessful, Please Try Again!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



                else{
                    Toast.makeText(SignupActivity.this,"Passwords do not match!",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
