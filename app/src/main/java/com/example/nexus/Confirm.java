package com.example.nexus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class Confirm extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        this.setTitle("Appointment Confirmed!");
        lottieAnimationView=findViewById(R.id.confirm);
        home=findViewById(R.id.button4);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Confirm.this, phome.class);
                startActivity(i);
            }
        });
    }
}