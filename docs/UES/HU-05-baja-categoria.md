# HU-05 - Dar de baja una categoria

## Metadata

- ID: HU-05
- Titulo: Dar de baja una categoria
- Prioridad: Media
- Story Points: 7
- Area: Consola / ABM Categorias
- Archivo principal a modificar: `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder dar de baja una categoria que ya no se utiliza para ocultarla del sistema sin perder el historial de datos.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.
- Recomendado: `HU-03 - Dar de alta una categoria`.

## Alcance funcional

Implementar en `Main` una opcion de baja logica de categoria dentro del submenu de Categorias.

La opcion debe:

1. Solicitar ID de categoria.
2. Validar que exista.
3. Validar que no este ya dada de baja.
4. Marcar `eliminado = true`.
5. Confirmar operacion mostrando el nombre de la categoria.
6. Garantizar que no aparezca en listados activos posteriores.

## Menu esperado

Ruta:

```text
Menu principal -> Categorias -> Baja logica de categoria
```

Ejemplo:

```text
=== Baja logica de categoria ===
Ingrese ID de categoria: 1
Categoria dada de baja correctamente: Bebidas
```

Si no existe:

```text
Error: no existe una categoria activa con el ID indicado.
```

Si ya esta dada de baja:

```text
Error: la categoria ya se encuentra dada de baja.
```

## Reglas de negocio

1. La baja es logica, no fisica.
2. No eliminar registros de la base de datos con `remove()`.
3. El campo `eliminado` debe quedar en `true`.
4. Una categoria dada de baja no debe aparecer en `listarActivos()`.
5. La consigna no pide baja en cascada de productos. No implementar cascadas manuales no solicitadas.
6. La consigna no pide impedir baja si existen productos asociados. No agregar esa restriccion salvo que el modelo/base genere un error inevitable.

## Reglas tecnicas

1. Usar `CategoriaRepository.buscarPorId(id)` para validar existencia y obtener el nombre.
2. Validar estado activo leyendo el campo `eliminado` con el getter real de la entidad.
3. Usar `CategoriaRepository.eliminarLogico(id)` para marcar baja.
4. No usar `EntityManager` directamente desde `Main`.
5. No modificar entidad `Categoria`.
6. Manejar ID no numerico sin cerrar el programa.

## Criterios de aceptacion

1. La baja es logica: se establece `eliminado = true`.
2. El registro permanece en la base de datos.
3. Si el ID no existe, el sistema informa el error.
4. Si el ID ya esta dado de baja, el sistema informa el error.
5. La categoria dada de baja no aparece en ningun listado activo.
6. Se confirma la operacion mostrando el nombre de la categoria afectada.
7. La opcion es accesible desde el menu principal.
8. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Eliminacion fisica.
2. Baja en cascada de productos.
3. Reglas de bloqueo por productos asociados, salvo que ya existan en el modelo base.
4. Reportes JPQL.

## Pruebas manuales

1. Crear una categoria.
2. Listar categorias activas y confirmar que aparece.
3. Dar de baja la categoria por ID.
4. Confirmar mensaje con el nombre.
5. Listar categorias activas y confirmar que no aparece.
6. Intentar dar de baja nuevamente el mismo ID.
7. Confirmar mensaje de error.
8. Intentar dar de baja ID inexistente.
9. Confirmar mensaje de error.

## Checklist de terminado

- [ ] Solicita ID.
- [ ] Valida ID inexistente.
- [ ] Valida categoria ya eliminada.
- [ ] Usa baja logica, no `remove`.
- [ ] Confirma mostrando nombre.
- [ ] La categoria no aparece en listados activos.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-05 en Main.java. Agrega baja logica de categoria: pedir ID, validar existencia y que no este eliminada, llamar a CategoriaRepository.eliminarLogico(id), confirmar con el nombre de la categoria y asegurar que no aparezca en listados activos. No hagas eliminacion fisica ni cascadas no pedidas. Maneja errores de input. Compila al finalizar.
```
