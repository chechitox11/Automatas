package tipo_automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import automata.Automata;

public class AutomataDeterminista extends Automata {
    private Map<Integer, Map<Character, Set<Integer>>> transiciones;

    public AutomataDeterminista(Set<Integer> estados, Set<Character> alfabeto, Map<Integer, Map<Character, Set<Integer>>> transiciones, int estadoInicial, Set<Integer> estadosFinales) {
        super(estados, alfabeto, estadoInicial, estadosFinales, transiciones);
        this.transiciones = transiciones;
    }

    public boolean procesarCadena(String cadena) {
        int estadoActual = estadoInicial;
        for (char c : cadena.toCharArray()) {
            if (!alfabeto.contains(c)) {
                System.out.println("La cadena contiene s�mbolos no v�lidos.");
                return false;
            }
            if (!transiciones.containsKey(estadoActual) || !transiciones.get(estadoActual).containsKey(c)) {
                System.out.println("No hay transici�n definida para el estado " + estadoActual + " con el s�mbolo " + c);
                return false;
            }
            estadoActual = transiciones.get(estadoActual).get(c).iterator().next();
        }
        return estadosFinales.contains(estadoActual);
    }

    public String getTipo() {
        return "Determinista";
    }

    public AutomataNoDeterminista convertirANoDeterminista() {
        Map<Integer, Map<Character, Set<Integer>>> transicionesNoDet = new HashMap<>();

        // Iterar sobre todos los estados del aut�mata determinista
        for (int estado : getEstados()) {
            // Obtener las transiciones del estado actual
            Map<Character, Set<Integer>> transicionesEstado = transiciones.getOrDefault(estado, new HashMap<>());

            // Iterar sobre todos los s�mbolos del alfabeto
            for (char simbolo : getAlfabeto()) {
                // Obtener los destinos de la transici�n para el s�mbolo actual
                Set<Integer> destinos = transicionesEstado.getOrDefault(simbolo, new HashSet<>());

                // Agregar la transici�n al conjunto de transiciones del estado actual
                if (!transicionesNoDet.containsKey(estado)) {
                    transicionesNoDet.put(estado, new HashMap<>());
                }
                transicionesNoDet.get(estado).put(simbolo, destinos);

                // Si hay destinos, agregar tambi�n las transiciones para los estados alcanzables
                // por el s�mbolo actual
                if (!destinos.isEmpty()) {
                    for (int destino : destinos) {
                        // Obtener las transiciones del estado destino
                        Map<Character, Set<Integer>> transicionesDestino = transiciones.getOrDefault(destino, new HashMap<>());
                        // Agregar las transiciones del estado destino al conjunto de transiciones del estado actual
                        for (Map.Entry<Character, Set<Integer>> entry : transicionesDestino.entrySet()) {
                            char nuevoSimbolo = entry.getKey();
                            Set<Integer> nuevosDestinos = entry.getValue();
                            if (!transicionesNoDet.containsKey(estado)) {
                                transicionesNoDet.put(estado, new HashMap<>());
                            }
                            transicionesNoDet.get(estado).computeIfAbsent(nuevoSimbolo, k -> new HashSet<>()).addAll(nuevosDestinos);
                        }
                    }
                }
            }
        }

        // Crear el aut�mata no determinista
        AutomataNoDeterminista automataNoDet = new AutomataNoDeterminista(getEstados(), getAlfabeto(), transicionesNoDet, getEstadoInicial(), getEstadosFinales());
        return automataNoDet;
    }

}

