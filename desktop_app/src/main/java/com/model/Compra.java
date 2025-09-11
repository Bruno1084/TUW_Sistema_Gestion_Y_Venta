package com.model;

import java.util.Date;

public class Compra {
    private String id;
    private float precioTotal;
    private Date fechaCreacion;
    private int idProveedor;
    private int idEmpleado;

    public Compra(
            String id,
            float precioTotal,
            Date fechaCreacion,
            int idProveedor,
            int idEmpleado
    ) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.idProveedor = idProveedor;
        this.idEmpleado = idEmpleado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
