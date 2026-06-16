# HU-03 - Dar de alta una categoria

## Metadata

- ID: HU-03
- Titulo: Dar de alta una categoria
- Prioridad: Alta
- Story Points: 8
- Area: Consola / ABM Categorias
- Archivo principal a modificar/crear: `src/main/java/com/tp/jpa/Main.java`

## Historia de usuario

Como operador del sistema, quiero poder crear una nueva categoria ingresando nombre y descripcion para organizar los productos del catalogo en grupos tematicos.

## Dependencias

Esta historia depende de:

- `HU-01 - Repositorio generico con CRUD`.
- `HU-02 - Repositorios especificos de Categoria y Producto`.

## Alcance funcional

Implementar en `Main` una opcion de alta de categoria dentro del submenu de Categorias.

La opcion debe:

1. Solicitar nombre.
2. Solicitar descripcion.
3. Validar que el nombre no este vacio.
4. Crear instancia de `Categoria` usando el constructor/setters disponibles en el modelo base.
5. Persistir usando `CategoriaRepository.guardar(...)`.
6. Mostrar el ID generado.
7. Dejar la categoria activa (`eliminado = false`).
8. Asegurar `createdAt` con fecha/hora actual si el modelo base no lo hace automaticamente.

## Menu esperado

Debe existir una ruta desde el menu principal:

```text
Menu principal -> Categorias -> Alta de categoria
```

Ejemplo de interaccion:

```text
=== Alta de categoria ===
Nombre: Bebidas
Descripcion: Bebidas frias y calientes
Categoria creada correctamente. ID generado: 1
```

Si el nombre esta vacio:

```text
Nombre: 
Error: el nombre de la categoria es obligatorio. No se guardo la categoria.
```

## Reglas tecnicas

1. Usar `CategoriaRepository`, no usar `EntityManager` directamente desde `Main`.
2. Usar el objeto retornado por `guardar(...)` para obtener el ID generado.
3. No modificar la entidad `Categoria`.
4. No modificar `persistence.xml`.
5. No agregar persistencia manual fuera del repositorio.
6. No persistir si el nombre esta vacio o compuesto solo por espacios.
7. Aplicar `trim()` al nombre para validar.
8. La descripcion puede ser vacia salvo que la entidad base tenga otra restriccion.
9. Si la entidad tiene campo `eliminado`, setearlo en `false` si no lo hace el constructor.
10. Si la entidad tiene campo `createdAt`, respetar la inicializacion existente. Si el campo existe y requiere setter, establecer fecha/hora actual.

## Consideraciones sobre constructores

El agente debe inspeccionar `Categoria` antes de crear la instancia:

1. Si existe constructor con nombre y descripcion, usarlo.
2. Si existe constructor vacio y setters, usar setters.
3. Si hay Lombok, verificar setters generados por anotaciones.
4. Si hay campos adicionales obligatorios, completar solo los que correspondan a la consigna y al modelo base.
5. No agregar nuevos constructores a la entidad.

## Criterios de aceptacion

1. El sistema solicita nombre por consola.
2. El sistema solicita descripcion por consola.
3. Si el nombre esta vacio, el sistema informa el error.
4. Si el nombre esta vacio, no se persiste ningun registro.
5. Al guardar exitosamente, se muestra el ID generado por la base de datos.
6. La categoria queda con `eliminado = false`.
7. La categoria queda con `createdAt` con la fecha/hora actual, si el modelo base expone ese campo y no lo inicializa automaticamente.
8. La opcion es accesible desde el menu principal.
9. El proyecto compila.

## Fuera de alcance

No implementar en esta historia:

1. Modificacion de categoria.
2. Baja de categoria.
3. Listado completo, salvo que sea necesario para navegacion minima del menu.
4. ABM de productos.
5. Reportes JPQL.

## Pruebas manuales

1. Ejecutar la aplicacion.
2. Entrar a Categorias.
3. Seleccionar Alta.
4. Ingresar nombre valido y descripcion.
5. Confirmar que muestra ID generado.
6. Volver a intentar con nombre vacio.
7. Confirmar que muestra error y no persiste.

## Checklist de terminado

- [ ] Existe submenu de Categorias o ruta equivalente.
- [ ] Alta solicita nombre y descripcion.
- [ ] Nombre vacio no persiste.
- [ ] Usa `CategoriaRepository.guardar`.
- [ ] Muestra ID generado.
- [ ] Setea o respeta `eliminado = false`.
- [ ] Setea o respeta `createdAt` actual.
- [ ] Compila.

## Prompt sugerido para agente

```text
Implementa la historia HU-03 en Main.java. Agrega una opcion de alta de categoria accesible desde el menu principal. Usa CategoriaRepository.guardar, solicita nombre y descripcion, valida que nombre no este vacio, crea la entidad segun el modelo base existente, persiste y muestra el ID generado usando el objeto retornado por guardar. No modifiques entidades ni configuracion. Compila al finalizar.
```
