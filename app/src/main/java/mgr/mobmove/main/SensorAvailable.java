package mgr.mobmove.main;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import mgr.mobmove.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorAvailable extends Fragment {




    public SensorAvailable() {
        // Required empty public constructor
    }
    private ImageView ACCELEROMETER;
    private ImageView L_ACCELEROMETER;
    private ImageView GYROSCOPE;
    private ImageView GYROSCOPE_U;
    private ImageView GRAVITY;
    private ImageView ROTATION_VECTOR;
    private ImageView ORIENTATION;
    private ImageView STEP_COUNTER;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_sensoravailable, container, false);

       ACCELEROMETER = (ImageView) v.findViewById(R.id.imageView2);
        L_ACCELEROMETER = (ImageView) v.findViewById(R.id.imageView3);
        GYROSCOPE = (ImageView) v.findViewById(R.id.imageView4);
   GYROSCOPE_U = (ImageView) v.findViewById(R.id.imageView5);
    GRAVITY = (ImageView) v.findViewById(R.id.imageView6);
  ROTATION_VECTOR = (ImageView) v.findViewById(R.id.imageView7);
        ORIENTATION = (ImageView) v.findViewById(R.id.imageView8);
       STEP_COUNTER = (ImageView) v.findViewById(R.id.imageView9);

        printAllSensors();
        return v;
    }


    public void printAllSensors() {
        PackageManager manager = getActivity().getPackageManager();
        SensorManager sensorMngr = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorMngr.getSensorList(Sensor.TYPE_ALL);


        for (Sensor sensor : sensors) {
            // debug or print the follwoing
           switch ( sensor.getType())
           {
               case Sensor.TYPE_ACCELEROMETER:
                   ACCELEROMETER.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_LINEAR_ACCELERATION:
                   L_ACCELEROMETER.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_GYROSCOPE:
                   GYROSCOPE.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                   GYROSCOPE_U.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_GRAVITY:
                   GRAVITY.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_ROTATION_VECTOR:
                   ROTATION_VECTOR.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_ORIENTATION:
                   ORIENTATION.setImageResource(R.mipmap.ic_yes);
                   break;
               case Sensor.TYPE_STEP_COUNTER:
                       STEP_COUNTER.setImageResource(R.mipmap.ic_yes);
                   break;

           }
        }
    }

}
