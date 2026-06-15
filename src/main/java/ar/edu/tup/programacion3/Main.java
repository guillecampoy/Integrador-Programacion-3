package ar.edu.tup.programacion3;

import ar.edu.tup.programacion3.seed.PersistenciaInicial;

import java.util.Scanner;

public class Main {
    private static final String JDBC_H2_LOCAL = "jdbc:h2:file:./data/jpa_db;AUTO_SERVER=TRUE";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PersistenciaInicial persistenciaInicial = PersistenciaInicial.inicializar();
            H2LocalConsole h2LocalConsole = H2LocalConsole.iniciar();

            mostrarEstado(persistenciaInicial, h2LocalConsole);
            boolean salir = false;
            while (!salir) {
                mostrarMenu();
                if (!scanner.hasNextLine()) {
                    break;
                }
                String opcion = scanner.nextLine().trim();

                switch (opcion) {
                    case "1" -> mostrarEstado(persistenciaInicial, h2LocalConsole);
                    case "2" -> {
                        h2LocalConsole.close();
                        persistenciaInicial.close();
                        PersistenciaInicial.borrarBaseLocal();
                        persistenciaInicial = PersistenciaInicial.inicializar();
                        h2LocalConsole = H2LocalConsole.iniciar();
                        System.out.println("Base local borrada y semilla persistida nuevamente.");
                        mostrarEstado(persistenciaInicial, h2LocalConsole);
                    }
                    case "0" -> salir = true;
                    default -> System.out.println("Opcion invalida.");
                }
            }

            h2LocalConsole.close();
            persistenciaInicial.close();
        }
    }

    private static void mostrarEstado(PersistenciaInicial persistenciaInicial, H2LocalConsole h2LocalConsole) {
        PersistenciaInicial.ResumenPersistencia resumen = persistenciaInicial.contarDatos();

        System.out.println();
        System.out.println("=== TP JPA ===");
        System.out.println("BD local existente al iniciar: " + (persistenciaInicial.isBaseLocalExistia() ? "si" : "no"));
        System.out.println("Datos iniciales persistidos: " + (persistenciaInicial.isDatosInicialesPersistidos() ? "si" : "no"));
        System.out.println("Usuarios: " + resumen.usuarios());
        System.out.println("Categorias: " + resumen.categorias());
        System.out.println("Productos: " + resumen.productos());
        System.out.println("Pedidos: " + resumen.pedidos());
        System.out.println("Consola H2: " + h2LocalConsole.getUrl());
        System.out.println("JDBC URL: " + JDBC_H2_LOCAL);
        System.out.println("Usuario: sa");
        System.out.println("Password: <vacio>");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("1 - Mostrar estado");
        System.out.println("2 - Borrar base local y reinstanciar semilla");
        System.out.println("0 - Salir");
        System.out.print("Opcion: ");
    }
}
