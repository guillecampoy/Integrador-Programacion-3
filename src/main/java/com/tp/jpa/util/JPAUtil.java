package com.tp.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JPAUtil {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = crearEntityManagerFactory();

    private JPAUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    private static EntityManagerFactory crearEntityManagerFactory() {
        silenciarHibernate();
        return Persistence.createEntityManagerFactory(
                "miUnidad",
                Map.of(
                        "hibernate.show_sql", "false",
                        "hibernate.format_sql", "false",
                        "hibernate.use_sql_comments", "false"
                )
        );
    }

    private static void silenciarHibernate() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.orm.jdbc.bind").setLevel(Level.OFF);
    }
}
