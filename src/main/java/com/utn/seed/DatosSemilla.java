package com.utn.seed;

import com.utn.entities.Categoria;
import com.utn.entities.Pedido;
import com.utn.entities.Producto;
import com.utn.entities.Usuario;

import java.util.Set;

public record DatosSemilla(
        Set<Usuario> usuarios,
        Set<Categoria> categorias,
        Set<Producto> productos,
        Set<Pedido> pedidos,
        Producto productoParaMostrar,
        Producto productoParaComparar
) {
}
