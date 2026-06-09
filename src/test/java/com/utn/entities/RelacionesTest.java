package com.utn.entities;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.seed.DatosSemilla;
import com.utn.seed.DatosSemillaFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelacionesTest {
    @Test
    void entidadesPrincipalesExtiendenBaseYPedidoImplementaCalculable() {
        assertInstanceOf(Base.class, Usuario.builder().build());
        assertInstanceOf(Base.class, Categoria.builder().build());
        assertInstanceOf(Base.class, Producto.builder().build());
        assertInstanceOf(Base.class, DetallePedido.builder().build());

        Pedido pedido = Pedido.builder().build();
        assertInstanceOf(Base.class, pedido);
        assertInstanceOf(Calculable.class, pedido);
    }

    @Test
    void usuarioMantieneRelacionConPedidos() {
        Usuario usuario = Usuario.builder()
                .mail("usuario@test.com")
                .build();
        Pedido pedido = crearPedidoSinUsuario();

        usuario.addPedido(pedido);

        assertTrue(usuario.getPedidos().contains(pedido));
        assertSame(usuario, pedido.getUsuario());
    }

    @Test
    void pedidoMantieneRelacionConUsuario() {
        Usuario usuario = Usuario.builder()
                .mail("usuario@test.com")
                .build();
        Pedido pedido = crearPedidoSinUsuario();

        pedido.setUsuario(usuario);

        assertSame(usuario, pedido.getUsuario());
        assertTrue(usuario.getPedidos().contains(pedido));
    }

    @Test
    void categoriaMantieneRelacionConProductos() {
        Categoria categoria = Categoria.builder()
                .nombre("Almacen")
                .build();
        Producto producto = crearProducto("Cafe molido", 3200.0);

        categoria.addProducto(producto);

        assertTrue(categoria.getProductos().contains(producto));
        assertSame(categoria, producto.getCategoria());
    }

    @Test
    void productoMantieneRelacionConCategoria() {
        Categoria categoria = Categoria.builder()
                .nombre("Almacen")
                .build();
        Producto producto = crearProducto("Cafe molido", 3200.0);

        producto.setCategoria(categoria);

        assertSame(categoria, producto.getCategoria());
        assertTrue(categoria.getProductos().contains(producto));
    }

    @Test
    void pedidoComponeDetallesYDetalleReferenciaUnProducto() {
        Pedido pedido = crearPedidoSinUsuario();
        Producto producto = crearProducto("Cafe molido", 3200.0);

        pedido.addDetallePedido(2, producto);

        DetallePedido detallePedido = pedido.findDetallePedidoByProducto(producto);
        assertEquals(1, pedido.getDetallePedidos().size());
        assertSame(producto, detallePedido.getProducto());
        assertEquals(6400.0, detallePedido.getSubtotal());
        assertEquals(6400.0, pedido.getTotal());
    }

    @Test
    void datosSemillaMantienenRelacionesDelDiagrama() {
        DatosSemilla datosSemilla = DatosSemillaFactory.crear();

        datosSemilla.pedidos().forEach(pedido -> {
            assertTrue(pedido.getUsuario().getPedidos().contains(pedido));
            assertTrue(pedido.getDetallePedidos().size() >= 1);
            pedido.getDetallePedidos().forEach(detallePedido ->
                    assertTrue(datosSemilla.productos().contains(detallePedido.getProducto()))
            );
        });
        datosSemilla.productos().forEach(producto ->
                assertTrue(producto.getCategoria().getProductos().contains(producto))
        );
        datosSemilla.categorias().forEach(categoria ->
                assertTrue(categoria.getProductos().size() >= 1)
        );
    }

    private Pedido crearPedidoSinUsuario() {
        return Pedido.builder()
                .fecha(LocalDate.of(2026, 5, 10))
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .build();
    }

    private Producto crearProducto(String nombre, Double precio) {
        return Producto.builder()
                .nombre(nombre)
                .precio(precio)
                .build();
    }
}
