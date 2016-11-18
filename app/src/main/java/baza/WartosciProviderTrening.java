package baza;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by mliwi on 18.09.2016.
 */
public class WartosciProviderTrening extends ContentProvider {
    private PomocnikBDTrening mPomocnikBD;
    //identyfikator (ang. authority) dostawcy
    private static final String IDENTYFIKATOR ="baza.WartosciProviderTrening";
    //sta�a � aby nie trzeba by�o wpisywa� tekstu samodzielnie
    public static final Uri URI_ZAWARTOSCI = Uri.parse("content://"+ IDENTYFIKATOR + "/" + PomocnikBDTrening.NAZWA_TABELI);
    //sta�e pozwalaj�ce zidentyfikowa� rodzaj rozpoznanego URI
    private static final int CALA_TABELA = 1;
    private static final int WYBRANY_WIERSZ = 2;
    //UriMacher z pustym korzeniem drzewa URI (NO_MATCH)
    private static final UriMatcher sDopasowanieUri =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //dodanie rozpoznawanych URI
        sDopasowanieUri.addURI(IDENTYFIKATOR, PomocnikBDTrening.NAZWA_TABELI,
                CALA_TABELA);
        sDopasowanieUri.addURI(IDENTYFIKATOR, PomocnikBDTrening.NAZWA_TABELI +
                "/#",WYBRANY_WIERSZ);
    }
    @Override
    public String getType(Uri uri) { return null; }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //czy wiersz czy ca�a tabela i otworzenie bazy
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        long idDodanego = 0;
        switch (typUri) {
            case CALA_TABELA:
                idDodanego =
                        baza.insert(PomocnikBDTrening.NAZWA_TABELI, //tablela
                                null, //nullColumnHack
                                values); //wartości
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PomocnikBDTrening.NAZWA_TABELI + "/" + idDodanego);
    }


    @SuppressLint("NewApi")
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        Cursor kursorTel = null;
        switch (typUri) {
            case CALA_TABELA:
                kursorTel = baza.query(false, //distinct
                        PomocnikBDTrening.NAZWA_TABELI, //tabela
                        projection, //kolumny
                        selection, //klauzula WHERE np. w=1 lub w=?
                        selectionArgs, //argumenty WHRERE je�eli wy�ej s� �?�
                        null, //GROUP BY
                        null, //HAVING
                        sortOrder, //ORDER BY
                        null, //ograniczenie liczby rekord�w, null - brak
                        null); //sygna� anulowania
                break;
            case WYBRANY_WIERSZ: //w przypadku jednego wiersza modyfikowana jest WHERE
                kursorTel = baza.query(false, PomocnikBDTrening.NAZWA_TABELI, projection,
                        dodajIdDoSelekcji(selection, uri), selectionArgs, null,
                        null, sortOrder, null, null);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }
        //URI mo�e by� monitorowane pod k�tem zmiany danych � tu jest rejestrowane.
        //obserwator (kt�rego trzeba zarejestrowa� b�dzie powiadamiany o zmianie danych)
        kursorTel.setNotificationUri(getContext().getContentResolver(), uri);
        return kursorTel;
    }

    @Override
    public boolean onCreate() {
        mPomocnikBD = new PomocnikBDTrening(getContext());
        return false;
    }
    //dodaje do klauzuli WHERE identyfikator wiersza odczytany z URI
    private String dodajIdDoSelekcji(String selekcja, Uri uri) {
        //je�eli ju� jest to dodajemy tylko dodatkowy warunek
        if (selekcja != null && !selekcja.equals(""))
            selekcja = selekcja + " and " + PomocnikBDTrening.ID + "="
                    + uri.getLastPathSegment();
            //je�eli nie ma WHERE tworzymy je od pocz�tku
        else
            selekcja = PomocnikBDTrening.ID + "=" + uri.getLastPathSegment();
        return selekcja;
    }

    @Override
    public int delete(Uri uri, String selection,
                      String[] selectionArgs) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        int liczbaUsunietych = 0;
        switch (typUri) {
            case CALA_TABELA:
                liczbaUsunietych = baza.delete(PomocnikBDTrening.NAZWA_TABELI,
                        selection, //WHERE
                        selectionArgs); //argumenty
                break;
            case WYBRANY_WIERSZ: //modyfikowane jest WHERE
                liczbaUsunietych = baza.delete(PomocnikBDTrening.NAZWA_TABELI,
                        dodajIdDoSelekcji(selection, uri), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }
        //powiadomienie o zmianie danych
        getContext().getContentResolver().notifyChange(uri, null);
        return liczbaUsunietych;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        int liczbaZaktualizowanych = 0;
        switch (typUri) {
            case CALA_TABELA:
                liczbaZaktualizowanych = baza.update(PomocnikBDTrening.NAZWA_TABELI,
                        values, //warto�ci
                        selection, //WHERE
                        selectionArgs); //argumenty WHERE
                break;
            case WYBRANY_WIERSZ: //modyfikacja WHERE
                liczbaZaktualizowanych = baza.update(PomocnikBDTrening.NAZWA_TABELI,
                        values, dodajIdDoSelekcji(selection, uri), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        } //powiadomienie o zmianie danych
        getContext().getContentResolver().notifyChange(uri, null);
        return liczbaZaktualizowanych;
    }

}
