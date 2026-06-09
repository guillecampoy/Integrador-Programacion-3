package ar.edu.tup.programacion3.seed;

import ar.edu.tup.programacion3.entities.Categoria;
import ar.edu.tup.programacion3.entities.Pedido;
import ar.edu.tup.programacion3.entities.Producto;
import ar.edu.tup.programacion3.entities.Usuario;

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
