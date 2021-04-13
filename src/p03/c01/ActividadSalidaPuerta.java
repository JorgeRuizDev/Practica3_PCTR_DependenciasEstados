package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Hilo Actividad Salida Puerta, permite sacar a los usuarios del parque.
 *
 * @author Iván Ruiz Gázquez <a>irg1008@alu.ubu.es</a>
 * @author Jorge Ruiz Gómez <a>jrg1011@alu.ubu.es</a>
 * @author José Barbero <a>https://github.com/JoseBarbero</a>
 */
public class ActividadSalidaPuerta implements Runnable {

	/**
	 * Entero que almacena el número de entradas que se van a realizar en una puerta.
	 */
	private static final int NUMSALIDAS = 20;

	/**
	 * Identificador de la puerta.
	 */
	private final String puerta;

	/**
	 * Parque en el que entraremos.
	 */
	private final IParque parque;

	/**
	 * Constructor de la clase.
	 *
	 * @param puerta ID de la Puerta.
	 * @param parque Parque en el que vamos a operar.
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}


	/**
	 * Método run del hilo.
	 * Introduce a NUMENTRADAS personas en el parque.
	 */
	@Override
	public void run() {
		for (int i = 0; i < NUMSALIDAS; i++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
