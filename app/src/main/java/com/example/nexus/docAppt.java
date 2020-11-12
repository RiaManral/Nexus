package com.example.nexus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class docAppt extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DocumentSnapshot> list;
    String docid;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_rview);
        this.setTitle("My Appointments");
        recyclerView = (RecyclerView) findViewById(R.id.prec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(list,this);
        recyclerView.setAdapter(adapter);
        loadData(adapter);



    }

    private void loadData(final MyAdapter adapter) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        docid = mFirebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("Doctors").document(docid).collection("My Appointments").orderBy("Date", Query.Direction.ASCENDING)
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
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_home_view,parent,false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

            try {
                String time=dataList.get(position).getString("Time");
                String date=dataList.get(position).getString("Date");
                String pn=dataList.get(position).getString("Patient");
                String phn=dataList.get(position).getString("Contact");

                holder.mdate.setText("Date: "+date);
                holder.mtime.setText("Time: "+time);
                holder.mpname.setText("Patient's Name: "+pn);
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
            TextView mdate,mtime,mpname,mphn;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mdate=(TextView)itemView.findViewById(R.id.pdate);
                mtime=(TextView)itemView.findViewById(R.id.ptime);
                mpname=(TextView)itemView.findViewById(R.id.pname);
                mphn=(TextView)itemView.findViewById(R.id.pphn);

            }
        }
    }

}
