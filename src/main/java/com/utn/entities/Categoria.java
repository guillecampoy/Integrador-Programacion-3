package com.utn.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    private Set<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new HashSet<>();
    }

    public void addProducto(Producto producto) {
        this.productos.add(producto);
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", productos=" + productos.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Categoria categoria)) return false;
        return Objects.equals(nombre, categoria.nombre) && Objects.equals(descripcion, categoria.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, descripcion);
    }
}
