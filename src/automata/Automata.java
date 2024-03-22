package automata;

import java.util.Map;
import java.util.Set;

public abstract class Automata {
    public Set<Integer> estados;
    public Set<Character> alfabeto;
    public int estadoInicial;
    public Set<Integer> estadosFinales;
    public Map<Integer, Map<Character, Set<Integer>>> transiciones;

    public Set<Integer> getEstados() {
        return estados;
    }

    public void setEstados(Set<Integer> estados) {
        this.estados = estados;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Set<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public int getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(int estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Set<Integer> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(Set<Integer> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public Map<Integer, Map<Character, Set<Integer>>> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Map<Integer, Map<Character, Set<Integer>>> transiciones) {
        this.transiciones = transiciones;
    }

    public Automata(Set<Integer> estados, Set<Character> alfabeto, int estadoInicial, Set<Integer> estadosFinales, Map<Integer, Map<Character, Set<Integer>>> transiciones) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.transiciones = transiciones;
    }

    public abstract boolean procesarCadena(String cadena);

    public abstract String getTipo();

    public boolean esDeterminista() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean validarCadena(String cadena) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

