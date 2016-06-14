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
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

import mgr.mobmove.R;


public class Informacje extends Fragment implements LocationListener{


    TextView czas;
    long time =0;
TextView speed;

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
speed = (TextView)v.findViewById(R.id.speedText);
      //  chronometer.setFormat("HH:MM:SS");
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked())
                {
                    chronometer.setBase(SystemClock.elapsedRealtime()+time);
                    chronometer.start();
                }
                else
                {
                    time = chronometer.getBase()+SystemClock.elapsedRealtime();
                    chronometer.stop();
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
        return v;
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
