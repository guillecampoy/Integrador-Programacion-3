package com.tup.programacion3;

import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Pedido;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.entities.Usuario;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Rol;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static DatosSemilla datos;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("No se recibio una opcion. Saliendo del programa.");
                break;
            }

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    instanciarDatos();
                    break;
                case "2":
                    mostrarProducto();
                    break;
                case "3":
                    listarProductos();
                    break;
                case "4":
                    mostrarPedidosDelUsuarioConMasPedidos();
                    break;
                case "5":
                    compararProductoConColeccion();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== TP ToString - Colecciones ===");
        System.out.println("1. Instanciar datos");
        System.out.println("2. Mostrar un producto usando toString()");
        System.out.println("3. Listar productos cargados");
        System.out.println("4. Mostrar pedidos del usuario con mas pedidos");
        System.out.println("5. Comparar producto nuevo contra la coleccion");
        System.out.println("0. Salir");
    }

    private static void instanciarDatos() {
        datos = crearDatosSemilla();
        System.out.println("Datos instanciados correctamente.");
        System.out.println("Usuarios: " + datos.usuarios().size());
        System.out.println("Categorias: " + datos.categorias().size());
        System.out.println("Productos: " + datos.productos().size());
        System.out.println("Pedidos: " + datos.pedidos().size());
    }

    private static void mostrarProducto() {
        DatosSemilla datosSemilla = obtenerDatos();
        Producto producto = datosSemilla.productoParaMostrar();

        System.out.println("Producto seleccionado:");
        System.out.println(producto);
    }

    private static void listarProductos() {
        DatosSemilla datosSemilla = obtenerDatos();

        System.out.println("Productos cargados:");
        int numero = 1;
        for (Producto producto : datosSemilla.productos()) {
            System.out.println(numero + ". " + producto);
            numero++;
        }
    }

    private static void mostrarPedidosDelUsuarioConMasPedidos() {
        DatosSemilla datosSemilla = obtenerDatos();
        Optional<Usuario> usuarioConMasPedidos = datosSemilla.usuarios().stream()
                .max((usuario1, usuario2) -> Integer.compare(
                        usuario1.getPedidos().size(),
                        usuario2.getPedidos().size()
                ));

        if (usuarioConMasPedidos.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        Usuario usuario = usuarioConMasPedidos.get();
        System.out.println("Usuario con mas pedidos:");
        System.out.println(usuario);
        System.out.println("Pedidos:");
        for (Pedido pedido : usuario.getPedidos()) {
            System.out.println(pedido);
        }
    }

    private static void compararProductoConColeccion() {
        DatosSemilla datosSemilla = obtenerDatos();
        Producto productoNuevo = new Producto(
                "Cafe molido",
                3200.0,
                "Cafe tostado molido 500g",
                30,
                "cafe-molido.jpg",
                true,
                datosSemilla.categoriaAlmacen()
        );

        System.out.println("Producto nuevo a comparar:");
        System.out.println(productoNuevo);
        System.out.println("Resultados contra la coleccion:");

        boolean encontrado = false;
        for (Producto producto : datosSemilla.productos()) {
            boolean sonIguales = productoNuevo.equals(producto);
            System.out.println("- " + sonIguales + " -> " + producto);
            if (sonIguales) {
                encontrado = true;
            }
        }

        System.out.println("La coleccion contiene un producto equivalente: " + encontrado);
        System.out.println("Resultado de Set.contains(productoNuevo): " + datosSemilla.productos().contains(productoNuevo));
    }

    private static DatosSemilla obtenerDatos() {
        if (datos == null) {
            datos = crearDatosSemilla();
            System.out.println("No habia datos cargados. Se instanciaron datos semilla automaticamente.");
            System.out.println();
        }
        return datos;
    }

    private static DatosSemilla crearDatosSemilla() {
        Set<Usuario> usuarios = new LinkedHashSet<>();
        Set<Categoria> categorias = new LinkedHashSet<>();
        Set<Producto> productos = new LinkedHashSet<>();
        Set<Pedido> pedidos = new LinkedHashSet<>();

        Usuario usuario1 = new Usuario("Ana", "Garcia", "ana.garcia@mail.com", "3515551001", "ana123", Rol.USUARIO);
        Usuario usuario2 = new Usuario("Bruno", "Perez", "bruno.perez@mail.com", "3515551002", "bruno123", Rol.ADMIN);
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        Categoria almacen = new Categoria("Almacen", "Productos secos y envasados");
        Categoria bebidas = new Categoria("Bebidas", "Bebidas frias y calientes");
        Categoria limpieza = new Categoria("Limpieza", "Articulos de limpieza del hogar");
        categorias.add(almacen);
        categorias.add(bebidas);
        categorias.add(limpieza);

        Producto cafeMolido = new Producto("Cafe molido", 3200.0, "Cafe tostado molido 500g", 30, "cafe-molido.jpg", true, almacen);
        Producto yerbaMate = new Producto("Yerba mate", 2800.0, "Yerba mate tradicional 1kg", 45, "yerba-mate.jpg", true, almacen);
        Producto arroz = new Producto("Arroz largo fino", 1300.0, "Arroz largo fino 1kg", 60, "arroz.jpg", true, almacen);
        Producto fideos = new Producto("Fideos tirabuzon", 950.0, "Fideos secos tirabuzon 500g", 80, "fideos.jpg", true, almacen);
        Producto gaseosa = new Producto("Gaseosa cola", 1800.0, "Gaseosa cola 2.25L", 35, "gaseosa-cola.jpg", true, bebidas);
        Producto aguaMineral = new Producto("Agua mineral", 900.0, "Agua mineral sin gas 1.5L", 50, "agua-mineral.jpg", true, bebidas);
        Producto jugoNaranja = new Producto("Jugo de naranja", 1500.0, "Jugo de naranja 1L", 25, "jugo-naranja.jpg", true, bebidas);
        Producto detergente = new Producto("Detergente", 1200.0, "Detergente concentrado 750ml", 40, "detergente.jpg", true, limpieza);
        Producto lavandina = new Producto("Lavandina", 1100.0, "Lavandina tradicional 1L", 38, "lavandina.jpg", true, limpieza);
        Producto esponja = new Producto("Esponja multiuso", 700.0, "Esponja multiuso doble cara", 70, "esponja.jpg", true, limpieza);
        productos.add(cafeMolido);
        productos.add(yerbaMate);
        productos.add(arroz);
        productos.add(fideos);
        productos.add(gaseosa);
        productos.add(aguaMineral);
        productos.add(jugoNaranja);
        productos.add(detergente);
        productos.add(lavandina);
        productos.add(esponja);

        Pedido pedido1 = new Pedido(LocalDate.of(2026, 5, 10), Estado.CONFIRMADO, FormaPago.TARJETA, usuario1);
        pedido1.addDetallePedido(cafeMolido, 1);
        pedido1.addDetallePedido(gaseosa, 2);

        Pedido pedido2 = new Pedido(LocalDate.of(2026, 5, 12), Estado.PENDIENTE, FormaPago.EFECTIVO, usuario1);
        pedido2.addDetallePedido(yerbaMate, 1);
        pedido2.addDetallePedido(detergente, 1);
        pedido2.addDetallePedido(aguaMineral, 3);

        Pedido pedido3 = new Pedido(LocalDate.of(2026, 5, 13), Estado.TERMINADO, FormaPago.TRANSFERENCIA, usuario2);
        pedido3.addDetallePedido(arroz, 2);
        pedido3.addDetallePedido(fideos, 4);
        pedido3.addDetallePedido(lavandina, 1);

        pedidos.add(pedido1);
        pedidos.add(pedido2);
        pedidos.add(pedido3);

        return new DatosSemilla(usuarios, categorias, productos, pedidos, cafeMolido, almacen);
    }

    private record DatosSemilla(
            Set<Usuario> usuarios,
            Set<Categoria> categorias,
            Set<Producto> productos,
            Set<Pedido> pedidos,
            Producto productoParaMostrar,
            Categoria categoriaAlmacen
    ) {
    }
}
