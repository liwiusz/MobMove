package mgr.mobmove.trening;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import mgr.mobmove.R;
import android.os.Handler;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.List;


public class Sensory extends Fragment implements SensorEventListener
 {


    private TextView xText, yText, zText;

    private SensorManager SM;


     Sensor accelerometer;
     //Todo: nie wiem czy potrzebne sa wszystkie, do pliku nie ma magnetrometer
     Sensor magnetometer;
     Sensor gravity;
     Sensor gyroscope;
     Sensor gyroscopeU;
     Sensor linearAccelometer;
     Sensor rotation;

Sensor orientation;



    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private LineGraphSeries<DataPoint> mSeries3;
    private double graph2LastXValue = 5d;

   public static float xValue;
    public static float yValue;
    public static float zValue;

     public static float orientationX;
     public static float orientationY;
     public static float orientationZ;

     public static float gravityX;
     public static float gravityY;
     public static float gravityZ;

     public static float gyroscopeX;
     public static float gyroscopeY;
     public static float gyroscopeZ;

     public static float gyroscopeUXa;
     public static float gyroscopeUYa;
     public static float gyroscopeUZa;
     public static float gyroscopeUXb;
     public static float gyroscopeUYb;
     public static float gyroscopeUZb;

     public static float linearAccelometerX;
     public static float linearAccelometerY;
     public static float linearAccelometerZ;

     public static float rotationX;
     public static float rotationY;
     public static float rotationZ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SM = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        accelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravity = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscope = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeU = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        linearAccelometer = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rotation = SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        orientation = SM.getDefaultSensor(Sensor.TYPE_ORIENTATION);


        //TODO: lista do wyswietlenia sensorow
//        List<Sensor> sensorList = SM.getSensorList(Sensor.TYPE_ALL);
//        StringBuilder message = new StringBuilder();
//
//        for(Sensor sensor : sensorList)
//        {
//          Log.d("SENSORY LISTA:",sensor.getName() );
//        }
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_ruch_sensory, container, false);


        // Assign TextView
        xText = (TextView)v.findViewById(R.id.xText);
        yText = (TextView)v.findViewById(R.id.yText);
        zText = (TextView)v.findViewById(R.id.zText);
        zText.setTextColor(Color.RED);
        yText.setTextColor(Color.BLUE);
        xText.setTextColor(Color.GREEN);


        GraphView graph2 = (GraphView) v.findViewById(R.id.graphview);
        mSeries1 = new LineGraphSeries<DataPoint>();
        mSeries1.setColor(Color.GREEN);
        graph2.addSeries(mSeries1);


        mSeries2 = new LineGraphSeries<DataPoint>();
        graph2.addSeries(mSeries2);

       // graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(50);
        graph2.getViewport().setMinY(-15);
        graph2.getViewport().setMaxY(15);
     graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(true);

       // graph2.setScaleX(5);


        mSeries3 = new LineGraphSeries<DataPoint>();
        mSeries3.setColor(Color.RED);
        graph2.addSeries(mSeries3);



        return v;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER) {

            xText.setText("X: " + event.values[0]);
            yText.setText("Y: " + event.values[1]);
            zText.setText("Z: " + event.values[2]);
            xValue = event.values[0];
            yValue = event.values[1];
            zValue = event.values[2];

        }

        else if(sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            Log.d("Magnetic X: ",String.valueOf(event.values[0]));
            Log.d("Magnetic Y: ",String.valueOf(event.values[1]));
            Log.d("Magnetic Z: ",String.valueOf(event.values[2]));
            Log.d("SENSOR : ","TYPE_MAGNETIC_FIELD");
        }
        else if(sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            Log.d("TYPE_GYROSCOPE x:",String.valueOf(event.values[0]));
            Log.d("TYPE_GYROSCOPE y:",String.valueOf(event.values[1]));
            Log.d("TYPE_GYROSCOPE z:",String.valueOf(event.values[2]));
            Log.d("SENSOR : ","TYPE_GYROSCOPE");
            gyroscopeX = event.values[0];
            gyroscopeY = event.values[1];
            gyroscopeZ = event.values[2];
        }
       else if(sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION)
        {
            Log.d("LINEAR_ACCELERATION x:",String.valueOf(event.values[0]));
            Log.d("LINEAR_ACCELERATION:",String.valueOf(event.values[1]));
            Log.d("LINEAR_ACCELERATION:",String.valueOf(event.values[2]));
            Log.d("SENSOR : ","TYPE_LINEAR_ACCELERATION");
            linearAccelometerX = event.values[0];
            linearAccelometerY = event.values[1];
            linearAccelometerZ = event.values[2];
        }
      else  if(sensor.getType()==Sensor.TYPE_ROTATION_VECTOR)
        {
            Log.d("TYPE_ROTATION_VECTOR x:",String.valueOf(event.values[0]));
            Log.d("TYPE_ROTATION_VECTOR y:",String.valueOf(event.values[1]));
            Log.d("TYPE_ROTATION_VECTOR z:",String.valueOf(event.values[2]));
            Log.d("SENSOR : ","TYPE_ROTATION_VECTOR");
            rotationX = event.values[0];
            rotationY = event.values[1];
            rotationZ = event.values[2];
        }
       else if(sensor.getType()==Sensor.TYPE_GRAVITY)
        {
            Log.d("TYPE_GRAVITY x:",String.valueOf(event.values[0]));
            Log.d("TYPE_GRAVITY y:",String.valueOf(event.values[1]));
            Log.d("TYPE_GRAVITY z:",String.valueOf(event.values[2]));
            Log.d("SENSOR : ","TYPE_GRAVITY");
            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
        }
      else  if(sensor.getType()==Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
        {
            Log.d("GYROSCOPE_U xa:",String.valueOf(event.values[0]));
            Log.d("GYROSCOPE_U ya:",String.valueOf(event.values[1]));
            Log.d("GYROSCOPE_U za:",String.valueOf(event.values[2]));
            Log.d("GYROSCOPE_U xb:",String.valueOf(event.values[3]));
            Log.d("GYROSCOPE_U yb:",String.valueOf(event.values[4]));
            Log.d("GYROSCOPE_U zb:",String.valueOf(event.values[5]));
            Log.d("SENSOR : ","TYPE_GYROSCOPE_UNCALIBRATED");
            gyroscopeUXa = event.values[0];
            gyroscopeUYa = event.values[1];
            gyroscopeUZa = event.values[2];
            gyroscopeUXb = event.values[3];
            gyroscopeUYb = event.values[4];
            gyroscopeUZb = event.values[5];
        }

        else if(sensor.getType()==Sensor.TYPE_ORIENTATION)
        {
            Log.d("SENSROR:","OREINT OK");
            Log.d("TYPE_ORIENTATION",String.valueOf(event.values[0]));
            Log.d("TYPE_ORIENTATION",String.valueOf(event.values[1]));
            Log.d("TYPE_ORIENTATION",String.valueOf(event.values[2]));
            orientationX = event.values[0];
            orientationY = event.values[1];
            orientationZ = event.values[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();

        SM.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,gravity,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,gyroscopeU,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,linearAccelometer,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,rotation,SensorManager.SENSOR_DELAY_NORMAL);

        SM.registerListener(this,orientation,SensorManager.SENSOR_DELAY_NORMAL);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries1.appendData(new DataPoint(graph2LastXValue, xValue), true, 100);
                mSeries2.appendData(new DataPoint(graph2LastXValue, yValue), true, 100);
                mSeries3.appendData(new DataPoint(graph2LastXValue, zValue), true, 100);

                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);
    }

    @Override
    public void onPause() {

        mHandler.removeCallbacks(mTimer2);
        super.onPause();

        SM.unregisterListener(this);
    }

}
