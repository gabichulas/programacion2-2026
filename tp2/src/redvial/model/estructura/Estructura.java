package redvial.model.estructura;

public abstract class Estructura {
    private int id;
    private String nombre;
    private float limiteVelocidad;

    public Estructura() {
    }

    public Estructura(int id, String nombre, float limiteVelocidad) {
        this.id = id;
        this.nombre = nombre;
        this.limiteVelocidad = limiteVelocidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLimiteVelocidad() {
        return limiteVelocidad;
    }

    public void setLimiteVelocidad(float limiteVelocidad) {
        if (limiteVelocidad < 0) {
            throw new IllegalArgumentException("El límite de velocidad no puede ser negativo.");
        }
        this.limiteVelocidad = limiteVelocidad;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ID=" + id + ", Nombre='" + nombre + "', Límite=" + limiteVelocidad + " km/h]";
    }
}
