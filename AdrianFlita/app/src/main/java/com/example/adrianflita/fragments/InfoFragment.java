package com.example.adrianflita.fragments;

import android.icu.text.IDNA;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adrianflita.R;
import com.example.adrianflita.adapter.InfoAdapter;
import com.example.adrianflita.models.BankInfo;
import com.example.adrianflita.models.Cont;
import com.example.adrianflita.networking.HttpManager;
import com.example.adrianflita.networking.JsonParse;
import com.example.adrianflita.utils.Constante;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {
    private TextView tvSucursala;
    private TextView tvSediu;
    private TextView tvCIF;
    private TextView tvTelefon;
    private TextView tvFax;
    private ListView listView;
    private HttpManager httpManager;

    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        tvSucursala = view.findViewById(R.id.tv_sucursala);
        tvSediu = view.findViewById(R.id.tv_sediu);
        tvCIF = view.findViewById(R.id.tv_cif);
        tvTelefon = view.findViewById(R.id.tv_telefon);
        tvFax = view.findViewById(R.id.tV_fax);
        listView = view.findViewById(R.id.lv_info);
        httpManager = new HttpManager(Constante.URL);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = httpManager.call();
                    BankInfo bankInfo = JsonParse.getFromJson(json);
                    tvSucursala.setText(" Sucursala: " +bankInfo.getSucursala());
                    tvSediu.setText(" Sediu: " +bankInfo.getSediu());
                    tvCIF.setText(" CIF: " +bankInfo.getCIF());
                    tvTelefon.setText(" Telefon: " +bankInfo.getTelefon());
                    tvFax.setText(" Fax: " +bankInfo.getFAX());

                    ArrayList<Cont> contList = (ArrayList<Cont>) bankInfo.getContList();
                    InfoAdapter adapter = new InfoAdapter(getActivity(), contList);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }
}