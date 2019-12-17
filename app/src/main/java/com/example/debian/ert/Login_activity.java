package com.example.debian.ert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    Button mCreate,mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);


        // TODO: 23/01/2019 find out why the error occur on the next click
        // TODO: 23/01/2019  refer to the video downloaded for the session/sharedpreference


        mLogin = (Button)findViewById(R.id.btnLogin);
        mLogin.setOnClickListener(this);
        mCreate = (Button)findViewById(R.id.btnCreate);
        mCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent log = new Intent(v.getContext(),Login_activity.class);
                startActivity(log);
                finish();
                break;
            case R.id.btnCreate:
                Intent reg = new Intent(v.getContext(), Register.class);
                startActivity(reg);
                finish();
                break;
                default:
                    break;
        }
    }
}
