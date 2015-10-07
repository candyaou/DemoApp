package com.example.candyaou.helloteest;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CandyAou on 10/2/15.
 */
public class Act2 extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener{//implements OnMapReadyCallback {//implements OnMapReadyCallback,,View.OnClickListener{

    GoogleMap googleMap;
    boolean setCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.act2);

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


    @Override
    public void onMyLocationChange(Location location) {
        TextView tvLocation = (TextView) findViewById(R.id.textView1);
        double lati = location.getLatitude();
        double longi = location.getLongitude();
        LatLng lat = new LatLng(lati,longi);

        if(setCurrent == false) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(lat));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat,15));
            setCurrent = true;

        }
        tvLocation.setText("Latitude : "+lati+",Longitude : "+longi);

    }

}
