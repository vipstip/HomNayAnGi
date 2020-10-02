package com.billi.homnayangi.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
    private String urlGetData = "https://noithattrangtrihoanganh.com/appLoadData.php";
    private String rootDomain = "https://noithattrangtrihoanganh.com";

    private List<DataCongThuc> lstCongThuc = new ArrayList<>();

    private AdapterCongThuc adapterCongThucTrua;

    private Boolean checkGetData = false;
    private Boolean checkLoad = false;

    private String TEXT_CHECK = "TRUA";
    ProgressBar progressBar;
    ProgressBar progressBarThucDon;

    LinearLayout lineTrua;
    LinearLayout lineLoad;
    LinearLayout lineCongThuc;

    private TextView tvGoiY;
    private Button btnGoiY;

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
        progressBarThucDon = (ProgressBar)view.findViewById(R.id.waitloadTrua);
        tvGoiY = view.findViewById(R.id.goiyTrua);
        lineTrua = view.findViewById(R.id.lineTrua);
        lineLoad = view.findViewById(R.id.lineLoad);
        lineCongThuc = view.findViewById(R.id.lineThucDonMoiNgay);
        btnGoiY = view.findViewById(R.id.btnGoiY);

        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);

        final Circle doubleBounce = new Circle();
        final Circle doubleBounce1 = new Circle();

        progressBar.setIndeterminateDrawable(doubleBounce);

        btnGoiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineLoad.setVisibility(View.VISIBLE);
                btnGoiY.setVisibility(View.GONE);
                getCongThucNew(getContext(), currentTime, lineTrua, TEXT_CHECK, new ListenerSang() {

                @Override
                public void onError(String message) {
                    Log.e("Err",message);
                }

                @Override
                public void onResponse() {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkLoad = true;
                            lineLoad.setVisibility(View.GONE);
                            lineCongThuc.setVisibility(View.VISIBLE);
                        }
                        }, 3000);
                }
                });
            }
        });

        if (checkLoad){
            btnGoiY.setVisibility(View.GONE);
            lineCongThuc.setVisibility(View.VISIBLE);
            getCongThucLate(view.getContext(),lineTrua,TEXT_CHECK);
        }

        tvGoiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarThucDon.setIndeterminateDrawable(doubleBounce1);
                progressBarThucDon.setVisibility(View.VISIBLE);
                lineTrua.setVisibility(View.GONE);
                tvGoiY.startAnimation(click);
                getGoiYCongThucNew(view.getContext(),TEXT_CHECK,currentTime,lineTrua);
            }
        });
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
                            if (BuaAn.equals(TEXT_CHECK)){
                                JSONArray array = new JSONArray(response);
                                SharedPrefs.getInstance().put(TEXT_CHECK,array.length());
                                lstCongThuc.clear();
                                for (int i = 0; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    lstCongThuc.add(
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
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
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

    public void getCongThucNew(final Context context, final int currentTime, final LinearLayout lineCongThuc, final String BuaAn, final ListenerSang listenerSang){
                getDataCongThuc(context, BuaAn, new ListenerSang() {
                    @Override
                    public void onError(String message) {
                        listenerSang.onError(message);
                    }

                    @Override
                    public void onResponse() {
                        SharedPrefs.getInstance().put("lateTime", currentTime);
                        checkGetData = true;
                        adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_recipes,lstCongThuc);
                        adapterCongThucTrua.notifyDataSetChanged();
                        for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                            lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                        }
                        listenerSang.onResponse();
                    }
                });
    }

    public void getCongThucLate(Context context,LinearLayout lineCongThuc,String BuaAn){
        if (BuaAn.equals(TEXT_CHECK)){
            if (!checkGetData){
                checkGetData = true;
                for (int i = 0; i < SharedPrefs.getInstance().get(BuaAn,Integer.class);i++){
                    lstCongThuc.add(new DataCongThuc(
                            SharedPrefs.getInstance().get("congthucid"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("tencongthuc"+BuaAn+i,String.class),
                            SharedPrefs.getInstance().get("thoigianlam"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoithich"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("luotnguoixem"+BuaAn+i,Integer.class),
                            SharedPrefs.getInstance().get("hinhanh"+BuaAn+i,String.class)
                    ));
                }
                adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_recipes,lstCongThuc);
                adapterCongThucTrua.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                }
            }
            else {
                adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_recipes,lstCongThuc);
                adapterCongThucTrua.notifyDataSetChanged();
                for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                    lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                }
            }
        }
    }
    public void getGoiYCongThucNew(final Context context, final String BuaAn, final int currentTime, final LinearLayout lineCongThuc){
        if (BuaAn.equals(TEXT_CHECK)){

            getDataCongThuc(context, BuaAn, new ListenerSang() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse() {
                    SharedPrefs.getInstance().put("lateTime", currentTime);
                    checkGetData = true;
                    adapterCongThucTrua = new AdapterCongThuc(context,R.layout.row_recipes,lstCongThuc);
                    adapterCongThucTrua.notifyDataSetChanged();
                    lineCongThuc.removeAllViews();
                    for (int i = 0; i < adapterCongThucTrua.getCount(); i++){
                        lineCongThuc.addView(adapterCongThucTrua.getView(i,null,null));
                    }
                    progressBarThucDon.setVisibility(View.GONE);
                    lineTrua.setVisibility(View.VISIBLE);
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
