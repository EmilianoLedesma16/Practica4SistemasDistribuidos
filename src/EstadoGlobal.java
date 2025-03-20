import java.util.*;

public class EstadoGlobal {
    private static final List<Pedido> pedidosListos = Collections.synchronizedList(new ArrayList<>());

    public static void agregarPedido(Pedido pedido) {
        pedido.marcarListo();
        pedidosListos.add(pedido);
    }

    public static List<Pedido> obtenerPedidosListos() {
        return new ArrayList<>(pedidosListos);
    }

    public static void mostrarPedidosListos() {
        synchronized (pedidosListos) {
            System.out.println("Pedidos listos:");
            for (Pedido p : pedidosListos) {
                System.out.println(p);
            }
        }
    }
}
