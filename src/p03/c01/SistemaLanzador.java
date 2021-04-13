package src.p03.c01;

/**
 *
 * Clase Sistema Lanzador
 * Cotiene el Main del proyecto que lanza los diferentes hilos.
 *
 * @author Iván Ruiz Gázquez <a>irg1008@alu.ubu.es</a>
 * @author Jorge Ruiz Gómez <a>jrg1011@alu.ubu.es</a>
 */
public class SistemaLanzador {

	/**
	 * Main del proyecto.
	 * @param args args[0]: Número de puertas a crear.
	 */
	public static void main(String[] args) {
		
		IParque parque = new Parque(); // TODO
		char letra_puerta = 'A';
		
		System.out.println("¡Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			// 
			// TODO
			//
			
			
		}
	}	
}