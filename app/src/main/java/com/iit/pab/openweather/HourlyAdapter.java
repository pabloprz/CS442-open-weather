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
        holder.description.setText(String.format("%s%s",
                Character.toUpperCase(item.getDetails().getDescription().charAt(0)),
                item.getDetails().getDescription().substring(1)));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Monday";
                break;
            case 2:
                day = "Tuesday";
                break;
            case 3:
                day = "Wednesday";
                break;
            case 4:
                day = "Thursday";
                break;
            case 5:
                day = "Friday";
                break;
            case 6:
                day = "Saturday";
                break;
            case 7:
                day = "Sunday";
                break;
        }
        return day;
    }
}
