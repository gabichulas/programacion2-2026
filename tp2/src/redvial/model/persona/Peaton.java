package redvial.model.persona;

import redvial.model.estructura.Estructura;
import redvial.model.estructura.Calle;

public class Peaton extends Persona {
    private String ubicacionActual;

    public Peaton() {
        super();
    }

    public Peaton(int id, String nombre, int edad, String ubicacionActual) {
        super(id, nombre, edad);
        this.ubicacionActual = ubicacionActual;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public void cruzarEstructura(Estructura estructura) {
        if (estructura == null) {
            System.out.println("[Peatón] " + getNombre() + " no puede cruzar una estructura inexistente.");
            return;
        }

        System.out.print("[Peatón] " + getNombre() + " está cruzando: " + estructura.getNombre());
        if (estructura instanceof Calle calle) {
            if (calle.isTieneCrucePeatonal()) {
                System.out.println(" por la senda peatonal habilitada.");
            } else {
                System.out.println(" con precaución (no dispone de senda peatonal).");
            }
        } else {
            System.out.println(".");
        }
        this.ubicacionActual = "Lado opuesto de " + estructura.getNombre();
    }

    @Override
    public String toString() {
        return super.toString() + " | Peatón [Ubicación='" + ubicacionActual + "']";
    }
}
