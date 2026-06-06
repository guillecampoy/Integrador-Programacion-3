package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
public class DetallePedido extends Base {
    @EqualsAndHashCode.Include
    private int cantidad;
    @EqualsAndHashCode.Include
    private Double subtotal;
    @EqualsAndHashCode.Include
    private Producto producto;

}
