import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;
        
        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket(host, puerto);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Ingrese el platillo: ");
            String platillo = scanner.nextLine();
            
            System.out.println("Ingrese la cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Enviar pedido al balanceador
            salida.println(platillo + "," + cantidad);
            
            // Recibir respuesta
            String respuesta = entrada.readLine();
            System.out.println("Respuesta del balanceador: " + respuesta);
            
            System.out.println("Desea ver los pedidos listos? (s/n)");
            String opcion = scanner.nextLine();
            
            if (opcion.equalsIgnoreCase("s")) {
                EstadoGlobal.mostrarPedidosListos();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
