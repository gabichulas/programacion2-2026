package redvial;

import redvial.model.Semaforo;
import redvial.model.estructura.Autopista;
import redvial.model.estructura.Calle;
import redvial.model.estructura.Rotonda;
import redvial.model.persona.Conductor;
import redvial.model.persona.Pasajero;
import redvial.model.persona.Peaton;
import redvial.model.vehiculo.Auto;
import redvial.model.vehiculo.Camion;
import redvial.model.vehiculo.Colectivo;
import redvial.model.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

// java tp2/src/redvial/Main.java

public class Main {

  public static void main(String[] args) {
    System.out.println("=== TRABAJO PRÁCTICO N° 2 - RED VIAL ===\n");

    // 1. Instanciación de Objetos
    Calle calleSanMartin = new Calle(1, "Avenida San Martín", 60.0f, true);
    Autopista autopistaPanamericana = new Autopista(2, "Autopista Panamericana", 120.0f, 4, true);
    Rotonda rotondaCaballito = new Rotonda(3, "Rotonda del Caballito", 40.0f, 35.5f, 4);

    Semaforo semaforoCentro = new Semaforo(1, Semaforo.VERDE, 45);

    Conductor conductorCarlos = new Conductor(101, "Carlos Tevez", 40, "B2-98432");
    Conductor conductorRodrigo = new Conductor(102, "Rodrigo De Paul", 48, "C1-11234");
    Conductor conductorPancho = new Conductor(103, "Pancho Villa", 52, "D2-55443");

    Pasajero pasajeraAna = new Pasajero(201, "Ana Frank", 22, "Plaza Independencia", "Universidad");
    Pasajero pasajeroLuis = new Pasajero(202, "Luis Alberto", 34, "Centro", "Terminal de Ómnibus");
    Pasajero pasajeraSofia = new Pasajero(203, "Sofía Gala", 29, "Paso de los Andes", "Parque General San Martín");

    Peaton peatonLionel = new Peaton(301, "Lionel Messi", 19, "Esquina San Martín y Garibaldi");

    // Vehículos (modelos habituales de Argentina)
    Auto autoGol = new Auto("AB123CD", "Volkswagen Gol Trend", 170.0f, conductorCarlos, 5);
    Camion camionScania = new Camion("AF456GH", "Scania R450", 110.0f, conductorRodrigo, 24000.0f);
    Colectivo colectivoUrbano = new Colectivo("AG789JK", "Mercedes-Benz OF 1621", 90.0f, conductorPancho, 110, 3);

    // Pruebas de Getters y Setters
    System.out.println("--- 1. Getters y Setters ---");
    System.out.println("Nombre original conductor: " + conductorCarlos.getNombre());
    conductorCarlos.setNombre("Carlos Alberto Tevez");
    System.out.println("Nombre modificado vía setter: " + conductorCarlos.getNombre());

    System.out.println("Límite de velocidad calle: " + calleSanMartin.getLimiteVelocidad() + " km/h");
    calleSanMartin.setLimiteVelocidad(50.0f);
    System.out.println("Límite modificado vía setter: " + calleSanMartin.getLimiteVelocidad() + " km/h");

    System.out.println("Tiempo semáforo inicial: " + semaforoCentro.getTiempoCambioSegundos() + "s");
    semaforoCentro.setTiempoCambioSegundos(35);
    System.out.println("Tiempo semáforo modificado: " + semaforoCentro.getTiempoCambioSegundos() + "s");

    System.out.println("\n--- Estado de Objetos Creados ---");
    System.out.println(calleSanMartin);
    System.out.println(autopistaPanamericana);
    System.out.println(rotondaCaballito);
    System.out.println(conductorCarlos);
    System.out.println(pasajeraAna);
    System.out.println(peatonLionel);
    System.out.println(autoGol);
    System.out.println(camionScania);
    System.out.println(colectivoUrbano);
    System.out.println(semaforoCentro);

    // Relaciones entre Clases
    System.out.println("\n--- 2. Demostración de Relaciones ---");
    System.out.println("Auto " + autoGol.getPatente() + " asignado a: " + autoGol.getConductor().getNombre());
    conductorCarlos.conducir();

    System.out.println("\nSubiendo pasajeros al colectivo línea " + colectivoUrbano.getNumeroLinea() + "...");
    pasajeraAna.subirAlVehiculo(colectivoUrbano);
    pasajeroLuis.subirAlVehiculo(colectivoUrbano);
    System.out.println("Pasajeros a bordo actualmente: " + colectivoUrbano.getPasajeros().size());

    System.out.println("\nTránsito por estructuras:");
    autoGol.transitar(autopistaPanamericana);
    camionScania.transitar(calleSanMartin);
    colectivoUrbano.transitar(rotondaCaballito);

    System.out.println();
    peatonLionel.cruzarEstructura(calleSanMartin);

    // Sobrecarga de Métodos
    System.out.println("\n--- 3. Sobrecarga de Métodos ---");

    System.out.println("[Auto: acelerar y frenar]");
    System.out.println("Velocidad inicial: " + autoGol.getVelocidadActual() + " km/h");
    autoGol.acelerar();
    autoGol.acelerar(45.0f);
    autoGol.frenar(20.0f);
    autoGol.frenar();

    System.out.println("\n[Colectivo: agregarPasajero individual y lote]");
    Pasajero pasajeroIndividual = new Pasajero(204, "Javier Zanetti", 45, "Barrio Cívico", "Estadio");
    colectivoUrbano.agregarPasajero(pasajeroIndividual);

    List<Pasajero> grupoPasajeros = new ArrayList<>();
    grupoPasajeros.add(pasajeraSofia);
    grupoPasajeros.add(new Pasajero(205, "Camila O'Gorman", 24, "Centro", "Plaza"));
    colectivoUrbano.agregarPasajero(grupoPasajeros);

    System.out.println("\n[Camión: cargar estándar y con descripción]");
    camionScania.cargar(5000.0f);
    camionScania.cargar(12000.0f, "Granos y cereales");
    camionScania.cargar(15000.0f, "Maquinaria pesada");

    // Polimorfismo
    System.out.println("\n--- 4. Polimorfismo (Colección de Vehículos) ---");
    List<Vehiculo> flota = List.of(autoGol, camionScania, colectivoUrbano);

    System.out.println("\n> Transitar por autopista:");
    for (Vehiculo v : flota) {
      v.transitar(autopistaPanamericana);
    }

    System.out.println("\n> Reacción ante semáforo en VERDE:");
    semaforoCentro.setEstadoActual(Semaforo.VERDE);
    for (Vehiculo v : flota) {
      v.reaccionarSemaforo(semaforoCentro);
    }

    System.out.println("\n> Reacción ante semáforo en AMARILLO:");
    semaforoCentro.cambiarEstado();
    for (Vehiculo v : flota) {
      v.reaccionarSemaforo(semaforoCentro);
    }

    System.out.println("\n> Reacción ante semáforo en ROJO:");
    semaforoCentro.cambiarEstado();
    for (Vehiculo v : flota) {
      v.reaccionarSemaforo(semaforoCentro);
    }
  }
}
