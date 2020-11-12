package com.example.nexus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class myAppts extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DocumentSnapshot> list;
    String pid;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rview);
        this.setTitle("My Appointments");
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(list,this);
        recyclerView.setAdapter(adapter);
        loadData(adapter);



    }

    private void loadData(final MyAdapter adapter) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        pid = mFirebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("Patients").document(pid).collection("My Appointments").orderBy("Date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if(error==null){
                            list.clear();
                            list.addAll(queryDocumentSnapshots.getDocuments());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        List<DocumentSnapshot> dataList;
        Context context;

        public MyAdapter(List<DocumentSnapshot> dataList, Context context) {
            this.dataList = dataList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

            try {
                String time=dataList.get(position).getString("Time");
                String date=dataList.get(position).getString("Date");
                String dn=dataList.get(position).getString("Doctor");
                String spec=dataList.get(position).getString("Speciality");
                String phn=dataList.get(position).getString("Contact");

                holder.mdate.setText("Date: "+date);
                holder.mtime.setText("Time: "+time);
                holder.mdocname.setText(dn);
                holder.mspec.setText(spec);
                holder.mphn.setText("Contact: "+phn);
            }
            catch (Exception e){
                Toast.makeText(context, "error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mdate,mtime,mdocname,mspec,mphn;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mdate=(TextView)itemView.findViewById(R.id.date);
                mtime=(TextView)itemView.findViewById(R.id.time);
                mdocname=(TextView)itemView.findViewById(R.id.textView14);
                mspec=(TextView)itemView.findViewById(R.id.textView43);
                mphn=(TextView)itemView.findViewById(R.id.phn);

            }
        }
    }

}