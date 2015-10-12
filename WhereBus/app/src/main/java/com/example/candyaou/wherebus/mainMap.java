package com.example.candyaou.wherebus;

/**
 * Created by CandyAou on 10/12/15.
 */

/**
 * Created by CandyAou on 10/5/15.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class mainMap extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener {
    GoogleMap mMap;
    GMapV2Direction md;
    boolean setCurrent;
    LatLng fromPosition ;
    LatLng toPosition = new LatLng(13.70988, 100.50283);
    MarkerOptions startMark,endMark;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            int request = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, request);
            dialog.show();
        } else {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(this);

        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        md = new GMapV2Direction();
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
    }

    @Override
    public void onMyLocationChange(Location location) {
        TextView tvLocation = (TextView) findViewById(R.id.latilongi);
        TextView EsDistance = (TextView) findViewById(R.id.distance);
        TextView EsTime = (TextView) findViewById(R.id.time);
        TextView st_address = (TextView) findViewById(R.id.adress);


        double lati = location.getLatitude();
        double longi = location.getLongitude();
        fromPosition = new LatLng(lati, longi);
        mMap.clear();

        //Pin MapMarker and show LatLng of current Location.
//        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
//        mMap.addMarker(new MarkerOptions().position(toPosition).title("End"));
        startMark = new MarkerOptions().position(fromPosition).title("Start");
        endMark = new MarkerOptions().position(toPosition).title("End");
        endMark.icon(BitmapDescriptorFactory.fromResource(R.drawable.raz));

        mMap.addMarker(startMark);
        mMap.addMarker(endMark);



        tvLocation.setText("Latitude : " + lati + ",Longitude : " + longi);


//        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
//        // Changing marker icon
//        // set yours icon here
//        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));
//        // adding marker
//        googleMap.addMarker(marker);




        //set bound between 2 place
        if(setCurrent == false) {
            LatLngBounds bounds = new LatLngBounds.Builder().include(fromPosition).include(toPosition).build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,90));
            setCurrent = true;

        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Get from GMapV2Direction.
        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);//MODE_TRANSIT
        int duration = (md.getDurationValue(doc)) / 60;
        String distance = md.getDistanceText(doc);
        String start_address = md.getStartAddress(doc);
        String copy_right = md.getCopyRights(doc);
        EsDistance.setText("Distance " + distance);
        EsTime.setText("Duration " + duration + " minutes");
        st_address.setText("Start from " + start_address);
        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.GREEN);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        mMap.addPolyline(rectLine);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Back to main page?");
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
                Intent intent = new Intent(mainMap.this, mainWhere.class);
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
