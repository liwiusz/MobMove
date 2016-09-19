package mgr.mobmove.trening;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import baza.PomocnikBD;
import baza.PomocnikBDTrening;
import baza.WartosciProvider;
import baza.WartosciProviderTrening;
import mgr.mobmove.R;
import mgr.mobmove.plik.Dane;
import mgr.mobmove.plik.Plik;



public class Informacje extends Fragment implements LocationListener, SensorEventListener{


    TextView czas;
    public  long time =0;
    TextView speed;
    TextView MapaX,MapaY,DystansMap;
   private static  float currentDustance  ;
    Location oldLocation;
    Button saveTrening;
    ArrayList<Dane> dane = new ArrayList<>();
    private SensorManager sensorManager;
    private Sensor stepCount;
    TextView step;

    private static float nCurrentSpeed=0;
    private static double mapaX=0;
    private static double mapaY=0;
    private static double mapaXold=0;
    private static double mapaYold=0;
    private static float krok=0;
    private static long elapsedMillis=0;
    private String rodzajAktywnosci;
    private String cel;
    private String inne;
    public   boolean trwaTrening ;
    public ToggleButton toggleButton;


    public Informacje() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

Intent intentMain = getActivity().getIntent();
        Bundle bundle = intentMain.getExtras();
        rodzajAktywnosci = (String) bundle.get("Rodzaj");
        cel = (String) bundle.get("Cel");
        inne =(String) bundle.get("Inne");

        sensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        stepCount = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        currentDustance = 0;
        krok =0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_informacje, container, false);

        ImageButton stopButton = (ImageButton) v.findViewById(R.id.stopButton);
        toggleButton =(ToggleButton)v.findViewById(R.id.toggleButton);
        final Chronometer chronometer = (Chronometer)v.findViewById(R.id.chronometer);

        //TEXTVIEW --------------
        step = (TextView)v.findViewById(R.id.iloscKrokow);
        MapaX = (TextView)v.findViewById(R.id.xMap);
        MapaY=(TextView)v.findViewById(R.id.yMap);
    DystansMap = (TextView)v.findViewById(R.id.dystansMap);
        speed = (TextView)v.findViewById(R.id.speedText);

      //  chronometer.setFormat("HH:MM:SS");
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked())
                {
                    chronometer.setBase(SystemClock.elapsedRealtime()+time);
                    chronometer.start();
                    trwaTrening = true;


                }
                else
                {
                    time = chronometer.getBase()-SystemClock.elapsedRealtime();
                    chronometer.stop();
                    trwaTrening = false;
                }
            }
        });

        czas = (TextView) v.findViewById(R.id.czasText);
        czas.setText("00:00:05");
        final CounterClass timer = new CounterClass(5000,1000);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });


       LocationManager locationManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        this.onLocationChanged(null);


        saveTrening = (Button)v.findViewById(R.id.saveTrening);
       saveTrening.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               zapisywanie();
           }
       });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
               elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;

                Dane d = new Dane();
                d.setCzas(String.valueOf(elapsedMillis));
               //Accelometr
                d.setXa(String.valueOf(Sensory.xValue));
                d.setYa(String.valueOf(Sensory.yValue));
                d.setZa(String.valueOf(Sensory.zValue));
                //
                d.setGravityX(String.valueOf(Sensory.gravityX));
                d.setGravityY(String.valueOf(Sensory.gravityY));
                d.setGravityZ(String.valueOf(Sensory.gravityZ));
                //
                d.setGyroscopeX(String.valueOf(Sensory.gyroscopeX));
                d.setGyroscopeY(String.valueOf(Sensory.gyroscopeY));
                d.setGyroscopeZ(String.valueOf(Sensory.gyroscopeY));
                //
                d.setGyroscopeUXa(String.valueOf(Sensory.gyroscopeUXa));
                d.setGyroscopeUYa(String.valueOf(Sensory.gyroscopeUYa));
                d.setGyroscopeUZa(String.valueOf(Sensory.gyroscopeUZa));
                d.setGyroscopeUXb(String.valueOf(Sensory.gyroscopeUXb));
                d.setGyroscopeUYb(String.valueOf(Sensory.gyroscopeUYb));
                d.setGyroscopeUZb(String.valueOf(Sensory.gyroscopeUZb));
                //
                d.setLinearAccelometerX(String.valueOf(Sensory.linearAccelometerX));
                d.setLinearAccelometerY(String.valueOf(Sensory.linearAccelometerY));
                d.setLinearAccelometerZ(String.valueOf(Sensory.linearAccelometerZ));
                //
                d.setOrientationX(String.valueOf(Sensory.orientationX));
                d.setOrientationY(String.valueOf(Sensory.orientationY));
                d.setOrientationZ(String.valueOf(Sensory.orientationZ));
                //
                d.setRotationX(String.valueOf(Sensory.rotationX));
                d.setRotationY(String.valueOf(Sensory.rotationY));
                d.setRotationZ(String.valueOf(Sensory.rotationZ));


                d.setSpeed(String.valueOf(nCurrentSpeed));
                d.setxMap(String.valueOf(mapaX));
                d.setyMap(String.valueOf(mapaY));
                d.setDystans(String.valueOf(currentDustance));
                d.setKrok(String.valueOf(krok));
                dane.add(d);




            }
        });

        return v;
    }




    public  void zapisywanie()
    {



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Plik.save(rodzajAktywnosci+" "+currentDateandTime,dane,rodzajAktywnosci,String.valueOf(currentDustance),cel,inne);
        ContentValues wartosci = new ContentValues();
String sredniaPredkosc;
        try {
           sredniaPredkosc = String.valueOf(Float.parseFloat(DystansMap.getText().toString())/elapsedMillis);
        }
        catch (NumberFormatException nfe)
        {
            sredniaPredkosc ="0.0";
        }
        wartosci.put(PomocnikBDTrening.NAZWATRENINGU,rodzajAktywnosci);
            wartosci.put(PomocnikBDTrening.DATA,currentDateandTime);
            wartosci.put(PomocnikBDTrening.CZAS,elapsedMillis+"");
            wartosci.put(PomocnikBDTrening.FILENAME,rodzajAktywnosci+" "+currentDateandTime+".txt");
            wartosci.put(PomocnikBDTrening.DYSTANS,DystansMap.getText().toString());
       wartosci.put(PomocnikBDTrening.SREDNIAPREDKOSC,sredniaPredkosc);
            Uri urinowego = getActivity().getContentResolver().insert(WartosciProviderTrening.URI_ZAWARTOSCI, wartosci);
getActivity().finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        trwaTrening = true;
        sensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        stepCount = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCount !=null)
        {
            sensorManager.registerListener(this,stepCount,SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(getActivity(),"Sensor kroku jest niedostępny",Toast.LENGTH_LONG).show();
        }
        sensorManager.registerListener(this,stepCount,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        trwaTrening = false;
    }

    @Override
    public void onLocationChanged(Location location) {
if(location==null)
{
    speed.setText("0.0 m/s");
    nCurrentSpeed = 0;
    mapaX = 0;
    mapaY = 0;
    mapaYold = 0;
    mapaXold =0;
} else {
     nCurrentSpeed = location.getSpeed();
    speed.setText(nCurrentSpeed +"m/s");
    mapaX = location.getLatitude();
    mapaY = location.getLongitude();
    MapaY.setText(mapaY+"");
MapaX.setText(mapaX+"");
    Log.d("LOKALIZACJA new ",String.valueOf(location.getLatitude())+" "+String.valueOf(location.getLongitude()));
if(trwaTrening && oldLocation!=null && oldLocation!=location)
{

    currentDustance += (oldLocation.distanceTo(location))/2/1000;
    DystansMap.setText(String.format("%.2f",String.valueOf(currentDustance)));
}
    oldLocation = location;



}


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

Sensor sensor = event.sensor;
        if(sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            //ToDo:Ogarnąc !
           step.setText(String.valueOf(event.values[0]));

            krok = event.values[0];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")



    public class CounterClass extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            czas.setText(hms);
        }

        @Override
        public void onFinish() {
toggleButton.setChecked(true);
        }

    }

}
