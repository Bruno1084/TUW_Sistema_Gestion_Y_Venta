package com.model.dto;

public class VentaDetalleDTO {
    private int ventaId;
    private String productoCodigoBarra;
    private int cantidad;
    private float precioTotal;
    private float precioUnitario;

    public VentaDetalleDTO() { }

    public VentaDetalleDTO(int ventaId, String productoCodigoBarra, int cantidad, float precioTotal, float precioUnitario) {
        this.ventaId = ventaId;
        this.productoCodigoBarra = productoCodigoBarra;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
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
