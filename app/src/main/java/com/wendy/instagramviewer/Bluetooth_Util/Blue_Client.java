package com.wendy.instagramviewer.Bluetooth_Util;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;


public class Blue_Client extends Thread
{
    private BluetoothDevice dev;
    private String file_path, file_name;
    private final String MY_UUID;

    public Blue_Client(BluetoothDevice d, String uuid, String file_path, String file_name)
    {
        dev = d;
        this.file_name = file_name;
        this.file_path = file_path;
        MY_UUID = uuid;
    }

    public String perform()
    {
        this.start();
        try
        {
            this.join();
            return API_Tags.RTN_SUCC;
        }
        catch (Exception e)
        {
            return API_Tags.RTN_FAIL;
        }
    }

    public void run()
    {
        BluetoothSocket socket = null;
        try
        {
            socket = dev.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
            socket.connect();
            System.out.println("step3");

            InputStream is = socket.getInputStream();
            System.out.println("step4");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.out.println("step5");
            byte[] buffer = new byte[8192]; // read in size of 4kB
            int len;
            int total_len = 0;
            while ((len = is.read(buffer)) > 0)
            {
                System.out.println("step6");
                total_len += len;
                output.write(buffer, 0, len);
            }
            System.out.println("step7");

            FileOutputStream fos = new FileOutputStream(file_path + "\\" + file_name);
            output.writeTo(fos);
            output.flush();
            output.close();
            fos.close();
            is.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception Found : " + e);
        }
    }
}
