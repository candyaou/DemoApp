package com.example.candyaou.shareme;

/**
 * Created by CandyAou on 10/6/15.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by CandyAou on 10/2/15.
 */
public class MainShare extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener{//implements OnMapReadyCallback {//implements OnMapReadyCallback,,View.OnClickListener{

    GoogleMap googleMap;
    boolean setCurrent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainshare);


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status != ConnectionResult.SUCCESS) {
            int request = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, request);
            dialog.show();
        }else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            googleMap = mapFragment.getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setOnMyLocationChangeListener(this);
            googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        }
     }



    @Override
    public void onMyLocationChange(Location location) {

        TextView tvLocation = (TextView) findViewById(R.id.lalong);
        double lati = location.getLatitude();
        double longi = location.getLongitude();
        LatLng lat = new LatLng(lati,longi);
        if(setCurrent == false) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(lat));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 16));
            setCurrent = true;

        }
        tvLocation.setText("Latitude : " + lati + ",Longitude : " + longi);


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to LogOut?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
//                System.exit(0);
//                finish();
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//                startActivity(intent);
//                finish();
//                System.exit(0);
                finish();
                Intent intent = new Intent(MainShare.this, LoginActivity.class);
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
        AlertDialog alert=builder.create();
        alert.show();



    }
}
