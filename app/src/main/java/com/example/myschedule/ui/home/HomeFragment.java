package com.example.myschedule.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myschedule.R;
import com.example.myschedule.addkelas;
import com.example.myschedule.profilDosenFragment;
import com.example.myschedule.register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    public static final String TAG = "tag";
    ImageView add;
    ListView listView;
    TextView mapel, semester, dosen, namaUser;
    TextView verifymsg,verified;
    String userID;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) v.findViewById(R.id.lvhome);
        mapel = (TextView) v.findViewById(R.id.mapel);
        semester = (TextView) v.findViewById(R.id.semester);
        dosen = (TextView) v.findViewById(R.id.namadosen);
        add = v.findViewById(R.id.add);
        verifymsg = v.findViewById(R.id.verifymsg);
        verified = v.findViewById(R.id.verified);
        namaUser = v.findViewById(R.id.namauser);

        fAuth = FirebaseAuth.getInstance();

        //menampilkan pesan verifikasi
        final FirebaseUser user = fAuth.getCurrentUser();
        if (!user.isEmailVerified()) {
            verifymsg.setVisibility(View.VISIBLE);
            verified.setVisibility(View.VISIBLE);

        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Sent verification link untuk mengetahui emailnya bukan asal2 an
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Verification Email Has Been Sent",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure : Verification not Sent" + e.getMessage());
                    }
                });
            }
        });
    }else {
            verifymsg.setVisibility(View.GONE);
            verified.setVisibility(View.GONE);
        }

        //Membuat tampilan welcome + nama user yg diambil dari database firestore
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference .addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                namaUser.setText(documentSnapshot.getString("fName"));
            }
        });



        //pengambilan data dari string array yang akan disimpan pada variabel baru
        final String[] dosen = getResources().getStringArray(R.array.Dosen);
        final String[] linkWA = getResources().getStringArray(R.array.Kontak);
        final profilDosenFragment pdf = new profilDosenFragment();

        setupListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bundle yang berguna untuk komunikasi antar fragment atau untuk mentransfer data dari 1 frament ke fragment lain
                Bundle bundle = new Bundle();

                //nama dosen sebagai unik key dan dosen[position] sebagai value nya
                bundle.putString("namadosen", dosen[position]);
                bundle.putString("linkWA", linkWA[position]);

                //men set argument ke profile dosen fragment
                pdf.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.nav_host_fragment, pdf).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ts.replace(R.id.nav_host_fragment, new addkelas());
                ts.commit();
            }
        });

        return v;
    }

    //adapter untuk menghubungkan data array dengan listview
    public class SimpleAdapter extends BaseAdapter {

        public Context mContext;
        public LayoutInflater layoutInflater;
        public TextView title, semt, dosen;
        public String[] titleArray;
        public String[] semtArray;
        public String[] dosenArray;
        public ImageView imageView;
        public CardView linearLayout;


        public SimpleAdapter(Context context, String[] title, String[] semt, String[] dosen) {
            mContext = context;
            titleArray = title;
            semtArray = semt;
            dosenArray = dosen;
            layoutInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return titleArray.length;
        }

        @Override
        public Object getItem(int position) {
            return titleArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_class, null);
            }

            title = (TextView) convertView.findViewById(R.id.mapel);
            semt = (TextView) convertView.findViewById(R.id.semester);
            dosen = (TextView) convertView.findViewById(R.id.namadosen);
            imageView = (ImageView) convertView.findViewById(R.id.fotodosen);
            linearLayout = (CardView) convertView.findViewById(R.id.kelas);

            title.setText(titleArray[position]);
            semt.setText(semtArray[position]);
            dosen.setText(dosenArray[position]);

            return convertView;
        }
    }

    public void setupListView() {
        String[] title = getResources().getStringArray(R.array.Mapel);
        String[] semt = getResources().getStringArray(R.array.Semt);
        String[] dosen1 = getResources().getStringArray(R.array.Dosen);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), title, semt, dosen1);
        listView.setAdapter(simpleAdapter);

    }
}
