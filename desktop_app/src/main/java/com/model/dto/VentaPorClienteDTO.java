package com.model.dto;

public class VentaPorClienteDTO {
    private int clienteId;
    private String clienteNombre;
    private int ventasTotales;
    private float precioTotal;

    public VentaPorClienteDTO() { }

    public VentaPorClienteDTO(int clienteId, String clienteNombre, int ventasTotales, float precioTotal) {
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.ventasTotales = ventasTotales;
        this.precioTotal = precioTotal;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public int getVentasTotales() {
        return ventasTotales;
    }

    public void setVentasTotales(int ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
