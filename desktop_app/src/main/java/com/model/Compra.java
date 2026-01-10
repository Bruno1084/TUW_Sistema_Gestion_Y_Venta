package com.model;

import java.util.Date;

public class Compra {
    private int id;
    private float precioTotal;
    private Date fechaCreacion;
    private Proveedor proveedor;
    private Usuario usuario;

    public Compra() {}

    public Compra(int id, float precioTotal, Date fechaCreacion, Proveedor proveedor, Usuario usuario) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.proveedor = proveedor;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
