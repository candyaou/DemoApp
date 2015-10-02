package com.example.candyaou.helloteest;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by CandyAou on 10/2/15.
 */
public class Act2 extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener{//implements OnMapReadyCallback {//implements OnMapReadyCallback,,View.OnClickListener{
    //Button btn;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act2);
//        btn=(Button)findViewById(R.id.button2);
//        btn.setOnClickListener(this);

        setContentView(R.layout.act2);
//        //setContentView(R.layout.activity_maps);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//        googleMap = mapFragment.getMap();
//        googleMap.setMyLocationEnabled(true);
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, true);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status != ConnectionResult.SUCCESS) {
            int request = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, request);
            dialog.show();
        }else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            googleMap = mapFragment.getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationChangeListener(this);

        }



    }



//    @Override
//    public void onMapReady(GoogleMap map) {
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//    }



    @Override
    public void onMyLocationChange(Location location) {
        TextView tvLocation = (TextView) findViewById(R.id.textView1);
        double lati = location.getLatitude();
        double longi = location.getLongitude();
        LatLng lat = new LatLng(lati,longi);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lat));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        tvLocation.setText("Latitude : "+lati+",Longitude : "+longi);

    }

//    @Override
//    public void onClick(View v) {
//        Intent myintent = new Intent(v.getContext(),MainActivity.class);
//        startActivity(myintent);
//
//    }



}
