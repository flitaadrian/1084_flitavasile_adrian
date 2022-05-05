package com.example.adrianflita.fragments;

import static android.content.Context.MODE_PRIVATE;

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
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.models.DAO.CreateUserDAO;
import com.example.adrianflita.utils.Constante;
import com.example.adrianflita.utils.DateConverter;


public class AccountFragment extends Fragment {

    private EditText etName;
    private EditText etSurname;
    private EditText etDateB;
    private TextView tvIban;
    private TextView tvDateExp;

    private Button btnActualizare;

    private ContUserDAO contUserDAO;
    private CreateUserDAO createUserDAO;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    CreateUser user;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (CreateUser) getArguments().getSerializable(Constante.USER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_account, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etName = view.findViewById(R.id.et_name2);
        etSurname = view.findViewById(R.id.et_surname2);
        tvDateExp = view.findViewById(R.id.tV_data_Expirare);
        tvIban = view.findViewById(R.id.tv_iban);
        etDateB = view.findViewById(R.id.et_birthday_date2);
        btnActualizare = view.findViewById(R.id.bt_actualizare);

        etName.setText(user.getName());
        etSurname.setText(user.getSurname());
        etDateB.setText(DateConverter.fromDate(user.getDatebirth()));

        contUserDAO = AppRoomDatabase.getInstance(getContext()).getContUserDAO();
        createUserDAO = AppRoomDatabase.getInstance(getContext()).getCreateUserDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContUser contUser = contUserDAO.contUser(sharedPreferences.getLong(Constante.USERID_KEY, 0));

                tvIban.setText(" IBAN: "+contUser.getIBAN());
                tvDateExp.setText(" Data Expirare: "+ DateConverter.fromDate(contUser.getDateExpires()));

            }
        }).start();

        btnActualizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      int update =  createUserDAO.updateUser(sharedPreferences.getLong(Constante.USERID_KEY, 0),etName.getText().toString(), etSurname.getText().toString());

                      if (update != -1){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Date Actualizate", Toast.LENGTH_LONG).show();
                            }
                        });

                      }
                    }
                }).start();
            }
        });


        return view;
    }
}