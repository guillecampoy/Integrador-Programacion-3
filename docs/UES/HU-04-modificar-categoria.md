# HU-04 - Modificar una categoria existente

## Metadata

- ID: HU-04
- Titulo: Modificar una categoria existente
- Prioridad: Alta
- Story Points: 10
- Area: Consola / ABM Categorias
- Archivo principal a modificar: `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder editar el nombre o la descripcion de una categoria ya creada para corregir errores sin tener que borrar y recrear el registro.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.
- Recomendado: `HU-03 - Dar de alta una categoria`.

## Alcance funcional

Implementar en `Main` una opcion de modificacion de categoria dentro del submenu de Categorias.

La opcion debe:

1. Listar categorias activas antes de pedir el ID.
2. Solicitar ID de categoria a modificar.
3. Validar que el ID exista.
4. Validar que la categoria este activa (`eliminado = false`).
5. Mostrar valores actuales.
6. Permitir editar nombre y descripcion.
7. Conservar valor anterior cuando el usuario deja un campo vacio.
8. Persistir cambios usando `CategoriaRepository.guardar(...)`.

## Menu esperado

Ruta:

```text
Menu principal -> Categorias -> Modificar categoria
```

Ejemplo:

```text
=== Modificar categoria ===
Categorias activas:
ID: 1 | Nombre: Bebidas | Descripcion: Bebidas frias y calientes
Ingrese ID de categoria: 1
Valores actuales:
Nombre actual: Bebidas
Descripcion actual: Bebidas frias y calientes
Nuevo nombre (enter para conservar): Infusiones
Nueva descripcion (enter para conservar): 
Categoria modificada correctamente.
```

## Reglas de negocio

1. Solo se pueden modificar categorias activas.
2. Si el ID no existe, mostrar error.
3. Si el ID existe pero la categoria esta dada de baja, mostrar error.
4. Si el usuario deja nombre vacio, se conserva el nombre anterior.
5. Si el usuario deja descripcion vacia, se conserva la descripcion anterior.
6. Si el usuario ingresa un nombre no vacio, aplicar `trim()` antes de persistir.

## Reglas tecnicas

1. Usar `CategoriaRepository.listarActivos()` para mostrar opciones previas.
2. Usar `CategoriaRepository.buscarPorId(id)` para obtener la categoria seleccionada.
3. Confirmar con getter real si la categoria esta eliminada.
4. Usar `CategoriaRepository.guardar(categoria)` para persistir cambios.
5. No usar `EntityManager` directamente desde `Main`.
6. No modificar la entidad `Categoria`.
7. Manejar errores de parseo de ID sin romper la aplicacion.

## Criterios de aceptacion

1. El sistema lista las categorias activas antes de pedir el ID.
2. Si no hay categorias activas, se informa explicitamente y se cancela la operacion.
3. Si el ID no corresponde a ninguna categoria activa, se muestra un mensaje de error.
4. Se muestran los valores actuales antes de pedir los nuevos.
5. Dejar el nuevo nombre en blanco mantiene el valor anterior.
6. Dejar la nueva descripcion en blanco mantiene el valor anterior.
7. El cambio se persiste correctamente en la base de datos.
8. La opcion es accesible desde el menu principal.
9. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Alta de categoria, si no existe ya.
2. Baja de categoria.
3. ABM de productos.
4. Reportes JPQL.

## Pruebas manuales

1. Crear una categoria.
2. Entrar a modificar categoria.
3. Confirmar que la categoria aparece listada.
4. Elegir su ID.
5. Cambiar solo nombre y dejar descripcion vacia.
6. Listar categorias y confirmar cambio de nombre y descripcion conservada.
7. Intentar modificar ID inexistente.
8. Confirmar mensaje de error.
9. Intentar ingresar ID no numerico.
10. Confirmar mensaje de error sin cierre inesperado.

## Checklist de terminado

- [ ] La opcion lista categorias activas antes de pedir ID.
- [ ] Maneja caso sin categorias activas.
- [ ] Valida ID inexistente.
- [ ] Valida categoria dada de baja.
- [ ] Muestra valores actuales.
- [ ] Campo vacio conserva valor anterior.
- [ ] Usa `CategoriaRepository.guardar`.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-04 en Main.java. Agrega modificacion de categoria: listar categorias activas, pedir ID, validar existencia y estado activo, mostrar valores actuales, pedir nuevo nombre y descripcion conservando valores cuando el input este vacio, persistir con CategoriaRepository.guardar. No modifiques entidades ni configuracion. Maneja errores de input. Compila al finalizar.
```
