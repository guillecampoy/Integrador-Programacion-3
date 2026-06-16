# HU-08 - Dar de baja un producto

## Metadata

- ID: HU-08
- Titulo: Dar de baja un producto
- Prioridad: Media
- Story Points: 8
- Area: Consola / ABM Productos
- Archivo principal a modificar: `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder dar de baja un producto que ya no esta disponible para retirarlo del catalogo activo sin eliminar su historial.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.
- `HU-06 - Dar de alta un producto`.

## Alcance funcional

Implementar en `Main` una opcion de baja logica de producto dentro del submenu de Productos.

La opcion debe:

1. Solicitar ID de producto.
2. Validar que exista.
3. Validar que este activo.
4. Marcar `eliminado = true`.
5. Confirmar operacion mostrando el nombre del producto.
6. Garantizar que no aparezca en listados activos posteriores.
7. Garantizar que no aparezca en reporte de productos por categoria.

## Menu esperado

Ruta:

```text
Menu principal -> Productos -> Baja logica de producto
```

Ejemplo:

```text
=== Baja logica de producto ===
Ingrese ID de producto: 10
Producto dado de baja correctamente: Cafe
```

Si no existe:

```text
Error: no existe un producto activo con el ID indicado.
```

Si ya esta dado de baja:

```text
Error: el producto ya se encuentra dado de baja.
```

## Reglas de negocio

1. La baja es logica, no fisica.
2. No eliminar registros de la base de datos con `remove()`.
3. El campo `eliminado` debe quedar en `true`.
4. Un producto dado de baja no debe aparecer en listado de productos activos.
5. Un producto dado de baja no debe aparecer en `buscarPorCategoria`.

## Reglas tecnicas

1. Usar `ProductoRepository.buscarPorId(id)` para validar existencia y obtener nombre.
2. Validar estado activo leyendo `eliminado` con el getter real.
3. Usar `ProductoRepository.eliminarLogico(id)` para marcar baja.
4. No usar `EntityManager` directamente desde `Main`.
5. No modificar entidad `Producto`.
6. Manejar ID no numerico sin cerrar el programa.

## Criterios de aceptacion

1. La baja es logica: `eliminado = true`.
2. El registro permanece en la base de datos.
3. Si el ID no existe, se informa el error.
4. Si ya esta dado de baja, se informa el error.
5. El producto dado de baja no aparece en el listado de productos activos.
6. Se muestra confirmacion con el nombre del producto afectado.
7. La opcion es accesible desde el menu principal.
8. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Eliminacion fisica.
2. Baja de categoria.
3. Modificacion de producto.
4. Reporte JPQL, salvo verificar que luego no muestre productos eliminados cuando HU-09 exista.

## Pruebas manuales

1. Crear categoria.
2. Crear producto.
3. Listar productos activos y confirmar que aparece.
4. Dar de baja el producto por ID.
5. Confirmar mensaje con nombre.
6. Listar productos activos y confirmar que no aparece.
7. Intentar dar de baja nuevamente el mismo ID.
8. Confirmar mensaje de error.
9. Intentar dar de baja ID inexistente.
10. Confirmar mensaje de error.

## Checklist de terminado

- [ ] Solicita ID.
- [ ] Valida ID inexistente.
- [ ] Valida producto ya eliminado.
- [ ] Usa baja logica, no `remove`.
- [ ] Confirma mostrando nombre.
- [ ] El producto no aparece en listados activos.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-08 en Main.java. Agrega baja logica de producto: pedir ID, validar existencia y que no este eliminado, llamar a ProductoRepository.eliminarLogico(id), confirmar con el nombre del producto y asegurar que no aparezca en listados activos. No hagas eliminacion fisica. Maneja errores de input. Compila al finalizar.
```
