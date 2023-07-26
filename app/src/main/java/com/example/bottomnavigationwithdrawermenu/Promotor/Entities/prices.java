package com.example.bottomnavigationwithdrawermenu.Promotor.Entities;

public class prices {

    String id,producto,marca,frasco,gramaje,precio;
    public prices(){

    }

    public prices(String id,String producto,String marca,String frasco,String gramaje,String precio)
    {
        this.id = id;
        this.producto = producto;
        this.marca = marca;
        this.frasco = frasco;
        this.gramaje = gramaje;
        this.precio = precio;
    }

    public String getId(){return id;}

    public String getProducto(){return producto;}

    public String getMarca(){return marca;}

    public String getFrasco(){return frasco;}

    public String getGramaje(){return gramaje;}

    public String getPrecio(){return precio;}

    public String getItem(){return marca+","+gramaje+","+precio;};

}