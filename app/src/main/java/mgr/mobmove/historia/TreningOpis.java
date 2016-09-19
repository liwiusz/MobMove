package mgr.mobmove.historia;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import baza.WartosciProviderTrening;
import mgr.mobmove.R;
import mgr.mobmove.main.Historia;

public class TreningOpis extends AppCompatActivity {
private String id="";
TextView nazwaPliku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_opis);

        Bundle bundle =getIntent().getExtras();
        id =bundle.getString(Historia.IDTRENINGU);
        Cursor c;
        c = getContentResolver().query(WartosciProviderTrening.URI_ZAWARTOSCI, null, "_id = " + id, null, null);
        c.moveToFirst();

        TextView rodzajAktywnosci = (TextView) findViewById(R.id.rodzajAktywnosci);
        TextView data = (TextView) findViewById(R.id.textViewData);
        TextView czas = (TextView) findViewById(R.id.textViewCzas);
        TextView dystans = (TextView)findViewById(R.id.textViewDystans);
        TextView sredniapredkosc= (TextView) findViewById(R.id.textViewSredniaPredkosc);
        TextView lokalizacjaPliku = (TextView)findViewById(R.id.textViewLokalizacja);
       nazwaPliku = (TextView)findViewById(R.id.textViewNazwaPliku);

        lokalizacjaPliku.setText(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        rodzajAktywnosci.setText(c.getString(c.getColumnIndex("nazwatreningu")));
        data.setText(c.getString(c.getColumnIndex("data")));
        czas.setText(c.getString(c.getColumnIndex("czas"))+" s");
        dystans.setText(c.getString(c.getColumnIndex("DYSTANS"))+" km");
        sredniapredkosc.setText(c.getString(c.getColumnIndex("SREDNIAPREDKOSC"))+ "m/s");
        nazwaPliku.setText(c.getString(c.getColumnIndex("FILENAME")));

    }
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +"/"+nazwaPliku.getText().toString());
        intent.setDataAndType(uri, "text/plain");
        startActivity(intent);
    }
}
