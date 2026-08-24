package redvial.model.vehiculo;

import redvial.model.Semaforo;
import redvial.model.estructura.Estructura;
import redvial.model.persona.Conductor;

public class Auto extends Vehiculo {
    private int cantidadPuertas;

    public Auto() {
        super();
    }

    public Auto(String patente, String modelo, float velocidadMax, int cantidadPuertas) {
        super(patente, modelo, velocidadMax);
        this.cantidadPuertas = cantidadPuertas;
    }

    public Auto(String patente, String modelo, float velocidadMax, Conductor conductor, int cantidadPuertas) {
        super(patente, modelo, velocidadMax, conductor);
        this.cantidadPuertas = cantidadPuertas;
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int cantidadPuertas) {
        if (cantidadPuertas <= 0) {
            throw new IllegalArgumentException("La cantidad de puertas debe ser mayor a 0.");
        }
        this.cantidadPuertas = cantidadPuertas;
    }

    @Override
    public void transitar(Estructura estructura) {
        super.transitar(estructura);
        System.out.println(" -> [Auto " + getPatente() + "] Conducción ágil y ligera sobre " + estructura.getNombre() + ".");
    }

    @Override
    public void reaccionarSemaforo(Semaforo semaforo) {
        if (semaforo == null) return;
        System.out.print("[Auto " + getPatente() + "] Detecta semáforo #" + semaforo.getId() + " en " + semaforo.getEstadoActual() + ": ");
        switch (semaforo.getEstadoActual()) {
            case Semaforo.VERDE -> {
                System.out.println("Acelera con agilidad.");
                acelerar(25.0f);
            }
            case Semaforo.AMARILLO -> {
                System.out.println("Desacelera suavemente por precaución.");
                frenar(15.0f);
            }
            case Semaforo.ROJO -> {
                System.out.println("Frena completamente en la línea de detención.");
                frenar();
            }
            default -> System.out.println("Estado desconocido.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Auto [Puertas=" + cantidadPuertas + "]";
    }
}
