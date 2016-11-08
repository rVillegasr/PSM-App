package fcfm.psm.psm_app;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Geocoder geocoder;
    GoogleMap map;
    LocationManager locationManager;

    boolean accessGranted = false;
    final int REQUEST_LOCATION_PERMISSIONS = 100;

    String address;
    String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        geocoder = new Geocoder(this);
        // Inicializa nuestro objeto LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Muestra el boton de "Mi Ubicacion" en el mapa (El tipico circulo azul de google)

        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(MapsActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MapsActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, REQUEST_LOCATION_PERMISSIONS);
        }else {
            accessGranted = true;
        }

        if(accessGranted){
            map.setMyLocationEnabled(true);
        }


        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        eventName = intent.getStringExtra("name");

        LatLng location = getAddressLocation(address);

        if(location != null) {
            MarkerOptions marker = new MarkerOptions().position(location).title(eventName).snippet(address);
            map.addMarker(marker);
            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(location, 16.0f);
            map.moveCamera(cu);
        }
        else{
            showToast("Location not found");
            LatLng currentLocation = null;
            try {
                currentLocation = getCurrentLocation();
            }catch (SecurityException e) {
                e.printStackTrace();
            }
            if (currentLocation != null) {
                // Movemos la camara para que apunte a otra coordenada diferente e la default
                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(currentLocation, 16.f);
                map.moveCamera(cu);

            }
        }
    }

    private LatLng getCurrentLocation() throws SecurityException {
        // Obtenemos los proveedores
        List<String> providers = locationManager.getProviders(true);

        //Location almacena latitud
        Location bestLocation = null;
        for (String provider : providers) {
            // getLastKnowLocation: Obtiene la ultima ubicacion conocidad por el proveedor seleccinado
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());
    }

    private LatLng getAddressLocation(String strAddress) {
        List<Address> foundAddress;
        LatLng result = null;
        try{
            foundAddress = geocoder.getFromLocationName(strAddress, 5);
            if(foundAddress == null){
                return null;
            }
            Address location = foundAddress.get(0);
            result = new LatLng(location.getLatitude(), location.getLongitude());


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                accessGranted = true;
                try {
                    map.setMyLocationEnabled(true);
                } catch(SecurityException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
