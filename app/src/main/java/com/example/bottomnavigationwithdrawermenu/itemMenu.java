package com.example.bottomnavigationwithdrawermenu;

public class itemMenu {
    private int icon; // Recurso del icono o imagen
    private String text; // Texto de la opción

    public itemMenu(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
}