package com.example.candyaou.helloteest;

/**
 * Created by CandyAou on 10/5/15.
 */
import android.app.Dialog;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class Routemap extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener {
    GoogleMap mMap;
    GMapV2Direction md;
    boolean setCurrent;
    LatLng fromPosition ;
    LatLng toPosition = new LatLng(13.764057, 100.538490);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routemap);


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
        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
        mMap.addMarker(new MarkerOptions().position(toPosition).title("End"));
        tvLocation.setText("Latitude : " + lati + ",Longitude : " + longi);

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
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        mMap.addPolyline(rectLine);
    }
}

