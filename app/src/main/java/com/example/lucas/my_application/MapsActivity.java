package com.example.lucas.my_application;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String ACTION_FILTER = "com.example.proximityalert";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        final LatLng cantina = new LatLng(-21.2273078, -44.9775676);
        final LatLng portaria = new LatLng(-21.2310722, -44.9940471);

        final MarkerOptions marcadorCatina = new MarkerOptions()
                .position(cantina)
                .title("Cantina Central")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        final MarkerOptions marcadorPortaria = new MarkerOptions()
                .position(portaria)
                .title("Portaria")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        mMap.addMarker(marcadorPortaria);
        mMap.addMarker(marcadorCatina);

        Float velocidade = 42.0f;
        final LatLng mamute = new LatLng(-21.2288712, -44.9846763);
        MarkerOptions marcadorMamute = new MarkerOptions()
                .position(mamute)
                .title("Mamute está aqui")
                .snippet("Descendo a " + velocidade.intValue() + " km/h")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        final Marker M = mMap.addMarker(marcadorMamute);

        LatLng mamute2 = cantina;



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms


                    M.setPosition(new LatLng(mamute.latitude - 0.0001, mamute.longitude - 0.0001));

            }
        }, 2000);


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mamute));
    }



    public static void main(String[] args) throws IOException {


        String serverHostname = new String ("127.0.0.1");
        int port = 10008;

        System.out.println ("Attemping to connect to host "+serverHostname+" on port "+port+".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;


        while (true) {
            userInput = stdIn.readLine();
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
        }
    }
}