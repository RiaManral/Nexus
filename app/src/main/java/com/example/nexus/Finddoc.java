package com.example.nexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Finddoc extends AppCompatActivity {
    Button gp,cardio,dent,dermat,ent,gyn,ped,psych,ortho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finddoc);
        this.setTitle("Find a Doctor");

        gp = findViewById(R.id.button22);
        cardio = findViewById(R.id.button20);
        dent = findViewById(R.id.button21);
        dermat = findViewById(R.id.button23);
        ent = findViewById(R.id.button25);
        gyn = findViewById(R.id.button24);
        ortho = findViewById(R.id.button28);
        ped = findViewById(R.id.button18);
        psych = findViewById(R.id.button27);

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findgp.class);
                startActivity(i);
            }
        });
        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findcardio2.class);
                startActivity(i);
            }
        });
        dent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), finddentist.class);
                startActivity(i);
            }
        });
        dermat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), finddermat.class);
                startActivity(i);
            }
        });
        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findent.class);
                startActivity(i);
            }
        });
        gyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findgynac.class);
                startActivity(i);
            }
        });
        ped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findpead.class);
                startActivity(i);
            }
        });
        psych.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findpsch.class);
                startActivity(i);
            }
        });
        ortho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), findortho.class);
                startActivity(i);
            }
        });

    }
}



