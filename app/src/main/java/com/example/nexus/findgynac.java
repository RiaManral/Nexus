package com.example.nexus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class findgynac extends AppCompatActivity implements CardioAdapter.OnListItemClick {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CardioAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findcardio2);
        this.setTitle("Select from Gynecologists");
        setupRecyclerView();
    }
    private void setupRecyclerView(){
        Query query=db.collection("Doctors").whereEqualTo("Speciality","Gynecologist");
        FirestoreRecyclerOptions<DoctorModel> options=new FirestoreRecyclerOptions.Builder<DoctorModel>()
                .setQuery(query, DoctorModel.class)
                .build();
        adapter=new CardioAdapter(options,this);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public int onItemClick(int position) {

        Intent intent=new Intent(this,cardiodeets.class);
        startActivity(intent);
        return position;
    }


}
