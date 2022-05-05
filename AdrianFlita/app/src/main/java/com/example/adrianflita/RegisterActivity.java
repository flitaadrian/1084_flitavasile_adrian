package com.example.adrianflita;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrianflita.database.AppRoomDatabase;
import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.models.DAO.CreateUserDAO;
import com.example.adrianflita.utils.Constante;
import com.example.adrianflita.utils.DateConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etSurname;
    private EditText etBirthDate;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvSignin;
    private Button btCreateUser;
    final Calendar calendar= Calendar.getInstance();

    private CreateUserDAO createUserDAO;
    private ContUserDAO contUserDAO;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences(Constante.SHARED_PREF_KEY,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        createUserDAO = AppRoomDatabase.getInstance(getApplicationContext()).getCreateUserDAO();
        contUserDAO = AppRoomDatabase.getInstance(getApplicationContext()).getContUserDAO();
        init();

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect()){
                    CreateUser createUser = new CreateUser(etName.getText().toString(), etSurname.getText().toString(),
                            DateConverter.fromString(etBirthDate.getText().toString()), etUsername.getText().toString(),
                            etPassword.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           long insert = createUserDAO.insertUser(createUser);
                           if ( insert == -1 ) {
                               Log.d("DB","Not inserted " +insert);
//                               Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_LONG).show();

                           }else{
                               Log.d("DB","Inserted " +insert);

//                               Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                               Random rand = new Random();
                               String card = "RO";
                               for (int i = 0; i < 14; i++)
                               {
                                   int n = rand.nextInt(10) + 0;
                                   card += Integer.toString(n);
                               }

                               Calendar calendar = Calendar.getInstance();
                               calendar.setTime(new Date());
                               calendar.add(Calendar.DAY_OF_YEAR, +5);
                               Date newDate = calendar.getTime();

                               long createCont = contUserDAO.insertContUser(new ContUser(card,newDate,1000,0, insert));
                               if (createCont !=-1) {
                                   Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                   intent.putExtra(Constante.USER_KEY,createUser);
                                   editor.putLong(Constante.USERID_KEY,insert);
                                   startActivity(intent);
                                   finish();
                               }
                           }
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(), "Check inputs", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init(){
        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_lastname);
        etBirthDate = findViewById(R.id.et_birthday);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvSignin = findViewById(R.id.tv_login_account);
        btCreateUser = findViewById(R.id.bt_create);
    }

    private boolean isCorrect(){
            if (etName.getText() == null || etName.getText().toString().isEmpty() || etName.getText().toString().trim().length() == 0){
                etName.setError("Invalid input");
                return false;
            }

        if (etSurname.getText() == null || etSurname.getText().toString().isEmpty() || etSurname.getText().toString().trim().length() == 0){
            etSurname.setError("Invalid input");
            return false;
        }

        if (etBirthDate.getText() == null || etBirthDate.getText().toString().isEmpty() || etBirthDate.getText().toString().trim().length() == 0){
            etBirthDate.setError("Invalid input");
            return false;
        }

        if (etUsername.getText() == null ||  etUsername.getText().toString().isEmpty() || etUsername.getText().toString().trim().length() == 0){
            etUsername.setError("Invalid input");
            return false;
        }

        if (etPassword.getText() == null ||  etPassword.getText().toString().isEmpty() || etPassword.getText().toString().trim().length() == 0 || etPassword.getText().toString().length() < 6){
            etPassword.setError("Invalid input, password < 6 characters");
            return false;
        }

        return true;
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        etBirthDate.setText(dateFormat.format(calendar.getTime()));
    }
}