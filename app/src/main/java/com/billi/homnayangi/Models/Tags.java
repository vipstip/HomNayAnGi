package com.billi.homnayangi.Models;

public class Tags {
    int idTag;
    String nameTag;

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public Tags(int idTag, String nameTag){
        this.idTag = idTag;
        this.nameTag = nameTag;
    }

}
