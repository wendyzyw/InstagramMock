package com.wendy.instagramviewer.Bluetooth_Util;

import java.util.UUID;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;

import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import java.io.DataOutputStream;
import java.io.FileInputStream;

public class Blue_Server extends Thread
{
    private BluetoothServerSocket server_socket;
    private BluetoothAdapter blue_adapter;
    private final String MY_UUID, TAG;
    private String file_path, file_name;

    public Blue_Server(String tag, String uuid, String file_path, String file_name)
    {
        MY_UUID = uuid;
        TAG = tag;
        this.file_name = file_name;
        this.file_path = file_path;
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
            System.out.println("Exception 3 Found: " + e);
            return API_Tags.RTN_FAIL;
        }
    }

    public void run()
    {
        try
        {
            server_socket = blue_adapter.listenUsingInsecureRfcommWithServiceRecord(TAG, UUID.fromString(MY_UUID));
        }
        catch (Exception e)
        {
            System.out.println("Exception Found:" + e);
        }
        while (true)
        {
            try
            {
                BluetoothSocket socket = server_socket.accept();
                BluetoothDevice dev = socket.getRemoteDevice();
                DataOutputStream ds = new DataOutputStream(socket.getOutputStream());

                FileInputStream fStream = new FileInputStream(file_path + "\\" + file_name);
                int bufferSize = 8192;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1)
                    ds.write(buffer, 0, length);
                fStream.close();
                ds.flush();
                ds.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception 2 Found: " + e);
            }
        }
    }

}
