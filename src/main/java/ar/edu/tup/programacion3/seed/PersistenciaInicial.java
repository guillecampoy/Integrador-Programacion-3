package ar.edu.tup.programacion3.seed;

import ar.edu.tup.programacion3.entities.Categoria;
import ar.edu.tup.programacion3.entities.Pedido;
import ar.edu.tup.programacion3.entities.Producto;
import ar.edu.tup.programacion3.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class PersistenciaInicial implements AutoCloseable {
    private static final Path BASE_LOCAL = Path.of("data", "jpa_db.mv.db");
    private static final List<Path> ARCHIVOS_BASE_LOCAL = List.of(
            Path.of("data", "jpa_db.mv.db"),
            Path.of("data", "jpa_db.trace.db"),
            Path.of("data", "jpa_db.lock.db")
    );
    private static final String UNIDAD_PERSISTENCIA = "miUnidad";

    private final EntityManagerFactory entityManagerFactory;
    private final boolean baseLocalExistia;
    private boolean datosInicialesPersistidos;

    private PersistenciaInicial(Path baseLocal, List<Path> archivosBaseLocal, String jdbcUrl) {
        this.baseLocalExistia = existeBaseLocal(baseLocal);
        this.entityManagerFactory = Persistence.createEntityManagerFactory(
                UNIDAD_PERSISTENCIA,
                Map.of("jakarta.persistence.jdbc.url", jdbcUrl)
        );
    }

    public static PersistenciaInicial inicializar() {
        PersistenciaInicial persistenciaInicial = new PersistenciaInicial(
                BASE_LOCAL,
                ARCHIVOS_BASE_LOCAL,
                "jdbc:h2:file:./data/jpa_db;AUTO_SERVER=TRUE"
        );
        persistenciaInicial.persistirDatosInicialesSiCorresponde();
        return persistenciaInicial;
    }

    static PersistenciaInicial inicializar(Path baseLocal, List<Path> archivosBaseLocal, String jdbcUrl) {
        PersistenciaInicial persistenciaInicial = new PersistenciaInicial(baseLocal, archivosBaseLocal, jdbcUrl);
        persistenciaInicial.persistirDatosInicialesSiCorresponde();
        return persistenciaInicial;
    }

    public static boolean existeBaseLocal() {
        return existeBaseLocal(BASE_LOCAL);
    }

    public static void borrarBaseLocal() {
        ARCHIVOS_BASE_LOCAL.forEach(PersistenciaInicial::borrarSiExiste);
    }

    public boolean isBaseLocalExistia() {
        return baseLocalExistia;
    }

    public boolean isDatosInicialesPersistidos() {
        return datosInicialesPersistidos;
    }

    public ResumenPersistencia contarDatos() {
        return consultar(entityManager -> new ResumenPersistencia(
                contar(entityManager, Usuario.class),
                contar(entityManager, Categoria.class),
                contar(entityManager, Producto.class),
                contar(entityManager, Pedido.class)
        ));
    }

    private void persistirDatosInicialesSiCorresponde() {
        if (!debePersistirDatosIniciales()) {
            return;
        }

        DatosSemilla datosSemilla = DatosSemillaFactory.crear();
        ejecutarEnTransaccion(entityManager -> {
            datosSemilla.categorias().forEach(entityManager::persist);
            datosSemilla.usuarios().forEach(entityManager::persist);
        });
        datosInicialesPersistidos = true;
    }

    private boolean debePersistirDatosIniciales() {
        ResumenPersistencia resumen = contarDatos();
        return !baseLocalExistia || resumen.totalRegistros() == 0;
    }

    private static boolean existeBaseLocal(Path baseLocal) {
        return Files.exists(baseLocal);
    }

    private long contar(EntityManager entityManager, Class<?> entityClass) {
        return entityManager.createQuery(
                        "select count(e) from " + entityClass.getSimpleName() + " e",
                        Long.class
                )
                .getSingleResult();
    }

    private void ejecutarEnTransaccion(Consumer<EntityManager> consumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            consumer.accept(entityManager);
            entityManager.getTransaction().commit();
        } catch (RuntimeException exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    private <T> T consultar(Function<EntityManager, T> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void close() {
        entityManagerFactory.close();
    }

    private static void borrarSiExiste(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo borrar " + path, exception);
        }
    }

    public record ResumenPersistencia(long usuarios, long categorias, long productos, long pedidos) {
        public long totalRegistros() {
            return usuarios + categorias + productos + pedidos;
        }
    }
}
