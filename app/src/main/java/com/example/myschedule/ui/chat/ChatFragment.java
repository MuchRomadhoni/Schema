package com.example.myschedule.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myschedule.R;
import com.example.myschedule.ui.home.HomeFragment;

public class ChatFragment extends Fragment {

    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) v.findViewById(R.id.lvchat);

        //listview onCLick yang akan langsung menuju ke halaman gabung grup whatsapp
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pengambilan url link grup pada string array pada folder re\values
                String[] links = getResources().getStringArray(R.array.Links);
                Uri uri = Uri.parse(links[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        setupListView();

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //men set tombol back langsung menuju ke homefragment
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

    //adapter untuk menghubungkan data array dengan listview
    public class SimpleAdapter extends BaseAdapter {

        public Context mContext;
        public LayoutInflater layoutInflater;
        public TextView title;
        public String[] titleArray;
        public LinearLayout linearLayout;
        public TextView chatbg;


        public SimpleAdapter(Context context, String[] title) {
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
                convertView = layoutInflater.inflate(R.layout.item_chat, null);
            }

            title = (TextView) convertView.findViewById(R.id.addmapel);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.kelassoon);
            chatbg = (TextView) convertView.findViewById(R.id.buttonsoon);

            title.setText(titleArray[position]);

            if (titleArray[position].equalsIgnoreCase("Pemrograman Mobile")) {
                chatbg.setBackgroundResource(R.drawable.kelasshape1);
            } else if (titleArray[position].equalsIgnoreCase("Pemrograman Web")) {
                chatbg.setBackgroundResource(R.drawable.kelasshape2);
            } else if (titleArray[position].equalsIgnoreCase("Basis Data")) {
                chatbg.setBackgroundResource(R.drawable.kelasshape3);
            } else if (titleArray[position].equalsIgnoreCase("Sistem Operasi")) {
                chatbg.setBackgroundResource(R.drawable.kelasshape4);
            }
            return convertView;
        }
    }

    public void setupListView() {
        String[] title = getResources().getStringArray(R.array.Mapel);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), title);
        listView.setAdapter(simpleAdapter);
    }

}
