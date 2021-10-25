package com.iit.pab.openweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.utils.FormattingUtils;
import com.iit.pab.openweather.utils.TempUnit;
import com.iit.pab.openweather.weather.DailyDetails;

import java.util.List;
import java.util.Locale;

public class DailyAdapter extends RecyclerView.Adapter<DailyHolder> {

    private final List<DailyDetails> details;
    private final TempUnit chosenUnit;
    private final DailyForecastActivity dailyActivity;

    public DailyAdapter(List<DailyDetails> details, TempUnit chosenUnit,
                        DailyForecastActivity dailyActivity) {
        this.details = details;
        this.chosenUnit = chosenUnit;
        this.dailyActivity = dailyActivity;
    }

    @NonNull
    @Override
    public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_element,
                        parent, false);
        return new DailyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyHolder holder, int position) {
        DailyDetails item = details.get(position);

        holder.dateTimeView.setText(FormattingUtils.formatTime(item.getDateTime()));
        holder.tempView.setText(String.format(Locale.getDefault(), "%s/%s",
                FormattingUtils.tempToText(item.getTemperature().getMaximum(), chosenUnit),
                FormattingUtils.tempToText(item.getTemperature().getMinimum(), chosenUnit)));
        holder.description.setText(item.getWeatherDetail().getDescription());
        holder.popView.setText(String.format(Locale.getDefault(), "(%.2f%% precip.)",
                item.getPop() * 100));
        holder.uv.setText(String.format(Locale.getDefault(), "UV Index: %.0f", item.getUvIndex()));
        holder.morningView.setText(FormattingUtils.tempToText(item.getTemperature().getMorning(),
                chosenUnit));
        holder.dayView.setText(FormattingUtils.tempToText(item.getTemperature().getDay(),
                chosenUnit));
        holder.eveningView.setText(FormattingUtils.tempToText(item.getTemperature().getEvening(),
                chosenUnit));
        holder.nightView.setText(FormattingUtils.tempToText(item.getTemperature().getNight(),
                chosenUnit));
        holder.icon.setImageResource(dailyActivity.getResources().getIdentifier("_" + item.getWeatherDetail().getIcon(), "drawable", dailyActivity.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
