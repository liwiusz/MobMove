package mgr.mobmove.main;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import baza.PomocnikBD;
import baza.WartosciProvider;
import mgr.mobmove.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Omnie extends Fragment {


    public Omnie() {
        // Required empty public constructor
    }
    EditText imie;
    EditText nazwisko;
    EditText dataU;
    EditText waga;
    EditText wzrost;
    EditText email;
    EditText haslo;
    RadioButton plec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_omnie, container, false);

        imie = (EditText)v.findViewById(R.id.editText);
        nazwisko =(EditText)v.findViewById(R.id.editText2);
        dataU=(EditText)v.findViewById(R.id.editText3);
        waga = (EditText)v.findViewById(R.id.editText7);
        wzrost = (EditText)v.findViewById(R.id.editText6);
        email = (EditText)v.findViewById(R.id.editText4);
        haslo = (EditText)v.findViewById(R.id.editText5);
        plec =(RadioButton)v.findViewById(R.id.radioButton);



//        try
//        {
//            Cursor c;
//            c = getActivity().getContentResolver().query(WartosciProvider.URI_ZAWARTOSCI, null, "producent = " + "a", null, null);
//            c.moveToFirst();
//
//
//            imie.setText(c.getString(c.getColumnIndex("producent")));
//            nazwisko.setText(c.getString(c.getColumnIndex("model")));
//        }
//        catch ()
//        {}



        Button dodaj = (Button)v.findViewById(R.id.buttonZapisz);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodajWartosc();


            }
        });


        return v;
    }

    void dodajWartosc()
    {
        ContentValues wartosci = new ContentValues();

        wartosci.put(PomocnikBD.IMIE,imie.getText().toString());
        wartosci.put(PomocnikBD.NAZWISKO,nazwisko.getText().toString());
        wartosci.put(PomocnikBD.DATA_URODZENIA,dataU.getText().toString());
        wartosci.put(PomocnikBD.WAGA,waga.getText().toString());
        wartosci.put(PomocnikBD.WZROST,wzrost.getText().toString());
        wartosci.put(PomocnikBD.EMAIL,email.getText().toString());
        wartosci.put(PomocnikBD.HASLO,haslo.getText().toString());
        if (plec.isChecked())
        {
            wartosci.put(PomocnikBD.PLEC,"K");
        } else wartosci.put(PomocnikBD.PLEC,"M");

        Uri urinowego = getActivity().getContentResolver().insert(WartosciProvider.URI_ZAWARTOSCI, wartosci);
    }
}
