package actividadarraylist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

// java /mnt/storage/.Documentos/projects/programacion2-2026/actividad-arraylist/src/actividadarraylist/ActividadArraylist.java

public class ActividadArraylist {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ArrayList<Empleado> empleados = new ArrayList<>();
    int opcion;

    do {
      System.out.println("\n--- Menú Principal ---");
      System.out.println("1. Agregar empleado");
      System.out.println("2. Mostrar un empleado específico");
      System.out.println("3. Mostrar lista completa (normal)");
      System.out.println("4. Mostrar lista completa (invertida)");
      System.out.println("5. Salir");
      System.out.print("Seleccione una opción: ");

      // Limpieza de buffer del Scanner tras leer valores numéricos.
      opcion = sc.nextInt();
      sc.nextLine();

      switch (opcion) {
        case 1 -> agregarEmpleado(sc, empleados);
        case 2 -> buscarEmpleado(sc, empleados);
        case 3 -> mostrarNormal(empleados);
        case 4 -> mostrarInvertido(empleados);
        case 5 -> System.out.println("Fin del programa.");
        default -> System.out.println("Opción no válida.");
      }
    } while (opcion != 5);
  }

  private static void agregarEmpleado(Scanner sc, ArrayList<Empleado> empleados) {
    System.out.print("Ingrese nombre: ");
    String nombre = sc.nextLine();
    System.out.print("Ingrese apellido: ");
    String apellido = sc.nextLine();
    System.out.print("Ingrese legajo: ");
    int legajo = sc.nextInt();
    sc.nextLine();

    empleados.add(new Empleado(nombre, apellido, legajo));
    System.out.println("Empleado agregado.");
  }

  private static void buscarEmpleado(Scanner sc, ArrayList<Empleado> empleados) {
    if (empleados.isEmpty()) {
      System.out.println("No hay empleados registrados.");
      return;
    }

    System.out.println("\nBuscar por:");
    System.out.println("1. Posición en lista");
    System.out.println("2. Nombre");
    System.out.println("3. Apellido");
    System.out.println("4. Legajo");
    System.out.print("Opción: ");
    int criterio = sc.nextInt();
    sc.nextLine();

    switch (criterio) {
      case 1 -> {
        System.out.print("Ingrese índice (0 a " + (empleados.size() - 1) + "): ");
        int indice = sc.nextInt();
        sc.nextLine();
        if (indice >= 0 && indice < empleados.size()) {
          System.out.println(empleados.get(indice));
        } else {
          System.out.println("Índice fuera de rango.");
        }
      }
      case 2 -> {
        System.out.print("Ingrese nombre: ");
        String nom = sc.nextLine();
        Empleado emp = buscarPorNombre(empleados, nom);
        System.out.println(emp != null ? emp : "Empleado no encontrado.");
      }
      case 3 -> {
        System.out.print("Ingrese apellido: ");
        String ape = sc.nextLine();
        Empleado emp = buscarPorApellido(empleados, ape);
        System.out.println(emp != null ? emp : "Empleado no encontrado.");
      }
      case 4 -> {
        System.out.print("Ingrese legajo: ");
        int leg = sc.nextInt();
        sc.nextLine();
        Empleado emp = buscarPorLegajo(empleados, leg);
        System.out.println(emp != null ? emp : "Empleado no encontrado.");
      }
      default -> System.out.println("Criterio no válido.");
    }
  }

  private static Empleado buscarPorNombre(ArrayList<Empleado> empleados, String nombre) {
    for (Empleado e : empleados) {
      if (e.getNombre().equalsIgnoreCase(nombre)) {
        return e;
      }
    }
    return null;
  }

  private static Empleado buscarPorApellido(ArrayList<Empleado> empleados, String apellido) {
    for (Empleado e : empleados) {
      if (e.getApellido().equalsIgnoreCase(apellido)) {
        return e;
      }
    }
    return null;
  }

  private static Empleado buscarPorLegajo(ArrayList<Empleado> empleados, int legajo) {
    for (Empleado e : empleados) {
      if (e.getLegajo() == legajo) {
        return e;
      }
    }
    return null;
  }

  private static void mostrarNormal(ArrayList<Empleado> empleados) {
    if (empleados.isEmpty()) {
      System.out.println("No hay empleados registrados.");
      return;
    }
    System.out.println("\nLista de empleados:");
    Iterator<Empleado> it = empleados.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }

  private static void mostrarInvertido(ArrayList<Empleado> empleados) {
    if (empleados.isEmpty()) {
      System.out.println("No hay empleados registrados.");
      return;
    }
    // Recorrido inverso con índices decrecientes
    System.out.println("\nLista de empleados (orden invertido):");
    for (int i = empleados.size() - 1; i >= 0; i--) {
      System.out.println(empleados.get(i));
    }
  }
}
