package mgr.mobmove.trening;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mgr.mobmove.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mapka extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    public Mapka() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapka, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
