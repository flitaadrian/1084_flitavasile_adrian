package com.example.adrianflita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adrianflita.database.AppRoomDatabase;
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.CreateUserDAO;
import com.example.adrianflita.utils.Constante;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvCreate;
    private Button btnLogin;
    private CreateUserDAO createUserDAO;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUserDAO = AppRoomDatabase.getInstance(getApplicationContext()).getCreateUserDAO();
        sharedPreferences = getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String password = sharedPreferences.getString(Constante.PASSWORD_KEY, null);
        String username = sharedPreferences.getString(Constante.USERNAME_KEY, null);



        init();
        if(password != null && username != null){
            etPassword.setText(password);
            etUsername.setText(username);
        }
        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           CreateUser user = createUserDAO.login(etUsername.getText().toString(), etPassword.getText().toString());

                           if(user != null){
                               editor.putString(Constante.USERNAME_KEY,etUsername.getText().toString());
                               editor.putString(Constante.PASSWORD_KEY,etPassword.getText().toString());
                               editor.putLong(Constante.USERID_KEY,user.getId());
                               editor.apply();
                               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                               intent.putExtra(Constante.USER_KEY,user);
                               startActivity(intent);
                               finish();
                           }

                        }
                    }).start();


                }

            }
        });
    }

    private boolean isCorrect() {
        if (etPassword.getText() == null || etPassword.getText().toString().isEmpty() || etPassword.getText().toString().trim().length() == 0) {
            etPassword.setError("Invalid input");
            return false;
        }

        if (etUsername.getText() == null || etUsername.getText().toString().isEmpty() || etUsername.getText().toString().trim().length() == 0) {
            etUsername.setError("Invalid input");
            return false;
        }

        return true;
    }

    private void init(){

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvCreate = findViewById(R.id.tv_create_account);
        btnLogin = findViewById(R.id.bt_login);
    }
}