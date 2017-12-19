package com.example.team04_final_project.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.team04_final_project.R;
import com.example.team04_final_project.data.Karaoke;
import com.example.team04_final_project.data.LatLngKaraoke;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



/**
 *  Create by Thanh Huynh
 */
public class GPSFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private List<LatLngKaraoke> karaokeList;
    DatabaseReference mData;
    CircleOptions circleOptions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gps, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }else {
            mapFragment.getMapAsync(this);
        }
    }
    public  void addMarker(GoogleMap mMap, List<LatLngKaraoke> list){
        int i = 0;
        for(LatLngKaraoke latlng : list){
            i++;
            LatLng mLatLng = new LatLng(latlng.getLat(),latlng.getLng());
            Double kc = CalculationByDistance(mLatLng,mLatLng) * 1000;
            String title = latlng.getTitle().toString();
            String snippet = latlng.getSnippet().toString();
            // Những địa điểm trong vòng 5km (mặc định từ chỗ đang đứng)
            if(kc < 5000) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(mLatLng).title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)));
            }
        }
    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP){
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        LatLng myhome = new LatLng(10.8398677, 106.5998327);
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
                .anchor(0.0f, 1.0f)
                .position(myhome)
                .title("Marker in myHome")
                .snippet("Welcome to myHome")
        );
        circleOptions = new CircleOptions().center(myhome).radius(5000).strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myhome, 18));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000,null);
        mMap.addCircle(circleOptions);
        addLatLngKaraoke();

    }

    public void addLatLngKaraoke() {
        karaokeList = new ArrayList<LatLngKaraoke>();
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Karaoke").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Karaoke kara = dataSnapshot.getValue(Karaoke.class);
                karaokeList.add(new LatLngKaraoke(kara.getmLat(),kara.getmLon(),kara.getmName(),kara.getmAddress()));
                addMarker(mMap,karaokeList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
