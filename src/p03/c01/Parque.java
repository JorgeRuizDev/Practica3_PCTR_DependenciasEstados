package src.p03.c01;

import java.util.Hashtable;

/**
 * Clase Parque
 *
 * @author Iván Ruiz Gázquez <a>irg1008@alu.ubu.es</a>
 * @author Jorge Ruiz Gómez <a>jrg1011@alu.ubu.es</a>
 * @author José Barbero <a>https://github.com/JoseBarbero</a>
 */
public class Parque implements IParque {

	/**
	 * Variable que controla el máximo de personas en el parque.
	 */
	public static int MAX_PERSONAS = 50;

	/**
	 * Variable que almacena el número de personas que hay dentro del parque.
	 */
	private int contadorPersonasTotales;

	/**
	 * Mapa que controla el balance de Entrada/Salida de cada puerta
	 */
	private final Hashtable<String, Integer> contadoresPersonasPuerta;

	/**
	 * Constructor de la clase
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<>();
	}

	/**
	 * Método sincronizado que pemite a un hilo introducir a una persona al parque
	 * por una puerta (identificada por su ID).
	 *
	 * @param puerta Un String  con el id de la puerta.
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) {

		comprobarAntesDeEntrar(); // Wait

		// Aumentamos el contador total y el individual
		incrementarContador(puerta);

		// Comprobamos invariante
		checkInvariante();

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");

		this.notifyAll();
	}

	/**
	 * Método de salida del parque. Disminuye en 1 la cantidad de personas en el parque.
	 *
	 * @param puerta Un String  con el id de la puerta.
	 */
	@Override
	public synchronized void salirDelParque(String puerta) {

		comprobarAntesDeSalir();

		// Disminuimos el contador total y el individual
		decrementarContador(puerta);

		// Comprobamos invariante
		checkInvariante();

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");

		this.notifyAll();
	}

	/**
	 * Método que saca por pantalla el estado del parque.
	 */
	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet())
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));

		System.out.println(" ");
	}

	/**
	 * Método auxiliar que permite obtener el número total de personas en el parque según el hashmap.
	 *
	 * @return el número de personas que hay acutalmente en el parque.
	 */
	private int sumarContadoresPuerta() {
		return contadoresPersonasPuerta.values().stream().mapToInt(Integer::intValue).sum();
	}

	/**
	 * Método que permite actualizar el hashmap
	 *
	 * @param puerta id de la puerta.
	 * @param number nuevo valor a asignar en la puerta (+1 o -1)
	 */
	private void changeVal(String puerta, int number) {
		int val = 0;
		if (this.contadoresPersonasPuerta.containsKey(puerta))
			val = this.contadoresPersonasPuerta.get(puerta);
		this.contadoresPersonasPuerta.put(puerta, val + number);
	}

	/**
	 * Método que incremental el número de personas en el parque.
	 *
	 * @param puerta id Puerta
	 */
	private void incrementarContador(String puerta) {
		this.changeVal(puerta, 1);
		this.contadorPersonasTotales++;
	}

	/**
	 * Método que incremental el número de personas en el parque.
	 *
	 * @param puerta id Puerta
	 */
	private void decrementarContador(String puerta) {
		this.changeVal(puerta, -1);
		this.contadorPersonasTotales--;
	}

	/**
	 * Método que deja a un hilo en espera.
	 */
	private void waitForCheck() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprobación de invariantes que controlen la exclusión mutua.
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parque";
		assert contadorPersonasTotales <= MAX_PERSONAS : "INV: El parque tiene más personas que su capacidad";
		assert contadorPersonasTotales >= 0 : "INV: El parque tiene personas demasiado negativas";
	}

	/**
	 * Método que realiza comprobaciones antes de introducir a un usuario en el parque.
	 */
	protected void comprobarAntesDeEntrar() {
		while (contadorPersonasTotales > MAX_PERSONAS)
			waitForCheck();
	}

	/**
	 * Método que realiza comprobaciones antes de expulsar a un usuario del parque.
	 */
	protected void comprobarAntesDeSalir() {
		while (contadorPersonasTotales < 1)
			waitForCheck();
	}


}
