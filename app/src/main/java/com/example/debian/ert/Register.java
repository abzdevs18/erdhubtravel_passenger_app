package com.example.debian.ert;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.fragment.Account;
import com.example.debian.ert.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    Button mReg,mData;
    EditText mEmail,mPass,mCPass;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mEmail = findViewById(R.id.email);
        mPass = findViewById(R.id.password);
        mCPass = findViewById(R.id.cpassword);
        pd = new ProgressDialog(this);

    }


}
