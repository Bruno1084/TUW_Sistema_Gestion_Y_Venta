package com.model.dto;

import com.model.Proveedor;
import com.model.Usuario;
import java.util.List;

public class CompraDetailResponseDTO {
    private int id;
    private float precioTotal;
    private String fechaCreacion;
    private Proveedor proveedor;
    private Usuario usuario;
    private List<CompraDetalleResponseDTO> detalles;

    public CompraDetailResponseDTO() { }

    public CompraDetailResponseDTO(int id, float precioTotal, String fechaCreacion, Proveedor proveedor, Usuario usuario, List<CompraDetalleResponseDTO> detalles) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.detalles = detalles;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CompraDetalleResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CompraDetalleResponseDTO> detalles) {
        this.detalles = detalles;
    }
}
