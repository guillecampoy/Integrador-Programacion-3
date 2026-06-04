package com.tup.programacion3.entities;

import com.tup.programacion3.enums.Estado;

import java.time.LocalDate;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;



    public void addDetallePedido(int cantidad, Producto producto) {};

    public DetallePedido findeDetallePedidoByProducto (Producto producto){
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto){};

    public Pedido(Usuario usuario, LocalDate fecha, Estado estado, Double total) {}

    @Override
    public void calcularTotal() {

    }

}
