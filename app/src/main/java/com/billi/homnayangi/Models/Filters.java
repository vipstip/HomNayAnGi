package com.billi.homnayangi.Models;

public class Filters {
    int idFilters;
    String nameFilters;

    public int getIdFilters() {
        return idFilters;
    }

    public void setIdFilters(int idFilters) {
        this.idFilters = idFilters;
    }

    public String getNameFilters() {
        return nameFilters;
    }

    public void setNameFilters(String nameFilters) {
        this.nameFilters = nameFilters;
    }

    public Filters(int idFilters, String nameFilters) {
        this.idFilters = idFilters;
        this.nameFilters = nameFilters;
    }


}
