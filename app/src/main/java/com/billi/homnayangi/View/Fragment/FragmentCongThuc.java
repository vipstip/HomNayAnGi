package com.billi.homnayangi.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.homnayangi.Adapter.AdapterSearch;
import com.billi.homnayangi.Interface.ListenerSearch;
import com.billi.homnayangi.Models.DataCongThuc;
import com.billi.homnayangi.R;
import com.billi.homnayangi.Utils.RecyclerItemClickListener;
import com.billi.homnayangi.View.Activity.ActivityFilters;
import com.billi.homnayangi.View.Activity.ActivitySearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCongThuc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCongThuc extends Fragment {
    private String urlGetData = "https://noithattrangtrihoanganh.com/appLoadData.php";
    private String rootDomain = "https://noithattrangtrihoanganh.com";
    Button btnBoLoc;
    AdapterSearch mAdapter;
    RecyclerView recycler;
    LinearLayout lineShowFilter;
    boolean checkFilter;
    List<DataCongThuc> lstdataCongThuc = new ArrayList<>();
    List<Integer> lstIdTag = new ArrayList<>();
    List<String> lstNameTag = new ArrayList<>();
    String txtListTagId;
    int start = 0;
    int countGet = 12;
    public FragmentCongThuc() {
        // Required empty public constructor
    }

    public static FragmentCongThuc newInstance() {
        FragmentCongThuc fragment = new FragmentCongThuc();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cong_thuc, container, false);

        recycler = (RecyclerView)view.findViewById(R.id.reclefrgCongThuc);
        start = 0;
        btnBoLoc = view.findViewById(R.id.btnBoLoc);
        lineShowFilter = view.findViewById(R.id.lineShowFilter);
        txtListTagId = "";
        btnBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ActivityFilters.class);
                startActivityForResult(i,1);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        if (!checkFilter){
            checkFilter = true;
            lstdataCongThuc.clear();
            recycler.setLayoutManager(layoutManager);
            mAdapter = new AdapterSearch(recycler,getActivity(),lstdataCongThuc);
            recycler.setAdapter(mAdapter);
            GetData(view.getContext(), String.valueOf(start), String.valueOf(countGet),"-1");
        } else {
            recycler.setLayoutManager(layoutManager);
            mAdapter = new AdapterSearch(recycler,getActivity(),lstdataCongThuc);
            recycler.setAdapter(mAdapter);
        }

        mAdapter.setLoadMore(new ListenerSearch() {
            @Override
            public void onLoadMore() {
                if(lstdataCongThuc.size() < 150) // Change max size
                {

                    //Random more data
                    start = start + countGet;
                    if (txtListTagId.isEmpty()){
                        GetData(view.getContext(),String.valueOf(start),String.valueOf(countGet),"-1");
                    } else {
                        GetData(view.getContext(),String.valueOf(start),String.valueOf(countGet),txtListTagId);
                    }

                }else{
                    Toast.makeText(getActivity(), "Load data completed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        try {
                            view.startAnimation(click);
                            Intent intent = new Intent(view.getContext(), ActivitySearch.class);
                            intent.putExtra("Search", lstdataCongThuc.get(position).getTenCongThuc());
                            view.getContext().startActivity(intent);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>20){
                    lineShowFilter.setVisibility(View.GONE);
                } else if (dy<-20){
                    lineShowFilter.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String txtSelected;
                lstdataCongThuc.clear();
                if (data != null) {
                    lstIdTag.clear();
                    lstNameTag.clear();
                    boolean checklst = data.getBooleanExtra("checklst",false);
                    if (checklst){
                        lstIdTag = data.getIntegerArrayListExtra("lstIdTags");

                        txtListTagId = lstIdTag.get(0).toString();
                        if (lstIdTag.size() > 1){
                            for (int i = 1; i< lstIdTag.size() ; i++){
                                txtListTagId += "/" + lstIdTag.get(i);
                            }
                        }
                        GetData(getContext(),String.valueOf(start),String.valueOf(countGet),txtListTagId);
                    } else {
                        start = 0;
                        GetData(getContext(),String.valueOf(start),String.valueOf(countGet),"-1");
                    }
                }
            }
        }
    }

    public void GetData(Context context, final String LimitStart, final String LimitEnd, final String txtListTagID) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                lstdataCongThuc.add(
                                        new DataCongThuc(
                                                object.getInt("congthucid"),
                                                object.getString("tencongthuc"),
                                                object.getInt("thoigianlam"),
                                                object.getInt("luotnguoithich"),
                                                object.getInt("luotnguoixem"),
                                                rootDomain + object.getString("hinhanh")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        lstdataCongThuc.add(null);
                        mAdapter.notifyItemInserted(lstdataCongThuc.size()-1);
                        lstdataCongThuc.remove(lstdataCongThuc.size()-1);
                        mAdapter.notifyItemRemoved(lstdataCongThuc.size());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("LimitStart", LimitStart);
                params.put("Limit", LimitEnd);
                params.put("txtListTagID",txtListTagID);
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
    public int dpToPx( float dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }
    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        onSaveInstanceState(bundle);
    }

}