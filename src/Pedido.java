import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger contadorId = new AtomicInteger(1);
    private final int id;
    private final String platillo;
    private final int cantidad;
    private String estado;

    public Pedido(String platillo, int cantidad) {
        this.id = contadorId.getAndIncrement();
        this.platillo = platillo;
        this.cantidad = cantidad;
        this.estado = "En preparación";
    }

    public int getId() {
        return id;
    }

    public String getPlatillo() {
        return platillo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void marcarListo() {
        this.estado = "Listo";
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id + ", Platillo: " + platillo + ", Cantidad: " + cantidad + ", Estado: " + estado;
    }
}
