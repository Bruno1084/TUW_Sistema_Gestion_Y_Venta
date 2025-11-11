package com.model;

public class CompraDetalleDTO {
    private int compraId;
    private String productoCodigoBarra;
    private int cantidad;
    private float precioTotal;
    private float precioUnitario;

    public CompraDetalleDTO() { }

    public CompraDetalleDTO(int compraId, String productoCodigoBarra, int cantidad, float precioTotal, float precioUnitario) {
        this.compraId = compraId;
        this.productoCodigoBarra = productoCodigoBarra;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public String getProductoCodigoBarra() {
        return productoCodigoBarra;
    }

    public void setProductoCodigoBarra(String productoCodigoBarra) {
        this.productoCodigoBarra = productoCodigoBarra;
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
