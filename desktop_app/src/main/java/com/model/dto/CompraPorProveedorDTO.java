package com.model.dto;

public class CompraPorProveedorDTO {
    private int proveedorId;
    private String proveedorNombre;
    private int comprasTotales;
    private float precioTotal;

    public CompraPorProveedorDTO() { }

    public CompraPorProveedorDTO(int proveedorId, String proveedorNombre, int comprasTotales, float precioTotal) {
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.comprasTotales = comprasTotales;
        this.precioTotal = precioTotal;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public int getComprasTotales() {
        return comprasTotales;
    }

    public void setComprasTotales(int comprasTotales) {
        this.comprasTotales = comprasTotales;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
