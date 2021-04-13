package src.p03.c01;

import java.util.Hashtable;

public class Parque implements IParque {

	public static int MAX_PERSONAS = 50;

	private int contadorPersonasTotales;
	private final Hashtable<String, Integer> contadoresPersonasPuerta;

	/**
	 * Constructor de la clase
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<>();
	}

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

	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet())
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));

		System.out.println(" ");
	}

	private int sumarContadoresPuerta() {
		return contadoresPersonasPuerta.values().stream().mapToInt(Integer::intValue).sum();
	}


    private void changeVal(String puerta, int number) {
        int val = 0;
        if (this.contadoresPersonasPuerta.containsKey(puerta))
            val = this.contadoresPersonasPuerta.get(puerta);
        this.contadoresPersonasPuerta.put(puerta, val + number);
    }

    private void incrementarContador(String puerta) {
        this.changeVal(puerta, 1);
        this.contadorPersonasTotales++;
    }

    private void decrementarContador(String puerta) {
        this.changeVal(puerta, -1);
        this.contadorPersonasTotales--;
    }

    private void waitForCheck() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parque";
		assert contadorPersonasTotales <= MAX_PERSONAS : "INV: El parque tiene más personas que su capacidad";
		assert contadorPersonasTotales >= 0 : "INV: El parque tiene personas demasiado negativas";
	}

	protected void comprobarAntesDeEntrar() {
		while (contadorPersonasTotales > MAX_PERSONAS)
            waitForCheck();
	}

	protected void comprobarAntesDeSalir() {
		while (contadorPersonasTotales < 1)
            waitForCheck();
	}


}
