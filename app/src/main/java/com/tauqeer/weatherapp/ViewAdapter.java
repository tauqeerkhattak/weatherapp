package com.tauqeer.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends BaseAdapter {
    private final List<ViewModel> viewModels;

    private final Context context;
    private final LayoutInflater inflater;

    public ViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.viewModels = new ArrayList<ViewModel>();
    }

    public ViewAdapter(Context context,List<ViewModel> viewModels) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.viewModels = viewModels;
    }

    public List<ViewModel> viewModels() {
        return this.viewModels;
    }

    @Override
    public int getCount() {
        return this.viewModels.size();
    }

    @Override
    public ViewModel getItem(int position) {
        return this.viewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewModel viewModel = getItem(position);
        ListRow row;
        if (convertView == null) {
            convertView = this.inflater.inflate(ListRow.LAYOUT,parent,false);
            row = new ListRow(this.context, convertView);
            convertView.setTag(row);
        }

        row = (ListRow) convertView.getTag();
        row.bind(viewModel);
        return convertView;
    }
}
