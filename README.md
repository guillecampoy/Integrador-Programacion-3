# Parcial 2 - Programacion III - JPA

## Proposito del documento

Este `README.md` es el documento de arranque para implementar la segunda entrega del trabajo integrador/parcial de Programacion III sobre Java Persistence API (JPA), repositorios y ABM de Categorias y Productos.

El objetivo es extender el proyecto Gradle existente del TP de la Unidad 8. El codigo base se considera existente y funcional. El desarrollo debe agregar solamente las piezas pedidas por la consigna, respetando los paquetes, entidades, `JPAUtil` y configuracion de persistencia ya provistos.

## Resultado esperado

Al finalizar, el proyecto debe permitir ejecutar una aplicacion de consola que exponga:

1. ABM de Categorias.
2. ABM de Productos.
3. Reporte de productos activos filtrados por categoria usando JPQL.
4. Repositorios JPA reutilizables para `Categoria` y `Producto`.
5. Baja logica mediante el campo `eliminado`.
6. Listados que muestren solo registros activos (`eliminado = false`).

## Restricciones obligatorias

Estas restricciones son de maxima prioridad para el agente de desarrollo:

1. No modificar ninguna clase del TP base.
   - No modificar entidades existentes.
   - No modificar enums existentes.
   - No modificar `JPAUtil.java`.
   - No modificar `persistence.xml`.
2. Crear los archivos nuevos en los paquetes indicados.
3. Mantener el proyecto Gradle compilable y ejecutable.
4. Implementar persistencia con JPA, `EntityManager`, transacciones y JPQL.
5. No reemplazar JPA por colecciones en memoria, JDBC directo, SQL nativo ni frameworks no pedidos.
6. No introducir dependencias nuevas salvo que el proyecto base ya las contemple o sea estrictamente necesario y justificable.
7. No cambiar el modelo de dominio para facilitar la solucion.
8. No cambiar nombres de paquetes, clases o metodos requeridos por la consigna.
9. El metodo `ProductoRepository.buscarPorCategoria(Long categoriaId)` debe tener un comentario que explique que hace la consulta JPQL.
10. Cada metodo de repositorio debe abrir y cerrar su propio `EntityManager`.
11. Todo `EntityManager` abierto debe cerrarse en un bloque `finally`.
12. Toda transaccion de escritura debe hacer `rollback` si ocurre un error antes del `commit`.
13. Las operaciones de menu deben manejar errores de usuario sin romper la aplicacion por una excepcion no controlada.

## Estructura de paquetes requerida

La estructura esperada dentro de `src/main/java/` es:

```text
com.tp.jpa/
  model/             # ya existe; no modificar
  model/enums/       # ya existe; no modificar
  util/              # ya existe; contiene JPAUtil.java; no modificar
  repository/        # nuevo; crear este paquete
    BaseRepository.java
    CategoriaRepository.java
    ProductoRepository.java
  Main.java          # nuevo; clase principal con menu de consola
```

En terminos de paquetes Java:

```text
com.tp.jpa
com.tp.jpa.model
com.tp.jpa.model.enums
com.tp.jpa.util
com.tp.jpa.repository
```

## Archivos nuevos obligatorios

| Archivo | Paquete | Responsabilidad |
|---|---|---|
| `BaseRepository.java` | `com.tp.jpa.repository` | Repositorio generico abstracto `<T>` con operaciones comunes: `guardar`, `buscarPorId`, `listarActivos`, `eliminarLogico`. |
| `CategoriaRepository.java` | `com.tp.jpa.repository` | Repositorio especifico de `Categoria`. Extiende `BaseRepository<Categoria>`. No agrega metodos. |
| `ProductoRepository.java` | `com.tp.jpa.repository` | Repositorio especifico de `Producto`. Extiende `BaseRepository<Producto>` y agrega `buscarPorCategoria(Long categoriaId)`. |
| `Main.java` | `com.tp.jpa` | Aplicacion de consola con menu principal, submenu de Categorias, submenu de Productos y submenu de Reportes. |

## Protocolo recomendado para agente de desarrollo

Antes de implementar cualquier historia, el agente debe inspeccionar el proyecto base:

```bash
find src/main/java -type f | sort
find src/main/resources -type f | sort
cat build.gradle || cat build.gradle.kts
```

Luego revisar especificamente:

```bash
find src/main/java -type f | grep -E 'Categoria|Producto|JPAUtil|persistence'
```

Confirmar:

