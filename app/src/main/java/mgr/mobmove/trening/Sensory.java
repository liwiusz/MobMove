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
import mgr.mobmove.Settings;
import mgr.mobmove.plik.Dane;

import android.os.Handler;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class Sensory extends Fragment implements SensorEventListener {
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
    Sensor magnetic;

    public static ArrayList<Dane> dane = new ArrayList<>();

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

    public static float xValue = 0;
    public static float yValue = 0;
    public static float zValue = 0;

    public static float orientationX = 0;
    public static float orientationY = 0;
    public static float orientationZ = 0;

    public static float gravityX = 0;
    public static float gravityY = 0;
    public static float gravityZ = 0;

    public static float gyroscopeX = 0;
    public static float gyroscopeY = 0;
    public static float gyroscopeZ = 0;

    public static float gyroscopeUXa = 0;
    public static float gyroscopeUYa = 0;
    public static float gyroscopeUZa = 0;
    public static float gyroscopeUXb = 0;
    public static float gyroscopeUYb = 0;
    public static float gyroscopeUZb = 0;

    public static float linearAccelometerX = 0;
    public static float linearAccelometerY = 0;
    public static float linearAccelometerZ = 0;

    public static float rotationX = 0;
    public static float rotationY = 0;
    public static float rotationZ = 0;

    public static float magneticX = 0;
    public static float magneticY = 0;
    public static float magneticZ = 0;

    private static float[] mOstatniaGrawitacja = null;
    private static float[] mOstatniePoleMagnetyczne = null;
    private static boolean mZmienionaMacierzObrotu = true;
    private static boolean mJestMacierzObrotu = false;

    private static float[] mR = new float[16];
    private static float[] mI = new float[16];
    public  float mE = Float.MAX_VALUE;
    public  float mN = Float.MAX_VALUE;
    public  float mZ2 = Float.MAX_VALUE;
    public  float mE2 = Float.MAX_VALUE;
    public  float mN2 = Float.MAX_VALUE;
    public  float mZ22 = Float.MAX_VALUE;

    private boolean liczA=false;

    public static String macierzObrotu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SM = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        accelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravity = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscope = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeU = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        linearAccelometer = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rotation = SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        orientation = SM.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        magnetic = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

this.liczA= Settings.isLiczLinearAccleration();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ruch_sensory, container, false);
        // Assign TextView
        xText = (TextView) v.findViewById(R.id.xText);
        yText = (TextView) v.findViewById(R.id.yText);
        zText = (TextView) v.findViewById(R.id.zText);
        zText.setTextColor(Color.RED);
        yText.setTextColor(Color.BLUE);
        xText.setTextColor(Color.GREEN);
        GraphView graph = (GraphView) v.findViewById(R.id.graphview);
        mSeries1 = new LineGraphSeries<DataPoint>();
        mSeries1.setColor(Color.GREEN);
        graph.addSeries(mSeries1);
        mSeries2 = new LineGraphSeries<DataPoint>();
        graph.addSeries(mSeries2);
        mSeries3 = new LineGraphSeries<DataPoint>();
        mSeries3.setColor(Color.RED);
        // graph2.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(50);
        graph.getViewport().setMinY(-15);
        graph.getViewport().setMaxY(15);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        // graph2.setScaleX(5);
        graph.addSeries(mSeries3);
        xText1 = (TextView) v.findViewById(R.id.xText1);
        yText1 = (TextView) v.findViewById(R.id.yText1);
        zText1 = (TextView) v.findViewById(R.id.zText1);
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
        Log.d("Test:",String.valueOf(liczA));

        return v;
    }
    final float alpha =0.8f;
    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;


