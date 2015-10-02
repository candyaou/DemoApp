package com.example.candyaou.helloteest;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by CandyAou on 10/2/15.
 */
public class Act2 extends FragmentActivity implements OnMapReadyCallback {//implements View.OnClickListener{
    //Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act2);
//        btn=(Button)findViewById(R.id.button2);
//        btn.setOnClickListener(this);

        setContentView(R.layout.act2);
        //setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));



    }

//    @Override
//    public void onClick(View v) {
//        Intent myintent = new Intent(v.getContext(),MainActivity.class);
//        startActivity(myintent);
//
//    }
}
