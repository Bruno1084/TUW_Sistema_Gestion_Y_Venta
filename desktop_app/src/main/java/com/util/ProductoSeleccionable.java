package com.util;

import com.model.Producto;
import java.util.List;

public interface ProductoSeleccionable {
    void recibirProductosSeleccionados(List<Producto> productos);
}
