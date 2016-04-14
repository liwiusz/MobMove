package mgr.mobmove.main;


import android.content.Intent;
import android.os.Bundle;
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

import mgr.mobmove.R;
import mgr.mobmove.trening.Ruch;


/**
 * A simple {@link Fragment} subclass.
 */
public class Trening extends Fragment {


    public Trening() {

    }
    String[] Cel = {"Dystans","Czas","Kalorie"};
String[] TypTreningu = {"Bieg","Spacer","Skok"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View fragmentTrening = inflater.inflate(R.layout.fragment_trening,container,false);
        final EditText text = (EditText) fragmentTrening.findViewById(R.id.celText8);
Spinner spinnerTrning = (Spinner)fragmentTrening.findViewById(R.id.spinner);
        ArrayAdapter<String> adapterTrening = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,TypTreningu);
        adapterTrening.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTrning.setAdapter(adapterTrening);

        Spinner spinner = (Spinner) fragmentTrening.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,Cel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               Log.d("TESTY:", String.valueOf(position));
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

        final Button startRuch = (Button)fragmentTrening.findViewById(R.id.startbutton);
        startRuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ruch.class);
                startActivity(intent);
            }
        });


        return fragmentTrening;
    }


}
