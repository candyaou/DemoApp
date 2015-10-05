package com.example.candyaou.helloteest;

/**
 * Created by CandyAou on 10/5/15.
 */
import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

public class Routemap extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener{
    GoogleMap mMap;
    GMapV2Direction md;


    LatLng fromPosition ;
    LatLng toPosition = new LatLng(13.683660045847258, 100.53900808095932);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routemap);


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status != ConnectionResult.SUCCESS) {
            int request = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, request);
            dialog.show();
        }else{
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
        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();


        LatLng coordinates = new LatLng(13.685400079263206, 100.537133384495975);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16));


//        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
//        int duration = md.getDurationValue(doc);
//        String distance = md.getDistanceText(doc);
//        String start_address = md.getStartAddress(doc);
//        String copy_right = md.getCopyRights(doc);
//
//        ArrayList<LatLng> directionPoint = md.getDirection(doc);
//        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
//
//        for(int i = 0 ; i < directionPoint.size() ; i++) {
//            rectLine.add(directionPoint.get(i));
//        }
//
//        mMap.addPolyline(rectLine);
    }

    @Override
    public void onMyLocationChange(Location location) {
        //TextView tvLocation = (TextView) findViewById(R.id.textView1);

        double lati = location.getLatitude();
        double longi = location.getLongitude();
        fromPosition = new LatLng(lati,longi);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
        mMap.addMarker(new MarkerOptions().position(toPosition).title("End"));
//        LatLng lat = new LatLng(lati,longi);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(lat));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //tvLocation.setText("Latitude : "+lati+",Longitude : "+longi);


        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
        int duration = md.getDurationValue(doc);
        String distance = md.getDistanceText(doc);
        String start_address = md.getStartAddress(doc);
        String copy_right = md.getCopyRights(doc);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

        for(int i = 0 ; i < directionPoint.size() ; i++) {
            rectLine.add(directionPoint.get(i));
        }

        mMap.addPolyline(rectLine);

    }
}
