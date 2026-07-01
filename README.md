# Programacion III - Backend JPA

Backend de consola para el TPI de Programacion III con Java, Gradle, JPA/Hibernate y H2 en archivo.

La referencia de entrega para este proyecto es `docs/TPI.pdf`, contrastada con `docs/diagrama.puml` para preservar las relaciones del dominio. En este repo se implementa el backend evaluable y, aparte, se conservan algunas utilidades de prueba y recuperacion que no cambian la consigna principal.

## Requerido por la rubrica

### Dominio

1. `Base` centraliza `id`, `eliminado` y `createdAt`.
2. `Categoria`, `Producto`, `Usuario`, `Pedido` y `DetallePedido` conforman el modelo persistente.
3. `Pedido` implementa `Calculable` y compone sus detalles.
4. `Producto` referencia una `Categoria`.
5. `Usuario` referencia sus `Pedido`.
6. `DetallePedido` referencia `Producto`.

### Repositorios

1. `BaseRepository<T>` implementa `guardar`, `buscarPorId`, `listarActivos` y `eliminarLogico`.
2. `CategoriaRepository` solo extiende la base.
3. `ProductoRepository.buscarPorCategoria(Long)` usa JPQL tipado con `:catId`.
4. `UsuarioRepository.buscarPorMail(String)` usa JPQL tipado con `:mail` y retorna `Optional<Usuario>`.
5. `PedidoRepository.buscarPorUsuario(Long)` y `buscarPorEstado(Estado)` usan JPQL tipado.
6. `total facturado` se calcula solo con pedidos `TERMINADO` activos; no suma `PENDIENTE`, `CONFIRMADO`, `CANCELADO` ni eliminados.

### Consola

1. El menu principal queda ordenado como `Categorias`, `Productos`, `Usuarios`, `Pedidos`, `Reportes`.
2. El submenu de categorias cubre alta, modificacion, baja logica y listado.
3. El submenu de productos cubre alta, modificacion, baja logica y listado.
4. El submenu de usuarios cubre alta, modificacion, baja logica y busqueda por mail.
5. El submenu de pedidos cubre alta con detalles, cambio de estado y baja logica.
6. El submenu de reportes cubre productos por categoria, pedidos por usuario, pedidos por estado y total facturado.

### Validacion

1. La operacion de alta de pedido corre en una transaccion unica.
2. Las bajas son logicas.
3. Los campos en blanco en modificaciones conservan el valor anterior.
4. Al salir de la aplicacion se cierra `JPAUtil`.

## Consideraciones adicionales

Estas opciones no forman parte del recorrido evaluable principal, pero se mantienen porque ayudan a probar el sistema sin tocar el contrato del modelo:

1. `Regenerar datos`.
2. `Revertir baja logica de categoria`.
3. `Revertir baja logica de producto`.

El menu las muestra separadas del bloque requerido, con una subtitulo propio, para que quede claro que son auxiliares de prueba y no cambian el orden de la entrega.

### Criterio aplicado

1. La busqueda de usuario por mail se dejo exacta porque eso es lo que pide la rubrica; la coincidencia parcial quedo solo como posible ampliacion futura.
2. El menu principal se reordeno para que la ruta evaluable quede primero y las utilidades de prueba queden al final.
3. El listado de productos agrega `disponible`; el reporte por categoria no arrastra columnas extra.
4. Las opciones de restauracion y regeneracion se conservaron porque facilitan las pruebas manuales, pero no reemplazan el recorrido principal de la entrega.
5. `docs/TPI.pdf` si explicita el origen del calculo: `Total facturado` toma `pedidoRepo.buscarPorEstado(Estado.TERMINADO)` y suma sus totales, filtrando `eliminado = false`.

## Estructura actual

```text
src/main/java/com/tp/jpa/
  Main.java
  H2LocalConsole.java
  model/
    Base.java
    Categoria.java
    Producto.java
    Usuario.java
    Pedido.java
    DetallePedido.java
    Calculable.java
    enums/
      Estado.java
      FormaPago.java
      Rol.java
  repository/
    BaseRepository.java
    CategoriaRepository.java
    ProductoRepository.java
    UsuarioRepository.java
    PedidoRepository.java
  service/
    CatalogoService.java
  seed/
    DatosSemilla.java
    DatosSemillaFactory.java
    PersistenciaInicial.java
  util/
    JPAUtil.java
    ConsolaUtils.java
    EntradaValidada.java
    ManejoErroresConsola.java

src/test/java/com/tp/jpa/
  MainTest.java
  integration/JpaIntegrationTest.java
  model/RelacionesTest.java
  repository/CategoriaRepositoryTest.java
  repository/ProductoRepositoryTest.java
  repository/PedidoRepositoryTest.java
  repository/UsuarioRepositoryTest.java
  seed/PersistenciaInicialTest.java
  service/CatalogoServiceTest.java
  service/CatalogoServicePedidosTest.java
  util/EntradaValidadaTest.java
  util/ManejoErroresConsolaTest.java
```

## Validacion

Los comandos que se usan como cierre son:

```bash
./gradlew test
./gradlew check
```

`test` valida el comportamiento funcional. `check` agrega Spotless y confirma que el cierre no deja desalineaciones de formato.
