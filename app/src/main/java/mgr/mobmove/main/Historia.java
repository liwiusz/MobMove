package mgr.mobmove.main;



import android.content.ContentUris;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import baza.PomocnikBD;
import baza.PomocnikBDTrening;
import baza.WartosciProvider;
import baza.WartosciProviderTrening;
import mgr.mobmove.R;
import mgr.mobmove.historia.TreningOpis;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * A simple {@link Fragment} subclass.
 */
public class Historia extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public Historia() {
        // Required empty public constructor
    }

    private SimpleCursorAdapter mAdapterKursora;
    private ListView mLista;
    public static final int KOD=2014;
    public static final String IDTRENINGU ="id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View v = inflater.inflate(R.layout.fragment_historia, container, false);
        mLista = (ListView) v.findViewById(R.id.lista_wartosci);
        wypelnijListe();
        //ustawienie trybu wielokrotnego wyboru
        mLista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        mLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startEdycja(id);
            }
        });
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projekcja = {PomocnikBDTrening.ID, PomocnikBDTrening.NAZWATRENINGU, PomocnikBDTrening.DATA, PomocnikBDTrening.CZAS};
        CursorLoader loaderKursora = new CursorLoader(getActivity(), WartosciProviderTrening.URI_ZAWARTOSCI, projekcja, null, null, null);
        return loaderKursora;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor dane) {
        mAdapterKursora.swapCursor(dane);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapterKursora.swapCursor(null);
    }

    private void startEdycja(long id)
    {
        String i = String.valueOf(id);
        Intent zamiar = new Intent(getActivity(),TreningOpis.class);
        zamiar.putExtra(IDTRENINGU,i);
        startActivity(zamiar);
    }

    private void wypelnijListe() {
        getLoaderManager().initLoader(0, null, this);
        String[] mapujZ = new String[]{PomocnikBDTrening.NAZWATRENINGU, PomocnikBDTrening.DATA,PomocnikBDTrening.CZAS};
        int[] mapujDo = new int[]{R.id.wartosc_nazwatreningu, R.id.wartosc_data, R.id.wartosc_czas};
        mAdapterKursora = new SimpleCursorAdapter(getActivity(), R.layout.wiersz_listy, null, mapujZ, mapujDo, 0);
        mLista.setAdapter(mAdapterKursora);
    }
//TODO: To zmienić że usuwanie będzie z edycji
//    private void kasujZaznaczone() {
//        long zaznaczone[] = mLista.getCheckedItemIds();
//        for (int i = 0; i < zaznaczone.length; ++i) {
//            getContentResolver().delete( //dodanie identyfikatora do URI
//                    ContentUris.withAppendedId(WartosciProvider.URI_ZAWARTOSCI,
//                            zaznaczone[i]), null, null);
//        }
//    }

}
