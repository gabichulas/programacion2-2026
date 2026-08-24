package redvial.model.persona;

import redvial.model.vehiculo.Vehiculo;

public class Conductor extends Persona {
    private String licenciaConducir;
    private Vehiculo vehiculoAsignado;

    public Conductor() {
        super();
    }

    public Conductor(int id, String nombre, int edad, String licenciaConducir) {
        super(id, nombre, edad);
        this.licenciaConducir = licenciaConducir;
    }

    public Conductor(int id, String nombre, int edad, String licenciaConducir, Vehiculo vehiculoAsignado) {
        super(id, nombre, edad);
        this.licenciaConducir = licenciaConducir;
        asignarVehiculo(vehiculoAsignado);
    }

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public void setVehiculoAsignado(Vehiculo vehiculoAsignado) {
        this.vehiculoAsignado = vehiculoAsignado;
    }

    /**
     * Asigna un vehículo al conductor y sincroniza la referencia bidireccional si es necesario.
     */
    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculoAsignado = vehiculo;
        if (vehiculo != null && vehiculo.getConductor() != this) {
            vehiculo.setConductor(this);
        }
        System.out.println("[Conductor] " + getNombre() + " fue asignado al vehículo con patente " 
                + (vehiculo != null ? vehiculo.getPatente() : "Ninguno") + ".");
    }

    /**
     * Inicia la conducción del vehículo asignado.
     */
    public void conducir() {
        if (vehiculoAsignado == null) {
            System.out.println("[Conductor] " + getNombre() + " no puede conducir porque no tiene un vehículo asignado.");
            return;
        }
        System.out.println("[Conductor] " + getNombre() + " está conduciendo el vehículo " 
                + vehiculoAsignado.getModelo() + " (Patente: " + vehiculoAsignado.getPatente() + ").");
        vehiculoAsignado.acelerar();
    }

    @Override
    public String toString() {
        return super.toString() + " | Conductor [Licencia='" + licenciaConducir + "', Vehiculo=" 
                + (vehiculoAsignado != null ? vehiculoAsignado.getPatente() : "Sin asignar") + "]";
    }
}
