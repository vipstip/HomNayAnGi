package com.billi.homnayangi.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
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
import com.billi.homnayangi.View.Activity.ActivitySearch;
import com.billi.homnayangi.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTimKiem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTimKiem extends Fragment {

    private String urlGetData = "https://noithattrangtrihoanganh.com/appLoadData.php";
    private String rootDomain = "https://noithattrangtrihoanganh.com";

    AutoCompleteTextView edtSearch;

    List<DataCongThuc> lstdataCongThuc = new ArrayList<>();
    AdapterSearch mAdapter;
    String txtSearch = "";
    TextView tvSearch;
    int start = 0;
    int countGet = 5;
    boolean checkSearch = false;
    boolean checkEdt;
    MainActivity activity;

    public FragmentTimKiem() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    public static FragmentTimKiem newInstance() {
        FragmentTimKiem fragment = new FragmentTimKiem();
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
        final View view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        final RecyclerView recycler = (RecyclerView)view.findViewById(R.id.recyclerSearch);
        edtSearch = view.findViewById(R.id.edt_search);
        tvSearch = view.findViewById(R.id.tvSearch);
        start = 0;

        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new AdapterSearch(recycler,getActivity(),lstdataCongThuc);
        recycler.setAdapter(mAdapter);
        if (checkSearch){
            tvSearch.setVisibility(View.GONE);
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edtSearch.clearFocus();
                closeKeyboard();
                return false;
            }
        });
        mAdapter.setLoadMore(new ListenerSearch() {
            @Override
            public void onLoadMore() {
                if(lstdataCongThuc.size() < 150) // Change max size
                {
                    //Random more data
                    start = start + countGet;
                    GetData(view.getContext(),String.valueOf(start),String.valueOf(countGet),txtSearch);

                }else{
                    Toast.makeText(getActivity(), "Load data completed !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txtSearch = editable.toString();
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    closeKeyboard();
                    edtSearch.clearFocus();
                    lstdataCongThuc.clear();
                    start = 0;
                    GetData(view.getContext(),String.valueOf(start),String.valueOf(countGet),txtSearch);
                    handled = true;
                }
                return handled;
            }
        });
        recycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edtSearch.clearFocus();
                closeKeyboard();
                return false;
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
        return view;
    }
    public void GetData(Context context, final String LimitStart, final String LimitEnd, final String txtSearch) {

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
                        checkSearch = true;
                        if (lstdataCongThuc.isEmpty()){
                            tvSearch.setText("Không có kết quả cần tìm kiếm");
                            tvSearch.setVisibility(View.VISIBLE);
                        } else {
                            tvSearch.setVisibility(View.GONE);
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
                params.put("txtSearch",txtSearch);
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
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        onSaveInstanceState(bundle);
        activity.setCheckEdt(false);
        edtSearch.clearFocus();
        closeKeyboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkEdt = activity.getCheckEdt();
        if (checkEdt){
            edtSearch.requestFocus();
            showKeyboard();
        }
    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
    }
}