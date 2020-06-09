package com.example.myschedule;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myschedule.ui.home.HomeFragment;
import com.example.myschedule.ui.jadwal.JadwalFragment;


public class JadwalDetailFragment extends Fragment {

    private ListView listView;
    public static String[] Senin;
    public static String[] Selasa;
    public static String[] Rabu;
    public static String[] Kamis;
    public static String[] Jumat;
    public static String[] Sabtu;
    public static String[] Dosen;
    public static String[] Ruangan;

    public static String[] Time1;
    public static String[] Time2;
    public static String[] Time3;
    public static String[] Time4;
    public static String[] Time5;
    public static String[] Time6;

    private String[] PreferredDay;
    private String[] PreferredTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jadwal_detail, container, false);
        listView = (ListView) v.findViewById(R.id.jadwalDetail);
        setupListView();

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.nav_host_fragment, new JadwalFragment());
                ts.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    private void setupListView() {
        Senin = getResources().getStringArray(R.array.Senin);
        Selasa = getResources().getStringArray(R.array.Selasa);
        Rabu = getResources().getStringArray(R.array.Rabu);
        Kamis = getResources().getStringArray(R.array.Kamis);
        Jumat = getResources().getStringArray(R.array.Jumat);
        Sabtu = getResources().getStringArray(R.array.Sabtu);

        Time1 = getResources().getStringArray(R.array.time1);
        Time2 = getResources().getStringArray(R.array.time2);
        Time3 = getResources().getStringArray(R.array.time3);
        Time4 = getResources().getStringArray(R.array.time4);
        Time5 = getResources().getStringArray(R.array.time5);
        Time6 = getResources().getStringArray(R.array.time6);

        Dosen = getResources().getStringArray(R.array.Dosen);
        Ruangan = getResources().getStringArray(R.array.Ruangan);

        String selectedDay = JadwalFragment.sharedPreferences.getString(JadwalFragment.SEL_DAY, null);

        if (selectedDay.equalsIgnoreCase("Senin")) {
            PreferredDay = Senin;
            PreferredTime = Time1;
        } else if (selectedDay.equalsIgnoreCase("Selasa")) {
            PreferredDay = Selasa;
            PreferredTime = Time2;
        } else if (selectedDay.equalsIgnoreCase("Rabu")) {
            PreferredDay = Rabu;
            PreferredTime = Time3;
        } else if (selectedDay.equalsIgnoreCase("Kamis")) {
            PreferredDay = Kamis;
            PreferredTime = Time4;
        } else if (selectedDay.equalsIgnoreCase("Jumat")) {
            PreferredDay = Jumat;
            PreferredTime = Time5;
        } else {
            PreferredDay = Sabtu;
            PreferredTime = Time6;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), PreferredDay, PreferredTime, Dosen, Ruangan);
        listView.setAdapter(simpleAdapter);
    }

    public class SimpleAdapter extends BaseAdapter {

        public Context mContext;
        public LayoutInflater layoutInflater;
        public TextView title, time, dosen, ruangan;
        public String[] titleArray;
        public String[] timeArray;
        public String[] dosenArray;
        public String[] ruanganArray;


        public SimpleAdapter(Context context, String[] title, String[] time, String[] dosen, String[] ruangan) {
            mContext = context;
            titleArray = title;
            timeArray = time;
            dosenArray = dosen;
            ruanganArray = ruangan;
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
                convertView = layoutInflater.inflate(R.layout.item_daydetail, null);
            }

            title = (TextView) convertView.findViewById(R.id.mapeldaydetail);
            time = (TextView) convertView.findViewById(R.id.waktu);
            dosen = (TextView) convertView.findViewById(R.id.namadosen);
            ruangan = (TextView) convertView.findViewById(R.id.ruangan);

            title.setText(titleArray[position]);
            time.setText(timeArray[position]);
            dosen.setText(dosenArray[position]);
            ruangan.setText(ruanganArray[position]);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.fotodosen);
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.kelas);

            if (titleArray[position].endsWith("1")) {
                imageView.setImageResource(R.drawable.logo2);
//                linearLayout.setBackgroundResource(R.drawable.kelasbg);
            } else if (titleArray[position].endsWith("2")) {
                imageView.setImageResource(R.drawable.logo2);
//                linearLayout.setBackgroundResource(R.drawable.kelasbgmerah);
            } else {
                imageView.setImageResource(R.drawable.logo2);
//                linearLayout.setBackgroundResource(R.drawable.kelasbgungu);
            }

            return convertView;
        }
    }
}
