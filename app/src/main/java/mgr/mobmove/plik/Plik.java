package mgr.mobmove.plik;


import android.os.Environment;
import android.util.Log;


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public  class  Plik {


    public static   void save(String filename, ArrayList<Dane> d,String rodzajAktywnosci,String dystans,String cel,String uwagi)
    {
String s;
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename+".txt");
        //    file = new File("/sdcard/"+ filename+".txt");

            outputStream = new FileOutputStream(file);
            outputStream.write("Rodzaj aktywnosci \t".getBytes());
            outputStream.write(rodzajAktywnosci.getBytes());
            outputStream.write("\t".getBytes());
            outputStream.write("Dystans \t".getBytes());
            outputStream.write(dystans.getBytes());
            outputStream.write("\t".getBytes());
            outputStream.write("Cel \t".getBytes());
            outputStream.write(cel.getBytes());
            outputStream.write("\t".getBytes());
            outputStream.write("Uwagi \t".getBytes());
            outputStream.write(uwagi.getBytes());

            outputStream.write("\n".getBytes());
            outputStream.write("czas".getBytes());
            outputStream.write("\t\t".getBytes());
            outputStream.write("accelerometer".getBytes());

            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("Gravity".getBytes());
            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("Gyroscope".getBytes());
            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("GyroscopeU".getBytes());
            outputStream.write("\t\t\t\t\t\t\t".getBytes());

            outputStream.write("LinearAccelometer".getBytes());
            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("Magnetic".getBytes());
            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("Macierz obrotu dla accelerometer".getBytes());
            outputStream.write("\t\t\t\t".getBytes());
            outputStream.write("Macierz obrotu dla LinearAccelometer".getBytes());
            outputStream.write("\t\t\t\t".getBytes());

            outputStream.write("Orientation".getBytes());
            outputStream.write("\t\t\t\t".getBytes());
            outputStream.write("Rotation".getBytes());
            outputStream.write("\t\t\t\t".getBytes());
            outputStream.write("Mapa".getBytes());
            outputStream.write("\t\t\t\t".getBytes());
            outputStream.write("Krok".getBytes());
            outputStream.write("\t".getBytes());
            outputStream.write("Dystans".getBytes());
            outputStream.write("\n".getBytes());

            for (int i=0;i<d.size();i++)
            {
                outputStream.write(d.get(i).getCzas().getBytes());
                outputStream.write("\t".getBytes());
                outputStream.write(d.get(i).getXa().getBytes());
                outputStream.write(d.get(i).getYa().getBytes());
                outputStream.write(d.get(i).getZa().getBytes());
                outputStream.write("\t".getBytes());

               outputStream.write(d.get(i).getGravityX().getBytes());
                outputStream.write(d.get(i).getGravityY().getBytes());
                outputStream.write(d.get(i).getGravityZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getGyroscopeX().getBytes());
                outputStream.write(d.get(i).getGyroscopeY().getBytes());
                outputStream.write(d.get(i).getGyroscopeZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getGyroscopeUXa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUYa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUZa().getBytes());
                outputStream.write(d.get(i).getGyroscopeUXb().getBytes());
                outputStream.write(d.get(i).getGyroscopeUYb().getBytes());
                outputStream.write(d.get(i).getGyroscopeUZb().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getLinearAccelometerX().getBytes());
                outputStream.write(d.get(i).getLinearAccelometerY().getBytes());
                outputStream.write(d.get(i).getLinearAccelometerZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getMagneticX().getBytes());
                outputStream.write(d.get(i).getMagneticY().getBytes());
                outputStream.write(d.get(i).getMagneticZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getMacierzObrotu().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getOrientationX().getBytes());
                outputStream.write(d.get(i).getOrientationY().getBytes());
                outputStream.write(d.get(i).getOrientationZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getRotationX().getBytes());
                outputStream.write(d.get(i).getRotationY().getBytes());
                outputStream.write(d.get(i).getRotationZ().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getSpeed().getBytes());
                outputStream.write(d.get(i).getxMap().getBytes());
                outputStream.write(d.get(i).getyMap().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getKrok().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write(d.get(i).getDystans().getBytes());
                outputStream.write("\t".getBytes());

                outputStream.write("\n".getBytes());
            }







            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
