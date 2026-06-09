package com.utn;

import com.utn.entities.Pedido;
import com.utn.entities.Producto;
import com.utn.seed.DatosSemilla;
import com.utn.seed.DatosSemillaFactory;

public class Main {
    public static void main(String[] args) {
        DatosSemilla datos = DatosSemillaFactory.crear();
        Pedido pedido = datos.pedidos().iterator().next();

        mostrarResumenDatos(datos);
        mostrarProductosDisponibles(datos);
        mostrarTotalPedido(pedido);
        mostrarCantidadItemsPedido(pedido);
        mostrarProductosConStockBajo(datos);
    }

    private static void mostrarResumenDatos(DatosSemilla datos) {
        System.out.println("=== TP Programacion Funcional ===");
        System.out.println("Usuarios: " + datos.usuarios().size());
        System.out.println("Categorias: " + datos.categorias().size());
        System.out.println("Productos: " + datos.productos().size());
        System.out.println("Pedidos: " + datos.pedidos().size());
        System.out.println();
    }

    private static void mostrarProductosDisponibles(DatosSemilla datos) {
        System.out.println("Productos disponibles:");
        datos.productos().stream()
                .filter(Producto::getDisponible)
                .forEach(producto -> System.out.println("- " + producto.getNombre()));
        System.out.println();
    }

    private static void mostrarTotalPedido(Pedido pedido) {
        pedido.calcularTotal();
        System.out.println("Total del pedido número(id):" + pedido.getId() + " Fecha pedido: " +  pedido.getFecha() + " Importe: $" + pedido.getTotal());
        System.out.println();
    }

    private static void mostrarCantidadItemsPedido(Pedido pedido) {
        int cantidadItems = pedido.getDetallePedidos().stream()
                .mapToInt(detallePedido -> detallePedido.getCantidad())
                .sum();

        System.out.println("Cantidad total de items del pedido(id):" + pedido.getId() + " Fecha pedido: " +  pedido.getFecha() + ": " + cantidadItems);
        System.out.println();
    }

    private static void mostrarProductosConStockBajo(DatosSemilla datos) {
        System.out.println("Productos con stock menor a 5:");
        datos.productos().stream()
                .filter(producto -> producto.getStock() < 5)
                .forEach(producto -> System.out.println("- " + producto.getNombre() + " (stock: " + producto.getStock() + ")"));
    }
}
