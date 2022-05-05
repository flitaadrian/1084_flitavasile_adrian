package com.example.adrianflita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.adrianflita.database.AppRoomDatabase;
import com.example.adrianflita.fragments.AccountFragment;
import com.example.adrianflita.fragments.HomeFragment;
import com.example.adrianflita.fragments.InfoFragment;
import com.example.adrianflita.fragments.OperationFragment;
import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.utils.Constante;
import com.example.adrianflita.utils.DateConverter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    Fragment homeFragment ;
    Fragment operationFramgent  ;
    Fragment infoFragment ;
    Fragment accountFragment ;

    FragmentManager fm ;
    Fragment active;

    CreateUser user;

    private ContUserDAO contUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (CreateUser) getIntent().getSerializableExtra(Constante.USER_KEY);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        homeFragment = new HomeFragment();
        operationFramgent = new OperationFragment();
        infoFragment = new InfoFragment();
        accountFragment = new AccountFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constante.USER_KEY, user);
        accountFragment.setArguments(bundle);

        fm = getSupportFragmentManager();
        active = homeFragment;

        fm.beginTransaction().add(R.id.frament_contianer,operationFramgent,"operation").hide(operationFramgent).commit();
        fm.beginTransaction().add(R.id.frament_contianer,infoFragment,"info").hide(infoFragment).commit();
        fm.beginTransaction().add(R.id.frament_contianer,accountFragment,"account").hide(accountFragment).commit();
        fm.beginTransaction().add(R.id.frament_contianer,homeFragment,"home").commit();


        sharedPreferences =  getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        contUserDAO = AppRoomDatabase.getInstance(getApplicationContext()).getContUserDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContUser contUser = contUserDAO.contUser(sharedPreferences.getLong(Constante.USERID_KEY, 0));

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constante.CONTID_KEY, contUser);

                operationFramgent.setArguments(bundle);
                homeFragment.setArguments(bundle);

            }
        }).start();
    }

    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                switch (item.getItemId()){
                    case R.id.item_home:
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
                        return true;
                    case R.id.item_operations:
                        fm.beginTransaction().hide(active).show(operationFramgent).commit();
                        active = operationFramgent;
                        return true;
                    case R.id.item_info:
                        fm.beginTransaction().hide(active).show(infoFragment).commit();
                        active = infoFragment;
                        return true;

                    case R.id.item_account:
                        fm.beginTransaction().hide(active).show(accountFragment).commit();

                        active = accountFragment;
                        return true;
                }
                return false;
            };
}