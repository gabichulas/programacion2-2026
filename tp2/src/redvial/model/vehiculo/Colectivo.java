package redvial.model.vehiculo;

import redvial.model.Semaforo;
import redvial.model.estructura.Estructura;
import redvial.model.persona.Conductor;
import redvial.model.persona.Pasajero;

import java.util.List;

public class Colectivo extends Vehiculo {
    private int numeroLinea;
    private int capacidadPasajeros;

    public Colectivo() {
        super();
    }

    public Colectivo(String patente, String modelo, float velocidadMax, int numeroLinea, int capacidadPasajeros) {
        super(patente, modelo, velocidadMax);
        this.numeroLinea = numeroLinea;
        setCapacidadPasajeros(capacidadPasajeros);
    }

    public Colectivo(String patente, String modelo, float velocidadMax, Conductor conductor, int numeroLinea, int capacidadPasajeros) {
        super(patente, modelo, velocidadMax, conductor);
        this.numeroLinea = numeroLinea;
        setCapacidadPasajeros(capacidadPasajeros);
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        if (numeroLinea <= 0) {
            throw new IllegalArgumentException("El número de línea debe ser positivo.");
        }
        this.numeroLinea = numeroLinea;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        if (capacidadPasajeros <= 0) {
            throw new IllegalArgumentException("La capacidad de pasajeros debe ser mayor a 0.");
        }
        this.capacidadPasajeros = capacidadPasajeros;
    }

    @Override
    public void agregarPasajero(Pasajero pasajero) {
        if (getPasajeros().size() >= capacidadPasajeros) {
            System.out.println("[Colectivo Línea " + numeroLinea + "] Colectivo lleno (" 
                    + capacidadPasajeros + " pasajeros). El pasajero " + (pasajero != null ? pasajero.getNombre() : "") + " debe esperar el siguiente.");
            return;
        }
        super.agregarPasajero(pasajero);
    }

    @Override
    public void agregarPasajero(List<Pasajero> nuevosPasajeros) {
        if (nuevosPasajeros == null || nuevosPasajeros.isEmpty()) {
            System.out.println("[Colectivo Línea " + numeroLinea + "] No hay pasajeros en la parada.");
            return;
        }
        int cupoDisponible = capacidadPasajeros - getPasajeros().size();
        System.out.println("[Colectivo Línea " + numeroLinea + "] Parada: Intentan subir " + nuevosPasajeros.size() 
                + " pasajeros. Cupo disponible: " + cupoDisponible + ".");
        
        for (Pasajero p : nuevosPasajeros) {
            if (getPasajeros().size() < capacidadPasajeros) {
                agregarPasajero(p);
            } else {
                System.out.println("[Colectivo Línea " + numeroLinea + "] Capacidad máxima alcanzada. " + p.getNombre() + " no pudo subir.");
            }
        }
    }

    @Override
    public void transitar(Estructura estructura) {
        super.transitar(estructura);
        System.out.println(" -> [Colectivo Línea " + numeroLinea + "] Cumpliendo frecuencia y paradas programadas en " + estructura.getNombre() + ".");
    }

    @Override
    public void reaccionarSemaforo(Semaforo semaforo) {
        if (semaforo == null) return;
        System.out.print("[Colectivo Línea " + numeroLinea + "] Detecta semáforo #" + semaforo.getId() + " en " + semaforo.getEstadoActual() + ": ");
        switch (semaforo.getEstadoActual()) {
            case Semaforo.VERDE -> {
                System.out.println("Acelera con suavidad cuidando la estabilidad de los pasajeros.");
                acelerar(15.0f);
            }
            case Semaforo.AMARILLO -> {
                System.out.println("Frena con antelación para no desestabilizar a los pasajeros de pie.");
                frenar();
            }
            case Semaforo.ROJO -> {
                System.out.println("Frena completamente en la parada antes del semáforo.");
                frenar();
            }
            default -> System.out.println("Estado desconocido.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Colectivo [Línea=" + numeroLinea + ", Pasajeros=" + getPasajeros().size() + "/" + capacidadPasajeros + "]";
    }
}
