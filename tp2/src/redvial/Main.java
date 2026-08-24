package redvial;

import redvial.model.Semaforo;
import redvial.model.estructura.Autopista;
import redvial.model.estructura.Calle;
import redvial.model.estructura.Estructura;
import redvial.model.estructura.Rotonda;
import redvial.model.persona.Conductor;
import redvial.model.persona.Pasajero;
import redvial.model.persona.Peaton;
import redvial.model.persona.Persona;
import redvial.model.vehiculo.Auto;
import redvial.model.vehiculo.Camion;
import redvial.model.vehiculo.Colectivo;
import redvial.model.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para testeo y validación integral del Trabajo Práctico N° 2.
 * Demuestra:
 *  1. Instanciación y Encapsulamiento (Getters y Setters).
 *  2. Relaciones entre Clases (Herencia, Composición, Asociación).
 *  3. Sobrecarga de Métodos (Method Overloading) en diversas clases.
 *  4. Polimorfismo mediante colecciones de la clase base Vehiculo y despacho dinámico.
 */
public class Main {

    public static void main(String[] args) {
        imprimirEncabezado("TRABAJO PRÁCTICO N° 2 - PROGRAMACIÓN II: RED VIAL EN JAVA");

        // =========================================================================
        // SECCIÓN 1: INSTANCIACIÓN, ENCAPSULAMIENTO Y GETTERS/SETTERS
        // =========================================================================
        imprimirSeccion("1. INSTANCIACIÓN, ENCAPSULAMIENTO Y USO DE GETTERS / SETTERS");

        // Estructuras
        Calle calleSanMartin = new Calle(1, "Avenida San Martín", 60.0f, true);
        Autopista autopistaPanamericana = new Autopista(2, "Autopista Panamericana", 120.0f, 4, true);
        Rotonda rotondaCaballito = new Rotonda(3, "Rotonda del Caballito", 40.0f, 35.5f, 4);

        // Personas
        Conductor conductorCarlos = new Conductor(101, "Carlos Tevez", 40, "B2-98432");
        Conductor conductorEsteban = new Conductor(102, "Esteban Bullrich", 48, "C1-11234");
        Conductor conductorMarcos = new Conductor(103, "Marcos Di Palma", 52, "D2-55443");

        Pasajero pasajeraAna = new Pasajero(201, "Ana Frank", 22, "Plaza Independencia", "Universidad");
        Pasajero pasajeroLuis = new Pasajero(202, "Luis Alberto", 34, "Centro", "Terminal de Ómnibus");
        Pasajero pasajeraSofia = new Pasajero(203, "Sofía Gala", 29, "Paso de los Andes", "Parque General San Martín");

        Peaton peatonMateo = new Peaton(301, "Mateo Silva", 19, "Esquina San Martín y Garibaldi");

        // Semáforo
        Semaforo semaforoCentro = new Semaforo(1, Semaforo.VERDE, 45);

        // Vehículos
        Auto autoFord = new Auto("AB123CD", "Ford Focus", 180.0f, conductorCarlos, 5);
        Camion camionVolvo = new Camion("AF456GH", "Volvo FH 540", 110.0f, conductorEsteban, 25000.0f);
        Colectivo colectivoUrbano = new Colectivo("AG789JK", "Mercedes-Benz OF 1721", 90.0f, conductorMarcos, 110, 3); // Capacidad reducida a 3 para probar límites

        // Pruebas explícitas de Getters y Setters
        System.out.println("--- Prueba de Getters y Setters ---");
        System.out.println("Nombre original del conductor: " + conductorCarlos.getNombre());
        conductorCarlos.setNombre("Carlos Alberto Tevez");
        System.out.println("Nombre modificado vía setter:  " + conductorCarlos.getNombre());

        System.out.println("Límite de velocidad original de la calle: " + calleSanMartin.getLimiteVelocidad() + " km/h");
        calleSanMartin.setLimiteVelocidad(50.0f);
        System.out.println("Límite modificado vía setter:             " + calleSanMartin.getLimiteVelocidad() + " km/h");

        System.out.println("Estado inicial semáforo: " + semaforoCentro.getEstadoActual() + " (Tiempo: " + semaforoCentro.getTiempoCambioSegundos() + "s)");
        semaforoCentro.setTiempoCambioSegundos(35);
        System.out.println("Tiempo semáforo modificado vía setter: " + semaforoCentro.getTiempoCambioSegundos() + "s");

        System.out.println("\n--- Estado de los Objetos Creados ---");
        System.out.println(calleSanMartin);
        System.out.println(autopistaPanamericana);
        System.out.println(rotondaCaballito);
        System.out.println(conductorCarlos);
        System.out.println(pasajeraAna);
        System.out.println(peatonMateo);
        System.out.println(autoFord);
        System.out.println(camionVolvo);
        System.out.println(colectivoUrbano);
        System.out.println(semaforoCentro);


        // =========================================================================
        // SECCIÓN 2: RELACIONES ENTRE CLASES
        // =========================================================================
        imprimirSeccion("2. DEMOSTRACIÓN DE RELACIONES ENTRE CLASES");

        System.out.println("[Relación 1: Composición Conductor <-> Vehículo (1 a 1)]");
        System.out.println("El auto " + autoFord.getPatente() + " tiene como conductor a: " + autoFord.getConductor().getNombre());
        System.out.println("El conductor " + conductorCarlos.getNombre() + " tiene asignado el vehículo: " + conductorCarlos.getVehiculoAsignado().getModelo());
        conductorCarlos.conducir();

        System.out.println("\n[Relación 2: Composición Vehículo <-> Pasajeros (1 a N)]");
        System.out.println("Subiendo pasajeros al colectivo línea " + colectivoUrbano.getNumeroLinea() + "...");
        pasajeraAna.subirAlVehiculo(colectivoUrbano);
        pasajeroLuis.subirAlVehiculo(colectivoUrbano);
        System.out.println("Pasajeros a bordo actualmente: " + colectivoUrbano.getPasajeros().size());

        System.out.println("\n[Relación 3: Asociación Vehículo -> Estructura (Ubicado en)]");
        autoFord.transitar(autopistaPanamericana);
        camionVolvo.transitar(calleSanMartin);
        colectivoUrbano.transitar(rotondaCaballito);

        System.out.println("\n[Relación 4: Interacción Peatón -> Estructura]");
        peatonMateo.cruzarEstructura(calleSanMartin);


        // =========================================================================
        // SECCIÓN 3: SOBRECARGA DE MÉTODOS (METHOD OVERLOADING)
        // =========================================================================
        imprimirSeccion("3. DEMOSTRACIÓN DE SOBRECARGA DE MÉTODOS (METHOD OVERLOADING)");

        System.out.println("--- Ejemplo A: Sobrecarga en Vehículo (acelerar y frenar) ---");
        System.out.println("Velocidad inicial del auto: " + autoFord.getVelocidadActual() + " km/h");
        
        System.out.println("> Invocando acelerar() [Incremento estándar por defecto]:");
        autoFord.acelerar();

        System.out.println("> Invocando acelerar(float) [Incremento personalizado de +45 km/h]:");
        autoFord.acelerar(45.0f);

        System.out.println("> Invocando frenar(float) [Desaceleración gradual de -20 km/h]:");
        autoFord.frenar(20.0f);

        System.out.println("> Invocando frenar() [Detención total a 0 km/h]:");
        autoFord.frenar();

        System.out.println("\n--- Ejemplo B: Sobrecarga en Vehículo / Colectivo (agregarPasajero individual vs lote) ---");
        System.out.println("Pasajeros actuales en colectivo: " + colectivoUrbano.getPasajeros().size() + "/" + colectivoUrbano.getCapacidadPasajeros());
        
        System.out.println("> Invocando agregarPasajero(Pasajero) [Individual]:");
        Pasajero pasajeroIndividual = new Pasajero(204, "Javier Zanetti", 45, "Barrio Cívico", "Estadio");
        colectivoUrbano.agregarPasajero(pasajeroIndividual);

        System.out.println("> Invocando agregarPasajero(List<Pasajero>) [Lote de pasajeros que intenta subir]:");
        List<Pasajero> grupoPasajeros = new ArrayList<>();
        grupoPasajeros.add(pasajeraSofia);
        grupoPasajeros.add(new Pasajero(205, "Camila O'Gorman", 24, "Centro", "Plaza"));
        colectivoUrbano.agregarPasajero(grupoPasajeros);

        System.out.println("\n--- Ejemplo C: Sobrecarga en Camión (cargar peso simple vs con descripción) ---");
        System.out.println("Capacidad de carga máxima: " + camionVolvo.getCapacidadCargaKg() + " kg");

        System.out.println("> Invocando cargar(float) [Carga estándar de 5000 kg]:");
        camionVolvo.cargar(5000.0f);

        System.out.println("> Invocando cargar(float, String) [Carga con especificación de mercadería]:");
        camionVolvo.cargar(12000.0f, "Cereales y Granos para exportación");

        System.out.println("> Intentando sobrecargar el camión más allá de su capacidad:");
        camionVolvo.cargar(15000.0f, "Maquinaria pesada");


        // =========================================================================
        // SECCIÓN 4: POLIMORFISMO Y DESPACHO DINÁMICO DE MÉTODOS
        // =========================================================================
        imprimirSeccion("4. DEMOSTRACIÓN DE POLIMORFISMO (COLECCIÓN DE VEHÍCULOS)");

        // Colección polimórfica de la clase padre abstracta Vehiculo
        List<Vehiculo> flotaDeVehiculos = List.of(autoFord, camionVolvo, colectivoUrbano);

        System.out.println("Se procesará una colección de tipo List<Vehiculo> conteniendo:");
        for (Vehiculo v : flotaDeVehiculos) {
            System.out.println(" - " + v.getClass().getSimpleName() + " (Patente: " + v.getPatente() + ", Modelo: " + v.getModelo() + ")");
        }

        System.out.println("\n>>> Prueba Polimórfica 1: transitar(Estructura) sobre la Autopista");
        for (Vehiculo vehiculo : flotaDeVehiculos) {
            vehiculo.transitar(autopistaPanamericana);
        }

        System.out.println("\n>>> Prueba Polimórfica 2: Reacción ante Semáforo en VERDE");
        semaforoCentro.setEstadoActual(Semaforo.VERDE);
        for (Vehiculo vehiculo : flotaDeVehiculos) {
            vehiculo.reaccionarSemaforo(semaforoCentro);
        }

        System.out.println("\n>>> Prueba Polimórfica 3: Reacción ante Semáforo en AMARILLO");
        semaforoCentro.cambiarEstado(); // Cambia a AMARILLO
        for (Vehiculo vehiculo : flotaDeVehiculos) {
            vehiculo.reaccionarSemaforo(semaforoCentro);
        }

        System.out.println("\n>>> Prueba Polimórfica 4: Reacción ante Semáforo en ROJO");
        semaforoCentro.cambiarEstado(); // Cambia a ROJO
        for (Vehiculo vehiculo : flotaDeVehiculos) {
            vehiculo.reaccionarSemaforo(semaforoCentro);
        }

        imprimirEncabezado("FIN DE LA EJECUCIÓN - TESTEO Y VALIDACIÓN COMPLETADOS CON ÉXITO");
    }

    private static void imprimirEncabezado(String titulo) {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(75));
    }

    private static void imprimirSeccion(String titulo) {
        System.out.println("\n" + "-".repeat(75));
        System.out.println("▶ " + titulo);
        System.out.println("-".repeat(75));
    }
}
