package com.wendy.instagramviewer;

import android.bluetooth.BluetoothDevice;

public class DeviceDataModel {

    private String id;
    private String mac_addr;
    private BluetoothDevice device;

    //userd for display discoverable devices in range
    public DeviceDataModel(String id, String mac_addr, BluetoothDevice device) {
        this.id = id;
        this.mac_addr = mac_addr;
        this.device = device;
    }

    public String getId() {
        return id;
    }

    public String getMac_addr() {
        return mac_addr;
    }

    public BluetoothDevice getDevice() {
        return device;
    }
}
