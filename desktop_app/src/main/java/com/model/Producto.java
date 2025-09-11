package com.model;

import java.util.Date;

public class Producto {
    private String codigoBarra;
    private String descripcion;
    private float precioVenta;
    private float precioCompra;
    private int stock;
    private String imgUri;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private int idProveedor;
    private int idMarca;
    private int idRubro;

    public Producto(
            String codigoBarra,
            String descripcion,
            float precioVenta,
            float precioCompra,
            int stock,
            String imgUri,
            Date fechaCreacion,
            Date fechaModificacion,
            int idProveedor,
            int idMarca,
            int idRubro
    ) {
        this.codigoBarra = codigoBarra;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.stock = stock;
        this.imgUri = imgUri;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.idProveedor = idProveedor;
        this.idMarca = idMarca;
        this.idRubro = idRubro;
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

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }
}
