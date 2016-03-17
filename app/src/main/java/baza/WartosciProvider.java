package baza;


import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Michał on 2016-03-17.
 */
public class WartosciProvider extends ContentProvider {
    private PomocnikBD mPomocnikBD;

   public static String nazwaTabeli=PomocnikBD.NAZWA_TRENING; // uzupełnić wybór tabeli
   public static String idTabeli = PomocnikBD.IDTRENING; // to też należy uzupełnić

    private static final String IDENTYFIKATOR ="baza.WartosciProvider";
    //sta³a – aby nie trzeba by³o wpisywaæ tekstu samodzielnie
    public static final Uri URI_ZAWARTOSCI = Uri.parse("content://"+ IDENTYFIKATOR + "/" + nazwaTabeli);
    //sta³e pozwalaj¹ce zidentyfikowaæ rodzaj rozpoznanego URI
    private static final int CALA_TABELA = 1;
    private static final int WYBRANY_WIERSZ = 2;
    //UriMacher z pustym korzeniem drzewa URI (NO_MATCH)


    private static final UriMatcher sDopasowanieUri =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //dodanie rozpoznawanych URI
        sDopasowanieUri.addURI(IDENTYFIKATOR, nazwaTabeli,
                CALA_TABELA);
        sDopasowanieUri.addURI(IDENTYFIKATOR, nazwaTabeli +
                "/#",WYBRANY_WIERSZ);
    }
    @Override
    public String getType(Uri uri) { return null; }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //czy wiersz czy ca³a tabela i otworzenie bazy
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        long idDodanego = 0;
        switch (typUri) {
            case CALA_TABELA:
                idDodanego =
                        baza.insert(nazwaTabeli, //tablela
                                null, //nullColumnHack
                                values); //wartoœci
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }
        //powiadomienie o zmianie danych (->np. odœwie¿enie listy)
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(nazwaTabeli + "/" + idDodanego);
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
                        nazwaTabeli, //tabela
                        projection, //kolumny
                        selection, //klauzula WHERE np. w=1 lub w=?
                        selectionArgs, //argumenty WHRERE je¿eli wy¿ej s¹ „?”
                        null, //GROUP BY
                        null, //HAVING
                        sortOrder, //ORDER BY
                        null, //ograniczenie liczby rekordów, null - brak
                        null); //sygna³ anulowania
                break;
            case WYBRANY_WIERSZ: //w przypadku jednego wiersza modyfikowana jest WHERE
                kursorTel = baza.query(false, nazwaTabeli, projection,
                        dodajIdDoSelekcji(selection, uri), selectionArgs, null,
                        null, sortOrder, null, null);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }
        //URI mo¿e byæ monitorowane pod k¹tem zmiany danych – tu jest rejestrowane.
        //obserwator (którego trzeba zarejestrowaæ bêdzie powiadamiany o zmianie danych)
        kursorTel.setNotificationUri(getContext().getContentResolver(), uri);
        return kursorTel;
    }

    @Override
    public boolean onCreate() {
        mPomocnikBD = new PomocnikBD(getContext());
        return false;
    }
    //dodaje do klauzuli WHERE identyfikator wiersza odczytany z URI
    private String dodajIdDoSelekcji(String selekcja, Uri uri) {
        //je¿eli ju¿ jest to dodajemy tylko dodatkowy warunek
        if (selekcja != null && !selekcja.equals(""))
            selekcja = selekcja + " and " + idTabeli + "="
                    + uri.getLastPathSegment();
            //je¿eli nie ma WHERE tworzymy je od pocz¹tku
        else
            selekcja = idTabeli + "=" + uri.getLastPathSegment();
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
                liczbaUsunietych = baza.delete(nazwaTabeli,
                        selection, //WHERE
                        selectionArgs); //argumenty
                break;
            case WYBRANY_WIERSZ: //modyfikowane jest WHERE
                liczbaUsunietych = baza.delete(nazwaTabeli,
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
                liczbaZaktualizowanych = baza.update(nazwaTabeli,
                        values, //wartoœci
                        selection, //WHERE
                        selectionArgs); //argumenty WHERE
                break;
            case WYBRANY_WIERSZ: //modyfikacja WHERE
                liczbaZaktualizowanych = baza.update(nazwaTabeli,
                        values, dodajIdDoSelekcji(selection, uri), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        } //powiadomienie o zmianie danych
        getContext().getContentResolver().notifyChange(uri, null);
        return liczbaZaktualizowanych;
    }

}
