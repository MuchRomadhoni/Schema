package com.example.myschedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myschedule.ui.home.HomeFragment;

public class addkelas extends Fragment {

    ListView listView;
    TextView mapel, semester, dosen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addkelas, container, false);
        listView = (ListView) v.findViewById(R.id.lv);
        mapel = (TextView) v.findViewById(R.id.mapel);
        semester = (TextView) v.findViewById(R.id.semester);
        dosen = (TextView) v.findViewById(R.id.namadosen);
        final String[] title1 = getResources().getStringArray(R.array.Mapel);
        final String[] semt1 = getResources().getStringArray(R.array.Semt);
        final String[] dosen1 = getResources().getStringArray(R.array.Dosen);
        final HomeFragment hf = new HomeFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "item added successfully to Home, but actually it's not. " +
                        "cause idk how to add this item to home fragment", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, hf).commit();

            }
        });
        setupListView();

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
        String[] dosen = getResources().getStringArray(R.array.Dosen);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), title, semt, dosen);
        listView.setAdapter(simpleAdapter);
    }
}
