package src.p03.c01;

import java.util.Hashtable;

public class Parque implements IParque {

	public static int MAX_PERSONAS = 50;

  private int contadorPersonasTotales;
  private final Hashtable<String, Integer> contadoresPersonasPuerta;

  public Parque() {
    contadorPersonasTotales = 0;
    contadoresPersonasPuerta = new Hashtable<String, Integer>();
  }

  @Override
  public void entrarAlParque(String puerta) {

    // Si no hay entradas por esa puerta, inicializamos
    contadoresPersonasPuerta.putIfAbsent(puerta, 0);

    // Aumentamos el contador total y el individual
    contadorPersonasTotales++;
    contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

    // Comprobamos invariante
    checkInvariante();

    // Imprimimos el estado del parque
    imprimirInfo(puerta, "Entrada");
  }

  @Override
  public void salirDelParque(String puerta) {
    // Disminuimos el contador total y el individual
    contadorPersonasTotales--;
    contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

    // Comprobamos invariante
    checkInvariante();

    // Imprimimos el estado del parque
    imprimirInfo(puerta, "Salida");
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

  protected void checkInvariante() {
    assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
  }

  protected void comprobarAntesDeEntrar() {  // TODO
    boolean entradaDisp = sumarContadoresPuerta() < MAX_PERSONAS;

  }

  protected void comprobarAntesDeSalir() {    // TODO
    boolean hay = sumarContadoresPuerta() > 0;
  }


}
