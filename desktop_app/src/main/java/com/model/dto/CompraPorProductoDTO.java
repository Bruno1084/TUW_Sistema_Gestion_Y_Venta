package com.model.dto;

public class CompraPorProductoDTO {
    private String codigoBarra;
    private String descripcion;
    private int cantidadComprada;
    private float precioTotal;

    public CompraPorProductoDTO() { }

    public CompraPorProductoDTO(String codigoBarra, String descripcion, int cantidadComprada, float precioTotal) {
        this.codigoBarra = codigoBarra;
        this.descripcion = descripcion;
        this.cantidadComprada = cantidadComprada;
        this.precioTotal = precioTotal;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
