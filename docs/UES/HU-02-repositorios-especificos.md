# HU-02 - Repositorios especificos de Categoria y Producto

## Metadata

- ID: HU-02
- Titulo: Repositorios especificos de Categoria y Producto
- Prioridad: Alta
- Story Points: 12
- Area: Repositorios / Persistencia JPA
- Archivos a crear:
  - `src/main/java/com/tp/jpa/repository/CategoriaRepository.java`
  - `src/main/java/com/tp/jpa/repository/ProductoRepository.java`

## Historia de usuario

Como desarrollador, quiero contar con `CategoriaRepository` y `ProductoRepository` que extiendan `BaseRepository` para operar sobre cada entidad sin reescribir el CRUD base.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.

## Alcance funcional

1. Crear repositorio especifico para `Categoria`.
2. Crear repositorio especifico para `Producto`.
3. Agregar consulta JPQL especifica para productos por categoria.

## Alcance tecnico

### CategoriaRepository

Crear:

```text
src/main/java/com/tp/jpa/repository/CategoriaRepository.java
```

Debe:

1. Estar en paquete `com.tp.jpa.repository`.
2. Importar la entidad `Categoria` desde el paquete real del modelo base.
3. Extender `BaseRepository<Categoria>`.
4. Tener constructor publico sin parametros.
5. Llamar a `super(Categoria.class)`.
6. No agregar metodos adicionales.

Estructura conceptual:

```java
public class CategoriaRepository extends BaseRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }
}
```

### ProductoRepository

Crear:

```text
src/main/java/com/tp/jpa/repository/ProductoRepository.java
```

Debe:

1. Estar en paquete `com.tp.jpa.repository`.
2. Importar la entidad `Producto` desde el paquete real del modelo base.
3. Extender `BaseRepository<Producto>`.
4. Tener constructor publico sin parametros.
5. Llamar a `super(Producto.class)`.
6. Implementar `buscarPorCategoria(Long categoriaId)`.

Firma esperada:

```java
public List<Producto> buscarPorCategoria(Long categoriaId)
```

## Reglas para `buscarPorCategoria(Long categoriaId)`

El metodo debe:

1. Abrir su propio `EntityManager`.
2. Cerrar el `EntityManager` en `finally`.
3. Usar JPQL.
4. Usar parametro nombrado `:categoriaId`.
5. Filtrar por categoria: `p.categoria.id = :categoriaId`.
6. Filtrar por activos: `p.eliminado = false`.
7. Usar `TypedQuery<Producto>`.
8. Retornar `List<Producto>`.
9. No usar casteos manuales.
10. No usar SQL nativo.
11. Incluir un comentario que explique que hace la consulta JPQL.

JPQL conceptual:

```java
SELECT p
FROM Producto p
WHERE p.categoria.id = :categoriaId
  AND p.eliminado = false
```

Comentario obligatorio sugerido:

```java
// Consulta JPQL que obtiene los productos activos asociados a la categoria indicada por su ID.
```

## Consideraciones importantes

1. Confirmar el nombre real del atributo de relacion entre `Producto` y `Categoria`.
   - La consigna asume `categoria`.
   - Si la entidad usa otro nombre, usar el nombre real sin modificar la entidad.
2. Confirmar si `Producto` tiene `@Entity(name = "...")`.
   - Si no tiene nombre personalizado, usar `Producto` en JPQL.
   - Si tiene nombre personalizado, usar el nombre definido por JPA.
3. Confirmar si el campo `eliminado` es `boolean` o `Boolean`; en JPQL puede compararse con `false`.
4. No agregar logica de menu en esta historia.

## Criterios de aceptacion

1. `CategoriaRepository` extiende `BaseRepository<Categoria>`.
2. `CategoriaRepository` llama a `super(Categoria.class)`.
3. `CategoriaRepository` no agrega metodos adicionales.
4. `ProductoRepository` extiende `BaseRepository<Producto>`.
5. `ProductoRepository` llama a `super(Producto.class)`.
6. `ProductoRepository` incluye `buscarPorCategoria(Long categoriaId)`.
7. `buscarPorCategoria` usa JPQL tipado.
8. La consulta filtra por `categoria.id = :categoriaId`.
9. La consulta filtra por `eliminado = false`.
10. El metodo usa `TypedQuery<Producto>`.
11. El metodo retorna `List<Producto>`.
12. No hay casteos manuales.
13. El metodo tiene un comentario explicando la consulta JPQL.
14. El codigo compila.

## Fuera de alcance

No implementar en esta historia:

1. Menu de consola.
2. Alta, baja o modificacion de categorias.
3. Alta, baja o modificacion de productos.
4. Reporte en consola.

## Validaciones tecnicas sugeridas

Ejecutar:

```bash
./gradlew clean build
```

## Checklist de terminado

- [ ] Existe `CategoriaRepository.java` en el paquete correcto.
- [ ] Existe `ProductoRepository.java` en el paquete correcto.
- [ ] Ambos extienden `BaseRepository` con el tipo correcto.
- [ ] Ambos llaman a `super(<Entidad>.class)`.
- [ ] `ProductoRepository.buscarPorCategoria` usa JPQL.
- [ ] Usa parametro nombrado `:categoriaId`.
- [ ] Usa `TypedQuery<Producto>`.
- [ ] Filtra por `eliminado = false`.
- [ ] Incluye comentario explicativo de JPQL.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-02. Crea CategoriaRepository y ProductoRepository en com.tp.jpa.repository. CategoriaRepository solo debe extender BaseRepository<Categoria> y llamar a super(Categoria.class). ProductoRepository debe extender BaseRepository<Producto>, llamar a super(Producto.class) e implementar buscarPorCategoria(Long categoriaId) con JPQL tipado, parametro nombrado :categoriaId y filtro eliminado = false. Inclui comentario explicando la consulta JPQL. No modifiques entidades ni configuracion base. Compila al finalizar.
```
