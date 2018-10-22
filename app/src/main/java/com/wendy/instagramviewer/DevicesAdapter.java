package com.wendy.instagramviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wendy.instagramviewer.Bluetooth_Util.Blue_Client;
import com.wendy.instagramviewer.Bluetooth_Util.Blue_Server;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private Context mContext;
    private ArrayList<DeviceDataModel> mDevices;
    private LayoutInflater mLayoutInflater;

    public DevicesAdapter(ArrayList<DeviceDataModel> devices, Context context) {
        this.mDevices = devices;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        View view = this.mLayoutInflater.inflate(R.layout.paired_device, parent, false);

        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DeviceDataModel device = this.mDevices.get(position);

        holder.getId().setText(device.getId());
        holder.getMac().setText(device.getMac_addr());
        // to start client to connect to listening server port
        holder.getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Blue_Client client = new Blue_Client(
                        device.getDevice(), BlueToothParam.uuid,
                        BlueToothParam.dir, "24.jpg"
                );
                if (client.perform().equals(API_Tags.RTN_SUCC)){
                    client.run();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mDevices!=null)?mDevices.size():0;
    }
}
