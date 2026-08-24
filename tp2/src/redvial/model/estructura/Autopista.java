package redvial.model.estructura;

public class Autopista extends Estructura {
    private int cantidadCarriles;
    private boolean tienePeaje;

    public Autopista() {
        super();
    }

    public Autopista(int id, String nombre, float limiteVelocidad, int cantidadCarriles, boolean tienePeaje) {
        super(id, nombre, limiteVelocidad);
        this.cantidadCarriles = cantidadCarriles;
        this.tienePeaje = tienePeaje;
    }

    public int getCantidadCarriles() {
        return cantidadCarriles;
    }

    public void setCantidadCarriles(int cantidadCarriles) {
        if (cantidadCarriles <= 0) {
            throw new IllegalArgumentException("La cantidad de carriles debe ser mayor a 0.");
        }
        this.cantidadCarriles = cantidadCarriles;
    }

    public boolean isTienePeaje() {
        return tienePeaje;
    }

    public void setTienePeaje(boolean tienePeaje) {
        this.tienePeaje = tienePeaje;
    }

    @Override
    public String toString() {
        return super.toString() + " | Autopista [Carriles=" + cantidadCarriles + ", Peaje=" + (tienePeaje ? "Sí" : "No") + "]";
    }
}
