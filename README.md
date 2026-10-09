# Sistema de Gestión - TechLab (Preentrega)

Aplicación de consola en Java para registrar, listar, buscar, actualizar y eliminar productos, y crear pedidos con varios productos.

## Estructura

```
src/com/techlab/
├── Main.java                         → menú interactivo y lectura de datos
├── productos/
│   ├── Producto.java                 → clase base (id, nombre, precio, stock)
│   ├── Bebida.java                   → subclase con volumen en litros
│   ├── Comida.java                   → subclase con fecha de vencimiento
│   └── ProductoService.java          → agregar / listar / buscar / actualizar / eliminar
├── pedidos/
│   ├── Pedido.java                   → lista de líneas + cálculo del total
│   ├── LineaPedido.java              → producto + cantidad
│   └── PedidoService.java            → valida stock, confirma pedidos y los guarda
└── excepciones/
    ├── StockInsuficienteException.java
    └── ProductoNoEncontradoException.java
```

## Cómo ejecutarlo

**IntelliJ / Eclipse / NetBeans:** abrir la carpeta, marcar `src` como carpeta de fuentes y ejecutar `com.techlab.Main`.

**Desde la terminal** (parado en la carpeta `techlab`):

```
javac -encoding UTF-8 -d out src/com/techlab/*.java src/com/techlab/*/*.java
java -cp out com.techlab.Main
```

Se cargan 3 productos de ejemplo al iniciar para poder probar enseguida.

## Cómo cubre cada requisito

| Requisito | Dónde |
|---|---|
| Variables `int`, `double`, `String`, `boolean` | `Producto`, `LineaPedido`, `Main.confirmar()` |
| Operadores aritméticos, lógicos y relacionales | `Pedido.calcularTotal()`, `Producto.hayStock()`, validaciones |
| Colecciones | `ArrayList<Producto>`, `ArrayList<LineaPedido>`, `ArrayList<Pedido>` |
| Encapsulamiento | atributos `private` + getters/setters que validan |
| Herencia | `Bebida` y `Comida` extienden `Producto` |
| Polimorfismo | `getTipo()`, `getDetalleExtra()` y `toString()` sobrescritos; la lista los trata a todos como `Producto` |
| Excepciones | `try/catch` con `NumberFormatException`; `StockInsuficienteException` y `ProductoNoEncontradoException` propias |
| Paquetes | `com.techlab.productos`, `com.techlab.pedidos`, `com.techlab.excepciones` |
| Búsqueda por ID o nombre + actualización con validación | opción 3 (stock y precio no pueden ser negativos) |
| Eliminar con confirmación | opción 4 |
| Pedido: valida stock, calcula total, descuenta stock al confirmar | opción 5 |
| Listar pedidos con total y productos | opción 6 |
