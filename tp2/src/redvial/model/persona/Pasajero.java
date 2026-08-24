package redvial.model.persona;

import redvial.model.vehiculo.Vehiculo;

public class Pasajero extends Persona {
    private String origen;
    private String destino;
    private Vehiculo vehiculoActual;

    public Pasajero() {
        super();
    }

    public Pasajero(int id, String nombre, int edad, String origen, String destino) {
        super(id, nombre, edad);
        this.origen = origen;
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Vehiculo getVehiculoActual() {
        return vehiculoActual;
    }

    public void setVehiculoActual(Vehiculo vehiculoActual) {
        this.vehiculoActual = vehiculoActual;
    }

    public void subirAlVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            System.out.println("[Pasajero] " + getNombre() + " no puede abordar un vehículo nulo.");
            return;
        }
        if (this.vehiculoActual != null) {
            System.out.println("[Pasajero] " + getNombre() + " ya se encuentra a bordo de otro vehículo.");
            return;
        }
        vehiculo.agregarPasajero(this);
    }

    public void bajarDelVehiculo() {
        if (vehiculoActual == null) {
            System.out.println("[Pasajero] " + getNombre() + " no se encuentra a bordo de ningún vehículo.");
            return;
        }
        Vehiculo v = this.vehiculoActual;
        this.vehiculoActual = null;
        v.removerPasajero(this);
        System.out.println("[Pasajero] " + getNombre() + " ha descendido del vehículo " + v.getModelo() + " en su destino (" + destino + ").");
    }

    @Override
    public String toString() {
        return super.toString() + " | Pasajero [Origen='" + origen + "', Destino='" + destino + "', A bordo=" 
                + (vehiculoActual != null ? vehiculoActual.getPatente() : "No") + "]";
    }
}