1. Paquete real de `Categoria` y `Producto`.
2. Nombre exacto de los campos.
3. Tipo de `id`.
4. Tipo de `precio`.
5. Tipo de `stock`.
6. Nombre del getter/setter de `eliminado` (`getEliminado`, `isEliminado`, `setEliminado`, etc.).
7. Si las entidades usan constructores con parametros, constructor vacio, setters o Lombok.
8. Si `createdAt` se inicializa automaticamente en la entidad o requiere seteo manual.
9. Metodo disponible en `JPAUtil` para obtener `EntityManagerFactory`.
10. Forma ya prevista por el proyecto para ejecutar una clase `main`.

Regla de trabajo: implementar una historia por vez, compilar, corregir y recien despues avanzar.

## Orden recomendado de implementacion

1. `HU-01` - BaseRepository generico.
2. `HU-02` - Repositorios especificos de Categoria y Producto.
3. `HU-03` - Alta de categoria.
4. `HU-04` - Modificacion de categoria.
5. `HU-05` - Baja logica de categoria.
6. `HU-06` - Alta de producto.
7. `HU-07` - Modificacion de producto.
8. `HU-08` - Baja logica de producto.
9. `HU-09` - Consulta JPQL de productos por categoria.
10. Validacion final integral y preparacion de entrega.

## Backlog incluido

Las historias estan en la carpeta `historias/`:

| Historia | Archivo | Puntos | Prioridad |
|---|---|---:|---|
| HU-01 | `historias/HU-01-base-repository.md` | 18 | Alta |
| HU-02 | `historias/HU-02-repositorios-especificos.md` | 12 | Alta |
| HU-03 | `historias/HU-03-alta-categoria.md` | 8 | Alta |
| HU-04 | `historias/HU-04-modificar-categoria.md` | 10 | Alta |
| HU-05 | `historias/HU-05-baja-categoria.md` | 7 | Media |
| HU-06 | `historias/HU-06-alta-producto.md` | 12 | Alta |
| HU-07 | `historias/HU-07-modificar-producto.md` | 10 | Alta |
| HU-08 | `historias/HU-08-baja-producto.md` | 8 | Media |
| HU-09 | `historias/HU-09-productos-por-categoria-jpql.md` | 15 | Alta |

Total: 100 puntos. Minimo para aprobar: 60 puntos.

## Contrato tecnico de BaseRepository

`BaseRepository<T>` debe ser una clase abstracta ubicada en `com.tp.jpa.repository`.

Responsabilidades:

1. Recibir `Class<T>` por constructor.
2. Obtener el `EntityManagerFactory` desde `JPAUtil`.
3. Implementar los metodos comunes.
4. Abrir su propio `EntityManager` en cada metodo.
5. Cerrar el `EntityManager` en `finally`.
6. Manejar transacciones en operaciones de escritura.

Firma esperada de metodos:

```java
public T guardar(T entity)
public Optional<T> buscarPorId(Long id)
public List<T> listarActivos()
public boolean eliminarLogico(Long id)
```

### Detalles importantes

- `guardar(T entity)` debe usar `merge()`, no `persist()`.
- `merge()` retorna la instancia administrada. Para mostrar IDs generados en consola, usar el objeto retornado por `guardar`, no asumir que el objeto original ya tiene ID actualizado.
- `buscarPorId(Long id)` debe usar `EntityManager.find(...)` y retornar `Optional.empty()` si no existe.
- `listarActivos()` debe usar JPQL con filtro `eliminado = false`.
- `eliminarLogico(Long id)` debe buscar por ID, marcar `eliminado = true`, persistir el cambio y retornar `true` si encontro el registro. Debe retornar `false` si no encontro el registro.
- Como `T` es generico y no se pueden modificar entidades para que implementen una interfaz comun, es aceptable usar reflexion para invocar `setEliminado(true)` dentro del repositorio base, siempre que sea claro, controlado y con manejo de error.
- Si las entidades tienen un ancestro comun ya existente con `setEliminado`, usarlo solo si ya existe en el codigo base. No crearlo si implica modificar entidades.

## Contrato tecnico de CategoriaRepository

`CategoriaRepository` debe:

1. Estar en `com.tp.jpa.repository`.
2. Extender `BaseRepository<Categoria>`.
3. Llamar a `super(Categoria.class)` en su constructor.
4. No agregar metodos adicionales.

## Contrato tecnico de ProductoRepository

`ProductoRepository` debe:

