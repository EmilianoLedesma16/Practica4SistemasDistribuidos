public class Cocinero implements Comparable<Cocinero>, Runnable {
    private final int id;
    private int pedidosEnCola;

    public Cocinero(int id) {
        this.id = id;
        this.pedidosEnCola = 0;
    }

    public int getId() {
        return id;
    }

    public int getPedidosEnCola() {
        return pedidosEnCola;
    }

    public void agregarPedido(Pedido pedido) {
        pedidosEnCola++;
        System.out.println("[Cocinero " + id + "] Preparando: " + pedido);
        try {
            Thread.sleep(2000); // Simulación del tiempo de preparación
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        pedidosEnCola--;
        EstadoGlobal.agregarPedido(pedido);
        System.out.println("[Cocinero " + id + "] Pedido listo: " + pedido);
    }

    @Override
    public int compareTo(Cocinero otro) {
        return Integer.compare(this.pedidosEnCola, otro.pedidosEnCola);
    }

    @Override
    public void run() {
        System.out.println("[Cocinero " + id + "] Listo para recibir pedidos...");
        while (true) {
            try {
                Thread.sleep(1000); // Simulación de espera activa
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Agregar el método main para ejecutar el Cocinero de forma independiente
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java -cp bin Cocinero <ID>");
            return;
        }

        int id = Integer.parseInt(args[0]);
        Cocinero cocinero = new Cocinero(id);
        Thread thread = new Thread(cocinero);
        thread.start();
    }
}
