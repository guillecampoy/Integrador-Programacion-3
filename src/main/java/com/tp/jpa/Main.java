package com.tp.jpa;

import ar.edu.tup.programacion3.entities.Categoria;
import ar.edu.tup.programacion3.utils.EntradaValidada;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;

import static ar.edu.tup.programacion3.utils.ConsolaUtils.SEPARADOR;
import static ar.edu.tup.programacion3.utils.ConsolaUtils.imprimirError;
import static ar.edu.tup.programacion3.utils.ConsolaUtils.imprimirMensaje;
import static ar.edu.tup.programacion3.utils.ConsolaUtils.imprimirOpcion;
import static ar.edu.tup.programacion3.utils.ConsolaUtils.imprimirTitulo;
import static ar.edu.tup.programacion3.utils.ConsolaUtils.prompt;

public class Main {
    private final EntradaValidada entrada;
    private final CategoriaRepository categoriaRepository;

    public Main(EntradaValidada entrada, CategoriaRepository categoriaRepository) {
        this.entrada = entrada;
        this.categoriaRepository = categoriaRepository;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(new EntradaValidada(scanner), new CategoriaRepository()).ejecutar();
        } finally {
            JPAUtil.close();
        }
    }

    private void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = entrada.leerOpcion(prompt("Seleccione una opcion"), Set.of("0", "1"));
            switch (opcion) {
                case "1" -> menuCategorias();
                case "0" -> salir = true;
                default -> imprimirError("Opcion invalida.");
            }
        }
    }

    private void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            mostrarMenuCategorias();
            String opcion = entrada.leerOpcion(prompt("Seleccione una opcion"), Set.of("0", "1"));
            switch (opcion) {
                case "1" -> altaCategoria();
                case "0" -> volver = true;
                default -> imprimirError("Opcion invalida.");
            }
        }
    }

    private void altaCategoria() {
        imprimirTitulo("Alta de categoria");
        String nombre = entrada.leerTexto(
                prompt("Nombre"),
                texto -> !texto.isBlank(),
                "Error: el nombre de la categoria es obligatorio. No se guardo la categoria."
        );
        String descripcion = entrada.leerTexto(
                prompt("Descripcion"),
                texto -> !texto.isBlank(),
                "Error: la descripcion de la categoria es obligatoria para el modelo actual."
        );

        try {
            Categoria categoria = new Categoria();
            categoria.setId(generarId());
            categoria.setNombre(nombre.trim());
            categoria.setDescripcion(descripcion.trim());
            categoria.setEliminado(false);
            categoria.setCreatedAt(LocalDateTime.now());

            Categoria guardada = categoriaRepository.guardar(categoria);
            imprimirMensaje("Categoria creada correctamente. ID generado: " + guardada.getId());
        } catch (RuntimeException exception) {
            imprimirError("No se guardo la categoria: " + exception.getMessage());
        }
    }

    private long generarId() {
        return System.currentTimeMillis();
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println(SEPARADOR);
        System.out.println("Sistema JPA - Categorias y Productos");
        System.out.println(SEPARADOR);
        imprimirOpcion("1", "Categorias");
        imprimirOpcion("0", "Salir");
        System.out.println(SEPARADOR);
    }

    private void mostrarMenuCategorias() {
        System.out.println();
        System.out.println(SEPARADOR);
        System.out.println("Categorias");
        System.out.println(SEPARADOR);
        imprimirOpcion("1", "Alta de categoria");
        imprimirOpcion("0", "Volver");
        System.out.println(SEPARADOR);
    }
}
