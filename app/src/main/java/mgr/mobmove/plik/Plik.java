package mgr.mobmove.plik;


import android.os.Environment;
import android.util.Log;


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public  class  Plik {


    public static   void save(String filename, ArrayList<Dane> d)
    {
String s;
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename+".txt");
        //    file = new File("/sdcard/"+ filename+".txt");
            outputStream = new FileOutputStream(file);


            for (int i=0;i<d.size();i++)
            {
                outputStream.write(d.get(i).getCzas().getBytes());
                outputStream.write(d.get(i).getXa().getBytes());
                outputStream.write(d.get(i).getYa().getBytes());
                outputStream.write(d.get(i).getZa().getBytes());

               outputStream.write(d.get(i).getGravityX().getBytes());
                outputStream.write(d.get(i).getGravityY().getBytes());
                outputStream.write(d.get(i).getGravityZ().getBytes());

                outputStream.write(d.get(i).getGyroscopeX().getBytes());
                outputStream.write(d.get(i).getGyroscopeY().getBytes());
                outputStream.write(d.get(i).getGyroscopeZ().getBytes());

                outputStream.write(d.get(i).getGyroscopeUXa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUYa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUZa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUXb().getBytes());
                outputStream.write(d.get(i).getGyroscopeUYb().getBytes());
                outputStream.write(d.get(i).getGyroscopeUZb().getBytes());

                outputStream.write(d.get(i).getLinearAccelometerX().getBytes());
                outputStream.write(d.get(i).getLinearAccelometerY().getBytes());
                outputStream.write(d.get(i).getLinearAccelometerZ().getBytes());

                outputStream.write(d.get(i).getOrientationX().getBytes());
                outputStream.write(d.get(i).getOrientationY().getBytes());
                outputStream.write(d.get(i).getOrientationZ().getBytes());

                outputStream.write(d.get(i).getRotationX().getBytes());
                outputStream.write(d.get(i).getRotationY().getBytes());
                outputStream.write(d.get(i).getRotationZ().getBytes());

                outputStream.write("\n".getBytes());
            }







            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
