package main;

import tipo_automata.*;
import java.util.*;
import automata.Automata;
@SuppressWarnings("resource")
public class Main {
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Ingrese el alfabeto deseado (por ejemplo, a,b,c): ");
	    String[] alfabetoArray = scanner.nextLine().split(",");
	    Set<Character> alfabeto = new HashSet<>();
	    for (String s : alfabetoArray) {
	        alfabeto.add(s.charAt(0));
	    }

	    System.out.print("Ingrese una cadena para procesar: ");
	    String cadena = scanner.nextLine();

	    Automata automata = crearAutomata(alfabeto, cadena);
	    System.out.println("El aut�mata es de tipo: " + automata.getTipo());

	    scanner.close();
	}

	private static Automata crearAutomata(Set<Character> alfabeto, String cadena) {
	    Scanner scanner = new Scanner(System.in);

	    // Solicitar al usuario que ingrese el tipo de aut�mata
	    System.out.println("Seleccione el tipo de aut�mata:");
	    System.out.println("1. Determinista");
	    System.out.println("2. No determinista");
	    System.out.println("3. No determinista con Lambda");
	    int opcionDeterminista = scanner.nextInt();

	    int numEstados = 0; // Variable para almacenar el n�mero de estados

	    switch (opcionDeterminista) {
	        case 1:
	            System.out.print("Ingrese el n�mero total de estados: ");
	            numEstados = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer del scanner

	            // Solicitar al usuario si desea ingresar las transiciones manualmente
	            System.out.print("�Desea ingresar manualmente las transiciones entre estados? (s/n): ");
	            String opcionManual = scanner.nextLine();
	            if (opcionManual.equalsIgnoreCase("s")) {
	                return crearAutomataDeterminista(alfabeto, numEstados, cadena);
	            } else {
	                return crearAutomataDeterminista(alfabeto, cadena);
	            }

	        case 2:
	            System.out.print("Ingrese el n�mero total de estados: ");
	            numEstados = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer del scanner

	            // Solicitar al usuario si desea ingresar manualmente las transiciones
	            System.out.print("�Desea ingresar manualmente las transiciones entre estados? (s/n): ");
	            String opcionManualND = scanner.nextLine();
	            if (opcionManualND.equalsIgnoreCase("s")) {
	                return crearAutomataNoDeterminista(alfabeto, numEstados, cadena);
	            } else {
	                return crearAutomataNoDeterminista(alfabeto, cadena);
	            }

	        case 3:
	            System.out.print("Ingrese el n�mero total de estados: ");
	            numEstados = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer del scanner

	            // Solicitar al usuario si desea ingresar manualmente las transiciones
	            System.out.print("�Desea ingresar manualmente las transiciones entre estados? (s/n): ");
	            String opcionManualNDLambda = scanner.nextLine();
	            if (opcionManualNDLambda.equalsIgnoreCase("s")) {
	                return crearAutomataNoDeterministaConLambda(alfabeto,numEstados ,cadena);
	            } else {
	                return crearAutomataNoDeterministaConLambda(alfabeto, cadena);
	            }

	        default:
	            System.out.println("Opci�n no v�lida. Por favor, seleccione una opci�n v�lida.");
	            return crearAutomata(alfabeto, cadena); // Reiniciar el proceso
	    }
	}



	private static Set<Integer> crearConjuntoEstados(int numEstados) {
        Set<Integer> estados = new HashSet<>();
        for (int i = 0; i < numEstados; i++) {
            estados.add(i);
        }
        return estados;
    }

    private static AutomataDeterminista crearAutomataDeterminista(Set<Character> alfabeto, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Crear un aut�mata aleatorio
        Random random = new Random();
        int numEstados = random.nextInt(5) + 2; // Generar un n�mero aleatorio de estados entre 2 y 6
        int numEstadosFinales = random.nextInt(numEstados) + 1; // Generar un n�mero aleatorio de estados finales entre 1 y el n�mero total de estados
        int estadoInicial = random.nextInt(numEstados); // Elegir un estado inicial aleatorio
        Set<Integer> estadosFinales = new HashSet<>();
        for (int i = 0; i < numEstadosFinales; i++) {
            estadosFinales.add(random.nextInt(numEstados)); // Elegir estados finales aleatorios
        }
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char simbolo : alfabeto) {
                Set<Integer> destinos = new HashSet<>();
                destinos.add(random.nextInt(numEstados)); // Agregar un destino aleatorio
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);
        }

        // Crear el aut�mata determinista con la informaci�n aleatoria
        AutomataDeterminista automata = new AutomataDeterminista(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);

        // Imprimir los estados de transici�n
        System.out.println("Estados de transici�n:");
        for (Map.Entry<Integer, Map<Character, Set<Integer>>> entry : transiciones.entrySet()) {
            int estadoOrigen = entry.getKey();
            Map<Character, Set<Integer>> transicionesEstado = entry.getValue();
            for (Map.Entry<Character, Set<Integer>> transicion : transicionesEstado.entrySet()) {
                char simbolo = transicion.getKey();
                Set<Integer> destinos = transicion.getValue();
                System.out.println("Desde el estado " + estadoOrigen + " con el s�mbolo '" + simbolo + "' se va a: " + destinos);
            }
        }

        scanner.close();

        return automata;
    }



    private static AutomataDeterminista crearAutomataDeterminista(Set<Character> alfabeto, int numEstados, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese las transiciones entre los estados
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            System.out.println("Ingrese las transiciones para el estado " + estado + ":");
            for (char simbolo : alfabeto) {
                System.out.print("Transici�n para el s�mbolo '" + simbolo + "': ");
                String destinosInput = scanner.nextLine();
                Set<Integer> destinos = new HashSet<>();
                String[] destinosArray = destinosInput.split(",");
                for (String destinoStr : destinosArray) {
                    try {
                        int destino = Integer.parseInt(destinoStr.trim());
                        destinos.add(destino);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un n�mero v�lido para el destino.");
                    }
                }
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);
        }

        // Generar autom�ticamente los estados finales basados en el aut�mata creado
        Set<Integer> estadosFinales = new HashSet<>();
        for (int estado : transiciones.keySet()) {
            boolean tieneTransiciones = false;
            Map<Character, Set<Integer>> transicionesEstado = transiciones.get(estado);
            for (char simbolo : alfabeto) {
                if (transicionesEstado.containsKey(simbolo) && !transicionesEstado.get(simbolo).isEmpty()) {
                    tieneTransiciones = true;
                    break;
                }
            }
            if (!tieneTransiciones) {
                estadosFinales.add(estado);
            }
        }

        // Solicitar al usuario que ingrese el estado inicial
        System.out.print("Ingrese el estado inicial: ");
        int estadoInicial = scanner.nextInt();

        // Crear el aut�mata determinista con la informaci�n proporcionada por el usuario
        AutomataDeterminista automata = new AutomataDeterminista(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);


        boolean esEstadoFinal = automata.procesarCadena(cadena);
        System.out.println("La cadena lleva al aut�mata a un estado final: " + esEstadoFinal);

        scanner.close();

        return automata;
    }


    
    private static AutomataNoDeterminista crearAutomataNoDeterminista(Set<Character> alfabeto, int numEstados, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese las transiciones entre los estados
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            System.out.println("Ingrese las transiciones para el estado " + estado + ":");
            for (char simbolo : alfabeto) {
                System.out.print("Transici�n para el s�mbolo '" + simbolo + "': ");
                String destinosInput = scanner.nextLine();
                Set<Integer> destinos = new HashSet<>();
                String[] destinosArray = destinosInput.split(",");
                for (String destinoStr : destinosArray) {
                    try {
                        int destino = Integer.parseInt(destinoStr.trim());
                        destinos.add(destino);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un n�mero v�lido para el destino.");
                    }
                }
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);
        }

        // Solicitar al usuario que ingrese el estado inicial
        System.out.print("Ingrese el estado inicial: ");
        int estadoInicial = scanner.nextInt();

        // Solicitar al usuario que ingrese los estados finales
        Set<Integer> estadosFinales = new HashSet<>();
        System.out.print("Ingrese el n�mero de estados finales: ");
        int numEstadosFinales = scanner.nextInt();
        System.out.println("Ingrese los estados finales:");
        for (int i = 0; i < numEstadosFinales; i++) {
            System.out.print("Estado final " + (i + 1) + ": ");
            int estadoFinal = scanner.nextInt();
            estadosFinales.add(estadoFinal);
        }

        // Crear el aut�mata no determinista con la informaci�n proporcionada por el usuario
        AutomataNoDeterminista automata = new AutomataNoDeterminista(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);

        // Identificar el tipo de aut�mata y procesar la cadena
        System.out.println("El aut�mata es de tipo: " + automata.getTipo());
        boolean esEstadoFinal = automata.procesarCadena(cadena);
        System.out.println("La cadena lleva al aut�mata a un estado final: " + esEstadoFinal);

        scanner.close();

        return automata;
    }
    
    


    private static AutomataNoDeterminista crearAutomataNoDeterminista(Set<Character> alfabeto, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Crear un aut�mata aleatorio
        Random random = new Random();
        int numEstados = random.nextInt(5) + 2; // Generar un n�mero aleatorio de estados entre 2 y 6
        int numEstadosFinales = random.nextInt(numEstados) + 1; // Generar un n�mero aleatorio de estados finales entre 1 y el n�mero total de estados
        int estadoInicial = random.nextInt(numEstados); // Elegir un estado inicial aleatorio
        Set<Integer> estadosFinales = new HashSet<>();
        for (int i = 0; i < numEstadosFinales; i++) {
            estadosFinales.add(random.nextInt(numEstados)); // Elegir estados finales aleatorios
        }
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char simbolo : alfabeto) {
                Set<Integer> destinos = new HashSet<>();
                destinos.add(random.nextInt(numEstados)); // Agregar un destino aleatorio
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);
        }

        // Crear el aut�mata no determinista con la informaci�n aleatoria
        AutomataNoDeterminista automata = new AutomataNoDeterminista(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);

        // Imprimir los estados de transici�n
        System.out.println("Estados de transici�n:");
        for (Map.Entry<Integer, Map<Character, Set<Integer>>> entry : transiciones.entrySet()) {
            int estadoOrigen = entry.getKey();
            Map<Character, Set<Integer>> transicionesEstado = entry.getValue();
            for (Map.Entry<Character, Set<Integer>> transicion : transicionesEstado.entrySet()) {
                char simbolo = transicion.getKey();
                Set<Integer> destinos = transicion.getValue();
                System.out.println("Desde el estado " + estadoOrigen + " con el s�mbolo '" + simbolo + "' se va a: " + destinos);
            }
        }

        scanner.close();

        return automata;
    }
    
    private static AutomataNoDeterministaConLambda crearAutomataNoDeterministaConLambda(Set<Character> alfabeto, int numEstados, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Map<Integer, Set<Integer>> transicionesLambda = new HashMap<>();
        Random random = new Random();

        // Generar las transiciones regulares aleatorias
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char simbolo : alfabeto) {
                Set<Integer> destinos = new HashSet<>();
                destinos.add(random.nextInt(numEstados)); // Agregar un destino aleatorio
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);
        }

        // Generar las transiciones lambda aleatorias y agregarlas a las transiciones Lambda
        for (int estado = 0; estado < numEstados; estado++) {
            Set<Integer> destinosLambda = new HashSet<>();
            destinosLambda.add(random.nextInt(numEstados)); // Agregar un destino lambda aleatorio
            transicionesLambda.put(estado, destinosLambda);
        }

        // Crear el aut�mata no determinista con lambda
        AutomataNoDeterministaConLambda automata = new AutomataNoDeterministaConLambda(
                crearConjuntoEstados(numEstados), alfabeto, transiciones, transicionesLambda, 0, new HashSet<>()
        );

        // Imprimir los estados de transici�n
        System.out.println("Estados de transici�n:");
        for (Map.Entry<Integer, Map<Character, Set<Integer>>> entry : transiciones.entrySet()) {
            int estadoOrigen = entry.getKey();
            Map<Character, Set<Integer>> transicionesEstado = entry.getValue();
            for (Map.Entry<Character, Set<Integer>> transicion : transicionesEstado.entrySet()) {
                char simbolo = transicion.getKey();
                Set<Integer> destinos = transicion.getValue();
                System.out.println("Desde el estado " + estadoOrigen + " con el s�mbolo '" + simbolo + "' se va a: " + destinos);
            }
        }

        // Imprimir las transiciones lambda
        System.out.println("Transiciones Lambda:");
        for (Map.Entry<Integer, Set<Integer>> entry : transicionesLambda.entrySet()) {
            int estadoOrigen = entry.getKey();
            Set<Integer> destinosLambda = entry.getValue();
            System.out.println("Desde el estado " + estadoOrigen + " con la transici�n lambda se va a: " + destinosLambda);
        }

        return automata;
    }
    
    private static AutomataNoDeterministaConLambda crearAutomataNoDeterministaConLambda(Set<Character> alfabeto, String cadena) {
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Map<Integer, Set<Integer>> transicionesLambda = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese las transiciones entre los estados
        System.out.print("Ingrese el n�mero total de estados: ");
        int numEstados = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            System.out.println("Ingrese las transiciones para el estado " + estado + ":");
            for (char simbolo : alfabeto) {
                System.out.print("Transici�n para el s�mbolo '" + simbolo + "': ");
                String destinosInput = scanner.nextLine();
                Set<Integer> destinos = new HashSet<>();
                String[] destinosArray = destinosInput.split(",");
                for (String destinoStr : destinosArray) {
                    try {
                        int destino = Integer.parseInt(destinoStr.trim());
                        destinos.add(destino);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un n�mero v�lido para el destino.");
                    }
                }
                transicionesEstado.put(simbolo, destinos);
            }
            transiciones.put(estado, transicionesEstado);

            // Solicitar al usuario que ingrese las transiciones lambda para el estado actual
            System.out.print("Transiciones Lambda para el estado " + estado + ": ");
            String destinosLambdaInput = scanner.nextLine();
            Set<Integer> destinosLambda = new HashSet<>();
            String[] destinosLambdaArray = destinosLambdaInput.split(",");
            for (String destinoStr : destinosLambdaArray) {
                try {
                    int destino = Integer.parseInt(destinoStr.trim());
                    destinosLambda.add(destino);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un n�mero v�lido para el destino lambda.");
                }
            }
            transicionesLambda.put(estado, destinosLambda);
        }

        // Solicitar al usuario que ingrese el estado inicial
        System.out.print("Ingrese el estado inicial: ");
        int estadoInicial = scanner.nextInt();

        // Crear el aut�mata no determinista con lambda
        AutomataNoDeterministaConLambda automata = new AutomataNoDeterministaConLambda(
                crearConjuntoEstados(numEstados), alfabeto, transiciones, transicionesLambda, estadoInicial, new HashSet<>()
        );

        scanner.close();

        return automata;
    }



}



    /*
     
     
    private static Automata crearAutomataNoDeterminista(Set<Character> alfabeto, int numEstados, int numEstadosFinales) {
        // Implementar la creaci�n de un aut�mata no determinista seg�n tus necesidades
        // Aqu� se crea un aut�mata no determinista aleatorio con los par�metros proporcionados
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Random random = new Random();
        int estadoInicial = random.nextInt(numEstados); // Elegir un estado inicial aleatorio
        Set<Integer> estadosFinales = new HashSet<>();
        for (int i = 0; i < numEstadosFinales; i++) {
            estadosFinales.add(random.nextInt(numEstados)); // Elegir estados finales aleatorios
        }
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char simbolo : alfabeto) {
                Set<Integer> nuevosEstados = new HashSet<>();
                nuevosEstados.add(random.nextInt(numEstados));
                transicionesEstado.put(simbolo, nuevosEstados);
            }
            transiciones.put(estado, transicionesEstado);
        }
        return new AutomataNoDeterminista(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);
    }

    private static Automata crearAutomataNoDeterministaConLambda(Set<Character> alfabeto, int numEstados, int numEstadosFinales) {
        // Implementar la creaci�n de un aut�mata no determinista con transiciones lambda seg�n tus necesidades
        // En este ejemplo, simplemente se crea un aut�mata no determinista con transiciones lambda aleatorio con los par�metros proporcionados
        Map<Integer, Map<Character, Set<Integer>>> transiciones = new HashMap<>();
        Map<Integer, Set<Integer>> transicionesLambda = new HashMap<>();
        Random random = new Random();
        int estadoInicial = random.nextInt(numEstados); // Elegir un estado inicial aleatorio
        Set<Integer> estadosFinales = new HashSet<>();
        for (int i = 0; i < numEstadosFinales; i++) {
            estadosFinales.add(random.nextInt(numEstados)); // Elegir estados finales aleatorios
        }
        for (int estado = 0; estado < numEstados; estado++) {
            Map<Character, Set<Integer>> transicionesEstado = new HashMap<>();
            for (char simbolo : alfabeto) {
                Set<Integer> nuevosEstados = new HashSet<>();
                nuevosEstados.add(random.nextInt(numEstados));
                transicionesEstado.put(simbolo, nuevosEstados);
            }
            transiciones.put(estado, transicionesEstado);
            // Agregar transiciones lambda
            Set<Integer> transicionesLambdaEstado = new HashSet<>();
            for (int i = 0; i < random.nextInt(numEstados); i++) {
                transicionesLambdaEstado.add(random.nextInt(numEstados));
            }
            transicionesLambda.put(estado, transicionesLambdaEstado);
        }
        return new AutomataNoDeterministaConLambda(crearConjuntoEstados(numEstados), alfabeto, transiciones, estadoInicial, estadosFinales);
    }
    

*/