1. Estar en `com.tp.jpa.repository`.
2. Extender `BaseRepository<Producto>`.
3. Llamar a `super(Producto.class)` en su constructor.
4. Implementar `public List<Producto> buscarPorCategoria(Long categoriaId)`.
5. Usar JPQL, no SQL nativo.
6. Usar parametro nombrado `:categoriaId`.
7. Filtrar productos activos con `eliminado = false`.
8. Usar `TypedQuery<Producto>`.
9. No hacer casteos manuales.
10. Incluir un comentario sobre que hace la consulta JPQL.

JPQL esperada conceptualmente:

```java
SELECT p
FROM Producto p
WHERE p.categoria.id = :categoriaId
  AND p.eliminado = false
```

Si la entidad usa un nombre JPA personalizado con `@Entity(name = "...")`, ajustar el nombre de entidad en JPQL respetando el modelo existente.

## Contrato de Main

`Main.java` debe estar en `com.tp.jpa` y exponer un menu principal de consola.

Menu principal sugerido:

```text
=== Sistema JPA - Categorias y Productos ===
1. Categorias
2. Productos
3. Reportes
0. Salir
Seleccione una opcion:
```

Submenu de Categorias:

```text
=== Categorias ===
1. Alta de categoria
2. Modificar categoria
3. Baja logica de categoria
4. Listar categorias activas
0. Volver
```

Submenu de Productos:

```text
=== Productos ===
1. Alta de producto
2. Modificar producto
3. Baja logica de producto
4. Listar productos activos
0. Volver
```

Submenu de Reportes:

```text
=== Reportes ===
1. Productos por categoria
0. Volver
```

El orden exacto puede variar, pero todas las opciones deben ser accesibles desde el menu principal.

## Validaciones globales de consola

1. No aceptar nombre vacio para categoria.
2. No aceptar precio menor o igual a 0.
3. No aceptar stock menor a 0.
4. En modificacion, un campo de texto vacio conserva el valor anterior.
5. En modificacion, un campo numerico vacio conserva el valor anterior.
6. Si el usuario ingresa un numero invalido, informar el error y no persistir cambios parciales.
7. Si el usuario selecciona un ID inexistente, informar el error.
8. Si el usuario selecciona un registro dado de baja, informar el error cuando la operacion requiera un activo.
9. Si no hay categorias activas, no permitir alta de producto ni reporte por categoria.
10. Si una categoria no tiene productos activos, informar explicitamente.

Sugerencia tecnica: leer entradas con `Scanner.nextLine()` y parsear manualmente. Esto evita problemas comunes al mezclar `nextInt()`/`nextDouble()` con `nextLine()`.

## Salidas minimas esperadas

### Categoria

Listado de categorias activas:

```text
ID: 1 | Nombre: Bebidas | Descripcion: Bebidas frias y calientes
```

Alta exitosa:

```text
Categoria creada correctamente. ID generado: 1
```

Baja exitosa:

```text
Categoria dada de baja correctamente: Bebidas
```

### Producto

Listado de productos activos:

```text
ID: 10 | Nombre: Cafe | Precio: 1500.00 | Stock: 20 | Categoria: Bebidas
```

Alta exitosa:

```text
Producto creado correctamente. ID generado: 10 | Categoria: Bebidas
```

Baja exitosa:

```text
Producto dado de baja correctamente: Cafe
```

### Reporte por categoria

```text
Productos activos de la categoria Bebidas:
ID: 10 | Nombre: Cafe | Precio: 1500.00 | Stock: 20
```

Si no hay resultados:

```text
No hay productos activos para la categoria seleccionada.
```

## Matriz de evaluacion

| HU | Item evaluado | Descripcion | Puntaje |
|---|---|---|---:|
| HU-01 | BaseRepository<T> | CRUD generico correcto, transacciones, Optional, cierre de EntityManager. | 18 |
| HU-02 | CategoriaRepo / ProductoRepo | Extension correcta, `super()` con `Class<T>`, `buscarPorCategoria` con JPQL. | 12 |
| HU-03 | Alta de categoria | Validacion de nombre, persistencia, ID visible. | 8 |
| HU-04 | Modificacion de categoria | Listado previo, error en ID invalido, campo vacio conserva valor. | 10 |
| HU-05 | Baja de categoria | Baja logica, error en ID invalido, no aparece en listados. | 7 |
| HU-06 | Alta de producto | Listado de categorias, validacion precio/stock, relacion `@ManyToOne` resuelta. | 12 |
| HU-07 | Modificacion de producto | Valores actuales visibles, validaciones de precio y stock. | 10 |
| HU-08 | Baja de producto | Baja logica, mensaje con nombre, error en ID invalido. | 8 |
| HU-09 | Consulta JPQL | JPQL correcto, `TypedQuery<Producto>`, parametro nombrado, sin casteos. | 15 |

