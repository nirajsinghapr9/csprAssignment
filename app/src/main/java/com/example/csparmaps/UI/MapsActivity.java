package com.example.csparmaps.UI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.csparmaps.R;
import com.example.csparmaps.apiService.ProfileAPiService;
import com.example.csparmaps.databinding.ActivityMapsBinding;
import com.example.csparmaps.model.APiResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ProfileAPiService.CallBackData {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private APiResponse response;
    int size;
    private Button profile;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        profile = findViewById(R.id.profile);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        profile.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityProfile.class);
            intent.putExtra("data", response);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProfileAPiService.getInstance(this, this).getLocation();
        if (!isGooglePlayServicesAvailable(MapsActivity.this)) {
            showAlert();
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Failure")
                .setMessage("No Play Service Installed")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
        ProfileAPiService.getInstance(this, this).getLocation();

        // Add a marker in Sydney and move the camera
        LatLng camera = new LatLng(0.0, 0.0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(camera));
    }


    private void addMarker(APiResponse data, int s) {
        for (int i = 0; i < data.getLocation().size(); i++) {
            Marker addedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(data.getLocation().get(i).getLat()), Double.parseDouble(data.getLocation().get(i).getLong()))).title("Lat :  " + data.getLocation().get(i).getLat() + " " +
                    "Long :  " + data.getLocation().get(i).getLong()));
            addedMarker.setTag(data.getSuccess().get(i));
        }
        LatLng camera = new LatLng(Double.parseDouble(data.getLocation().get(s - 1).getLat()), Double.parseDouble(data.getLocation().get(s - 1).getLong()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(camera)
                .zoom(5).build();
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResponse(APiResponse data) {
        this.response = data;
        this.size = response.getLocation().size();
        addMarker(data, size);
    }


    @Override
    public void onFailure(Throwable e) {

    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

}