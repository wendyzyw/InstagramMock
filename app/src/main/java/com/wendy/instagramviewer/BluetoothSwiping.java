package com.wendy.instagramviewer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wendy.instagramviewer.Bluetooth_Util.Blue_Server;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothSwiping extends AppCompatActivity {

    private static final int REQUEST_DISCOVERABLE_CODE = 2;

    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<DeviceDataModel> mDevices;
    private DevicesAdapter mDevicesAdapter;
    private DevicesAdapter newDeviceAdapter;

    private RecyclerView mPairedDevicesList;
    private ImageView selectedImgView;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(context, "Starting device discovery ...", Toast.LENGTH_SHORT).show();
            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //when a new bt device is found
                BluetoothDevice new_device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println(new_device);
                if (new_device != null && new_device.getUuids() != null) {
                    mDevices.add(new DeviceDataModel(
                            new_device.getName(), new_device.getUuids()[0].toString(), new_device));
                }
                mDevicesAdapter.notifyDataSetChanged();
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context, "Discovery ended", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_swiping);
        File directory = this.getApplicationContext().getFilesDir();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        BlueToothParam.uuid = telephonyManager.getDeviceId();
        BlueToothParam.dir = directory.getPath();

        //start server listening
        Button server_btn = (Button) findViewById(R.id.bluetooth_server);
        server_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Blue_Server server = new Blue_Server("bluetooth", BlueToothParam.uuid,
                        directory.getPath(), "24.jpg");
                String respond = server.perform();
                if (respond.equals(API_Tags.RTN_SUCC)){
                    server.run();
                }
            }
        });

        mPairedDevicesList = (RecyclerView) findViewById(R.id.paired_devices_recyclerView);
        selectedImgView = (ImageView) findViewById(R.id.selected_image);
        selectedImgView.setImageResource(SelectedPhotoForBluethooth.selectedImg);
        this.mDevices = new ArrayList<DeviceDataModel>();

        // search for discoverable devices
        IntentFilter filter_start = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(broadcastReceiver, filter_start);
        IntentFilter filter_finish = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, filter_finish);
        IntentFilter filter_found = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter_found);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0){
            for (BluetoothDevice device: pairedDevices){
                //retrieve public identifier and MAC addr
                this.mDevices.add(new DeviceDataModel(
                        device.getName(), device.getUuids()[0].toString(), device));
            }
        } else {
            if (mBluetoothAdapter.startDiscovery()){
                
            }
        }

        initRecyclerView();
    }

    //the recyclerview is to hold list of device
    public void initRecyclerView(){
        mPairedDevicesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDevicesAdapter = new DevicesAdapter(this.mDevices, this);
        this.mPairedDevicesList.setAdapter(this.mDevicesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }
}
