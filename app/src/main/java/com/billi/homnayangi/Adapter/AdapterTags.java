package com.billi.homnayangi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.billi.homnayangi.Interface.OnItemClick;
import com.billi.homnayangi.Models.ListTags;
import com.billi.homnayangi.Models.Tags;
import com.billi.homnayangi.R;

import java.util.List;

public class AdapterTags extends BaseAdapter {
    private Context context;
    private int layout;
    int filterId;
    String nameFilter;
    private ListTags listTags;
    private OnItemClick onItemClick;
    public AdapterTags(Context context, int layout,ListTags listTags, String nameFilter ,OnItemClick onItemClick) {
        this.context = context;
        this.layout = layout;
        this.listTags = listTags;
        this.nameFilter = nameFilter;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getCount() {
        return listTags.getSize(nameFilter);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final AdapterTags.ViewHolder viewHolder;
        if (view == null){
            viewHolder = new AdapterTags.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtNameTag = view.findViewById(R.id.txtNameTag);
            view.setTag(viewHolder);
        } else {
            viewHolder = (AdapterTags.ViewHolder) view.getTag();
        }
            viewHolder.check = false;
            viewHolder.txtNameTag.setText(listTags.getNameTag(nameFilter,i));
            viewHolder.txtNameTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!viewHolder.check){
                        viewHolder.check = true;
                        viewHolder.txtNameTag.setBackgroundColor(Color.parseColor("#FF304FFE"));
                        viewHolder.txtNameTag.setTextColor(Color.parseColor("#FFFFFF"));
                        onItemClick.onClick(listTags.getIdTag(nameFilter,i),listTags.getNameTag(nameFilter,i),viewHolder.check);
                    } else {
                        viewHolder.check = false;
                        viewHolder.txtNameTag.setBackgroundResource(R.drawable.custom_row_buaan);
                        viewHolder.txtNameTag.setTextColor(Color.parseColor("#000000"));
                        onItemClick.onClick(listTags.getIdTag(nameFilter,i),listTags.getNameTag(nameFilter,i),viewHolder.check);
                    }
                }
            });
        return view;
    }

    private static class ViewHolder{
        TextView txtNameTag;
        boolean check;
    }
}
