package baza;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mliwi on 18.09.2016.
 */
public class PomocnikBDTrening extends SQLiteOpenHelper {
    private Context mKontekst;

    public final static int WERSJA_BAZY = 1;
    public final static String NAZWA_BAZY = "baza_wartosci";
    public final static String NAZWA_TABELI = "wartosci";
    public final static String ID = "_id";

    public final static String NAZWATRENINGU = "nazwatreningu";
    public final static String DATA = "data";
    public final static String CZAS = "czas";

    public final static String FILENAME = "FILENAME";
    public final static String DYSTANS = "DYSTANS";
    public final static String SREDNIAPREDKOSC = "SREDNIAPREDKOSC";

    public final static String ETYKIETA = "PomocnikBD";


    public final static String TW_BAZY = "CREATE TABLE " + NAZWA_TABELI + "("
            + ID + " integer primary key autoincrement, " + NAZWATRENINGU
            + " text not null," + DATA + " text not null," + CZAS + " text not null,"
            + DYSTANS + " text not null,"
            + SREDNIAPREDKOSC + " text not null,"

            + FILENAME + " text not null);";

    private static final String KAS_BAZY = "DROP TABLE IF EXISTS "
            + NAZWA_TABELI;
    public PomocnikBDTrening(Context context) {
        super(context, NAZWA_BAZY, null, WERSJA_BAZY);
        mKontekst = context;
    }
    //tworzenie bazy danych
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TW_BAZY);
    }
    //aktualizacja bazy danych
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KAS_BAZY);
        onCreate(db);
    }
}