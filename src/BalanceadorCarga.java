import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class BalanceadorCarga {
    private static final PriorityBlockingQueue<Cocinero> colaCocineros = new PriorityBlockingQueue<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);  // Usamos un pool de hilos

    public static void main(String[] args) {
        int puerto = 5000;
        
        // Agregar cocineros al pool
        colaCocineros.add(new Cocinero(1));
        colaCocineros.add(new Cocinero(2));
        colaCocineros.add(new Cocinero(3));
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("[Balanceador] Escuchando en el puerto " + puerto);
            
            while (true) {
                Socket socketCliente = servidor.accept();
                executor.submit(new ManejadorPedido(socketCliente));  // Usamos el ExecutorService para manejar los hilos
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Cocinero obtenerCocineroDisponible() {
        try {
            return colaCocineros.take(); // Extrae el cocinero con menor carga
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static void devolverCocinero(Cocinero cocinero) {
        colaCocineros.add(cocinero);
    }
}

class ManejadorPedido implements Runnable {  // Cambié "extends Thread" por "implements Runnable"
    private Socket socketCliente;

    public ManejadorPedido(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
             PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true)) {
            
            String linea = entrada.readLine();
            String[] datos = linea.split(",");
            String platillo = datos[0];
            int cantidad = Integer.parseInt(datos[1]);
            
            Pedido pedido = new Pedido(platillo, cantidad);
            Cocinero cocinero = BalanceadorCarga.obtenerCocineroDisponible();
            
            if (cocinero != null) {
                cocinero.agregarPedido(pedido);
                BalanceadorCarga.devolverCocinero(cocinero);
                salida.println("Pedido " + pedido.getId() + " en preparación por Cocinero " + cocinero.getId());
            } else {
                salida.println("No hay cocineros disponibles en este momento.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
