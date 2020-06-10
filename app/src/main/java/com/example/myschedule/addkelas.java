package com.example.myschedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedule.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addkelas extends Fragment{

    RecyclerView recyclerView;
    TextView mapel, semester, dosen;
    DatabaseReference db;
    CustomAdapter adapter;
    ArrayList<FirebaseData> firebaseData;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;
    String mapelHome,semtHome,dosenHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addkelas, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.lv);
        mapel = (TextView) v.findViewById(R.id.mapel);
        semester = (TextView) v.findViewById(R.id.semester);
        dosen = (TextView) v.findViewById(R.id.namadosen);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseData = new ArrayList<FirebaseData>();

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //get data from firebase
        db = FirebaseDatabase.getInstance().getReference().child("Schema");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code to retrieve data
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    FirebaseData p = dataSnapshot1.getValue(FirebaseData.class);
                    firebaseData.add(p);
                }
                adapter = new CustomAdapter(getContext(), firebaseData);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        firebaseData.get(position);

                        mapelHome = firebaseData.get(position).getMapel();
                        semtHome = firebaseData.get(position).getSemt();
                        dosenHome = firebaseData.get(position).getDosen();
                        //store data user to firestore
                        userID = firebaseAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID)
                                .collection("Mapel Pilihan").document(String.valueOf(position));

                        Map<String, Object> user = new HashMap<>();
                        user.put("Mapel", mapelHome);
                        user.put("Semester", semtHome);
                        user.put("Dosen", dosenHome);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "onSuccess ");
                            }
                        });
                        Toast.makeText(getContext(), "Mapel berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.replace(R.id.nav_host_fragment, new HomeFragment());
                        ts.commit();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ts.replace(R.id.nav_host_fragment, new HomeFragment());
                ts.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
