package com.vc19005768.weatherv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DailyForecastsRecyclerViewAdapter extends RecyclerView.Adapter<DailyForecastsRecyclerViewAdapter.ViewHolder> {

    private final List<DailyForecasts> mValues;


    public DailyForecastsRecyclerViewAdapter(List<DailyForecasts> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vholder, int position) { // parameters of Viewholder class defined bottom of page
        DailyForecasts item = mValues.get(position);
        vholder.mItem = item;
        vholder.ltvDate.setText(item.getDate().substring(0, 10));
        vholder.ltvMinTemp.setText(item.getTemperature().getMinimum().getValue() +
                " " + item.getTemperature().getMinimum().getUnit());
        vholder.ltvMaxTemp.setText(item.getTemperature().getMaximum().getValue() +
                " " + item.getTemperature().getMaximum().getUnit());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

////////////////////////////////////////////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public DailyForecasts mItem;
        public final TextView ltvMinTemp;
        public final TextView ltvMaxTemp;
        public TextView ltvDate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ltvMinTemp = (TextView) view.findViewById(R.id.lblMinTemp);
            ltvMaxTemp = (TextView) view.findViewById(R.id.lblMaxTemp);
            ltvDate = (TextView) view.findViewById(R.id.lblDate);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + ltvDate.getText() + "'";
        }
    }
    /////////////////////////////////////
}