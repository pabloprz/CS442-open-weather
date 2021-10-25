package com.iit.pab.openweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.utils.FormattingUtils;
import com.iit.pab.openweather.utils.HourlyElementOnClickListener;
import com.iit.pab.openweather.utils.TempUnit;
import com.iit.pab.openweather.weather.HourlyDetails;

import java.time.LocalDateTime;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyHolder> {

    private final List<HourlyDetails> details;
    private final MainActivity mainActivity;
    private final HourlyElementOnClickListener listener;
    private final TempUnit chosenUnit;

    public HourlyAdapter(List<HourlyDetails> details, MainActivity mainActivity,
                         HourlyElementOnClickListener listener, TempUnit chosenUnit) {
        this.details = details;
        this.mainActivity = mainActivity;
        this.listener = listener;
        this.chosenUnit = chosenUnit;
    }

    @NonNull
    @Override
    public HourlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_element,
                        parent, false);
        itemView.setOnClickListener(listener);

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

        holder.time.setText(FormattingUtils.formatTime(item.getDateTime()));
        holder.temp.setText(FormattingUtils.tempToText(item.getTemperature(), chosenUnit));
        holder.description.setText(String.format("%s%s",
                Character.toUpperCase(item.getDetails().getDescription().charAt(0)),
                item.getDetails().getDescription().substring(1)));
        holder.icon.setImageResource(mainActivity.getResources().getIdentifier("_" + item.getDetails().getIcon(), "drawable", mainActivity.getPackageName()));
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