switch (sensor.getType())
{
    case Sensor.TYPE_ACCELEROMETER:
        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);
        xValue = event.values[0];
        yValue = event.values[1];
        zValue = event.values[2];
        if(liczA)
        {

            gravityX = alpha * gravityX + (1 - alpha) * xValue;
            gravityY = alpha * gravityY + (1 - alpha) * yValue;
            gravityZ = alpha * gravityZ + (1 - alpha) * zValue;

            ustawGrawitacja(new float[]{gravityX,gravityY,gravityZ});

            linearAccelometerX = xValue-gravityX;
            linearAccelometerY = yValue-gravityY;
            linearAccelometerZ = zValue-gravityZ;

        }
        break;

    case Sensor.TYPE_GYROSCOPE:
        gyroscopeX = event.values[0];
        gyroscopeY = event.values[1];
        gyroscopeZ = event.values[2];
    break;
    case Sensor.TYPE_LINEAR_ACCELERATION:
       if(!liczA) {

            linearAccelometerX = event.values[0];
            linearAccelometerY = event.values[1];
            linearAccelometerZ = event.values[2];
        }
        break;
    case Sensor.TYPE_ROTATION_VECTOR:
        rotationX = event.values[0];
        rotationY = event.values[1];
        rotationZ = event.values[2];
    break;
    case Sensor.TYPE_GRAVITY:
        if(!liczA) {

            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
            ustawGrawitacja(event.values);
        }
    break;
    case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
        gyroscopeUXa = event.values[0];
        gyroscopeUYa = event.values[1];
        gyroscopeUZa = event.values[2];
        gyroscopeUXb = event.values[3];
        gyroscopeUYb = event.values[4];
        gyroscopeUZb = event.values[5];
    break;
    case Sensor.TYPE_ORIENTATION:
        orientationX = event.values[0];
        orientationY = event.values[1];
        orientationZ = event.values[2];

    break;
    case Sensor.TYPE_MAGNETIC_FIELD:
        magneticX = event.values[0];
        magneticY = event.values[1];
        magneticZ = event.values[2];
        xText1.setText("" + event.values[0] + " ");
        yText1.setText("" + event.values[1] + " ");
        zText1.setText("" + event.values[2]);
        ustawNowePoleMagnetyczne(event.values);
    break;


}


        macierzObrotu = obrocDoWspolrzednychSwiata();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        SM.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, gyroscopeU, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, linearAccelometer, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, orientation, SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries1.appendData(new DataPoint(graph2LastXValue, xValue), true, 1000);
                mSeries2.appendData(new DataPoint(graph2LastXValue, yValue), true, 1000);
                mSeries3.appendData(new DataPoint(graph2LastXValue, zValue), true, 1000);
                graph22LastXValue += 1d;
                mSeries11.appendData(new DataPoint(graph22LastXValue, magneticX), true, 1000);
                mSeries22.appendData(new DataPoint(graph22LastXValue, magneticY), true, 1000);
                mSeries33.appendData(new DataPoint(graph22LastXValue, magneticZ), true, 1000);
                mHandler.postDelayed(this, 0);
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

    private String obrocDoWspolrzednychSwiata() {
        String dane="";
        String danev2="";

        if (mZmienionaMacierzObrotu && mOstatniaGrawitacja != null
                && mOstatniePoleMagnetyczne != null) {
            // obliczenie bieżącej macierzy obrotu
            mJestMacierzObrotu = SensorManager.getRotationMatrix(mR, mI,
                    mOstatniaGrawitacja, mOstatniePoleMagnetyczne);
            mZmienionaMacierzObrotu = false;
            dane= "0;0;0";
            danev2="0;0;0";

        }
        if (mJestMacierzObrotu) {

            mE = mR[0] * xValue + mR[1] * yValue + mR[2] * zValue;
            mN = mR[4] * xValue + mR[5] * yValue + mR[6] * zValue;
            mZ2 = mR[8] * xValue + mR[9] * yValue + mR[10] * zValue;
            dane = String.valueOf(mE) + ";" + String.valueOf(mN) + ";" + String.valueOf(mZ2);

            mE2 = mR[0] * linearAccelometerX + mR[1] * linearAccelometerY + mR[2] * linearAccelometerZ;
            mN2 = mR[4] * linearAccelometerX + mR[5] * linearAccelometerY + mR[6] * linearAccelometerZ;
            mZ22 = mR[8] * linearAccelometerX + mR[9] * linearAccelometerY + mR[10] * linearAccelometerZ;
            danev2 = String.valueOf(mE2) + ";" + String.valueOf(mN2) + ";" + String.valueOf(mZ22);

        }

        return dane+";"+danev2;

    }
    private static void ustawGrawitacja (float [] nowaGrawitacja)
    {
        mOstatniaGrawitacja = nowaGrawitacja;

        mZmienionaMacierzObrotu = true;
    }
    public static void ustawNowePoleMagnetyczne(float[] nowePoleMagnetyczne) {
        mOstatniePoleMagnetyczne = nowePoleMagnetyczne;
        // wymuszenie aktualizacji macierzy obrotu
        mZmienionaMacierzObrotu = true;
    }

}
