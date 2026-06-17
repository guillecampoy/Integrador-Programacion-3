# HU-01 - Repositorio generico con CRUD

## Metadata

- ID: HU-01
- Titulo: Repositorio generico con CRUD
- Prioridad: Alta
- Story Points: 18
- Area: Repositorios / Persistencia JPA
- Archivo principal a crear: `src/main/java/com/tp/jpa/repository/BaseRepository.java`

## Historia de usuario

Como desarrollador, quiero contar con un `BaseRepository<T>` que implemente las operaciones CRUD comunes para no repetir codigo de persistencia en cada repositorio especifico.

## Contexto

El proyecto base ya contiene entidades JPA, enums, `JPAUtil` y `persistence.xml`. Esta historia agrega una capa de repositorio generica. No se deben modificar las entidades ni la configuracion de persistencia.

`BaseRepository<T>` debe ser abstracto y reutilizable por `CategoriaRepository` y `ProductoRepository`.

## Alcance funcional

Implementar estos metodos:

```java
public T guardar(T entity)
public Optional<T> buscarPorId(Long id)
public List<T> listarActivos()
public boolean eliminarLogico(Long id)
```

## Alcance tecnico

Crear clase:

```text
src/main/java/com/tp/jpa/repository/BaseRepository.java
```

Paquete esperado:

```java
package com.tp.jpa.repository;
```

La clase debe:

1. Ser `abstract`.
2. Ser generica: `BaseRepository<T>`.
3. Recibir `Class<T>` por constructor.
4. Guardar internamente esa `Class<T>` para operaciones `find` y JPQL tipada.
5. Obtener `EntityManagerFactory` desde `JPAUtil`.
6. No crear un `EntityManagerFactory` propio usando `Persistence.createEntityManagerFactory(...)`.
7. Abrir un `EntityManager` nuevo por cada metodo.
8. Cerrar el `EntityManager` en `finally`.

## Reglas por metodo

### `guardar(T entity)`

Debe:

1. Abrir `EntityManager`.
2. Iniciar transaccion.
3. Persistir o actualizar usando `merge(entity)`.
4. Hacer `commit`.
5. Devolver la entidad retornada por `merge`, no necesariamente la instancia recibida.
6. Si ocurre error, hacer `rollback` si la transaccion esta activa.
7. Relanzar la excepcion o envolverla en una excepcion clara.
8. Cerrar `EntityManager` en `finally`.

Detalle clave: para altas con ID autogenerado, `merge` devuelve la instancia administrada que normalmente contiene el ID generado. El menu debe usar el retorno de `guardar` para mostrar el ID.

### `buscarPorId(Long id)`

Debe:

1. Abrir `EntityManager`.
2. Usar `entityManager.find(entityClass, id)`.
3. Retornar `Optional.of(entidad)` si existe.
4. Retornar `Optional.empty()` si no existe.
5. No requiere transaccion de escritura.
6. Cerrar `EntityManager` en `finally`.

### `listarActivos()`

Debe:

1. Abrir `EntityManager`.
2. Usar JPQL.
3. Filtrar por `eliminado = false`.
4. Retornar `List<T>`.
5. Usar consulta tipada si es posible.
6. Cerrar `EntityManager` en `finally`.

JPQL conceptual:

```java
SELECT e FROM <Entidad> e WHERE e.eliminado = false
```

Consideraciones:

- Si las entidades no tienen `@Entity(name = "...")`, el nombre JPQL suele ser `entityClass.getSimpleName()`.
- Si alguna entidad tiene nombre JPA personalizado, usar ese nombre.
- No usar SQL nativo.

### `eliminarLogico(Long id)`

Debe:

1. Abrir `EntityManager`.
2. Iniciar transaccion.
3. Buscar la entidad por ID.
4. Si no existe, retornar `false`.
5. Si existe, establecer `eliminado = true`.
6. Persistir el cambio.
7. Hacer `commit`.
8. Retornar `true`.
9. Si ocurre error, hacer `rollback` si la transaccion esta activa.
10. Cerrar `EntityManager` en `finally`.

