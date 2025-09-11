package com.model;

import java.util.Date;

public class Venta {
    private String id;
    private float precioTotal;
    private Date fechaCreacion;
    private int idCliente;
    private int idEmpleado;

    public Venta(String id, float precioTotal, Date fechaCreacion, int idCliente, int idEmpleado) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.idCliente = idCliente;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
