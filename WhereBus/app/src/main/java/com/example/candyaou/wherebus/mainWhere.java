package com.example.candyaou.wherebus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CandyAou on 10/12/15.
 */
public class mainWhere extends ActionBarActivity {
    private Button bStart;
    private  Spinner spinner;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwhere);
        addItemsOnSpinner();
        bStart = (Button) findViewById(R.id.button_start);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = spinner.getSelectedItem().toString();

                finish();
                Intent intent = new Intent(mainWhere.this, mainMap.class);
                startActivity(intent);
            }
        });

    }

    public void getLocation()throws URISyntaxException, HttpException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://172.20.10.4/test/getLoca.php");
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("busID",text));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String responseBody = EntityUtils.toString(response.getEntity());

        }catch  (IOException e) {

        }

    }

    public void addItemsOnSpinner() {
        //get Bus from database then loop bus that active
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("140");
        list.add("558");
        list.add("88");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }



}
