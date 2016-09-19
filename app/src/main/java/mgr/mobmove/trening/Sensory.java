package mgr.mobmove.trening;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import mgr.mobmove.R;
import android.os.Handler;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



public class Sensory extends Fragment implements SensorEventListener
 {
    private TextView xText, yText, zText;
    private TextView xText1, yText1, zText1;
    private SensorManager SM;
     Sensor accelerometer;
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

     private LineGraphSeries<DataPoint> mSeries11;
     private LineGraphSeries<DataPoint> mSeries22;
     private LineGraphSeries<DataPoint> mSeries33;

    private double graph2LastXValue = 5d;
     private double graph22LastXValue = 5d;

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
        gravity = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscope = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeU = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        linearAccelometer = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rotation = SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        orientation = SM.getDefaultSensor(Sensor.TYPE_ORIENTATION);
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
        mSeries3 = new LineGraphSeries<DataPoint>();
        mSeries3.setColor(Color.RED);
       // graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(50);
        graph2.getViewport().setMinY(-15);
        graph2.getViewport().setMaxY(15);
     graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(true);
       // graph2.setScaleX(5);
        graph2.addSeries(mSeries3);
        xText1 = (TextView)v.findViewById(R.id.xText1);
        yText1 = (TextView)v.findViewById(R.id.yText1);
        zText1 = (TextView)v.findViewById(R.id.zText1);
        zText1.setTextColor(Color.RED);
        yText1.setTextColor(Color.BLUE);
        xText1.setTextColor(Color.GREEN);
        GraphView graph3 = (GraphView) v.findViewById(R.id.graphview1);
        mSeries11 = new LineGraphSeries<DataPoint>();
        mSeries11.setColor(Color.GREEN);
        graph3.addSeries(mSeries11);
        mSeries22 = new LineGraphSeries<DataPoint>();
        graph3.addSeries(mSeries22);
        mSeries33 = new LineGraphSeries<DataPoint>();
        mSeries33.setColor(Color.RED);
        graph3.addSeries(mSeries33);
        // graph2.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(50);
        graph3.getViewport().setMinY(-15);
        graph3.getViewport().setMaxY(15);
        graph3.getViewport().setScalable(true);
        graph3.getViewport().setScrollable(true);







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
        else if(sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            gyroscopeX = event.values[0];
            gyroscopeY = event.values[1];
            gyroscopeZ = event.values[2];
        }
       else if(sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION)
        {
            linearAccelometerX = event.values[0];
            linearAccelometerY = event.values[1];
            linearAccelometerZ = event.values[2];
        }
      else  if(sensor.getType()==Sensor.TYPE_ROTATION_VECTOR)
        {
            rotationX = event.values[0];
            rotationY = event.values[1];
            rotationZ = event.values[2];
        }
       else if(sensor.getType()==Sensor.TYPE_GRAVITY)
        {
            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
        }
      else  if(sensor.getType()==Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
        {
            gyroscopeUXa = event.values[0];
            gyroscopeUYa = event.values[1];
            gyroscopeUZa = event.values[2];
            gyroscopeUXb = event.values[3];
            gyroscopeUYb = event.values[4];
            gyroscopeUZb = event.values[5];
        }
        else if(sensor.getType()==Sensor.TYPE_ORIENTATION)
        {
            orientationX = event.values[0];
            orientationY = event.values[1];
            orientationZ = event.values[2];
            xText1.setText("" + event.values[0]+" ");
            yText1.setText("" + event.values[1]+ " ");
            zText1.setText("" + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        SM.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
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
                graph22LastXValue += 1d;
                mSeries11.appendData(new DataPoint(graph22LastXValue, orientationX), true, 100);
                mSeries22.appendData(new DataPoint(graph22LastXValue, orientationY), true, 100);
                mSeries33.appendData(new DataPoint(graph22LastXValue, orientationZ), true, 100);
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
