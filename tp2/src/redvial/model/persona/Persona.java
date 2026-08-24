package redvial.model.persona;

public abstract class Persona {
    private int id;
    private String nombre;
    private int edad;

    public Persona() {
    }

    public Persona(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.edad = edad;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ID=" + id + ", Nombre='" + nombre + "', Edad=" + edad + "]";
    }
}
