package com.example.adrianflita.fragments;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrianflita.R;
import com.example.adrianflita.database.AppRoomDatabase;
import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.models.DAO.TransferDAO;
import com.example.adrianflita.models.TransferBancar;
import com.example.adrianflita.utils.Constante;
import com.example.adrianflita.utils.DateConverter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private TextView tvValoareCont;
    private Button btnExtras;
    private TextView tvValoareEconomii;
    private Button btnEconomiseste;
    private EditText etEconomisire;

    SharedPreferences sharedPreferences;
    private ContUserDAO contUserDAO;
    private TransferDAO transferDAO;


    ContUser contUser;
    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnEconomiseste = view.findViewById(R.id.bt_Economiste);
        btnExtras = view.findViewById(R.id.bt_extras);
        tvValoareCont = view.findViewById(R.id.tv_valoare_cont);
        tvValoareEconomii = view.findViewById(R.id.tv_valoare_conteco);
        etEconomisire = view.findViewById(R.id.et_economisire);

        sharedPreferences = getContext().getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        contUserDAO = AppRoomDatabase.getInstance(getContext()).getContUserDAO();
        transferDAO = AppRoomDatabase.getInstance(getContext()).getTransferDAO();


        new Thread(new Runnable() {
            @Override
            public void run() {
                contUser = contUserDAO.contUser(sharedPreferences.getLong(Constante.USERID_KEY, 0));

                tvValoareCont.setText(" Valoare Cont: "+contUser.getAmount());

                tvValoareEconomii.setText(" Valoare Economii: "+contUser.getAmountEco());
            }
        }).start();


        btnEconomiseste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etEconomisire.getText().toString().isEmpty()) {
                    float amount = Float.parseFloat(etEconomisire.getText().toString());

                    if (amount < contUser.getAmount()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                            int update = contUserDAO.updateAmountEco(contUser.getId(),contUser.getAmountEco()+amount, contUser.getAmount() - amount );

                                if(update != -1){
                                    tvValoareCont.setText(" Valoare Cont: "+ (contUser.getAmount() - amount));

                                    tvValoareEconomii.setText(" Valoare Economii: "+ (contUser.getAmountEco()+amount));
                                }
                            }
                        }).start();
                    } else {

                        Toast.makeText(getContext(), "Suma este prea mare", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        btnExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       List<TransferBancar> list = transferDAO.getAllTransactions(sharedPreferences.getLong(Constante.USERID_KEY, 0));
                        StringBuilder builder = new StringBuilder();


                       for(TransferBancar transferBancar : list){
                           builder.append("EXTRAS DE CONT");
                           builder.append("\n");
                           builder.append("\n");

                           builder.append("Valoare: "+ transferBancar.getAmount());
                            builder.append("\n");

                           builder.append("Data Tranzactiei: "+ DateConverter.fromDate(transferBancar.getDateExpires()));
                           builder.append("\n");

                       }
                        writeFileOnInternalStorage(getContext(),"tranzactii", builder.toString());

                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(getContext(), "Date salvate in fisier text", Toast.LENGTH_LONG).show();

                           }
                       });

                    }
                }).start();
            }
        });

        return view;
    }

    public void writeFileOnInternalStorage(Context context, String fileName, String body){
        File dir = new File(context.getFilesDir(), "director");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(body);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}