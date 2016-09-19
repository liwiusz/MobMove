package mgr.mobmove.trening;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import mgr.mobmove.R;

public  class Mapa extends Fragment  implements OnMapReadyCallback,LocationListener {
    private GoogleMap map;
    MapView m;
                   Marker now;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapka,container,false);

        m = (MapView) v.findViewById(R.id.mapka);
        m.onCreate(savedInstanceState);
        m.getMapAsync(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        m.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        m.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        m.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        m.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap map) {
       map.setMyLocationEnabled(true);

LatLng mapCenter = new LatLng(41.889, -87.622);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));


        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(13)
                .bearing(90)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);


        map.addPolyline(new PolylineOptions().add(
                mapCenter,new LatLng(10.761184,106.683331),
                new LatLng(10.760710,106.682204)).width(10).color(Color.RED)
        );
        Log.d("MAPA","TEST");
    }


                   @Override
                   public void onLocationChanged(Location location) {

                       if(now != null){
                           now.remove();

                       }



                       // Getting latitude of the current location
                       double latitude = location.getLatitude();

                       // Getting longitude of the current location
                       double longitude = location.getLongitude();

                       // Creating a LatLng object for the current location
                       LatLng latLng = new LatLng(latitude, longitude);
                       now = map.addMarker(new MarkerOptions().position(latLng));
                       // Showing the current location in Google Map
                       map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                       // Zoom in the Google Map
                       map.animateCamera(CameraUpdateFactory.zoomTo(15));

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
               }
