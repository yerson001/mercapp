package com.example.bottomnavigationwithdrawermenu.Promotor.Entities;

public class prices {

    String id,producto,marca,gramaje,precio;

    public prices(String id,String producto,String precio)
    {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
    }

    public String getId(){return id;}

    public String getProducto(){return producto;}
    public String getPrecioPri(){return precio;}
    public  void setPrecioPri(String precio){this.precio = precio;}
    public String getItem() {
        return marca + "," + gramaje + "," + precio;
    }

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}