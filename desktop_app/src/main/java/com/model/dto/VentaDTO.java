package com.model.dto;

import java.util.Date;

public class VentaDTO {
    private int id;
    private float precioTotal;
    private Date fechaCreacion;
    private int clienteId;
    private int usuarioId;

    public VentaDTO() { }

    public VentaDTO(int id, float precioTotal, Date fechaCreacion, int clienteId, int usuarioId) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
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

    public int getClienteId() {
        return clienteId;
    }

    public void setCLienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
