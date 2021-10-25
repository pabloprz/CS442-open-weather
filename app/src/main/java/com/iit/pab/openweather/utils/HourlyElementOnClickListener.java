package com.iit.pab.openweather.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.weather.HourlyDetails;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class HourlyElementOnClickListener implements View.OnClickListener {

    private final Activity activity;
    private final RecyclerView recyclerView;
    private final List<HourlyDetails> details;

    public HourlyElementOnClickListener(Activity activity, RecyclerView recyclerView,
                                        List<HourlyDetails> details) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.details = details;
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        ZonedDateTime zonedDateTime = details.get(position).getDateTime().atZone(ZoneId.of(
                "America/Chicago"));

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, zonedDateTime.toInstant().toEpochMilli());

        activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(builder.build()));
    }
}
