package evolucion;

import java.util.HashMap;
import automata.*;
import tipo_automata.AutomataDeterminista;
import tipo_automata.AutomataNoDeterminista;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public abstract class Evolucion extends Automata {

    public Evolucion(Set<Integer> estados, Set<Character> alfabeto, Map<Integer, Map<Character, Set<Integer>>> transiciones, int estadoInicial, Set<Integer> estadosFinales) {
        super(estados, alfabeto, estadoInicial, estadosFinales, transiciones);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ingresar el alfabeto desde la terminal
        System.out.println("Ingrese los caracteres del alfabeto separados por espacio (ejemplo: a b):");
        String alfabetoInput = scanner.nextLine();
        Set<Character> alfabeto = new HashSet<>();
        for (String character : alfabetoInput.split(" ")) {
            alfabeto.add(character.charAt(0));
        }

        // Definir las transiciones del aut�mata determinista
        Map<Integer, Map<Character, Set<Integer>>> transicionesDet = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            System.out.println("Ingrese las transiciones para el estado " + i + ":");
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char symbol : alfabeto) {
                System.out.println("Ingrese los estados de transición para el símbolo '" + symbol + "' separados por espacio (ejemplo: 1 2 3):");
                String destinosInput = scanner.nextLine();
                Set<Integer> destinos = new HashSet<>();
                for (String destino : destinosInput.split(" ")) {
                    destinos.add(Integer.parseInt(destino));
                }
                transicionesEstado.put(symbol, destinos);
            }
            transicionesDet.put(i, transicionesEstado);
        }

        // Ingresar el estado inicial desde la terminal
        System.out.println("Ingrese el estado inicial:");
        int estadoInicial = Integer.parseInt(scanner.nextLine());

        // Ingresar los estados finales desde la terminal
        System.out.println("Ingrese los estados finales separados por espacio (ejemplo: 2 3):");
        String estadosFinalesInput = scanner.nextLine();
        Set<Integer> estadosFinales = new HashSet<>();
        for (String estado : estadosFinalesInput.split(" ")) {
            estadosFinales.add(Integer.parseInt(estado));
        }

        scanner.close();

        // Crear el aut�mata determinista con los valores ingresados
        AutomataDeterminista automataDet = new AutomataDeterminista(
                Set.of(0, 1, 2, 3, 4),
                alfabeto,
                transicionesDet,
                estadoInicial,
                estadosFinales
        );

        // Imprimir el aut�mata determinista original
        System.out.println("Aut�mata Determinista Original:");
        imprimirAutomata(automataDet);

        // Convertir el aut�mata determinista en uno no determinista
        AutomataNoDeterminista automataNoDet = automataDet.convertirANoDeterminista();

        // Imprimir el aut�mata no determinista resultante
        System.out.println("\nAut�mata No Determinista Resultante:");
        imprimirAutomata(automataNoDet);
    }

    // M�todo para imprimir un aut�mata
    private static void imprimirAutomata(Automata automata) {
        System.out.println("Estados:");
        System.out.println(automata.getEstados());
        System.out.println("Alfabeto:");
        System.out.println(automata.getAlfabeto());
        System.out.println("Estado Inicial:");
        System.out.println(automata.getEstadoInicial());
        System.out.println("Estados Finales:");
        System.out.println(automata.getEstadosFinales());
        System.out.println("Transiciones:");
        for (Map.Entry<Integer, Map<Character, Set<Integer>>> entry : automata.getTransiciones().entrySet()) {
            int estadoOrigen = entry.getKey();
            Map<Character, Set<Integer>> transicionesEstado = entry.getValue();
            for (Map.Entry<Character, Set<Integer>> transicion : transicionesEstado.entrySet()) {
                char simbolo = transicion.getKey();
                Set<Integer> destinos = transicion.getValue();
                System.out.println("Desde el estado " + estadoOrigen + " con el s�mbolo '" + simbolo + "' se va a: " + destinos);
            }
        }
    }
}
