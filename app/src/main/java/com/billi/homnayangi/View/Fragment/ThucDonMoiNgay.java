package com.billi.homnayangi.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.homnayangi.Adapter.AdapterCongThuc;
import com.billi.homnayangi.Interface.ListenerSang;

import com.billi.homnayangi.Models.DataCongThuc;
import com.billi.homnayangi.R;
import com.billi.homnayangi.Utils.SharedPrefs;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThucDonMoiNgay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThucDonMoiNgay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String rootDomain = "http://192.168.1.61/crawldata";
    private String urlGetData = "http://192.168.1.61/crawldata/appLoadData.php";

    private List<DataCongThuc> lstCongThucSang = new ArrayList<>();
    private List<DataCongThuc> lstCongThucTrua = new ArrayList<>();
    private List<DataCongThuc> lstCongThucToi = new ArrayList<>();

    private AdapterCongThuc adapterCongThucSang;
    private AdapterCongThuc adapterCongThucTrua;
    private AdapterCongThuc adapterCongThucToi;

    private Boolean checkGetSang = false;
    private Boolean checkGetTrua = false;
    private Boolean checkGetToi = false;
    private Boolean checkLoad = false;

    private String TEXT_SANG = "SANG";
    private String TEXT_TRUA = "TRUA";
    private String TEXT_TOI = "TOI";

    ProgressBar progressBar;
    ProgressBar progressBarSang;
    ProgressBar progressBarTrua;
    ProgressBar progressBarToi;

    LinearLayout lineSang;
    LinearLayout lineTrua;
    LinearLayout lineToi;
    LinearLayout lineLoad;
    LinearLayout lineCongThuc;

    private TextView goiySang,goiyTrua,goiyToi;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThucDonMoiNgay() {

    }

    public static ThucDonMoiNgay newInstance() {
        ThucDonMoiNgay fragment = new ThucDonMoiNgay();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_thuc_don_moi_ngay, container, false);

        final int currentTime = Calendar.getInstance().getTime().getDate();
        final int lateTime = SharedPrefs.getInstance().get("lateTime", Integer.class);
        progressBar = (ProgressBar)view.findViewById(R.id.waitload);
        progressBarSang = (ProgressBar)view.findViewById(R.id.waitloadSang);
        progressBarTrua = (ProgressBar)view.findViewById(R.id.waitloadTrua);
        progressBarToi = (ProgressBar)view.findViewById(R.id.waitloadToi);
        lineSang = view.findViewById(R.id.lineSang);
        lineTrua = view.findViewById(R.id.lineTrua);
        lineToi = view.findViewById(R.id.lineToi);
        lineLoad = view.findViewById(R.id.lineLoad);
        lineCongThuc = view.findViewById(R.id.lineThucDonMoiNgay);

        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);

        final Circle doubleBounce = new Circle();
        final Circle doubleBounce1 = new Circle();
        final Circle doubleBounce2 = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        if (!checkLoad){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkLoad = true;
                    lineLoad.setVisibility(View.GONE);
                    lineCongThuc.setVisibility(View.VISIBLE);
                }
            }, 5000);
        } else {
            lineLoad.setVisibility(View.GONE);
            lineCongThuc.setVisibility(View.VISIBLE);
        }
        goiySang = view.findViewById(R.id.goiySang);
        goiyTrua = view.findViewById(R.id.goiyTrua);
        goiyToi = view.findViewById(R.id.goiyToi);

        goiySang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarSang.setIndeterminateDrawable(doubleBounce);
                progressBarSang.setVisibility(View.VISIBLE);
                lineSang.setVisibility(View.GONE);
                goiySang.startAnimation(click);
                getGoiYCongThucNew(view.getContext(),TEXT_SANG,currentTime,lineSang);
            }
        });

        goiyTrua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarTrua.setIndeterminateDrawable(doubleBounce1);
                progressBarTrua.setVisibility(View.VISIBLE);
                lineTrua.setVisibility(View.GONE);
                goiyTrua.startAnimation(click);
                getGoiYCongThucNew(view.getContext(),TEXT_TRUA,currentTime,lineTrua);
            }
        });

        goiyToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarToi.setIndeterminateDrawable(doubleBounce2);
                progressBarToi.setVisibility(View.VISIBLE);
                lineToi.setVisibility(View.GONE);
                goiyToi.startAnimation(click);
                getGoiYCongThucNew(view.getContext(),TEXT_TOI,currentTime,lineToi);
            }
        });

        if (lateTime != currentTime){
            Log.e("late",lateTime + "");
            //Get cong thuc buoi Sang
            getCongThucNew(view.getContext(),currentTime,lineSang,TEXT_SANG);
            getCongThucNew(view.getContext(),currentTime,lineTrua,TEXT_TRUA);
            getCongThucNew(view.getContext(),currentTime,lineToi,TEXT_TOI);
        } else {
            getCongThucLate(view.getContext(),lineSang,TEXT_SANG);
            getCongThucLate(view.getContext(),lineTrua,TEXT_TRUA);
            getCongThucLate(view.getContext(),lineToi,TEXT_TOI);
        }

        //
        return view;
    }

    private void getDataCongThuc(final Context context, final String BuaAn, final ListenerSang listenerSang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            if (BuaAn.equals(TEXT_SANG)){
                                JSONObject object = new JSONObject(response);
                                lstCongThucSang.clear();
                                lstCongThucSang.add(
                                        new DataCongThuc(
                                                object.getInt("congthucid"),
                                                object.getString("tencongthuc"),
                                                object.getInt("thoigianlam"),
                                                object.getInt("luotnguoithich"),
                                                object.getInt("luotnguoixem"),
                                                rootDomain +  object.getString("hinhanh")));
                                putShared(BuaAn,0 ,object.getInt("congthucid"),
                                        object.getString("tencongthuc"),
                                        object.getInt("thoigianlam"),
                                        object.getInt("luotnguoithich"),
                                        object.getInt("luotnguoixem"),
                                        rootDomain + object.getString("hinhanh"));
                            }else if (BuaAn.equals(TEXT_TRUA)){
                                JSONArray array = new JSONArray(response);
                                SharedPrefs.getInstance().put(TEXT_TRUA,array.length());
                                lstCongThucTrua.clear();
                                for (int i = 0; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    lstCongThucTrua.add(
                                            new DataCongThuc(
                                                    object.getInt("congthucid"),
                                                    object.getString("tencongthuc"),
                                                    object.getInt("thoigianlam"),
                                                    object.getInt("luotnguoithich"),
                                                    object.getInt("luotnguoixem"),
                                                    rootDomain +  object.getString("hinhanh")));
                                    putShared(BuaAn,i ,object.getInt("congthucid"),
                                            object.getString("tencongthuc"),
                                            object.getInt("thoigianlam"),
                                            object.getInt("luotnguoithich"),
                                            object.getInt("luotnguoixem"),
                                            rootDomain + object.getString("hinhanh"));
                                }

                            }else if (BuaAn.equals(TEXT_TOI)){
                                JSONArray array = new JSONArray(response);
                                SharedPrefs.getInstance().put(TEXT_TOI,array.length());
                                lstCongThucToi.clear();
                                for (int i = 0; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    lstCongThucToi.add(
                                            new DataCongThuc(
                                                    object.getInt("congthucid"),
                                                    object.getString("tencongthuc"),
                                                    object.getInt("thoigianlam"),
                                                    object.getInt("luotnguoithich"),
                                                    object.getInt("luotnguoixem"),
                                                   rootDomain + object.getString("hinhanh")));
                                    putShared(BuaAn,i ,object.getInt("congthucid"),
                                            object.getString("tencongthuc"),
                                            object.getInt("thoigianlam"),
                                            object.getInt("luotnguoithich"),
                                            object.getInt("luotnguoixem"),
                                            rootDomain + object.getString("hinhanh"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                            listenerSang.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listenerSang.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("BuaAn",BuaAn);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        onSaveInstanceState(bundle);
    }

    public void getCongThucNew(final Context context, final int currentTime, final LinearLayout lineCongThuc, final String BuaAn){
        if (BuaAn.equals(TEXT_SANG)){
            if (!checkGetSang){
                getDataCongThuc(context, BuaAn, new ListenerSang() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse() {
                        SharedPrefs.getInstance().put("lateTime", currentTime);
                        SharedPrefs.getInstance().put("lstCongThucSang",lstCongThucSang);
                        checkGetSang = true;
                        adapterCongThucSang = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucSang);
                        adapterCongThucSang.notifyDataSetChanged();

                        for (int i = 0; i < adapterCongThucSang.getCount(); i++){
                            lineCongThuc.addView(adapterCongThucSang.getView(i,null,null));
                        }

                    }
                });
            } else {
                adapterCongThucSang = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucSang);
                adapterCongThucSang.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucSang.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucSang.getView(i,null,null));
                }
            }
        }else if (BuaAn.equals(TEXT_TRUA)){
            if (!checkGetTrua){
                getDataCongThuc(context, BuaAn, new ListenerSang() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse() {
                        SharedPrefs.getInstance().put("lateTime", currentTime);
                        checkGetTrua = true;
                        adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucTrua);
                        adapterCongThucTrua.notifyDataSetChanged();
                        for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                            lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                        }
                    }
                });
            } else {
                adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucTrua);
                adapterCongThucTrua.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                }
            }
        }else if (BuaAn.equals(TEXT_TOI)){
            if (!checkGetToi){
                getDataCongThuc(context, BuaAn, new ListenerSang() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse() {
                        SharedPrefs.getInstance().put("lateTime", currentTime);
                        SharedPrefs.getInstance().put("lstCongThuc"+BuaAn,lstCongThucToi);
                        checkGetToi = true;
                        adapterCongThucToi = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucToi);
                        adapterCongThucToi.notifyDataSetChanged();
                        for (int i = 0; i < adapterCongThucToi.getCount(); i++){
                            lineCongThuc.addView(adapterCongThucToi.getView(i,null,null));
                        }
                    }
                });
            } else {
                adapterCongThucToi = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucToi);
                adapterCongThucToi.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucToi.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucToi.getView(i,null,null));
                }
            }
        }
    }

    public void getCongThucLate(Context context,LinearLayout lineCongThuc,String BuaAn){
        if (BuaAn.equals(TEXT_SANG)){
            if (!checkGetSang){
                checkGetSang = true;
                lstCongThucSang.add(new DataCongThuc(
                        SharedPrefs.getInstance().get("congthucid"+BuaAn+0,Integer.class),
                        SharedPrefs.getInstance().get("tencongthuc"+BuaAn+0,String.class),
                        SharedPrefs.getInstance().get("thoigianlam"+BuaAn+0,Integer.class),
                        SharedPrefs.getInstance().get("luotnguoithich"+BuaAn+0,Integer.class),
                        SharedPrefs.getInstance().get("luotnguoixem"+BuaAn+0,Integer.class),
                        SharedPrefs.getInstance().get("hinhanh"+BuaAn+0,String.class)
                ));
                adapterCongThucSang = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucSang);
                adapterCongThucSang.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucSang.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucSang.getView(i,null,null));
                }
            }
            else {
                adapterCongThucSang = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucSang);
                adapterCongThucSang.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucSang.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucSang.getView(i,null,null));
                }
            }
        }else if (BuaAn.equals(TEXT_TRUA)){
            if (!checkGetTrua){
                checkGetTrua = true;
                for (int i = 0; i < SharedPrefs.getInstance().get(BuaAn,Integer.class);i++){
                    lstCongThucTrua.add(new DataCongThuc(
                            SharedPrefs.getInstance().get("congthucid"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("tencongthuc"+BuaAn+i,String.class),
                            SharedPrefs.getInstance().get("thoigianlam"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoithich"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoixem"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("hinhanh"+BuaAn+i,String.class)
                    ));
                }
                adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucTrua);
                adapterCongThucTrua.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                }
            }
            else {
                adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucTrua);
                adapterCongThucTrua.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                }
            }
        } else if (BuaAn.equals(TEXT_TOI)){
            if (!checkGetToi){
                checkGetToi = true;
                for (int i = 0; i < SharedPrefs.getInstance().get(BuaAn,Integer.class);i++){
                    lstCongThucToi.add(new DataCongThuc(
                            SharedPrefs.getInstance().get("congthucid"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("tencongthuc"+BuaAn+i,String.class),
                            SharedPrefs.getInstance().get("thoigianlam"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoithich"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoixem"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("hinhanh"+BuaAn+i,String.class)
                    ));
                }
                adapterCongThucToi = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucToi);
                adapterCongThucToi.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucToi.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucToi.getView(i,null,null));
                }
            }
            else {
                adapterCongThucToi = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucToi);
                adapterCongThucToi.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucToi.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucToi.getView(i,null,null));
                }
            }
        }
    }
    public void getGoiYCongThucNew(final Context context, final String BuaAn, final int currentTime, final LinearLayout lineCongThuc){
        if (BuaAn.equals(TEXT_SANG)){
            getDataCongThuc(context, BuaAn, new ListenerSang() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse() {
                    SharedPrefs.getInstance().put("lateTime", currentTime);
                    checkGetSang = true;
                    adapterCongThucSang = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucSang);
                    adapterCongThucSang.notifyDataSetChanged();

                    for (int i = 0; i < adapterCongThucSang.getCount(); i++){
                        lineCongThuc.removeAllViews();
                        lineCongThuc.addView(adapterCongThucSang.getView(i,null,null));
                    }
                    progressBarSang.setVisibility(View.GONE);
                    lineSang.setVisibility(View.VISIBLE);
                }
            });
        }else if (BuaAn.equals(TEXT_TRUA)){

            getDataCongThuc(context, BuaAn, new ListenerSang() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse() {
                    SharedPrefs.getInstance().put("lateTime", currentTime);
                    checkGetTrua = true;
                    adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucTrua);
                    adapterCongThucTrua.notifyDataSetChanged();
                    lineCongThuc.removeAllViews();
                    for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                        lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                    }
                    progressBarTrua.setVisibility(View.GONE);
                    lineTrua.setVisibility(View.VISIBLE);
                }
            });

        }else if (BuaAn.equals(TEXT_TOI)){
            getDataCongThuc(context, BuaAn, new ListenerSang() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse() {
                    SharedPrefs.getInstance().put("lateTime", currentTime);
                    checkGetToi = true;
                    adapterCongThucToi = new AdapterCongThuc(context,R.layout.row_cong_thuc,lstCongThucToi);
                    adapterCongThucToi.notifyDataSetChanged();
                    lineCongThuc.removeAllViews();
                    for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                        lineCongThuc.addView(adapterCongThucToi.getView(i,null,null));
                    }
                    progressBarToi.setVisibility(View.GONE);
                    lineToi.setVisibility(View.VISIBLE);
                }
            });
        }

    }
    public void putShared(String BuaAn,int stt, int congThucID, String tenCongThuc, int thoiGianLam, int luongNguoiThich,int luotNguoiXem, String hinhAnh){
        SharedPrefs.getInstance().put("congthucid"+BuaAn+stt,congThucID);
        SharedPrefs.getInstance().put("tencongthuc"+BuaAn+stt,tenCongThuc);
        SharedPrefs.getInstance().put("thoigianlam"+BuaAn+stt,thoiGianLam);
        SharedPrefs.getInstance().put("luotnguoithich"+BuaAn+stt, luongNguoiThich);
        SharedPrefs.getInstance().put("luotnguoixem"+BuaAn+stt,luotNguoiXem);
        SharedPrefs.getInstance().put("hinhanh"+BuaAn+stt,hinhAnh);
    }
}
