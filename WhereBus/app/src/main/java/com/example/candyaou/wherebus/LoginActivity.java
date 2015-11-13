package com.example.candyaou.wherebus;

/**
 * Created by CandyAou on 11/12/15.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends ActionBarActivity {

    private Button mLogin,mRegis;
    private EditText mUsername,mPassword;
    private String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);



        mLogin = (Button) findViewById(R.id.button_login);
        mRegis = (Button) findViewById(R.id.button_regis);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    username = mUsername.getText().toString();
                    password = mPassword.getText().toString();
                    postData();
                } catch (URISyntaxException e) {
                    e.printStackTrace();

                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });

        mRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //finish();
                    Intent intent = new Intent(LoginActivity.this , RegisActivity.class);
                    startActivity(intent);


            }
        });



    }



    public void postData() throws URISyntaxException, HttpException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://172.20.10.4/user/validate_user_login.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("users_name",username));
            nameValuePairs.add(new BasicNameValuePair("users_pass",password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String responseBody = EntityUtils.toString(response.getEntity());

            //SharedPreferences to keep username and password
            SharedPreferences sp = getSharedPreferences("wherebus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.commit();


            JSONObject reader = new JSONObject(responseBody);
            Boolean status = reader.getBoolean("status");



            if (status) {
                Intent intent = new Intent(this, mainWhere.class);
                startActivity(intent);
                finish();
            }
            else {
                ShowMes("Please try again");
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void ShowMes(String text){
        //Create Alert Message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Username Or Password Incorrect");
        builder.setMessage(text);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();



    }
    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                System.exit(0);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();


    }
}

