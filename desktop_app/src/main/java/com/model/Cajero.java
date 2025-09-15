package com.model;

import java.util.Date;

public class Cajero extends Empleado{
    private String contrasenia;

    public Cajero() {
        super();
    }

    public Cajero(
        String id,
        String nombre,
        String direccion,
        String telefono,
        Date fechaCreacion,
        Date fechaModificacion,
        boolean esActivo,
        String contrasenia
    ) {
        super(id, nombre, direccion, telefono, fechaCreacion, fechaModificacion, esActivo);
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
