# TP Lombok - DTO

Trabajo practico de Programacion III sobre modelado de objetos, uso de Lombok para reducir codigo repetitivo y creacion de DTOs para ocultar informacion sensible.

## Objetivo

Configurar el proyecto con Gradle, incorporar Lombok y crear DTOs con `record` para transferir informacion sin exponer datos sensibles.

## Consigna

A partir del UML incluido en [docs/PROGRAMACION III Lombok - DTO.pdf](docs/PROGRAMACI%C3%93N%20III%20Lombok%20-%20DTO.pdf), se debe tomar como base el modelo de clases desarrollado en la Unidad 5 y adaptarlo a un proyecto Gradle con la libreria Lombok.

En las clases del modelo se deben reemplazar metodos repetitivos utilizando, al menos, las siguientes anotaciones:

- `@Getter` / `@Setter`
- `@ToString`
- `@EqualsAndHashCode`
- `@Builder` / `@SuperBuilder`
- `@AllArgsConstructor`
- `@NoArgsConstructor`

Tambien se debe crear un paquete `DTOs` con un `record` llamado `UsuarioDTO`, que represente la informacion de `Usuario` sin exponer:

- `Rol`
- `Contrasena`

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

En la clase `Main` se deben crear las instancias utilizando el patron builder:

- 2 usuarios
- 3 pedidos, con al menos 2 detalles por pedido
- 3 categorias
- 10 productos

Luego se debe mostrar por consola:

- Un producto usando `toString()`
- El listado de productos cargados
- Los pedidos del usuario que mas pedidos tenga
- La comparacion de un producto nuevo contra la coleccion, usando los campos definidos en `equals()`
- La informacion de usuario mediante `UsuarioDTO`, ocultando rol y contrasena

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

[docs/PROGRAMACION III Lombok - DTO.pdf](docs/PROGRAMACI%C3%93N%20III%20Lombok%20-%20DTO.pdf)
