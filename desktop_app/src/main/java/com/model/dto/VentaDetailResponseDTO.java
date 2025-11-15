package com.model.dto;

import com.model.Cliente;
import com.model.Usuario;
import java.util.List;

public class VentaDetailResponseDTO {
    private int id;
    private float precioTotal;
    private String fechaCreacion;
    private Cliente cliente;
    private Usuario usuario;
    private List<VentaDetalleResponseDTO> detalles;

    public VentaDetailResponseDTO() { }

    public VentaDetailResponseDTO(int id, float precioTotal, String fechaCreacion, Cliente cliente, Usuario usuario, List<VentaDetalleResponseDTO> detalles) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<VentaDetalleResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<VentaDetalleResponseDTO> detalles) {
        this.detalles = detalles;
    }
}
