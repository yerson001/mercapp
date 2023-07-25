package com.example.bottomnavigationwithdrawermenu.Promotor.Entities;

public class detalle {
    String id,fecha,distribuidor,cliente,telefono,dirrecion,nonComercial,ventas,observaciones;

    public detalle() {
    }

    public detalle(String id, String fecha, String distribuidor, String cliente, String telefono, String direccion, String nonCommercial, String ventas, String observaciones) {
        this.id = id;
        this.fecha = fecha;
        this.distribuidor = distribuidor;
        this.cliente = cliente;
        this.telefono = telefono;
        this.dirrecion = direccion;
        this.nonComercial = nonCommercial;
        this.ventas = ventas;
        this.observaciones = observaciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDirrecion() {
        return dirrecion;
    }

    public String getNonCommercial() {
        return nonComercial;
    }

    public String getVentas() {
        return ventas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setDirrecion(String dirrecion) {
        this.dirrecion = dirrecion;
    }

}