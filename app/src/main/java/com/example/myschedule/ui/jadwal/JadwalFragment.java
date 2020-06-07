package com.example.myschedule.ui.jadwal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myschedule.JadwalDetailFragment;
import com.example.myschedule.R;
import com.example.myschedule.ui.home.HomeFragment;

public class JadwalFragment extends Fragment {
    GridView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);
        listView = (GridView) v.findViewById(R.id.week);
        sharedPreferences = this.getActivity().getSharedPreferences("SEL_DAY", Context.MODE_PRIVATE);
        setupGridView();

        return v;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction ts = getFragmentManager().beginTransaction();
                ts.replace(R.id.lvgrid, new HomeFragment());
                ts.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public class WeekAdapter extends BaseAdapter {

        public Context mContext;
        public LayoutInflater layoutInflater;
        public TextView title;
        public String[] titleArray;
        public RelativeLayout linearLayout;
        public TextView letter;


        public WeekAdapter(Context context, String[] title) {
            mContext = context;
            titleArray = title;
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
                convertView = layoutInflater.inflate(R.layout.item_soon, null);
            }

            title = (TextView) convertView.findViewById(R.id.addmapel);
            linearLayout = (RelativeLayout) convertView.findViewById(R.id.kelassoon);
            letter = (TextView) convertView.findViewById(R.id.textletter);

            title.setText(titleArray[position]);

            if (titleArray[position].equalsIgnoreCase("Senin")) {
                letter.setText("S");
                linearLayout.setBackgroundResource(R.drawable.kelasbg);
            } else if (titleArray[position].equalsIgnoreCase("Selasa")) {
                letter.setText("S");
                linearLayout.setBackgroundResource(R.drawable.kelasbgmerah);
            } else if (titleArray[position].equalsIgnoreCase("Rabu")) {
                letter.setText("R");
                linearLayout.setBackgroundResource(R.drawable.kelasbgungu);
            } else if (titleArray[position].equalsIgnoreCase("Kamis")) {
                letter.setText("K");
                linearLayout.setBackgroundResource(R.drawable.kelasbgorange);
            } else if (titleArray[position].equalsIgnoreCase("Jumat")) {
                letter.setText("J");
                linearLayout.setBackgroundResource(R.drawable.kelasbg);
            } else {
                letter.setText("S");
                linearLayout.setBackgroundResource(R.drawable.kelasbgmerah);
            }
            return convertView;
        }
    }

    public void setupGridView() {
        String[] title = getResources().getStringArray(R.array.Week);

        WeekAdapter weekAdapter = new WeekAdapter(getContext(), title);
        listView.setAdapter(weekAdapter);

        FragmentTransaction ts = getFragmentManager().beginTransaction();
        ts.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
        ts.commit();
        sharedPreferences.edit().putString(SEL_DAY, "Senin").apply();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Senin").apply();
                        break;
                    }
                    case 1: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Selasa").apply();
                        break;
                    }
                    case 2: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Rabu").apply();
                        break;
                    }
                    case 3: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Kamis").apply();
                        break;
                    }
                    case 4: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Jumat").apply();
                        break;
                    }
                    case 5: {
                        FragmentTransaction ts = getFragmentManager().beginTransaction();
                        ts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ts.replace(R.id.lvgrid, new JadwalDetailFragment());
                        ts.commit();
                        sharedPreferences.edit().putString(SEL_DAY, "Sabtu").apply();
                        break;
                    }
                }
            }
        });
    }

}
