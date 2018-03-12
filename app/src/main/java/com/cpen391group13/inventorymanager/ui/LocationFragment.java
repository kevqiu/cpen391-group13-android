package com.cpen391group13.inventorymanager.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationFragment extends Fragment {
    @BindView(R.id.location_text) TextView locationText;
    @BindView(R.id.lat_lng_text) TextView latLngText;
    @BindView(R.id.close_map_btn) ImageButton closeMapButton;
    @BindView(R.id.map_view) MapView mapView;

    private GoogleMap googleMap;
    boolean showMarker = false;

    LatLng latLng;
    String location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);

        mapView.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        latLng = bundle.getParcelable("latlng");
        location = bundle.getString("warehouse");

        final String latLngString = String.format("(%.6f, %.6f)", latLng.latitude, latLng.longitude);
        latLngText.setText(latLngString);
        locationText.setText(location);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;

                if (showMarker) {
                    // For showing a move to my location button
                    googleMap.setMyLocationEnabled(true);
                }

                // For dropping a marker at a point on the Map
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(location)
                        .snippet(latLngString));

                // For zooming automatically to the location of the marker
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            }
        });

        closeMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("mapfrag", "closing fragment");
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMarker = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied for location services", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
