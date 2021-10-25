package com.iit.pab.openweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyHolder extends RecyclerView.ViewHolder {

    protected TextView dateTimeView;
    protected TextView tempView;
    protected TextView description;
    protected TextView popView;
    protected TextView uv;
    protected TextView morningView;
    protected TextView dayView;
    protected TextView eveningView;
    protected TextView nightView;
    protected ImageView icon;

    public DailyHolder(@NonNull View itemView) {
        super(itemView);
        dateTimeView = itemView.findViewById(R.id.dailyDateTimeView);
        tempView = itemView.findViewById(R.id.dailyTempView);
        description = itemView.findViewById(R.id.dailyWeatherDescriptionView);
        popView = itemView.findViewById(R.id.dailyPopView);
        uv = itemView.findViewById(R.id.dailyUvIndexView);
        morningView = itemView.findViewById(R.id.dailyMorningView);
        dayView = itemView.findViewById(R.id.dailyDayView);
        eveningView = itemView.findViewById(R.id.dailyEveningView);
        nightView = itemView.findViewById(R.id.dailyNightView);
        icon = itemView.findViewById(R.id.dailyIconImage);
    }
}
