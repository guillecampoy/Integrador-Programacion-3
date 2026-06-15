package ar.edu.tup.programacion3.seed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersistenciaInicialTest {
    @TempDir
    private Path tempDir;

    @Test
    void creaBaseLocalYPersisteSemillaCuandoNoExiste() {
        Path baseSinExtension = tempDir.resolve("jpa_db");
        Path archivoBase = tempDir.resolve("jpa_db.mv.db");
        List<Path> archivosBase = List.of(
                archivoBase,
                tempDir.resolve("jpa_db.trace.db"),
                tempDir.resolve("jpa_db.lock.db")
        );
        String jdbcUrl = "jdbc:h2:file:" + baseSinExtension.toAbsolutePath();

        assertFalse(Files.exists(archivoBase));

        try (PersistenciaInicial persistenciaInicial = PersistenciaInicial.inicializar(
                archivoBase,
                archivosBase,
                jdbcUrl
        )) {
            PersistenciaInicial.ResumenPersistencia resumen = persistenciaInicial.contarDatos();

            assertFalse(persistenciaInicial.isBaseLocalExistia());
            assertTrue(persistenciaInicial.isDatosInicialesPersistidos());
            assertEquals(2L, resumen.usuarios());
            assertEquals(3L, resumen.categorias());
            assertEquals(10L, resumen.productos());
            assertEquals(3L, resumen.pedidos());
        }

        assertTrue(Files.exists(archivoBase));
    }
}
