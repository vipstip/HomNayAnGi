package com.billi.homnayangi.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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

    private String urlGetData = "http://192.168.1.61/crawldata/appLoadData.php";
    private List<DataCongThuc> lstCongThucSang = new ArrayList<>();
    private List<DataCongThuc> lstCongThucTrua = new ArrayList<>();
    private List<DataCongThuc> lstCongThucToi = new ArrayList<>();
    private AdapterCongThuc adapterCongThuc;
    private Boolean checkGetSang = false;
    private Boolean checkGetTrua = false;
    private Boolean checkGetToi = false;
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

        int currentTime = Calendar.getInstance().getTime().getDate();
        //Get cong thuc buoi Sang
        try {
            Log.e("ABC", currentTime + " ");
        } catch (Exception e) {
            Log.e("ERRR", e.toString());
        }
        final LinearLayout lineSang = view.findViewById(R.id.lineSang);
        if (!checkGetSang){
            getDataCongThuc(view.getContext(), new ListenerSang() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse() {
                    SharedPrefs.getInstance().put("lstCongThucSang",lstCongThucSang);
                    checkGetSang = true;
                    adapterCongThuc = new AdapterCongThuc(view.getContext(),R.layout.row_cong_thuc,lstCongThucSang);
                    adapterCongThuc.notifyDataSetChanged();

                    for (int i = 0; i < adapterCongThuc.getCount(); i++){
                        lineSang.addView(adapterCongThuc.getView(i,null,null));
                    }
                }
            });
        } else {
            adapterCongThuc = new AdapterCongThuc(view.getContext(),R.layout.row_cong_thuc,lstCongThucSang);
            adapterCongThuc.notifyDataSetChanged();
            for (int i = 0; i < adapterCongThuc.getCount(); i++){
                lineSang.addView(adapterCongThuc.getView(i,null,null));
            }
        }
        //
        return view;
    }

    private void getDataCongThuc(final Context context, final ListenerSang listenerSang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            lstCongThucSang.add(new DataCongThuc(object.getInt("congthucid"),object.getString("tencongthuc"),object.getInt("thoigianlam"),object.getInt("luotnguoithich"),object.getInt("luotnguoixem"),object.getString("hinhanh")));
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
}
