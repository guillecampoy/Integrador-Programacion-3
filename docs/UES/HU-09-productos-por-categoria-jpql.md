# HU-09 - Listar productos de una categoria

## Metadata

- ID: HU-09
- Titulo: Listar productos de una categoria
- Prioridad: Alta
- Story Points: 15
- Area: Reportes / Consulta JPQL
- Archivos involucrados:
  - `src/main/java/com/tp/jpa/repository/ProductoRepository.java`
  - `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder ver todos los productos activos que pertenecen a una categoria especifica para consultar el catalogo filtrado sin tener que revisar todos los productos.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.
- `HU-03 - Dar de alta una categoria`.
- `HU-06 - Dar de alta un producto`.
- Recomendado: `HU-08 - Dar de baja un producto`, para validar que el reporte no muestre productos eliminados.

## Alcance funcional

Agregar en `Main` una opcion `Productos por categoria` dentro de un submenu de Reportes.

La opcion debe:

1. Listar categorias activas.
2. Permitir seleccionar una categoria activa por ID.
3. Llamar a `ProductoRepository.buscarPorCategoria(id)`.
4. Mostrar productos activos de esa categoria.
5. Mostrar por cada producto: ID, nombre, precio y stock.
6. Si no hay productos activos en la categoria, informar explicitamente.

## Menu esperado

Ruta:

```text
Menu principal -> Reportes -> Productos por categoria
```

Ejemplo con resultados:

```text
=== Productos por categoria ===
Categorias activas:
ID: 1 | Nombre: Bebidas | Descripcion: Bebidas frias y calientes
Seleccione ID de categoria: 1
Productos activos de la categoria Bebidas:
ID: 10 | Nombre: Cafe | Precio: 1500.00 | Stock: 20
```

Ejemplo sin resultados:

```text
No hay productos activos para la categoria seleccionada.
```

## Reglas tecnicas de repositorio

`ProductoRepository.buscarPorCategoria(Long categoriaId)` debe:

1. Estar implementado en `ProductoRepository`.
2. Usar JPQL.
3. Usar parametro nombrado `:categoriaId`.
4. Filtrar por `categoria.id = :categoriaId`.
5. Filtrar por `eliminado = false`.
6. Usar `TypedQuery<Producto>`.
7. Retornar `List<Producto>`.
8. No usar casteos manuales.
9. No usar SQL nativo.
10. Abrir y cerrar su propio `EntityManager`.
11. Cerrar `EntityManager` en `finally`.
12. Incluir comentario explicativo de la JPQL.

JPQL conceptual:

```java
SELECT p
FROM Producto p
WHERE p.categoria.id = :categoriaId
  AND p.eliminado = false
```

Comentario requerido sugerido:

```java
// Consulta JPQL que obtiene solo los productos activos pertenecientes a la categoria seleccionada.
```

## Reglas tecnicas de Main

1. El submenu Reportes debe ser accesible desde el menu principal.
2. Antes de pedir ID, listar categorias activas usando `CategoriaRepository.listarActivos()`.
3. Si no hay categorias activas, informar y cancelar.
4. Validar ID de categoria.
5. Validar que la categoria exista y este activa.
6. Llamar a `productoRepository.buscarPorCategoria(idSeleccionado)`.
7. Si la lista esta vacia, informar explicitamente.
8. Si la lista tiene elementos, imprimir ID, nombre, precio y stock.
9. No mostrar productos dados de baja.
10. No usar `EntityManager` directamente desde `Main`.

## Criterios de aceptacion

1. El sistema lista las categorias activas para que el operador seleccione una.
2. Si no hay categorias activas, se informa y se cancela la operacion.
3. La consulta esta implementada en `ProductoRepository` con JPQL.
4. La consulta usa parametro nombrado `:categoriaId`.
5. Se usa `TypedQuery<Producto>`.
6. No hay casteos manuales en el codigo.
7. Solo se incluyen productos con `eliminado = false`.
8. El resultado muestra ID de cada producto.
9. El resultado muestra nombre de cada producto.
10. El resultado muestra precio de cada producto.
11. El resultado muestra stock de cada producto.
12. Si la categoria no tiene productos activos, se informa explicitamente.
13. La opcion es accesible desde el menu principal.
14. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Nuevos filtros de reporte.
2. Ordenamiento no pedido.
3. Exportacion a archivo.
4. Busqueda por nombre.
5. Modificacion de categorias o productos.

## Pruebas manuales

1. Crear una categoria.
2. Crear dos productos asociados a esa categoria.
3. Ejecutar reporte por categoria.
4. Confirmar que muestra ambos productos.
5. Dar de baja uno de los productos.
6. Ejecutar nuevamente el reporte.
7. Confirmar que el producto dado de baja ya no aparece.
8. Crear una categoria sin productos.
9. Ejecutar reporte para esa categoria.
10. Confirmar mensaje: no hay productos activos.
11. Intentar categoria inexistente.
12. Confirmar mensaje de error.

## Checklist de terminado

- [ ] Existe submenu Reportes.
- [ ] Existe opcion Productos por categoria.
- [ ] Lista categorias activas.
- [ ] Valida categoria seleccionada.
- [ ] Llama a `ProductoRepository.buscarPorCategoria`.
- [ ] JPQL usa `:categoriaId`.
- [ ] JPQL filtra `eliminado = false`.
- [ ] Usa `TypedQuery<Producto>`.
- [ ] No hay casteos manuales.
- [ ] Muestra ID, nombre, precio y stock.
- [ ] Informa explicitamente lista vacia.
- [ ] Producto dado de baja no aparece.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-09. En ProductoRepository verifica/implementa buscarPorCategoria(Long categoriaId) con JPQL tipado, parametro nombrado :categoriaId, filtro por categoria.id y eliminado = false, sin casteos manuales e incluyendo comentario explicativo. En Main agrega submenu Reportes con opcion Productos por categoria: listar categorias activas, validar seleccion, llamar al repositorio, mostrar ID/nombre/precio/stock o mensaje explicito si no hay productos activos. No modifiques entidades ni configuracion. Compila al finalizar.
```
