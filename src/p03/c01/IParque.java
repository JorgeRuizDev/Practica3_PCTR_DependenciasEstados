package src.p03.c01;

/**
 * Interfaz Parque
 *
 * @author Iván Ruiz Gázquez <a>irg1008@alu.ubu.es</a>
 * @author Jorge Ruiz Gómez <a>jrg1011@alu.ubu.es</a>
 * @author José Barbero <a>https://github.com/JoseBarbero</a>

 */
public interface IParque {

	/**
	 * Método de entrada del parque. Aumenta en 1 la cantidad de personas en el parque.
	 * @param puerta Un String  con el id de la puerta.
	 */
	void entrarAlParque(String puerta);

	/**
	 * Método de salida del parque. Disminuye en 1 la cantidad de personas en el parque.
	 * @param puerta Un String  con el id de la puerta.
	 */
	void salirDelParque(String puerta);
}
