package com.example.bottomnavigationwithdrawermenu.Promotor.Entities;

import com.example.bottomnavigationwithdrawermenu.itemMenu;

public class Frescos {
    public itemMenu valueCount;
    public itemMenu valueSale;
    String id,name;
    String venta;

    public Frescos(String id, String name){
        this.id = id;
        this.name = name;
        this.venta = "0";
    }
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String setId(){
        return this.id;
    }
    public String setNombre(){
        return this.name;
    }

    public String getVenta(){return this.venta;}

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
