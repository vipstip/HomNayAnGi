package com.billi.homnayangi.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.homnayangi.Adapter.AdapterFilters;
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


public class ActivityFilters extends AppCompatActivity implements OnItemClick {
    private String urlGetData = "https://noithattrangtrihoanganh.com/appLoadData.php";
    private String rootDomain = "https://noithattrangtrihoanganh.com";
    AdapterFilters adapterFilters;
    LinearLayout lineFilters;
    List<Filters> lstFilters = new ArrayList<>();
    List<Integer> lstIdTags = new ArrayList<>();
    List<String> lstNameTag = new ArrayList<>();
    ListTags listTags = new ListTags();
    List<Tags> lstTag = new ArrayList<>();
    LinearLayout lineSelect;
    TextView txtSelect;
    TextView clickSave;
    String textSelect = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        lineFilters = findViewById(R.id.lineFilters);
        lineSelect = findViewById(R.id.lineSelect);
        txtSelect = findViewById(R.id.txtSelect);
        clickSave = findViewById(R.id.clickSave);
        getDataFilters(getApplicationContext(), new ListenerSang() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse() {
                for (int dem = 0 ; dem < lstFilters.size(); dem++){
                    final int finalDem = dem;
                    getDataTags(getApplicationContext(), lstFilters.get(dem).getIdFilters(), new ListenerSang() {
                        @Override
                        public void onError(String message) {

                        }

                        @Override
                        public void onResponse() {
                            listTags.setSize(lstFilters.get(finalDem).getNameFilters(),lstTag.size());
                            for (int i = 0; i < lstTag.size(); i ++){
                                listTags.setMapIdTag(lstFilters.get(finalDem).getNameFilters(),i,lstTag.get(i).getIdTag());
                                listTags.setMapNameTag(lstFilters.get(finalDem).getNameFilters(),i,lstTag.get(i).getNameTag());
                            }
                            adapterFilters = new AdapterFilters(getApplicationContext(),R.layout.row_filter_recipes,listTags,lstFilters,ActivityFilters.this);
                            View addView = adapterFilters.getView(finalDem,null,null);
                            lineFilters.addView(addView);
                        }
                    });


                }
            }
        });
        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);
        clickSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(click);
                Intent intentResult = new Intent();
                if (!lstNameTag.isEmpty()){
                    intentResult.putExtra("checklst",true);
                    intentResult.putIntegerArrayListExtra("lstIdTags", (ArrayList<Integer>) lstIdTags);
                }else {
                    intentResult.putExtra("checklst",false);
                }
                setResult(RESULT_OK,intentResult);
                finish();
            }
        });
    }
    private void getDataFilters(final Context context, final ListenerSang listenerSang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                lstFilters.add(new Filters(object.getInt("id"),object.getString("name")));
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
                params.put("Filters","");
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
    private void getDataTags(final Context context, final int idFilters, final ListenerSang listenerSang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lstTag.clear();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                lstTag.add(new Tags(object.getInt("id"),object.getString("name")));
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
                params.put("Tags", String.valueOf(idFilters));
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
    public void onClick(Integer idTag,String nameTag, boolean check) {
        textSelect ="";
        if (check){
            lstIdTags.add(idTag);
            lstNameTag.add(nameTag);
        }else {
            lstIdTags.remove(idTag);
            lstNameTag.remove(nameTag);
        }
        if (!lstNameTag.isEmpty()){
            lineSelect.setVisibility(View.VISIBLE);
            textSelect = lstNameTag.get(0);
            if (lstNameTag.size()>1){
                for (int i = 1; i < lstNameTag.size(); i++){
                    textSelect += " + " + lstNameTag.get(i);
                }
            }
            txtSelect.setText(textSelect);
        }else {
            lineSelect.setVisibility(View.GONE);
        }
    }

}