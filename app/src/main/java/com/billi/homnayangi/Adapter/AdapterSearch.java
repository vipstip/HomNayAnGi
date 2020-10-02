package com.billi.homnayangi.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billi.homnayangi.Interface.ListenerSearch;
import com.billi.homnayangi.Models.DataCongThuc;
import com.billi.homnayangi.R;
import com.billi.homnayangi.View.Activity.ActivitySearch;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder
{

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{

    TextView txtTenCongThuc;
    TextView txtView;
    TextView txtLike;
    TextView txtTime;
    ImageView imgCongThuc;

    public ItemViewHolder(View itemView) {
        super(itemView);
        txtTenCongThuc = (TextView)itemView.findViewById(R.id.txtTenCongThuc);
        txtView = (TextView)itemView.findViewById(R.id.txtView);
        txtLike = (TextView)itemView.findViewById(R.id.txtLike);
        txtTime = (TextView)itemView.findViewById(R.id.txtTime);
        imgCongThuc = (ImageView) itemView.findViewById(R.id.imgCongThuc);
    }
}

public class AdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ListenerSearch loadMore;
    boolean isLoading;
    Activity activity;
    List<DataCongThuc> dataCongThucs;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;

    public AdapterSearch(RecyclerView recyclerView,Activity activity, List<DataCongThuc> dataCongThucs) {
        this.activity = activity;
        this.dataCongThucs = dataCongThucs;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }



    @Override
    public int getItemViewType(int position) {
        return dataCongThucs.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ListenerSearch loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.row_recipes,parent,false);

            return new ItemViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  ItemViewHolder)
        {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Glide.with(activity)
                    .load(dataCongThucs.get(position).getHinhAnh())
                    .transform(new CenterCrop(), new RoundedCorners(10))
                    .into(viewHolder.imgCongThuc);
            String txtTimeAdd = dataCongThucs.get(position).getThoigianNau() + " phút";
            viewHolder.txtTenCongThuc.setText(dataCongThucs.get(position).getTenCongThuc());
            viewHolder.txtView.setText(String.valueOf(dataCongThucs.get(position).getLuongNguoiXem()));
            viewHolder.txtLike.setText(String.valueOf(dataCongThucs.get(position).getLuongThich()));
            viewHolder.txtTime.setText(txtTimeAdd);

        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return dataCongThucs.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}
