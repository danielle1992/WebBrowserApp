package com.example.trewoodani.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    //list of bookmarks to be displayed
    Context context;
    private List<Bookmark> bookmarkList;



    public ListViewAdapter(Context context, List<Bookmark> bookmarkList){
        this.context = context;
        this.bookmarkList = bookmarkList;
    }

    //abstract methods used by parent - dunno if need these
    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //store to use with tagging
    public class ViewHolder {
        TextView bookmarkTitle;
        TextView bookmarkUrl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView = inflater.inflate(R.layout.bookmark_data, parent, false);
            holder = new ViewHolder();
            holder.bookmarkTitle = (TextView) convertView.findViewById(R.id.bookmarkTitle);
            holder.bookmarkUrl = (TextView) convertView.findViewById(R.id.bookmarkUrl);

            convertView.setTag(holder);
        }
        else {
            //get holder to prepare for update with new data
            holder = (ViewHolder)convertView.getTag();
        }

        //getting title and image from items class
        String bookMarkName = bookmarkList.get(position).getTitle();
        String bookMarkTitle = bookmarkList.get(position).getUrl();
        holder.bookmarkTitle.setText(bookMarkName);
        holder.bookmarkUrl.setText(bookMarkTitle);

        return convertView;
    }
}
