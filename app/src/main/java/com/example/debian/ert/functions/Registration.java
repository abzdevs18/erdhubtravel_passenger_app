package com.example.debian.ert.functions;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.debian.ert.fragment.Account;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Registration extends AsyncTask<String,Void,String> {

    Context ctx;

    Registration(Context ctx){
        this.ctx =ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String reg_url = "https://helpagency.000webhostapp.com/inc/m_signup.php";
        String method = params[0];
        if (method.equals("registration")){
            String firstname = params[1];
            String lastname = params[2];
            String email = params[3];
            String password = params[4];

            try {
                URL url  = new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream os = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data = URLEncoder.encode("Username","UTF-8")+"="+ URLEncoder.encode(firstname,"UTF-8")+"&"+
                        URLEncoder.encode("Lastname","UTF-8")+"="+ URLEncoder.encode(lastname,"UTF-8")+"&"+
                        URLEncoder.encode("Email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8")+"&";

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS = httpURLConnection.getInputStream();

                IS.close();
                Intent intent = new Intent(ctx, Account.class);
                ctx.startActivity(intent);
                return "Register Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(ctx,Account.class);

        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
