# TP Programacion Funcional

Trabajo practico de Programacion III sobre programacion funcional en Java. El proyecto toma el modelo de clases de entregas anteriores y aplica expresiones lambda y Streams para procesar colecciones del dominio.

## Consigna

La consigna original se encuentra en [docs/Trabajo Practico Programacion Funcional.pdf](docs/Trabajo%20Pr%C3%A1ctico%20Programaci%C3%B3n%20Funcional.pdf). Pide resolver:

- Desarrollar un metodo en `Pedido` que calcule el total del pedido.
- Mostrar por consola los productos disponibles.
- Mostrar por consola la cantidad total de items que tiene un pedido.
- Detectar productos que tengan menos de 5 unidades en stock.

## Implementacion final

El metodo `Pedido.calcularTotal()` implementa la interfaz `Calculable` y calcula el total con Stream:

```java
this.total = detallePedidos.stream()
        .mapToDouble(DetallePedido::getSubtotal)
        .sum();
```

La clase `Main` instancia los datos semilla y ejecuta directamente las operaciones pedidas:

- `mostrarProductosDisponibles(DatosSemilla)`: filtra productos con `Producto::getDisponible`.
- `mostrarTotalPedido(Pedido)`: invoca `pedido.calcularTotal()` y muestra el total.
- `mostrarCantidadItemsPedido(Pedido)`: suma las cantidades de los detalles con `mapToInt(...).sum()`.
- `mostrarProductosConStockBajo(DatosSemilla)`: filtra productos con `stock < 5`.

Los datos semilla se crean en `DatosSemillaFactory`:

- 2 usuarios.
- 3 categorias.
- 10 productos.
- 3 pedidos.
- Cada pedido tiene al menos 2 detalles.
- Al menos un producto tiene stock menor a 5 para validar la ultima operacion.

## Modelo del proyecto

El diagrama actualizado esta en [docs/diagrama.puml](docs/diagrama.puml). Incluye las clases, records, enums, atributos, metodos explicitos y relaciones actuales del proyecto.

Entidades:

- `Base`
- `Usuario`
- `Categoria`
- `Producto`
- `Pedido`
- `DetallePedido`

Interfaz:

- `Calculable`

DTO y datos semilla:

- `UsuarioDTO`
- `DatosSemilla`
- `DatosSemillaFactory`

Enums:

- `Estado`
- `FormaPago`
- `Rol`

## Ejecucion

Requisitos:

- Java instalado
- Gradle Wrapper incluido en el proyecto
- Lombok configurado como dependencia del proyecto

Ejecutar desde la raiz del repositorio:

```bash
./gradlew run
```

Compilar el proyecto:

```bash
./gradlew build
```

## Salida esperada

Al ejecutar `./gradlew run`, el programa muestra:

- Cantidad de usuarios, categorias, productos y pedidos instanciados.
- Productos disponibles.
- Total calculado de un pedido.
- Cantidad total de items de ese pedido.
- Productos con stock menor a 5.
