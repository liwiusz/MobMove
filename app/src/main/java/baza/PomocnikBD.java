package baza;


/**
 * Created by Michał on 2016-03-17.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PomocnikBD  extends SQLiteOpenHelper {

    private Context mKontekst;
    public final static int WERSJA_BAZY = 1;
    public final static String NAZWA_BAZY="BAZA.db";

    public final static String NAZWA_USER ="Uzytkownik";
    public final static String IDUSER="ID";
    public final static String IMIE ="IMIE";
    public final static String NAZWISKO ="NAZWISKO";
    public final static String DATA_URODZENIA ="DATA URODZENIA";
    public final static String PLEC ="PLEC";
    public final static String WAGA ="WAGA";
    public final static String WZROST ="WAGA";
    public final static String EMAIL ="EMAIL";
    public final static String HASLO ="HASLO";

    public final static String NAZWA_TRENING="TRENING";
    public final static String IDTRENING = "ID";
    public final static String CZAS ="CZAS";
    public final static String DYSTANS ="DYSTANS";
    public final static String TRASA ="TRASA";
    public final static String PREDKOSCSREDNIA ="PREDKOSC_SREDNIA";
    public final static String PREDKOSCMAX ="PREDKOSC_MAX";
    public final static String KALORIE ="KALORIE";
    public final static String DATA ="DATA";
    public final static String KEY_TypTreningu ="KEY";
    public final static String KEY_User="KEY";

    public final static String NAZWA_TYPTRENINGU = "TYP_TRENINGU";
    public final static String IDTYP_TRENINGU = "ID";
    public final static String NAZWA ="NAZWA";

    public final static String NAZWA_TEMPO ="TEMPO";
    public final static String IDTEMPO = "ID";
    public final static String DYSTANS_T = "DYSTANS";
    public final static String CZAS_T = "CZAS";

    public final static String NAZWA_TRENING_TEMPO ="TRENING_TEMPO";
    public final static String KEY_IDTRENING="IDKEY";
    public final static String KEY_IDTEMPO="IDKEY";


public static String TW_TAB_USER = "CREATE TABLE"+NAZWA_USER+"("+IDUSER+"INTEGER PRIMARY KEY,"+
        IMIE+"TEXT NOT NULL,"+NAZWISKO+"TEXT,"+DATA_URODZENIA+"DATE,"+PLEC+"TEXT NOT NULL,"+
        WAGA+"DOUBLE,"+WZROST+"INTEGER,"+EMAIL+"TEXT NOT NULL,"+HASLO+"TEXT NOT NULL"+")";

public static String TW_TAB_TRENING ="CREATE TABLE"+NAZWA_TRENING+"("+IDTRENING+"INTEGER PRIMARY KEY,"+
        CZAS+"TIME,"+DYSTANS+"DOUBLE, "+TRASA+"TEXT,"+PREDKOSCSREDNIA+"DOUBLE,"+PREDKOSCMAX+"DOUBLE,"+
        KALORIE+"INTEGER,"+DATA+"DATE,"+KEY_TypTreningu+"INTEGER,"+KEY_User+"INTEGER"+")";

    public static String TW_TAB_TYPTRENINGU = "CREATE TABLE"+NAZWA_TYPTRENINGU+"("+IDTYP_TRENINGU+"INTEGER PRIMARY KEY,"+
            NAZWA+"TEXT not null"+")";

    public static String TW_TAB_TEMPO="CREATE TABLE"+NAZWA_TEMPO+"("+IDTEMPO+" INTEGER PRIMARY KEY,"+DYSTANS_T+"DOUBLE,"+CZAS_T+"TIME"+")";

    public static String TW_TAB_TRENING_TEMPO = "CREATE TABLE"+NAZWA_TRENING_TEMPO+"("+KEY_IDTRENING+"INTEGER,"+KEY_IDTEMPO+"INTEGER"+")";



    public PomocnikBD(Context context) {
        super(context, NAZWA_BAZY, null, WERSJA_BAZY);
        mKontekst = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TW_TAB_USER);
        db.execSQL(TW_TAB_TRENING);
        db.execSQL(TW_TAB_TEMPO);
        db.execSQL(TW_TAB_TRENING_TEMPO);
        db.execSQL(TW_TAB_TYPTRENINGU);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_USER);
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TRENING_TEMPO);
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TRENING);
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TEMPO);
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TYPTRENINGU);
    }
}
