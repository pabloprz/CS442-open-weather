package com.iit.pab.openweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyHolder extends RecyclerView.ViewHolder {

    protected TextView day;
    protected TextView time;
    protected TextView temp;
    protected TextView description;
    protected ImageView icon;

    public HourlyHolder(@NonNull View itemView) {
        super(itemView);
        day = itemView.findViewById(R.id.hourlyDay);
        time = itemView.findViewById(R.id.hourlyTime);
        temp = itemView.findViewById(R.id.hourlyTemp);
        description = itemView.findViewById(R.id.hourlyDescription);
        icon = itemView.findViewById(R.id.hourlyIconImage);
    }

}
