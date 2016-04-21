package io.wellbeings.anatome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Security;

/**
 * Display 'plugged-in' organization's
 * information to guide user to extra help.
 */
public class OrganizationActivity extends FragmentActivity implements OnMapReadyCallback {

    // Map-related private fields.
    private LatLng orgLocation;
    private final float ZOOM_LEVEL = 18.25f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // overridePendingTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        // TODO: Network checking!!

        populateContent();

        initGUI();

        // Create the inner map fragment.
        if(orgLocation != null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.organization_map);
            mapFragment.getMapAsync(this);
        }
    }

    private void initGUI() {

        // Exit button.
        ((ImageButton)findViewById(R.id.organization_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganizationActivity.this, MainScroll.class);
                intent.putExtra("from", "OrganizationActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            }
        });

    }

    private void populateContent() {

        // Attempt to retrieve the organization's location.
        orgLocation = UtilityManager.getDbUtility(this).getLatLong();

        /* Set textual content. */

        // Set organization name.
        ((TextView) findViewById(R.id.organization_name)).setText(
                UtilityManager.getDbUtility(this).getOrgName()
        );

        // Set organization description.
        ((TextView) findViewById(R.id.organization_description)).setText(
                UtilityManager.getDbUtility(this).getOrgDescription()
        );

    }

    /**
     * Implement customized map set-up.
     *
     * @param map Reference to the physical map element.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        // Style the map.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add location services.
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException s) {}

        // Allow the user to pinch around to see surroundings.
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);

        // Set pin to organization's location and style it.
        String pinName = UtilityManager.getDbUtility(this).getOrgName();
        if(pinName != null) {
            Marker orgMarker = map.addMarker(new MarkerOptions().position(orgLocation));
        }
        else {
            Marker orgMarker = map.addMarker(new MarkerOptions().position(orgLocation));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(orgLocation, ZOOM_LEVEL));

    }

}