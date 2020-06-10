package com.example.myschedule;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public static final String TAG = "TAG";
    Context context;
    ArrayList<FirebaseData> firebaseData;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public CustomAdapter(Context c, ArrayList<FirebaseData> p) {
        context = c;
        firebaseData = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class, viewGroup, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.mapel.setText(firebaseData.get(i).getMapel());
        myViewHolder.semester.setText(firebaseData.get(i).getSemt());
        myViewHolder.namadosen.setText(firebaseData.get(i).getDosen());
    }

    @Override
    public int getItemCount() {
        return firebaseData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mapel, semester, namadosen, keydoes;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mapel = (TextView) itemView.findViewById(R.id.mapel);
            semester = (TextView) itemView.findViewById(R.id.semester);
            namadosen = (TextView) itemView.findViewById(R.id.namadosen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }


}