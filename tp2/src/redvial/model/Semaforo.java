package redvial.model;

public class Semaforo {
    public static final String VERDE = "VERDE";
    public static final String AMARILLO = "AMARILLO";
    public static final String ROJO = "ROJO";

    private int id;
    private String estadoActual;
    private int tiempoCambioSegundos;

    public Semaforo() {
        this.estadoActual = VERDE;
        this.tiempoCambioSegundos = 30;
    }

    public Semaforo(int id, String estadoActual, int tiempoCambioSegundos) {
        this.id = id;
        setEstadoActual(estadoActual);
        this.tiempoCambioSegundos = tiempoCambioSegundos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        if (estadoActual == null || (!estadoActual.equalsIgnoreCase(VERDE) 
                && !estadoActual.equalsIgnoreCase(AMARILLO) 
                && !estadoActual.equalsIgnoreCase(ROJO))) {
            throw new IllegalArgumentException("Estado no válido. Debe ser VERDE, AMARILLO o ROJO.");
        }
        this.estadoActual = estadoActual.toUpperCase();
    }

    public int getTiempoCambioSegundos() {
        return tiempoCambioSegundos;
    }

    public void setTiempoCambioSegundos(int tiempoCambioSegundos) {
        if (tiempoCambioSegundos <= 0) {
            throw new IllegalArgumentException("El tiempo de cambio debe ser positivo.");
        }
        this.tiempoCambioSegundos = tiempoCambioSegundos;
    }

    public void cambiarEstado() {
        switch (this.estadoActual) {
            case VERDE -> this.estadoActual = AMARILLO;
            case AMARILLO -> this.estadoActual = ROJO;
            case ROJO -> this.estadoActual = VERDE;
            default -> this.estadoActual = VERDE;
        }
        System.out.println("[Semáforo #" + id + "] Cambió de estado a: " + this.estadoActual);
    }

    @Override
    public String toString() {
        return "Semaforo [ID=" + id + ", Estado=" + estadoActual + ", Tiempo=" + tiempoCambioSegundos + "s]";
    }
}
