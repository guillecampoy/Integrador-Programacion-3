# TP Programacion Funcional

Trabajo practico de Programacion III sobre programacion funcional en Java, usando expresiones lambda y Streams para procesar colecciones de forma declarativa.

## Objetivo

Practicar operaciones intermedias y terminales de Streams en Java para procesar colecciones, calcular totales y generar consultas sobre los datos del dominio.

## Consigna

A partir del UML incluido en [docs/Trabajo Practico Programacion Funcional.pdf](docs/Trabajo%20Pr%C3%A1ctico%20Programaci%C3%B3n%20Funcional.pdf), se debe tomar como base el modelo de clases desarrollado en las entregas anteriores y aplicar programacion funcional.

Se deben resolver las operaciones pedidas utilizando Streams y expresiones lambda:

- Desarrollar un metodo en `Pedido` que calcule el total del pedido.
- Mostrar por consola los productos disponibles.
- Mostrar por consola la cantidad total de items que tiene un pedido.
- Detectar productos que tengan menos de 5 unidades en stock.

## Modelo del proyecto

El proyecto contiene las siguientes entidades base del dominio:

- `Usuario`
- `Pedido`
- `DetallePedido`
- `Producto`
- `Categoria`
- `Base`

Tambien incluye enums para representar datos del dominio:

- `Estado`
- `FormaPago`
- `Rol`

Ademas, debe incluir el DTO:

- `UsuarioDTO`

## Datos a instanciar

En la clase `Main` se deben crear las instancias necesarias para ejecutar las operaciones funcionales:

- 2 usuarios
- 3 pedidos, con al menos 2 detalles por pedido
- 3 categorias
- 10 productos

Luego se debe mostrar por consola:

- Productos disponibles.
- Total calculado de un pedido.
- Cantidad total de items de un pedido.
- Productos con stock menor a 5.

El modelo conserva la base de las entregas anteriores, incluyendo Lombok y el `record` `UsuarioDTO`.

## Como ejecutar

Requisitos:

- Java instalado
- Gradle Wrapper incluido en el proyecto
- Lombok configurado como dependencia del proyecto

Ejecutar desde la raiz del repositorio:

```bash
./gradlew run
```

Si el plugin `application` no esta configurado, se puede compilar con:

```bash
./gradlew build
```

## Documento original

La consigna completa se encuentra en:

[docs/Trabajo Practico Programacion Funcional.pdf](docs/Trabajo%20Pr%C3%A1ctico%20Programaci%C3%B3n%20Funcional.pdf)
