package com.example.candyaou.helloteest;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by CandyAou on 10/2/15.
 */
public class Act2 extends Activity implements View.OnClickListener{
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2);
        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent myintent = new Intent(v.getContext(),MainActivity.class);
        startActivity(myintent);

    }
}
