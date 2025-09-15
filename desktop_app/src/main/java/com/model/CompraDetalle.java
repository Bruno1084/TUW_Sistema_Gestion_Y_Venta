package com.model;

public class CompraDetalle {
    private String idCompra;
    private String codigoProducto;
    private int cantidad;
    private float precioTotal;
    private float precioUnitario;

    public CompraDetalle() {}

    public CompraDetalle(
            String idCompra,
            String codigoProducto,
            int cantidad,
            float precioTotal,
            float precioUnitario
    ) {
        this.idCompra = idCompra;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
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
