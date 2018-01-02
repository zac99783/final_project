package com.example.zac.project1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapBusStation extends AppCompatActivity {


    LocationManager locationManager ;
    String provider;
    String  str_json;
    double latitude;
    double longitude;


    Button button;
    TextView location;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bus_station);



        button = (Button)findViewById(R.id.button);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapppp);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (
                        ActivityCompat.checkSelfPermission(MapBusStation.this
                                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(MapBusStation.this,
                                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(true);




                final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (loc == null) {

                    final LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(final Location location) {


                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };



                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 500, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
                }
                else {

                      latitude = loc.getLatitude();
                      longitude = loc.getLongitude();
                }




            }
        });




        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });










    }




}















