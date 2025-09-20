package com.tienda.presentacion;

import com.tienda.modelo.*;
import com.tienda.servicio.*;
import com.tienda.util.*;

import java.time.LocalDateTime;
import java.util.*;

public class TiendaCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IAdminService adminService = new AdminService();
    private static final IClienteService clienteService = new ClienteService();
    private static final Administrador admin = new Administrador("admin", "admin123");

    public static void main(String[] args) {
        seedProductos();
        menuPrincipal();
    }

    private static void seedProductos() {
        if (adminService.listarProductos().isEmpty()) {
            adminService.agregarProducto(new Producto(0, "Vela aromática", 5000, 10, true));
            adminService.agregarProducto(new Producto(0, "Vela decorativa", 3500, 15, true));
            adminService.agregarProducto(new Producto(0, "Vela grande", 7000, 5, true));
            adminService.agregarProducto(new Producto(0, "Pack 3 velas", 12000, 8, true));
        }
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n=========================");
            System.out.println("      MENÚ PRINCIPAL     ");
            System.out.println("=========================");
            System.out.println("1. Cliente");
            System.out.println("2. Administrador");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1" -> menuCliente();
                case "2" -> menuAdmin();
                case "3" -> { System.out.println("Gracias por usar la Tienda de Velas"); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void menuCliente() {
        while (true) {
            System.out.println("\n=========================");
            System.out.println("       MENÚ CLIENTE      ");
            System.out.println("=========================");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            String op = scanner.nextLine().trim();
            if (op.equals("1")) {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine().trim();
                System.out.print("Clave: ");
                String clave = scanner.nextLine().trim();
                if (!Validador.validarNombre(nombre) || !Validador.validarClave(clave)) {
                    System.out.println("Datos inválidos. Nombre: letras y espacios (3-30). Clave: 4-20 chars.");
                } else {
                    Cliente c = new Cliente(0, nombre, clave);
                    clienteService.registrarCliente(c);
                    System.out.println("Registrado correctamente. Iniciando sesión...");
                    Cliente logged = clienteService.login(nombre, clave);
                    if (logged != null) menuClienteSesion(logged);
                    else System.out.println("Error al iniciar sesión.");
                }
            } else if (op.equals("2")) {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine().trim();
                System.out.print("Clave: ");
                String clave = scanner.nextLine().trim();
                Cliente c = clienteService.login(nombre, clave);
                if (c == null) {
                    System.out.println("Credenciales incorrectas.");
                } else {
                    menuClienteSesion(c);
                }
            } else if (op.equals("3")) {
                return;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void menuClienteSesion(Cliente cliente) {
        Map<Producto, Integer> carrito = new LinkedHashMap<>();
        boolean salir = false;
        while (!salir) {
            System.out.println("\n======================================");
            System.out.println("   SESIÓN CLIENTE - " + cliente.getNombre());
            System.out.println("======================================");
            System.out.println("1. Ver productos");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Ver carrito");
            System.out.println("4. Modificar cantidad en carrito");
            System.out.println("5. Eliminar producto del carrito");
            System.out.println("6. Comprar (checkout)");
            System.out.println("7. Ver historial de pedidos");
            System.out.println("8. Exportar historial a CSV");
            System.out.println("9. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1" -> mostrarProductos();
                case "2" -> {
                    mostrarProductos();
                    System.out.print("Número de producto (ID): ");
                    String sid = scanner.nextLine().trim();
                    if (!Validador.validarEntero(sid)) { System.out.println("ID inválido."); break; }
                    int id = Integer.parseInt(sid);
                    Producto p = adminService.obtenerProducto(id);
                    if (p == null) { System.out.println("Producto no encontrado."); break; }
                    if (!p.isActivo() || p.getStock() <= 0) { System.out.println("Producto no disponible."); break; }
                    carrito.put(p, carrito.getOrDefault(p, 0) + 1);
                    System.out.println("Producto agregado al carrito.");
                }
                case "3" -> {
                    if (carrito.isEmpty()) { System.out.println("Carrito vacío."); break; }
                    System.out.println("\n--- CARRITO ---");
                    carrito.forEach((prod, qty) -> System.out.println(prod.getId() + ". " + prod.getNombre() + " x" + qty + " = $" + (prod.getPrecio()*qty)));
                }
                case "4" -> {
                    if (carrito.isEmpty()) { System.out.println("Carrito vacío."); break; }
                    System.out.print("ID del producto a modificar: "); String sidm = scanner.nextLine().trim();
                    if (!Validador.validarEntero(sidm)) { System.out.println("ID inválido."); break; }
                    int idm = Integer.parseInt(sidm);
                    Producto prodm = adminService.obtenerProducto(idm);
                    if (prodm == null || !carrito.containsKey(prodm)) { System.out.println("Producto no está en el carrito."); break; }
                    System.out.print("Nueva cantidad: "); String snew = scanner.nextLine().trim();
                    if (!Validador.validarEntero(snew)) { System.out.println("Cantidad inválida."); break; }
                    int newq = Integer.parseInt(snew);
                    if (newq <= 0) { carrito.remove(prodm); System.out.println("Producto eliminado del carrito."); break; }
                    carrito.put(prodm, newq);
                    System.out.println("Cantidad actualizada.");
                }
                case "5" -> {
                    if (carrito.isEmpty()) { System.out.println("Carrito vacío."); break; }
                    System.out.print("ID del producto a eliminar: "); String sdel = scanner.nextLine().trim();
                    if (!Validador.validarEntero(sdel)) { System.out.println("ID inválido."); break; }
                    int idd = Integer.parseInt(sdel);
                    Producto prod = adminService.obtenerProducto(idd);
                    if (prod == null || !carrito.containsKey(prod)) { System.out.println("Producto no está en el carrito."); break; }
                    carrito.remove(prod);
                    System.out.println("Producto eliminado del carrito.");
                }
                case "6" -> {
                    if (carrito.isEmpty()) { System.out.println("Carrito vacío."); break; }
                    double total = 0;
                    for (Map.Entry<Producto, Integer> e : carrito.entrySet()) {
                        Producto prod = e.getKey();
                        int q = e.getValue();
                        if (prod.getStock() < q) {
                            System.out.println("Stock insuficiente para " + prod.getNombre() + ". Disponible: " + prod.getStock());
                            total = -1;
                            break;
                        }
                        total += prod.getPrecio() * q;
                    }
                    if (total < 0) { System.out.println("Ajusta el carrito antes de comprar."); break; }
                    // actualizar stock
                    for (Map.Entry<Producto, Integer> e : carrito.entrySet()) {
                        Producto prod = e.getKey();
                        int q = e.getValue();
                        prod.setStock(prod.getStock() - q);
                    }
                    Pedido pedido = new Pedido(0, LocalDateTime.now(), new LinkedHashMap<>(carrito), total);
                    clienteService.realizarPedido(cliente, pedido);
                    carrito.clear();
                    System.out.println("Compra realizada. Total: $" + total);
                }
                case "7" -> {
                    if (cliente.getPedidos().isEmpty()) { System.out.println("No hay pedidos."); break; }
                    System.out.println("\n--- HISTORIAL ---");
                    for (Pedido p : cliente.getPedidos()) {
                        System.out.println("Pedido: " + p.getFecha() + " - Total: $" + p.getTotal());
                        for (Map.Entry<Producto, Integer> e : p.getProductos().entrySet()) {
                            System.out.println("  - " + e.getKey().getNombre() + " x" + e.getValue());
                        }
                    }
                }
                case "8" -> {
                    System.out.print("Ruta archivo CSV (ej: pedidos.csv): "); String ruta = scanner.nextLine().trim();
                    try {
                        ExportadorCSV.exportarPedidos(ruta, cliente);
                        System.out.println("Historial exportado a " + ruta);
                    } catch (Exception ex) {
                        System.out.println("Error al exportar: " + ex.getMessage());
                    }
                }
                case "9" -> { System.out.println("Cerrando sesión..."); salir = true; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void mostrarProductos() {
        List<Producto> productos = adminService.listarProductos();
        if (productos.isEmpty()) { System.out.println("No hay productos disponibles."); return; }
        System.out.println("\n--- PRODUCTOS ---");
        for (Producto p : productos) {
            System.out.println(p.toString());
        }
    }

    private static void menuAdmin() {
        System.out.print("Usuario: "); String u = scanner.nextLine().trim();
        System.out.print("Clave: "); String c = scanner.nextLine().trim();
        if (!u.equals(admin.getNombre()) || !c.equals(admin.getClave())) {
            System.out.println("Credenciales incorrectas.");
            return;
        }
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=========================");
            System.out.println("    MENÚ ADMINISTRADOR   ");
            System.out.println("=========================");
            System.out.println("1. Ver productos");
            System.out.println("2. Agregar producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Ver clientes y compras");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1" -> mostrarProductos();
                case "2" -> {
                    System.out.print("Nombre: "); String nombre = scanner.nextLine().trim();
                    System.out.print("Precio: "); String sPrecio = scanner.nextLine().trim();
                    System.out.print("Stock: "); String sStock = scanner.nextLine().trim();
                    if (!Validador.validarPrecio(sPrecio) || !Validador.validarEntero(sStock)) {
                        System.out.println("Precio o stock inválido.");
                        break;
                    }
                    double precio = Double.parseDouble(sPrecio);
                    int stock = Integer.parseInt(sStock);
                    adminService.agregarProducto(new Producto(0, nombre, precio, stock, true));
                    System.out.println("Producto agregado.");
                }
                case "3" -> {
                    System.out.print("ID del producto a editar: "); String sid = scanner.nextLine().trim();
                    if (!Validador.validarEntero(sid)) { System.out.println("ID inválido."); break; }
                    int id = Integer.parseInt(sid);
                    Producto p = adminService.obtenerProducto(id);
                    if (p == null) { System.out.println("Producto no existe."); break; }
                    System.out.print("Nuevo nombre (enter para mantener '" + p.getNombre() + "'): "); String nn = scanner.nextLine().trim();
                    System.out.print("Nuevo precio (enter para mantener " + p.getPrecio() + "): "); String np = scanner.nextLine().trim();
                    System.out.print("Nuevo stock (enter para mantener " + p.getStock() + "): "); String ns = scanner.nextLine().trim();
                    if (!nn.isEmpty()) p.setNombre(nn);
                    if (!np.isEmpty() && Validador.validarPrecio(np)) p.setPrecio(Double.parseDouble(np));
                    if (!ns.isEmpty() && Validador.validarEntero(ns)) p.setStock(Integer.parseInt(ns));
                    adminService.editarProducto(p);
                    System.out.println("Producto editado.");
                }
                case "4" -> {
                    System.out.print("ID del producto a eliminar: "); String sdel = scanner.nextLine().trim();
                    if (!Validador.validarEntero(sdel)) { System.out.println("ID inválido."); break; }
                    int idd = Integer.parseInt(sdel);
                    adminService.eliminarProducto(idd);
                    System.out.println("Producto eliminado.");
                }
                case "5" -> {
                    System.out.println("\n--- CLIENTES Y COMPRAS ---");
                    ClienteService cs = (ClienteService) clienteService;
                    List<Cliente> clientes = cs.listarClientes();
                    if (clientes.isEmpty()) { System.out.println("No hay clientes registrados."); break; }
                    for (Cliente cl : clientes) {
                        System.out.println("Cliente: " + cl.getNombre());
                        if (cl.getPedidos().isEmpty()) { System.out.println("  - Sin pedidos."); continue; }
                        for (Pedido ped : cl.getPedidos()) {
                            System.out.println("  Pedido: " + ped.getFecha() + " - Total: $" + ped.getTotal());
                            for (Map.Entry<Producto, Integer> e : ped.getProductos().entrySet()) {
                                System.out.println("    - " + e.getKey().getNombre() + " x" + e.getValue());
                            }
                        }
                    }
                }
                case "6" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
