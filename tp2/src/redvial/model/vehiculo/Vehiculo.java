package redvial.model.vehiculo;

import redvial.model.Semaforo;
import redvial.model.estructura.Estructura;
import redvial.model.persona.Conductor;
import redvial.model.persona.Pasajero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Vehiculo {
    private String patente;
    private String modelo;
    private float velocidadMax;
    private float velocidadActual;
    private Conductor conductor;
    private List<Pasajero> pasajeros;
    private Estructura estructuraActual;

    public Vehiculo() {
        this.pasajeros = new ArrayList<>();
        this.velocidadActual = 0.0f;
    }

    public Vehiculo(String patente, String modelo, float velocidadMax) {
        this();
        this.patente = patente;
        this.modelo = modelo;
        this.velocidadMax = velocidadMax;
    }

    public Vehiculo(String patente, String modelo, float velocidadMax, Conductor conductor) {
        this(patente, modelo, velocidadMax);
        setConductor(conductor);
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(float velocidadMax) {
        if (velocidadMax <= 0) {
            throw new IllegalArgumentException("La velocidad máxima debe ser mayor a 0.");
        }
        this.velocidadMax = velocidadMax;
    }

    public float getVelocidadActual() {
        return velocidadActual;
    }

    public void setVelocidadActual(float velocidadActual) {
        if (velocidadActual < 0) {
            this.velocidadActual = 0;
        } else if (velocidadActual > this.velocidadMax) {
            this.velocidadActual = this.velocidadMax;
        } else {
            this.velocidadActual = velocidadActual;
        }
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
        if (conductor != null && conductor.getVehiculoAsignado() != this) {
            conductor.setVehiculoAsignado(this);
        }
    }

    public List<Pasajero> getPasajeros() {
        return Collections.unmodifiableList(pasajeros);
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.pasajeros = (pasajeros != null) ? new ArrayList<>(pasajeros) : new ArrayList<>();
    }

    public Estructura getEstructuraActual() {
        return estructuraActual;
    }

    public void setEstructuraActual(Estructura estructuraActual) {
        this.estructuraActual = estructuraActual;
    }

    public void acelerar() {
        acelerar(10.0f);
    }

    // Metodo sobrecargado
    public void acelerar(float incremento) {
        if (incremento <= 0) {
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Incremento de velocidad inválido: " + incremento);
            return;
        }
        float nuevaVelocidad = this.velocidadActual + incremento;
        if (nuevaVelocidad > this.velocidadMax) {
            this.velocidadActual = this.velocidadMax;
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Aceleró al límite máximo: " + this.velocidadActual + " km/h.");
        } else {
            this.velocidadActual = nuevaVelocidad;
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Aceleró a: " + this.velocidadActual + " km/h.");
        }
    }

    // Metodo sobrecargado
    public void frenar() {
        this.velocidadActual = 0.0f;
        System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Se detuvo completamente (0 km/h).");
    }

    public void frenar(float decremento) {
        if (decremento <= 0) {
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Decremento inválido: " + decremento);
            return;
        }
        this.velocidadActual = Math.max(0.0f, this.velocidadActual - decremento);
        System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Redujo velocidad a: " + this.velocidadActual + " km/h.");
    }

    // Metodo sobrecargado
    public void agregarPasajero(Pasajero pasajero) {
        if (pasajero == null) {
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] No se puede agregar un pasajero nulo.");
            return;
        }
        if (!pasajeros.contains(pasajero)) {
            pasajeros.add(pasajero);
            pasajero.setVehiculoActual(this);
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Pasajero " + pasajero.getNombre() + " abordó.");
        }
    }

    public void agregarPasajero(List<Pasajero> nuevosPasajeros) {
        if (nuevosPasajeros == null || nuevosPasajeros.isEmpty()) {
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Lista de pasajeros vacía.");
            return;
        }
        System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Abordando grupo de " + nuevosPasajeros.size() + " pasajeros...");
        for (Pasajero p : nuevosPasajeros) {
            agregarPasajero(p);
        }
    }

    public void removerPasajero(Pasajero pasajero) {
        if (pasajeros.remove(pasajero)) {
            pasajero.setVehiculoActual(null);
            System.out.println("[" + getClass().getSimpleName() + " " + patente + "] Pasajero " + pasajero.getNombre() + " descendió.");
        }
    }

    public void transitar(Estructura estructura) {
        this.estructuraActual = estructura;
        System.out.println("[" + getClass().getSimpleName() + " " + patente + " - " + modelo + "] Transitando por: " 
                + estructura.getNombre() + " (Límite: " + estructura.getLimiteVelocidad() + " km/h).");
    }

    /**
     * Método polimórfico: Cada tipo de vehículo reacciona de forma diferente según el semáforo.
     */
    public abstract void reaccionarSemaforo(Semaforo semaforo);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Patente='" + patente + "', Modelo='" + modelo 
                + "', VelActual=" + velocidadActual + " km/h, VelMax=" + velocidadMax 
                + " km/h, Conductor=" + (conductor != null ? conductor.getNombre() : "Ninguno") 
                + ", Pasajeros=" + pasajeros.size() 
                + ", Ubicación=" + (estructuraActual != null ? estructuraActual.getNombre() : "Ninguna") + "]";
    }
}
