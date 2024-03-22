package tipo_automata;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import automata.Automata;

public class AutomataNoDeterminista extends Automata {
    private Map<Integer, Map<Character, Set<Integer>>> transiciones;

    public AutomataNoDeterminista(Set<Integer> estados, Set<Character> alfabeto, Map<Integer, Map<Character, Set<Integer>>> transiciones, int estadoInicial, Set<Integer> estadosFinales) {
        super(estados, alfabeto, estadoInicial, estadosFinales, transiciones);
        this.transiciones = transiciones;
    }

    public boolean procesarCadena(String cadena) {
        Set<Integer> estadosActuales = new HashSet<>();
        estadosActuales.add(estadoInicial);

        for (char simbolo : cadena.toCharArray()) {
            Set<Integer> nuevosEstados = new HashSet<>();

            // Transiciones regulares
            for (int estado : estadosActuales) {
                if (transiciones.containsKey(estado) && transiciones.get(estado).containsKey(simbolo)) {
                    nuevosEstados.addAll(transiciones.get(estado).get(simbolo));
                }
            }

            estadosActuales = nuevosEstados;
        }

        return estadosActuales.stream().anyMatch(estadosFinales::contains);
    }

    public String getTipo() {
        return "No Determinista";
    }
    
    
}


