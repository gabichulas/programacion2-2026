package redvial.model.estructura;

public class Rotonda extends Estructura {
    private float radioMetros;
    private int cantidadSalidas;

    public Rotonda() {
        super();
    }

    public Rotonda(int id, String nombre, float limiteVelocidad, float radioMetros, int cantidadSalidas) {
        super(id, nombre, limiteVelocidad);
        this.radioMetros = radioMetros;
        this.cantidadSalidas = cantidadSalidas;
    }

    public float getRadioMetros() {
        return radioMetros;
    }

    public void setRadioMetros(float radioMetros) {
        if (radioMetros <= 0) {
            throw new IllegalArgumentException("El radio de la rotonda debe ser positivo.");
        }
        this.radioMetros = radioMetros;
    }

    public int getCantidadSalidas() {
        return cantidadSalidas;
    }

    public void setCantidadSalidas(int cantidadSalidas) {
        if (cantidadSalidas <= 0) {
            throw new IllegalArgumentException("La cantidad de salidas debe ser mayor a 0.");
        }
        this.cantidadSalidas = cantidadSalidas;
    }

    @Override
    public String toString() {
        return super.toString() + " | Rotonda [Radio=" + radioMetros + "m, Salidas=" + cantidadSalidas + "]";
    }
}
