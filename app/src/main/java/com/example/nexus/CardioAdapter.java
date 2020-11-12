package com.example.nexus;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class CardioAdapter extends FirestoreRecyclerAdapter<DoctorModel,CardioAdapter.CardioHolder> {
    private static final String TAG = "TAG";
    private OnListItemClick onListItemClick;
    
    public static String docid;




    public CardioAdapter(@NonNull FirestoreRecyclerOptions<DoctorModel> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull CardioHolder holder, int position, @NonNull DoctorModel model) {
        holder.name.setText(model.getFullName());
        holder.sp.setText(model.getSpeciality());
        holder.exp.setText(model.getExperience()+" experience overall");
        holder.edu.setText(model.getEducation());
        holder.fee.setText("~ "+model.getConsultationFee()+" Consultation Fee");
        holder.add.setText("~ "+model.getHospitalClinicAddress());
        holder.phone.setText("~ "+model.getPhoneNumber());

    }

    @NonNull
    @Override
    public CardioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardio_item,parent,false);
        return new CardioHolder(v);

    }

    public class CardioHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,sp,exp,edu,fee,phone,add;
        public CardioHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            sp=itemView.findViewById(R.id.sp);
            exp=itemView.findViewById(R.id.exp);
            edu=itemView.findViewById(R.id.edu);
            fee=itemView.findViewById(R.id.fee);
            add=itemView.findViewById(R.id.add);
            phone=itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int pos=onListItemClick.onItemClick(getAdapterPosition());
            DocumentSnapshot snapshot = getSnapshots()
                    .getSnapshot(pos);
            docid = snapshot.getId();
            Log.d(TAG, "onSuccess: doc id created for:"+docid);
            Intent i = new Intent (itemView.getContext(), cardiodeets.class);
            i.putExtra("FullName", name.getText().toString());
            i.putExtra("Speciality",sp.getText().toString());
            i.putExtra("Experience",exp.getText().toString());
            i.putExtra("ConsultationFee",fee.getText().toString());
            i.putExtra("PhoneNumber",phone.getText().toString());
            i.putExtra("Education",edu.getText().toString());
            i.putExtra("uid",docid);
            itemView.getContext().startActivity(i);
        }
    }

public interface OnListItemClick{
        int onItemClick(int position);


}

}
