package com.model;

public class CompraDetalle {
    private Compra compra;
    private Producto producto;
    private int cantidad;
    private float precioTotal;
    private float precioUnitario;

    public CompraDetalle() {}

    public CompraDetalle(
            Compra compra,
            Producto producto,
            int cantidad,
            float precioTotal,
            float precioUnitario
    ) {
        this.compra = compra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    public void recalcularTotal() {
        this.precioTotal = this.cantidad * this.precioUnitario;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
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
}
