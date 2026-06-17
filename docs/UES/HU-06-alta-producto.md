# HU-06 - Dar de alta un producto

## Metadata

- ID: HU-06
- Titulo: Dar de alta un producto
- Prioridad: Alta
- Story Points: 12
- Area: Consola / ABM Productos
- Archivo principal a modificar: `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder registrar un nuevo producto asociandolo a una categoria existente para incorporar articulos al catalogo con toda su informacion basica.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.
- `HU-03 - Dar de alta una categoria`.

## Alcance funcional

Implementar en `Main` una opcion de alta de producto dentro del submenu de Productos.

La opcion debe:

1. Listar categorias activas.
2. Permitir seleccionar una categoria activa.
3. Solicitar nombre.
4. Solicitar descripcion.
5. Solicitar precio.
6. Solicitar stock.
7. Validar precio mayor a 0.
8. Validar stock mayor o igual a 0.
9. Asociar el producto a la categoria seleccionada.
10. Persistir usando `ProductoRepository.guardar(...)`.
11. Mostrar ID generado y categoria asignada.

## Menu esperado

Ruta:

```text
Menu principal -> Productos -> Alta de producto
```

Ejemplo:

```text
=== Alta de producto ===
Categorias activas:
ID: 1 | Nombre: Bebidas | Descripcion: Bebidas frias y calientes
Seleccione ID de categoria: 1
Nombre: Cafe
Descripcion: Cafe molido 500g
Precio: 1500.00
Stock: 20
Producto creado correctamente. ID generado: 10 | Categoria: Bebidas
```

Si no hay categorias activas:

```text
No hay categorias activas disponibles. Debe crear una categoria antes de cargar productos.
```

## Reglas de negocio

1. No se puede crear un producto sin categoria activa.
2. La categoria seleccionada debe existir y estar activa.
3. El precio debe ser mayor a 0.
4. El stock debe ser mayor o igual a 0.
5. Si precio o stock son invalidos, se informa el error y no se persiste.
6. El producto debe quedar activo (`eliminado = false`).
7. La relacion `@ManyToOne` debe quedar resuelta asignando la instancia de `Categoria` al producto.

## Reglas tecnicas

1. Usar `CategoriaRepository.listarActivos()` para mostrar categorias.
2. Usar `CategoriaRepository.buscarPorId(id)` para obtener la categoria seleccionada.
3. Validar que la categoria no este eliminada.
4. Usar `ProductoRepository.guardar(producto)` para persistir.
5. Usar el producto retornado por `guardar` para mostrar ID generado.
6. No usar `EntityManager` directamente desde `Main`.
7. No modificar `Producto` ni `Categoria`.
8. Confirmar el tipo real de `precio` en la entidad.
   - Si es `BigDecimal`, parsear con `new BigDecimal(input)` y validar con `compareTo(BigDecimal.ZERO) > 0`.
   - Si es `Double`, `double` o `Float`, parsear y validar `> 0`.
9. Confirmar el tipo real de `stock` en la entidad.
   - Si es `Integer` o `int`, parsear entero y validar `>= 0`.
10. Manejar errores de parseo sin excepciones no controladas.

## Consideraciones sobre nombre

La rubrica explicita validacion de precio y stock para producto. Aunque no menciona validacion de nombre, es recomendable no persistir productos con nombre vacio para mantener consistencia con categoria. Si se agrega esta validacion, no debe interferir con los criterios obligatorios.

## Criterios de aceptacion

1. El sistema lista las categorias activas para que el operador seleccione una.
2. Si no hay categorias activas, se informa y se cancela la operacion.
3. Se solicita nombre.
4. Se solicita descripcion.
5. Se solicita precio mayor a 0.
6. Se solicita stock mayor o igual a 0.
7. Si precio tiene valor invalido, se informa el error y no se persiste.
8. Si stock tiene valor invalido, se informa el error y no se persiste.
9. El producto queda asociado a la categoria seleccionada.
10. Al guardar, se muestra el ID generado.
11. Al guardar, se muestra la categoria asignada.
12. La opcion es accesible desde el menu principal.
13. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Modificacion de producto.
2. Baja de producto.
3. Consulta JPQL de productos por categoria.
4. Cambio de categoria de un producto existente.

## Pruebas manuales

1. Ejecutar la aplicacion sin categorias activas.
2. Intentar alta de producto y confirmar mensaje de cancelacion.
3. Crear una categoria.
4. Intentar alta de producto.
5. Confirmar que lista categorias activas.
6. Ingresar precio `0` y confirmar error sin persistir.
7. Ingresar precio negativo y confirmar error sin persistir.
8. Ingresar stock negativo y confirmar error sin persistir.
9. Ingresar datos validos y confirmar ID generado.
10. Listar productos activos y confirmar categoria asociada.

## Checklist de terminado

- [ ] Alta de producto lista categorias activas.
- [ ] Cancela si no hay categorias activas.
- [ ] Solicita nombre, descripcion, precio y stock.
- [ ] Valida precio > 0.
- [ ] Valida stock >= 0.
- [ ] Asocia `Producto` con `Categoria`.
- [ ] Usa `ProductoRepository.guardar`.
- [ ] Muestra ID generado y categoria.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-06 en Main.java. Agrega alta de producto: listar categorias activas, cancelar si no hay, pedir ID de categoria y validar que este activa, solicitar nombre, descripcion, precio y stock, validar precio > 0 y stock >= 0, crear Producto segun el modelo base, asignar Categoria en la relacion ManyToOne, persistir con ProductoRepository.guardar y mostrar ID generado y categoria asignada. No modifiques entidades ni configuracion. Compila al finalizar.
```
