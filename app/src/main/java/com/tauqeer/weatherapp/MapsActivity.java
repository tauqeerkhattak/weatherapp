package com.tauqeer.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.IOException;
import java.util.List;
import io.reactivex.functions.Consumer;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private LatLngBounds PAKISTAN = new LatLngBounds(
            new LatLng(25.1, 61.7), new LatLng(35.4, 80.2)
    );
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //if (isNetworkAvailable()) {
            mapFragment.getMapAsync(this);
        //} else {
          //  Toast.makeText(this,"Please turn on your internet and restart the application.",Toast.LENGTH_LONG).show();
        //}
        rxPermissions = new RxPermissions(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        } else {
                            Toast.makeText(MapsActivity.this,"We need location data to show your current exact area weather!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addMarkerToMap("Karachi",24.906,67.082);
        addMarkerToMap("Hyderabad",25.392,68.374);
        addMarkerToMap("Murree",33.907, 73.393);
        addMarkerToMap("Lahore",31.550, 74.344);
        addMarkerToMap("Peshawar",34.0123846, 71.5787458);
        addMarkerToMap("Quetta",30.199, 67.010);
        addMarkerToMap("Islamabad",33.6938118, 73.0651511);
        addMarkerToMap("Multan",30.196, 71.475);
        addMarkerToMap("Khairpur",27.529, 68.763);
        addMarkerToMap("Nawabshah", 26.248, 68.410);
        addMarkerToMap("Sibi",29.5500097, 67.8833398);
        addMarkerToMap("Faisalabad",31.4220558, 73.0923253);
        addMarkerToMap("Sanghar",26.0470447, 68.9492402);
        addMarkerToMap("Mardan",34.1937969, 72.0451467);
        addMarkerToMap("Skardu",35.288, 75.633);
        mMap.setLatLngBoundsForCameraTarget(PAKISTAN);
        try {
            List<Address> address = new Geocoder(this).getFromLocationName("Pakistan", 1);
            if (address == null) {
                Log.e("ERROR", "Not found");
            } else {
                Address loc = address.get(0);
                Log.e("ERROR", loc.getLatitude() + " " + loc.getLongitude());
                LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.setMinZoomPreference(6);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    public Marker addMarkerToMap (String title, double v,double v1) {
        LatLng location = new LatLng(v,v1);
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(title));
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();
        if (title.equalsIgnoreCase("Karachi")) {
            startNewActivity(karachiActivity.class);
        }
        else if (title.equalsIgnoreCase("Hyderabad")) {
            startNewActivity(HyderabadActivity.class);
        }
        else if (title.equalsIgnoreCase("Murree")) {
            startNewActivity(MurreeActivity.class);
        }
        else if (title.equalsIgnoreCase("Lahore")) {
            startNewActivity(LahoreActivity.class);
        }
        else if (title.equalsIgnoreCase("Peshawar")) {
            startNewActivity(PeshawarActivity.class);
        }
        else if (title.equalsIgnoreCase("Quetta")) {
            startNewActivity(QuettaActivity.class);
        }
        else if (title.equalsIgnoreCase("Islamabad")) {
            startNewActivity(IslamabadActivity.class);
        }
        else if (title.equalsIgnoreCase("Multan")) {
            startNewActivity(MultanActivity.class);
        }
        else if (title.equalsIgnoreCase("Khairpur")) {
            startNewActivity(KhairpurActivity.class);
        }
        else if (title.equalsIgnoreCase("Nawabshah")) {
            startNewActivity(NawabActivity.class);
        }
        else if (title.equalsIgnoreCase("Sibi")) {
            startNewActivity(SibiActivity.class);
        }
        else if (title.equalsIgnoreCase("Faisalabad")) {
            startNewActivity(FaisalabadActivity.class);
        }
        else if (title.equalsIgnoreCase("Sanghar")) {
            startNewActivity(SangharActivity.class);
        }
        else if (title.equalsIgnoreCase("Mardan")) {
            startNewActivity(MardanActivity.class);
        }
        else if (title.equalsIgnoreCase("Skardu")) {
            startNewActivity(SkarduActivity.class);
        }
        System.out.println("Marker pressed!");
        return false;
    }

    //Custom method to start a new Activity
    public void startNewActivity(Class activity) {
        Intent intent = new Intent(MapsActivity.this,activity);
        startActivity(intent);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Location location = getLocation(this);
                if (location != null) {
                    Intent intent = new Intent(MapsActivity.this,GPSLocationActivity.class);
                    System.out.println(location.getAltitude());
                    intent.putExtra("Latitude",location.getLatitude());
                    intent.putExtra("Longitude",location.getLongitude());
                    startActivity(intent);
                    return true;
                }
                else {
                    Toast.makeText(this,"Location not found!",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            else {
                Toast.makeText(this,"Please turn on your location!",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            Toast.makeText(this,"Please turn on location permission to use this feature!",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this,"Current Location: "+location,Toast.LENGTH_SHORT).show();
        System.out.println(location.toString());
    }

    //Method to get Location from GPS or Network. Taken from the internet.
    public Location getLocation (Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location gpsLocation = null, networkLocation = null,finalLocation = null;
        if (isGPSEnabled) {
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                return null;
            }
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (isNetworkEnabled) {
            networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (gpsLocation != null && networkLocation != null) {
            if (gpsLocation.getAccuracy() > networkLocation.getAccuracy()) {
                return finalLocation = networkLocation;
            }
            else {
                return finalLocation = gpsLocation;
            }
        }
        else {
            if (gpsLocation != null) {
                return finalLocation = gpsLocation;
            }
            else if (networkLocation != null){
                return finalLocation = networkLocation;
            }
        }
        return finalLocation;
    }

    private boolean isNetworkAvailable () {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}