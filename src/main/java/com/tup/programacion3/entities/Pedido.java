package com.tup.programacion3.entities;

import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private Set<DetallePedido> detallePedidos;



    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido detallePedido = new DetallePedido(producto, cantidad);
        this.detallePedidos.add(detallePedido);
        calcularTotal();
    }

    public void addDetallePedido(Producto producto, int cantidad) {
        addDetallePedido(cantidad, producto);
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detallePedido : detallePedidos) {
            if (Objects.equals(detallePedido.getProducto(), producto)) {
                return detallePedido;
            }
        }
        return null;
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        return findDetallePedidoByProducto(producto);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detallePedidos.removeIf(detallePedido -> Objects.equals(detallePedido.getProducto(), producto));
        calcularTotal();
    }

    public Pedido(Usuario usuario) {
        this(LocalDate.now(), Estado.PENDIENTE, FormaPago.EFECTIVO, usuario);
    }

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
        this.fecha = fecha;
        this.estado = estado;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallePedidos = new HashSet<>();
        if (usuario != null) {
            usuario.addPedido(this);
        }
    }

    @Override
    public void calcularTotal() {
        this.total = detallePedidos.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(fecha, pedido.fecha) && estado == pedido.estado && formaPago == pedido.formaPago && Objects.equals(usuario, pedido.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, estado, formaPago, usuario);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", formaPago=" + formaPago +
                ", usuario=" + usuario +
                ", detallePedidos=" + detallePedidos +
                '}';
    }
}