Como `T` es generico y no se pueden modificar entidades para agregar una interfaz, resolver la marca de `eliminado` de una de estas formas, en este orden de preferencia:

1. Si ya existe una superclase comun en el modelo base con `setEliminado`, usarla.
2. Si no existe, usar reflexion controlada para invocar `setEliminado(true)`.
3. Si el setter espera `Boolean`, pasar `Boolean.TRUE`.
4. Si el setter espera `boolean`, pasar `true`.
5. No modificar las entidades para forzar una interfaz nueva.

## Criterios de aceptacion

1. `guardar()` abre su propia transaccion, persiste con `merge()` y cierra el `EntityManager`.
2. `guardar()` hace `rollback` ante error.
3. `guardar()` devuelve la entidad resultante de `merge()`.
4. `buscarPorId()` retorna `Optional<T>`.
5. `buscarPorId()` retorna `Optional.of(entidad)` si existe.
6. `buscarPorId()` retorna `Optional.empty()` si no existe.
7. `listarActivos()` usa JPQL con `WHERE e.eliminado = false`.
8. `listarActivos()` retorna `List<T>`.
9. `eliminarLogico()` busca por ID.
10. `eliminarLogico()` establece `eliminado = true`.
11. `eliminarLogico()` persiste el cambio.
12. `eliminarLogico()` retorna `true` si encontro el registro.
13. `eliminarLogico()` retorna `false` si no encontro el registro.
14. Cada metodo abre su propio `EntityManager`.
15. Cada metodo cierra el `EntityManager` en `finally`.
16. El codigo compila sin modificar entidades, enums, `JPAUtil` ni `persistence.xml`.

## Fuera de alcance

No implementar en esta historia:

1. `CategoriaRepository`.
2. `ProductoRepository`.
3. Menus de consola.
4. Validaciones de entrada por consola.
5. Consultas especificas de producto por categoria.

## Validaciones tecnicas sugeridas

Ejecutar:

```bash
./gradlew clean build
```

Si el proyecto no tiene wrapper Gradle, usar el comando equivalente disponible.

## Riesgos y notas para el agente

1. Confirmar si el proyecto usa `jakarta.persistence` o `javax.persistence`; usar el mismo namespace que el proyecto base.
2. Confirmar el metodo real de `JPAUtil` para obtener el `EntityManagerFactory`.
3. Confirmar si el campo se llama exactamente `eliminado`.
4. Confirmar si el setter es `setEliminado(boolean)` o `setEliminado(Boolean)`.
5. No silenciar errores de persistencia; deben ser visibles para depuracion.
6. No dejar transacciones abiertas.
7. No dejar `EntityManager` abierto.

## Checklist de terminado

- [ ] Existe `BaseRepository.java` en el paquete correcto.
- [ ] La clase es abstracta y generica.
- [ ] Recibe `Class<T>` por constructor.
- [ ] Usa `JPAUtil`.
- [ ] `guardar` usa `merge`.
- [ ] `buscarPorId` usa `Optional`.
- [ ] `listarActivos` usa JPQL.
- [ ] `eliminarLogico` marca `eliminado = true`.
- [ ] Hay rollback ante errores de escritura.
- [ ] Todos los `EntityManager` se cierran en `finally`.
- [ ] Compila sin modificar codigo base.

## Prompt sugerido para agente

```text
Implementa la historia HU-01. Crea BaseRepository<T> en com.tp.jpa.repository con guardar, buscarPorId, listarActivos y eliminarLogico. Antes de escribir codigo, inspecciona JPAUtil y las entidades para confirmar imports jakarta/javax, metodo de EntityManagerFactory y setter de eliminado. No modifiques entidades, enums, JPAUtil ni persistence.xml. Al finalizar ejecuta compilacion Gradle y reporta archivos creados, decisiones tecnicas y riesgos.
```
