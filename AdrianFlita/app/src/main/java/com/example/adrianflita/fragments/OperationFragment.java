package com.example.adrianflita.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.models.DAO.CreateUserDAO;
import com.example.adrianflita.models.DAO.TransferDAO;
import com.example.adrianflita.models.TransferBancar;
import com.example.adrianflita.utils.Constante;

import java.util.Date;


public class OperationFragment extends Fragment {

    private EditText etAmount;
    private EditText etIBAN;
    private TextView tvAmount;
    private Button btnTransfera;

    private TransferDAO transferDAO;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    private ContUser contUser;
    private ContUserDAO contUserDAO;
    public OperationFragment() {
        // Required empty public constructor
    }

    public static OperationFragment newInstance(String param1, String param2) {
        OperationFragment fragment = new OperationFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contUser = (ContUser) getArguments().getSerializable(Constante.CONTID_KEY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_operation, container, false);

        etAmount = view.findViewById(R.id.et_amount);
        etIBAN = view.findViewById(R.id.et_iban);
        tvAmount = view.findViewById(R.id.tv_amount_cont);

        btnTransfera = view.findViewById(R.id.btn_transfera);

        transferDAO = AppRoomDatabase.getInstance(getContext()).getTransferDAO();
        sharedPreferences = getActivity().getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        contUserDAO = AppRoomDatabase.getInstance(getContext()).getContUserDAO();

        if (getArguments() != null) {
            contUser = (ContUser) getArguments().getSerializable(Constante.CONTID_KEY);

        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContUser cont = contUserDAO.contUser(sharedPreferences.getLong(Constante.USERID_KEY, 0));

                contUser = cont;

            }
        }).start();

        btnTransfera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float amount = Float.parseFloat(etAmount.getText().toString());

                if (amount < contUser.getAmount()) {
                    if (isIbanValid(etIBAN.getText().toString()) && !etAmount.getText().toString().isEmpty()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                long id = transferDAO.insertTransfer(new TransferBancar(Float.parseFloat(etAmount.getText().toString()), new Date(),
                                        sharedPreferences.getLong(Constante.USERID_KEY, 0), contUser.getId()));

                                if (id != -1) {
                                    contUserDAO.updateCont(contUser.getId(), contUser.getAmount() - amount);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Transfer Realizat", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } else {
                        Toast.makeText(getContext(), "IBAN-ul este incorect", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Suma este prea mare", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean isIbanValid(String iban) {

        int IBAN_MIN_SIZE = 15;
        int IBAN_MAX_SIZE = 34;
        long IBAN_MAX = 999999999;
        long IBAN_MODULUS = 97;

        String trimmed = iban.trim();

        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            return false;
        }

        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);
        long total = 0;

        for (int i = 0; i < reformat.length(); i++) {

            int charValue = Character.getNumericValue(reformat.charAt(i));

            if (charValue < 0 || charValue > 35) {
                return false;
            }

            total = (charValue > 9 ? total * 100 : total * 10) + charValue;

            if (total > IBAN_MAX) {
                total = (total % IBAN_MODULUS);
            }
        }

        return (total % IBAN_MODULUS) == 1;
    }
}