package com.billi.homnayangi.Models;

import java.util.HashMap;
import java.util.Map;

public class ListTags {
    Map<String,String> mapNameTag = new HashMap<>();
    Map<String,Integer> mapIdTag = new HashMap<>();
    Map<String,Integer> mapSize = new HashMap<>();


    public int getIdTag(String nameFilter,int count) {
        if (mapIdTag.get(nameFilter+count)!=null){
            return mapIdTag.get(nameFilter+count);
        }
        return -1;
    }

    public String getNameTag(String nameFilter,int count) {
        if (mapNameTag.get(nameFilter+count)!=null){
            return mapNameTag.get(nameFilter+count);
        }
        return null;
    }
    public void setSize(String nameFilter, int size){
        mapSize.put(nameFilter,size);
    }
    public void setMapNameTag(String nameFilter,int count,String nameTag){
            mapNameTag.put(nameFilter+count,nameTag);

    }
    public void setMapIdTag(String nameFilter,int count,int idTag){
            mapIdTag.put(nameFilter+count,idTag);
    }
    public int getSize(String nameFilter){
        return mapSize.get(nameFilter);
    }
}
