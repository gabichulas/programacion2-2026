package agenda;

public class Main {

  public static void main(String[] args) {
    System.out.println("--- Conexión y creación de tablas ---");
    GestorContactos gestor = new GestorContactos();
    gestor.crearTablas();

    System.out.println("\n--- Inserción de registros iniciales ---");
    int c1 = gestor.crearContacto("Juan", "Pérez", "2614001122");
    gestor.crearEmail(c1, "juan.perez@mail.com");
    gestor.crearDireccion(c1, "San Martín 120, Mendoza");

    int c2 = gestor.crearContacto("María", "Gómez", "2615112233");
    gestor.crearEmail(c2, "maria.gomez@mail.com");
    gestor.crearDireccion(c2, "Las Heras 450, Ciudad");

    int c3 = gestor.crearContacto("Carlos", "López", "2616223344");
    gestor.crearEmail(c3, "carlos.lopez@mail.com");
    gestor.crearDireccion(c3, "Belgrano 780, Godoy Cruz");

    int c4 = gestor.crearContacto("Ana", "Martínez", "2617334455");
    gestor.crearEmail(c4, "ana.martinez@mail.com");
    gestor.crearDireccion(c4, "Sarmiento 310, Guaymallén");

    int c5 = gestor.crearContacto("Lucía", "Fernández", "2618445566");
    gestor.crearEmail(c5, "lucia.fernandez@mail.com");
    gestor.crearDireccion(c5, "Mitre 650, Luján de Cuyo");

    System.out.println("5 contactos iniciales cargados.");

    System.out.println("\n--- Listado completo ---");
    gestor.leerContactos();

    System.out.println("\n--- Modificación de datos de un contacto ---");
    gestor.actualizarTelefono(c1, "2619998877");
    int mailExtra = gestor.crearEmail(c1, "juan.trabajo@mail.com");
    int dirExtra = gestor.crearDireccion(c1, "Colón 200, Ciudad");
    gestor.actualizarEmail(mailExtra, "jperez@trabajo.com");
    gestor.actualizarDireccion(dirExtra, "Colón 250, Ciudad");
    gestor.leerContactos();

    System.out.println("\n--- Eliminación de un contacto ---");
    gestor.eliminarEmail(mailExtra);
    gestor.eliminarDireccion(dirExtra);
    gestor.eliminarContacto(c2);
    gestor.leerContactos();

    System.out.println("\n--- Manejo de excepciones ---");
    new GestorContactos("jdbc:sqlite:/carpeta_inexistente/agenda.sqlite");
    gestor.crearEmail(c1, "");
    gestor.crearContacto("", "Apellido", "2610000000");
    gestor.actualizarTelefono(999, "2610000000");
    gestor.eliminarContacto(999);

    gestor.cerrarConexion();
  }
}
