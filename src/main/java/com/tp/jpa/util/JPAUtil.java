package com.tp.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JPAUtil {
  private static EntityManagerFactory entityManagerFactory;

  private JPAUtil() {}

  public static synchronized EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
      entityManagerFactory = crearEntityManagerFactory();
    }
    return entityManagerFactory;
  }

  public static synchronized void close() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
    entityManagerFactory = null;
  }

  private static EntityManagerFactory crearEntityManagerFactory() {
    silenciarHibernate();
    Map<String, String> propiedades = new HashMap<>();
    propiedades.put("hibernate.show_sql", "false");
    propiedades.put("hibernate.format_sql", "false");
    propiedades.put("hibernate.use_sql_comments", "false");
    agregarPropiedadSiExiste(propiedades, "jakarta.persistence.jdbc.url", "com.tp.jpa.jdbc.url");
    agregarPropiedadSiExiste(propiedades, "hibernate.hbm2ddl.auto", "com.tp.jpa.hbm2ddl.auto");

    return Persistence.createEntityManagerFactory("miUnidad", propiedades);
  }

  private static void agregarPropiedadSiExiste(
      Map<String, String> propiedades, String nombreJpa, String nombreSistema) {
    String valor = System.getProperty(nombreSistema);
    if (valor != null && !valor.isBlank()) {
      propiedades.put(nombreJpa, valor);
    }
  }

  public static void silenciarHibernate() {
    Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
    Logger.getLogger("org.hibernate.orm.jdbc.bind").setLevel(Level.OFF);
    Logger.getLogger("org.hibernate.orm.connections.pooling").setLevel(Level.OFF);
    Logger.getLogger("org.hibernate.engine.jdbc.connections").setLevel(Level.OFF);
  }
}
