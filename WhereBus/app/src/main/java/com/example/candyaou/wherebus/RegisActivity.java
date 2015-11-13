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



public class RegisActivity extends ActionBarActivity {

    private Button mSignup;
    private EditText mUsername,mPassword,mCPassword;
    private String username,password,Cpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_regis);



        mSignup = (Button) findViewById(R.id.button_signup);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mCPassword = (EditText) findViewById(R.id.con_password);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    username = mUsername.getText().toString();
                    password = mPassword.getText().toString();
                    Cpassword = mCPassword.getText().toString();
                    if(!username.isEmpty()){
                        if(password.equals(Cpassword)) {
                            postData();
                        }else{
                            ShowMes("Password and Confirm Password Mismatch");
                        }
                    }else{
                        ShowMes("Please insert username");
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();

                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    public void postData() throws URISyntaxException, HttpException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://172.20.10.4/user/validate_user_regis.php");

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
                Confirm("Press OK to back to Main page");
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
        builder.setTitle("ERROR");
        builder.setMessage(text);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void Confirm(String text){
        //Create Alert Message

        DialogInterface.OnClickListener negClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Finish register");
        builder.setMessage(text);
        builder.setPositiveButton("OK", negClickListener);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
    }



    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to back to main page?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(intent);

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


