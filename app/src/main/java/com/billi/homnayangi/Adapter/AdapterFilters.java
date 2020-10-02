package com.billi.homnayangi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.homnayangi.Interface.ListenerSang;
import com.billi.homnayangi.Interface.OnItemClick;
import com.billi.homnayangi.Models.Filters;
import com.billi.homnayangi.Models.ListTags;
import com.billi.homnayangi.Models.Tags;
import com.billi.homnayangi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterFilters extends BaseAdapter implements OnItemClick {

    private String rootDomain = "http://192.168.1.61/crawldata";
    private String urlGetData = "http://192.168.1.61/crawldata/appLoadData.php";
    private Context context;
    List<Filters> lstFilters;
    AdapterTags adapterTags;
    ListTags listTags ;
    private int layout;
    private OnItemClick onItemClick;
    public AdapterFilters(Context context, int layout,ListTags listTags, List<Filters> lstFilters,OnItemClick onItemClick) {
        this.context = context;
        this.layout = layout;
        this.lstFilters = lstFilters;
        this.onItemClick = onItemClick;
        this.listTags = listTags;
    }

    @Override
    public int getCount() {
        return lstFilters.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
     final AdapterFilters.ViewHolder viewHolder;
        if (view == null){
            viewHolder = new AdapterFilters.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtNameFilters = view.findViewById(R.id.txtFilter);
            viewHolder.col1 = view.findViewById(R.id.lineCol1);
            viewHolder.col2 = view.findViewById(R.id.lineCol2);
            viewHolder.col3 = view.findViewById(R.id.lineCol3);
            viewHolder.col4 = view.findViewById(R.id.lineCol4);
            view.setTag(viewHolder);
        } else {
            viewHolder = (AdapterFilters.ViewHolder) view.getTag();
        }
        viewHolder.txtNameFilters.setText(lstFilters.get(i).getNameFilters());
        adapterTags = new AdapterTags(context,R.layout.row_tag,listTags,lstFilters.get(i).getNameFilters(),AdapterFilters.this);
        int dem = 0;
        for (int j = 1; j <= listTags.getSize(lstFilters.get(i).getNameFilters()); j++) {
            dem++;

            View addView = adapterTags.getView(j - 1, null, null);
            if (dem == 1) {
                viewHolder.col1.addView(addView);
            } else if (dem == 2) {
                viewHolder.col2.addView(addView);
            } else if (dem == 3) {
                viewHolder.col3.addView(addView);
            } else if (dem == 4) {
                viewHolder.col4.addView(addView);
                dem = 0;
            }
        }
        return view;
    }

    @Override
    public void onClick(Integer idTag,String nameTag,boolean check) {
        onItemClick.onClick(idTag,nameTag,check);
    }

    private static class ViewHolder{
        TextView txtNameFilters;
        LinearLayout col1, col2, col3 , col4;
    }


}
