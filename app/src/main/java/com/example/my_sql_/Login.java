package com.example.my_sql_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView register;
    LinearLayout login;
    EditText email, password;

    private static final String url = "http://192.168.151.250/nikhil_api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login_button);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt = email.getText().toString();
                String passwordtxt = password.getText().toString();
                login(emailtxt, passwordtxt);
            }
        });


    }


    private void login(String emailtxt, String passwordtxt) {


        String qry = "?email=" + emailtxt + "&password=" + passwordtxt;

        class dbprocess extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("exist")) {
                    Toast.makeText(getApplicationContext(), "sucessfully login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
                }else if (s.equals("wrong password")){
                    Toast.makeText(getApplicationContext(), "Please enter correct password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), emailtxt+" is not register.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String[] strings) {

                String furl = strings[0];

                try {
                    URL url = new URL(furl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            }

        }
        dbprocess obj=new dbprocess();
        obj.execute(url+qry);
    }

}