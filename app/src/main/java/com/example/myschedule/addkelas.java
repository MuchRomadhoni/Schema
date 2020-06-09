package com.example.myschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addkelas extends Fragment {

    RecyclerView recyclerView;
    TextView mapel, semester, dosen;
    DatabaseReference db;
    CustomAdapter adapter;
    ArrayList<FirebaseData> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addkelas, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.lv);
        mapel = (TextView) v.findViewById(R.id.mapel);
        semester = (TextView) v.findViewById(R.id.semester);
        dosen = (TextView) v.findViewById(R.id.namadosen);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<FirebaseData>();

        //get data from firebase
        db = FirebaseDatabase.getInstance().getReference().child("Schema");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code to retrieve data
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    FirebaseData p = dataSnapshot1.getValue(FirebaseData.class);
                    list.add(p);
                }
                adapter = new CustomAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "item added successfully to Home, but actually it's not. " +
                        "cause idk how to add this item to home fragment", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
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
