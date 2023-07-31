package com.example.bottomnavigationwithdrawermenu.Mercaderista.Entity;


public class Register {
    String id, fecha, local, motivo, fechafin,tiempo,estado,user;
    boolean isFinalizado;

    public Register() {
    }

    public Register(String id, String fecha, String local, String motivo, String fechafin,String tiempo,String estado,String user) {
        this.id = id;
        this.fecha = fecha;
        this.local = local;
        this.motivo = motivo;
        this.fechafin = fechafin;
        this.tiempo = tiempo;
        this.estado = estado;
        this.user = user;
        this.isFinalizado = false; // Por defecto, se establece como no finalizado
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isFinalizado() {
        return isFinalizado;
    }
    public void setFinalizado(boolean finalizado) {
        isFinalizado = finalizado;
    }
    public String getEstado() {
        return estado;
    }

    public  String getUser(){return user;}
    public void setEstado(String statdo) {
        this.estado = estado;
    }
}