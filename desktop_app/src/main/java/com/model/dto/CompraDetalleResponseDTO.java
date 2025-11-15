package com.model.dto;

import com.model.Producto;

public class CompraDetalleResponseDTO {
    private int cantidad;
    private float precioTotal;
    private float precioUnitario;
    private Producto producto;

    public CompraDetalleResponseDTO() { }

    public CompraDetalleResponseDTO(int cantidad, float precioTotal, float precioUnitario, Producto producto) {
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
