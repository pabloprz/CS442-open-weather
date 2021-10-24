package com.iit.pab.openweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.weather.HourlyDetails;

import java.time.LocalDateTime;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyHolder> {

    private final List<HourlyDetails> details;
    private final MainActivity mainActivity;

    public HourlyAdapter(List<HourlyDetails> details, MainActivity mainActivity) {
        this.details = details;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public HourlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_element,
                        parent, false);

        return new HourlyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyHolder holder, int position) {
        HourlyDetails item = details.get(position);

        if (item.getDateTime().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
            holder.day.setText(R.string.today);
        } else {
            holder.day.setText(getDayOfWeek(item.getDateTime().getDayOfWeek().getValue()));
        }

        holder.time.setText(mainActivity.formatTime(item.getDateTime()));
        holder.temp.setText(mainActivity.tempToText(item.getTemperature()));
        holder.description.setText(item.getDetails().getDescription());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }
}
