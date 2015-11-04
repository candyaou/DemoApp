package com.example.candyaou.shareme;

/**
 * Created by CandyAou on 10/6/15.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by CandyAou on 10/2/15.
 */
public class MainShare extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener{//implements OnMapReadyCallback {//implements OnMapReadyCallback,,View.OnClickListener{

    GoogleMap googleMap;
    boolean setCurrent;

    String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainshare);
        SharedPreferences sp = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        username = sp.getString("username","NoUser");
        password = sp.getString("password", "NoPass");



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


    //test sp
    public void ShowMes(String text){
        //Create Alert Message
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Test Check");
        builder.setMessage(text);
        builder.setPositiveButton("OK", null);
        android.support.v7.app.AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();



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

        try {
            updatePlace(lati,longi);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void updatePlace(double lati,double longi) throws URISyntaxException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.5.7.97/test/set_posi.php");


        //"http://localhost/test/update.php?username="+username+"&lati="+lati+" &longi="+longi


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