## Pruebas manuales integrales

Ejecutar este flujo completo antes de entregar:

1. Compilar el proyecto.
2. Ejecutar `Main`.
3. Crear una categoria con nombre y descripcion.
4. Confirmar que se muestra el ID generado.
5. Listar categorias y confirmar que aparece activa.
6. Modificar nombre o descripcion de la categoria.
7. Dejar un campo vacio y confirmar que conserva el valor anterior.
8. Crear un producto seleccionando la categoria activa.
9. Validar que precio `0`, precio negativo y texto no numerico no persisten.
10. Validar que stock negativo y texto no numerico no persisten.
11. Confirmar que se muestra el ID generado del producto y la categoria asignada.
12. Listar productos activos y confirmar que aparece ID, nombre, precio, stock y categoria.
13. Ejecutar reporte `Productos por categoria` y confirmar que muestra el producto.
14. Dar de baja logicamente el producto.
15. Listar productos activos y confirmar que ya no aparece.
16. Ejecutar nuevamente reporte `Productos por categoria` y confirmar que ya no aparece el producto dado de baja.
17. Dar de baja logicamente una categoria.
18. Listar categorias activas y confirmar que ya no aparece.
19. Intentar modificar o dar de baja IDs inexistentes y confirmar mensajes de error.
20. Salir del sistema sin excepciones.

## Comandos de verificacion

Usar los comandos que correspondan al proyecto base. Como referencia:

```bash
./gradlew clean build
```

Para ejecutar, usar la configuracion existente del proyecto. Posibles alternativas:

```bash
./gradlew run
```

O ejecutar desde el IDE la clase:

```text
com.tp.jpa.Main
```

No tocar `build.gradle` salvo que el proyecto base ya lo requiera para ejecutar el `main` y sea imprescindible. Si se modifica, debe ser un cambio minimo y documentado.

## Condiciones de entrega

La entrega final de la materia debe cumplir:

1. Proyecto Gradle completo comprimido en `.zip`.
2. Nombre del zip segun consigna institucional. El PDF menciona dos variantes:
   - `Apellido_Nombre_ParcialJPA.zip`
   - `Nombre_Alumno_parcial_2.zip`
   Confirmar en el aula virtual cual formato prefiere el docente y usar ese.
3. Proyecto funcional y ejecutable.
4. Sin errores de compilacion. Cada error de compilacion descuenta puntos.
5. Incluir README del proyecto con descripcion breve e instrucciones de ejecucion.
6. Dejar en comentarios de la entrega el link al video.
7. Video obligatorio entre 10 y 15 minutos.
8. Camara encendida durante toda la exposicion.
9. Audio claro y comprensible.

## Guion minimo del video obligatorio

El video debe mostrar:

1. Presentacion breve del alumno.
2. Funcionamiento de la aplicacion.
3. Generacion de una categoria.
4. Creacion de productos asociados a categoria.
5. Consulta de productos por ID/categoria.
6. Eliminacion logica de un producto de la categoria.
7. Nueva consulta de productos por categoria para comprobar que el producto eliminado ya no se muestra.
8. Explicacion de funcionalidades implementadas.
9. Explicacion del abordaje tecnico.
10. Dificultades encontradas y como se resolvieron.

## Prompt inicial sugerido para agente

Usar este prompt al iniciar el trabajo en el repositorio:

```text
Lee README.md completo y luego implementa la historia indicada en historias/<archivo>.md.
Trabaja una historia por vez.
No modifiques entidades, enums, JPAUtil ni persistence.xml.
Respeta los paquetes existentes del proyecto base.
Antes de modificar codigo, inspecciona las entidades Categoria y Producto, JPAUtil y build.gradle.
Despues de implementar, ejecuta compilacion Gradle.
Entrega un resumen de cambios, archivos tocados, validaciones realizadas y riesgos pendientes.
No hagas commit automaticamente.
```

## Definicion de terminado global

El desarrollo se considera terminado cuando:

1. Existen los 4 archivos Java nuevos pedidos.
2. Los paquetes coinciden con la consigna.
3. El proyecto compila sin errores.
4. La aplicacion de consola ejecuta sin romperse.
5. Todas las opciones de menu son accesibles desde el menu principal.
6. Los listados muestran solo registros activos.
7. La baja logica oculta registros de listados y reportes.
8. `buscarPorCategoria` usa JPQL tipado con parametro nombrado `:categoriaId`.
9. La consulta JPQL no usa casteos manuales.
10. Las validaciones obligatorias funcionan.
11. La entrega puede demostrarse en video siguiendo el flujo requerido.
