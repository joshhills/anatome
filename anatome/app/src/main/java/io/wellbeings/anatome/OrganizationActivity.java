package io.wellbeings.anatome;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // overridePendingTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.organization_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException s) {}
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        final LatLng TutorialsPoint = new LatLng(54.978935, -1.613498);
        Marker TP = map.addMarker(new MarkerOptions().position(TutorialsPoint).title("TutorialsPoint"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.978935, -1.613498), 18.25f));
    }

}