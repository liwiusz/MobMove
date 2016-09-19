package mgr.mobmove.main;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mgr.mobmove.R;
import mgr.mobmove.trening.Ruch;


/**
 * A simple {@link Fragment} subclass.
 */
public class Trening extends Fragment {


    public Trening() {

    }
    String[] Cel = {"Dystans","Czas","Kalorie"};
String[] TypTreningu = {
        "Chod","EYE Tracker: Przejscie po linii prostej",
        "Przysiad MAX na LEWEJ: oczy OTWARTE",
        "Przysiad MAX na LEWEJ: oczy ZAMKNIETE",
        "Przysiad MAX na PRAWEJ: oczy OTWARTE",
        "Przysiad MAX na PRAWEJ oczy ZAMKNIETE",
        "Przysiad MAX: OTWARTE oczy",
        "Przysiad MAX: ZAMKNIETE oczy",
        "ROWNOWAGA LEWA OTWARTE",
        "ROWNOWAGE PRAWA OTWAERE",
        "ROWNOWAGA LEWA ZAMKNIETE",
        "ROWNOWAGA PRAWA ZAMKNIETE",
        "SKLON W PRZOD",
        "WSPIECIE NA PALCE: OTWARTE",
        "WSPIECIE NA PALCE: ZAMKNIETE",
        "INNE"};
    private LocationManager locationManager;
    private LocationListener listener;
    private Button startRuch;
    private String rodzajAktywnosci;
    private Spinner spinnerTrning;
    private Spinner spinner;
    private EditText uwagi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View fragmentTrening = inflater.inflate(R.layout.fragment_trening,container,false);
        uwagi = (EditText) fragmentTrening.findViewById(R.id.editTextUwagi);

        final EditText text = (EditText) fragmentTrening.findViewById(R.id.celText8);
        startRuch = (Button)fragmentTrening.findViewById(R.id.startbutton);
 spinnerTrning = (Spinner)fragmentTrening.findViewById(R.id.spinner);
        ArrayAdapter<String> adapterTrening = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,TypTreningu);
        adapterTrening.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTrning.setAdapter(adapterTrening);

        spinner = (Spinner) fragmentTrening.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,Cel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


               switch (position) {
                   case 0:
                        text.setInputType(InputType.TYPE_CLASS_NUMBER);
                   case 1:
                       text.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                   case 2:
                       text.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                   default:
                       text.setInputType(InputType.TYPE_CLASS_NUMBER);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               text.setInputType(InputType.TYPE_CLASS_NUMBER);
           }
       });
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();



        return fragmentTrening;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){

        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        startRuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                Intent intent = new Intent(getActivity(), Ruch.class);
                rodzajAktywnosci = spinnerTrning.getSelectedItem().toString();

                intent.putExtra("Rodzaj",rodzajAktywnosci);
                intent.putExtra("Cel",spinner.getSelectedItem().toString());
                intent.putExtra("Inne",uwagi.getText().toString());
                startActivity(intent);

            }
        });
    }
}
