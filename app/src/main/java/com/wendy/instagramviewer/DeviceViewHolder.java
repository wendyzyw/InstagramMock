package com.wendy.instagramviewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView id;
    private TextView mac;
    private Button btn;

    public DeviceViewHolder(View itemView) {
        super(itemView);

        id = (TextView) itemView.findViewById(R.id.device_id);
        mac = (TextView) itemView.findViewById(R.id.device_mac);
        btn = (Button) itemView.findViewById(R.id.device_connect);
    }

    public TextView getId() {
        return id;
    }

    public TextView getMac() {
        return mac;
    }

    public Button getBtn() {
        return btn;
    }
}
