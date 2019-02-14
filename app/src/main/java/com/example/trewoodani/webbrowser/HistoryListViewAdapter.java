package com.example.trewoodani.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class HistoryListViewAdapter extends BaseAdapter {

    //list of bookmarks to be displayed
    Context context;
    private List<History> historyList;

    public HistoryListViewAdapter(Context context, List<History> historyList){
        this.context = context;
        this.historyList = historyList;
    }

    //abstract methods used by parent - dunno if need these
    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //store to use with tagging
    public class ViewHolder {
        TextView date;
        TextView url;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView = inflater.inflate(R.layout.history_data, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.currentDate);
            holder.url = (TextView) convertView.findViewById(R.id.currentUrl);

            convertView.setTag(holder);
        }
        else {
            //get holder to prepare for update with new data
            holder = (ViewHolder)convertView.getTag();
        }

        //getting title and image from items class
        String currentDate = historyList.get(position).getCurrentDate();
        String currentUrl = historyList.get(position).getCurrentUrl();
        holder.date.setText(currentDate);
        holder.url.setText(currentUrl);

        return convertView;
    }
}
