package redvial.model.vehiculo;

import redvial.model.Semaforo;
import redvial.model.estructura.Estructura;
import redvial.model.persona.Conductor;

public class Camion extends Vehiculo {
    private float capacidadCargaKg;
    private float cargaActualKg;

    public Camion() {
        super();
        this.cargaActualKg = 0.0f;
    }

    public Camion(String patente, String modelo, float velocidadMax, float capacidadCargaKg) {
        super(patente, modelo, velocidadMax);
        setCapacidadCargaKg(capacidadCargaKg);
        this.cargaActualKg = 0.0f;
    }

    public Camion(String patente, String modelo, float velocidadMax, Conductor conductor, float capacidadCargaKg) {
        super(patente, modelo, velocidadMax, conductor);
        setCapacidadCargaKg(capacidadCargaKg);
        this.cargaActualKg = 0.0f;
    }

    public float getCapacidadCargaKg() {
        return capacidadCargaKg;
    }

    public void setCapacidadCargaKg(float capacidadCargaKg) {
        if (capacidadCargaKg <= 0) {
            throw new IllegalArgumentException("La capacidad de carga debe ser mayor a 0 kg.");
        }
        this.capacidadCargaKg = capacidadCargaKg;
    }

    public float getCargaActualKg() {
        return cargaActualKg;
    }

    public void setCargaActualKg(float cargaActualKg) {
        if (cargaActualKg < 0 || cargaActualKg > capacidadCargaKg) {
            throw new IllegalArgumentException("Carga inválida. Debe estar entre 0 y " + capacidadCargaKg + " kg.");
        }
        this.cargaActualKg = cargaActualKg;
    }

    // --- MÉTODOS SOBRECARGADOS: Carga de Mercadería ---
    /**
     * Sobrecarga 1: Carga peso estándar en el camión.
     */
    public void cargar(float pesoKg) {
        if (pesoKg <= 0) {
            System.out.println("[Camión " + getPatente() + "] Peso a cargar inválido: " + pesoKg + " kg.");
            return;
        }
        if (this.cargaActualKg + pesoKg > this.capacidadCargaKg) {
            System.out.println("[Camión " + getPatente() + "] No se puede cargar " + pesoKg + " kg. Supera la capacidad máxima (" 
                    + this.capacidadCargaKg + " kg). Carga actual: " + this.cargaActualKg + " kg.");
            return;
        }
        this.cargaActualKg += pesoKg;
        System.out.println("[Camión " + getPatente() + "] Se cargaron " + pesoKg + " kg. Carga total: " + this.cargaActualKg + "/" + this.capacidadCargaKg + " kg.");
    }

    /**
     * Sobrecarga 2: Carga peso indicando el tipo/descripción de la mercadería.
     */
    public void cargar(float pesoKg, String tipoMercaderia) {
        System.out.println("[Camión " + getPatente() + "] Cargando mercadería: '" + tipoMercaderia + "'...");
        cargar(pesoKg);
    }

    public void descargar(float pesoKg) {
        if (pesoKg <= 0) {
            System.out.println("[Camión " + getPatente() + "] Peso a descargar inválido: " + pesoKg + " kg.");
            return;
        }
        this.cargaActualKg = Math.max(0.0f, this.cargaActualKg - pesoKg);
        System.out.println("[Camión " + getPatente() + "] Se descargaron " + pesoKg + " kg. Carga restante: " + this.cargaActualKg + " kg.");
    }

    @Override
    public void transitar(Estructura estructura) {
        super.transitar(estructura);
        System.out.println(" -> [Camión " + getPatente() + "] Circulando por carril derecho con " + cargaActualKg + " kg de carga sobre " + estructura.getNombre() + ".");
    }

    @Override
    public void reaccionarSemaforo(Semaforo semaforo) {
        if (semaforo == null) return;
        System.out.print("[Camión " + getPatente() + "] Detecta semáforo #" + semaforo.getId() + " en " + semaforo.getEstadoActual() + ": ");
        switch (semaforo.getEstadoActual()) {
            case Semaforo.VERDE -> {
                System.out.println("Aceleración gradual y progresiva debido al tonelaje.");
                acelerar(10.0f);
            }
            case Semaforo.AMARILLO -> {
                System.out.println("Anticipa frenada por gran inercia de la carga y frena a 0.");
                frenar();
            }
            case Semaforo.ROJO -> {
                System.out.println("Frena completamente manteniendo distancia de seguridad.");
                frenar();
            }
            default -> System.out.println("Estado desconocido.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Camión [Carga=" + cargaActualKg + "/" + capacidadCargaKg + " kg]";
    }
}
