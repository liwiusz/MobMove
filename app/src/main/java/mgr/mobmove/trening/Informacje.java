package mgr.mobmove.trening;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import mgr.mobmove.R;
import mgr.mobmove.plik.Dane;
import mgr.mobmove.plik.Plik;



public class Informacje extends Fragment implements LocationListener{


    TextView czas;
    public  long time =0;
TextView speed;
Button saveTrening;

    ArrayList<Dane> dane = new ArrayList<>();

public static  boolean trwaTrening ;

    public Informacje() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View v = inflater.inflate(R.layout.fragment_informacje, container, false);
        ImageButton stopButton = (ImageButton) v.findViewById(R.id.stopButton);
        final ToggleButton toggleButton =(ToggleButton)v.findViewById(R.id.toggleButton);
        final Chronometer chronometer = (Chronometer)v.findViewById(R.id.chronometer);
        final TextView step = (TextView)v.findViewById(R.id.iloscKrokow);
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
        czas.setText("00:03:00");
        final CounterClass timer = new CounterClass(180000,1000);

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
                long elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;

                Dane d = new Dane();
                d.setCzas(String.valueOf(elapsedMillis));
               //
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

                d.setSpeed(speed.getText().toString());



                dane.add(d);




            }
        });

        return v;
    }




    public  void zapisywanie()
    {

Plik.save("ok",dane);
    }



    @Override
    public void onLocationChanged(Location location) {
if(location==null)
{
    speed.setText("0,0 m/s");
} else {
    float nCurrentSpeed = location.getSpeed();
    speed.setText(nCurrentSpeed +"m/s");
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

        }

    }

}
