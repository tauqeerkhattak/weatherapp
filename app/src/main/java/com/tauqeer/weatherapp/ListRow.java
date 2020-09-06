package com.tauqeer.weatherapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListRow {

    public static final int LAYOUT = R.layout.list_view_item;
    private final Context context;
    private TextView date,desc;
    private ImageView icon;

    public ListRow(Context context, View convertView) {
        this.context = context;
        this.icon = (ImageView) convertView.findViewById(R.id.forecast_icon);
        this.date = (TextView) convertView.findViewById(R.id.forecast_date);
        this.desc = (TextView) convertView.findViewById(R.id.forecast_desc);
    }

    public void bind(ViewModel viewModel) {
        this.date.setText(viewModel.getDate());
        new DownloadImageFromWeb(icon).execute(viewModel.getImageUrl());
        this.desc.setText(viewModel.getDesc());
    }
}
