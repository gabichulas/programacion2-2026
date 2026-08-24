package redvial.model.estructura;

public class Calle extends Estructura {
    private boolean tieneCrucePeatonal;

    public Calle() {
        super();
    }

    public Calle(int id, String nombre, float limiteVelocidad, boolean tieneCrucePeatonal) {
        super(id, nombre, limiteVelocidad);
        this.tieneCrucePeatonal = tieneCrucePeatonal;
    }

    public boolean isTieneCrucePeatonal() {
        return tieneCrucePeatonal;
    }

    public void setTieneCrucePeatonal(boolean tieneCrucePeatonal) {
        this.tieneCrucePeatonal = tieneCrucePeatonal;
    }

    @Override
    public String toString() {
        return super.toString() + " | Calle [Cruce Peatonal=" + (tieneCrucePeatonal ? "Sí" : "No") + "]";
    }
}
