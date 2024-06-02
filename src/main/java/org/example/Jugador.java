package org.example;

public class Jugador {
    private String nombre;
    private int[] puntajes;
    private String[] categorias;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntajes = new int[13]; // Ejemplo de 13 categorías de puntajes
        this.categorias = new String[]{"1", "2", "3", "4", "5", "6", "Escalera", "Full", "Poker", "Generala", "Doble Generala", "Bonus", "Total"};
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getPuntajes() {
        return puntajes;
    }

    public void setPuntaje(int indice, int puntaje) {
        puntajes[indice] = puntaje;
    }

    public String[] getCategorias() {
        return categorias;
    }
}

