package tipo_automata;

import automata.Automata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Clase que representa un autómata no determinista con transiciones lambda.
 * Este tipo de autómata puede tener múltiples transiciones para un mismo símbolo de entrada
 * y puede realizar transiciones lambda, es decir, transiciones sin consumir ningún símbolo.
 */
public class AutomataNoDeterministaConLambda extends Automata {
    private final Map<Integer, Map<Character, Set<Integer>>> transiciones;
    private final Map<Integer, Set<Integer>> transicionesLambda;

    /**
     * Constructor para un autómata no determinista con transiciones lambda.
     *
     * @param estados             Conjunto de estados del autómata.
     * @param alfabeto            Alfabeto del autómata.
     * @param transiciones        Mapa que representa las transiciones regulares del autómata.
     * @param transicionesLambda  Mapa que representa las transiciones lambda del autómata.
     * @param estadoInicial       Estado inicial del autómata.
     * @param estadosFinales      Conjunto de estados finales del autómata.
     */
    public AutomataNoDeterministaConLambda(Set<Integer> estados, Set<Character> alfabeto, Map<Integer, Map<Character, Set<Integer>>> transiciones, Map<Integer, Set<Integer>> transicionesLambda, int estadoInicial, Set<Integer> estadosFinales) {
        super(estados, alfabeto, estadoInicial, estadosFinales, transiciones);
        this.transiciones = transiciones;
        this.transicionesLambda = transicionesLambda;
    }

    /**
     * Procesa una cadena de entrada en el autómata.
     *
     * @param cadena Cadena de entrada a procesar.
     * @return true si la cadena lleva al autómata a un estado final, false en caso contrario.
     */
    @Override
    public boolean procesarCadena(String cadena) {
        Set<Integer> estadosActuales = new HashSet<>();
        estadosActuales.add(estadoInicial);

        for (char simbolo : cadena.toCharArray()) {
            Set<Integer> nuevosEstados = new HashSet<>();

            // Transiciones regulares
            estadosActuales.forEach(estado -> {
                if (transiciones.containsKey(estado) && transiciones.get(estado).containsKey(simbolo)) {
                    nuevosEstados.addAll(transiciones.get(estado).get(simbolo));
                }
            });

            // Transiciones lambda
            estadosActuales.stream()
                    .map(transicionesLambda::get)
                    .filter(destinos -> destinos != null)
                    .forEach(nuevosEstados::addAll);

            estadosActuales = nuevosEstados;
        }

        return estadosActuales.stream().anyMatch(estadosFinales::contains);
    }

    /**
     * Obtiene el tipo de autómata.
     *
     * @return Una cadena de texto que indica el tipo de autómata.
     */
    @Override
    public String getTipo() {
        return "No Determinista con Lambda";
    }
}


