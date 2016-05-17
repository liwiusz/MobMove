package mgr.mobmove.trening;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
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


import java.util.Random;


public class Sensory extends Fragment implements SensorEventListener {


    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;



    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private LineGraphSeries<DataPoint> mSeries3;
    private double graph2LastXValue = 5d;
    private static float xValue;
    private static float yValue;
    private static float zValue;

    public Sensory() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SM = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);



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
        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);
xValue = event.values[0];
        yValue = event.values[1];
        zValue = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onResume() {
        super.onResume();
//        mTimer1 = new Runnable() {
//            @Override
//            public void run() {
//                mSeries1.resetData(generateData());
//                mHandler.postDelayed(this, 300);
//            }
//        };
//        mHandler.postDelayed(mTimer1, 300);

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
       // mHandler.removeCallbacks(mTimer1);
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }

}
