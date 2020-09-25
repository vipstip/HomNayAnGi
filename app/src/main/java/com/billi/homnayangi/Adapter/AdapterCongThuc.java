package com.billi.homnayangi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billi.homnayangi.View.Activity.ActivitySearch;
import com.billi.homnayangi.Models.DataCongThuc;
import com.billi.homnayangi.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class AdapterCongThuc extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DataCongThuc> lstCongThuc;

    public AdapterCongThuc(Context context, int layout, List<DataCongThuc> lstCongThuc) {
        this.context = context;
        this.layout = layout;
        this.lstCongThuc = lstCongThuc;


    }

    @Override
    public int getCount() {
        return lstCongThuc.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.imgCongThuc = view.findViewById(R.id.imgCongThuc);
            viewHolder.txtTenCongThuc = view.findViewById(R.id.txtTenCongThuc);
            viewHolder.txtView = view.findViewById(R.id.txtView);
            viewHolder.txtLike = view.findViewById(R.id.txtLike);
            viewHolder.txtTime = view.findViewById(R.id.txtTime);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context)
                .load(lstCongThuc.get(i).getHinhAnh())
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(viewHolder.imgCongThuc);
        viewHolder.txtTenCongThuc.setText(lstCongThuc.get(i).getTenCongThuc());
        viewHolder.txtView.setText(String.valueOf(lstCongThuc.get(i).getLuongNguoiXem()));
        viewHolder.txtLike.setText(String.valueOf(lstCongThuc.get(i).getLuongThich()));
        viewHolder.txtTime.setText(String.valueOf(lstCongThuc.get(i).getThoigianNau()));
        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    view.startAnimation(click);
                    Intent intent = new Intent(view.getContext(),ActivitySearch.class);
                    intent.putExtra("Search", lstCongThuc.get(i).getTenCongThuc());
                    context.startActivity(intent);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        return view;
    }

    private static class ViewHolder{
        TextView txtTenCongThuc;
        TextView txtView;
        TextView txtLike;
        TextView txtTime;
        ImageView imgCongThuc;
    }
}
