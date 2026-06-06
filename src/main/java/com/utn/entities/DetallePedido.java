package com.utn.entities;

import java.util.Objects;

public class DetallePedido extends Base {
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetallePedido that)) return false;
        return Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto);
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", producto=" + producto +
                '}';
    }
}
