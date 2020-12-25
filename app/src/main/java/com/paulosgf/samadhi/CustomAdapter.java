package com.paulosgf.samadhi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MessageModel> MessageModelArrayList;

    public CustomAdapter(Context context, ArrayList<MessageModel> MessageModelArrayList) {

        this.context = context;
        this.MessageModelArrayList = MessageModelArrayList;
    }

    @Override
    public int getCount() {
        return MessageModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return MessageModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvmsg = (TextView) convertView.findViewById(R.id.message);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvmsg.setText(MessageModelArrayList.get(position).getMessage());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvmsg;
    }

}
