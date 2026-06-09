package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
public class Categoria extends Base {
    @EqualsAndHashCode.Include
    private String nombre;
    private String descripcion;
    @Builder.Default
    @ToString.Exclude
    private Set<Producto> productos = new HashSet<>();

    public void addProducto(Producto producto) {
        if (producto == null) {
            return;
        }
        this.productos.add(producto);
        if (producto.getCategoria() != this) {
            producto.setCategoria(this);
        }
    }

}
