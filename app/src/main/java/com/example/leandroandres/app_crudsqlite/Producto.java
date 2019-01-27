package com.example.leandroandres.app_crudsqlite;

import java.io.Serializable;

public class Producto implements Serializable {
    private String codigo;
    private String descripcion;
    private String precio;

    public Producto() {
    }

    public Producto(String codigo, String descripcion, String precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "codigo: " + codigo +
                "\ndescripcion: " + descripcion +
                "\nprecio: " + precio;
    }
}
