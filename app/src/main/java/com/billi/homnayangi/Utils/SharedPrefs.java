package com.billi.homnayangi.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by FRAMGIA\dang.anh.quan on 29/08/2017.
 */

public class SharedPrefs {

    private static final String PREFS_NAME = "share_prefs";
    private static SharedPrefs mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPrefs() {
        mSharedPreferences = App.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPrefs();
        }
        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else {
            return (T) App.self()
                    .getGSon()
                    .fromJson(mSharedPreferences.getString(key, ""), anonymousClass);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass, T defaultValue) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, (String) defaultValue);
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, (Boolean) defaultValue));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, (Float) defaultValue));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, (Integer) defaultValue));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, (Long) defaultValue));
        } else {
            return (T) App.self()
                    .getGSon()
                    .fromJson(mSharedPreferences.getString(key, ""), anonymousClass);
        }
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, App.self().getGSon().toJson(data));
        }
        editor.apply();
    }

    public <T> void putList(String key,  Class<T> anonymousClass, List<T> list){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key,list.size());
        if (anonymousClass == String.class) {
            for (int i = 0; i < list.size(); i++){
                editor.putString(key+i,(String) list.get(i));
            }
        } else if (anonymousClass == Boolean.class ) {
            for (int i = 0; i < list.size(); i++){
                editor.putBoolean(key+i,(Boolean) list.get(i));
            }
        } else if (anonymousClass == Float.class) {
            for (int i = 0; i < list.size(); i++){
                editor.putFloat(key+i,(Float) list.get(i));
            }
        } else if (anonymousClass == Integer.class) {
            for (int i = 0; i < list.size(); i++){
                editor.putInt(key+i,(Integer) list.get(i));
            }
        } else if (anonymousClass == Long.class) {
            for (int i = 0; i < list.size(); i++){
                editor.putLong(key+i,(Long) list.get(i));
            }
        } else {
            for (int i = 0; i < list.size(); i++){
                editor.putString(key+i,App.self().getGSon().toJson(list));
            }
        }
        editor.apply();
    }
    public <T> List<T> getList(String key ,Class<T> anonymousClass){

        int size = mSharedPreferences.getInt(key,0);

        if (anonymousClass == String.class) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getString(key+i,""));
            }
            return (List<T>) list;
        } else if (anonymousClass == Boolean.class) {
            List<Boolean> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getBoolean(key+i,false));
            }
            return (List<T>) list;
        } else if (anonymousClass == Float.class) {
            List<Float> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getFloat(key+i,0));
            }
            return (List<T>) list;
        } else if (anonymousClass == Integer.class) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getInt(key+i,0));
            }
            return (List<T>) list;
        } else if (anonymousClass == Long.class) {
            List<Long> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getLong(key+i,0));
            }
            return (List<T>) list;
        } else {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < size; i++){
                list.add(mSharedPreferences.getString(key+i,""));
            }
            return (List<T>) list;
        }
    }
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}