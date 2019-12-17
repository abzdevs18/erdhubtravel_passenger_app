package com.example.debian.ert.functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debian.ert.R;
import com.example.debian.ert.Register;
import com.example.debian.ert.fragment.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private static String url_login ="http://192.168.0.82:8080/ert/sort/api/m_signup.php";
    private String mEmail,mPassword;
    private ProgressDialog progressDialog;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        email = (EditText)findViewById(R.id.username);
        password =(EditText)findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
    }

    public void regU(View view) {
        Intent reg = new Intent(Login.this, Register.class);
        startActivity(reg);
    }

    public void login(View view) {
        mEmail = email.getText().toString();
        mPassword = password.getText().toString();

        if (!mEmail.isEmpty() || !mPassword.isEmpty()){
            login(mEmail,mPassword);
        }else{
            // TODO: 24/01/2019 Add something drawable next to this error
            email.setError("Please insert Email");
            password.setError("Please check password");
        }
    }

    private void login(final String mEmail, final String mPassword) {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response));
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")){
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String Name = object.getString("firstname").trim();
                                    String email = object.getString("email").trim();

                                    SharedPreferences sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("Name",Name);
                                    editor.putString("Email",email);
                                    editor.apply();

                                    // TODO: 24/01/2019 its easy to write the below code for intent 
                                    startActivity(new Intent(getApplicationContext(), Account.class));
                                    finish();

                                    Toast.makeText(Login.this,"Email: " + email, Toast.LENGTH_LONG).show();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this,"Err "+e.toString()+" signing-in", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,"Failed "+error.toString()+" signing-in", Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", mEmail);
                params.put("password", mPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
