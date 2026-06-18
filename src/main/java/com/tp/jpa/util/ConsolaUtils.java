package com.tp.jpa.util;

import java.util.List;

public final class ConsolaUtils {
  public static final String SEPARADOR =
      "------------------------------------------------------------";

  private static final String PREFIJO_MENSAJE = "> ";
  private static final String PREFIJO_ERROR = "  ! ";

  private ConsolaUtils() {}

  public static void imprimirTitulo(String titulo) {
    System.out.println();
    System.out.println(SEPARADOR);
    System.out.println(titulo);
    System.out.println(SEPARADOR);
  }

  public static void imprimirSubtitulo(String titulo) {
    System.out.println();
    System.out.println(titulo);
    System.out.println(SEPARADOR);
  }

  public static void imprimirOpcion(String opcion, String descripcion) {
    System.out.printf("  %s) %-50s%n", opcion, descripcion);
  }

  public static void imprimirDato(String etiqueta, Object valor) {
    System.out.printf("  %-35s %s%n", etiqueta + ":", valor);
  }

  public static void imprimirTabla(String[] encabezados, List<String[]> filas) {
    int[] anchos = new int[encabezados.length];
    for (int i = 0; i < encabezados.length; i++) {
      anchos[i] = encabezados[i].length();
    }
    for (String[] fila : filas) {
      for (int i = 0; i < encabezados.length; i++) {
        anchos[i] = Math.max(anchos[i], texto(fila[i]).length());
      }
    }

    imprimirLineaTabla(anchos);
    imprimirFilaTabla(encabezados, anchos);
    imprimirLineaTabla(anchos);
    for (String[] fila : filas) {
      imprimirFilaTabla(fila, anchos);
    }
    imprimirLineaTabla(anchos);
  }

  public static void imprimirMensaje(String mensaje) {
    System.out.println(PREFIJO_MENSAJE + mensaje);
  }

  public static void imprimirError(String mensaje) {
    System.out.println(PREFIJO_ERROR + mensaje);
  }

  public static String prompt(String etiqueta) {
    return PREFIJO_MENSAJE + etiqueta + ": ";
  }

  private static void imprimirLineaTabla(int[] anchos) {
    System.out.print("  +");
    for (int ancho : anchos) {
      System.out.print("-".repeat(ancho + 2) + "+");
    }
    System.out.println();
  }

  private static void imprimirFilaTabla(String[] valores, int[] anchos) {
    System.out.print("  |");
    for (int i = 0; i < anchos.length; i++) {
      System.out.printf(" %-" + anchos[i] + "s |", texto(valores[i]));
    }
    System.out.println();
  }

  private static String texto(Object valor) {
    return valor == null ? "" : valor.toString();
  }
}
