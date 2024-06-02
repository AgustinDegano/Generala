package org.example;

import java.util.Arrays;

public class JuegoGenerala {
    private Jugador jugador1;
    private Jugador jugador2;
    private Dado[] dados;
    private int turno;
    private int tirosRestantes;

    public JuegoGenerala(String nombre1, String nombre2) {
        jugador1 = new Jugador(nombre1);
        jugador2 = new Jugador(nombre2);
        dados = new Dado[5];
        for (int i = 0; i < 5; i++) {
            dados[i] = new Dado();
        }
        turno = 1;
        tirosRestantes = 3; // Cada jugador tiene 3 tiros por turno
    }

    public void lanzarDados(boolean[] relanzar) {
        for (int i = 0; i < dados.length; i++) {
            if (relanzar[i]) {
                dados[i].lanzar();
            }
        }
        tirosRestantes--;
    }

    public int[] getValoresDados() {
        int[] valores = new int[5];
        for (int i = 0; i < 5; i++) {
            valores[i] = dados[i].getValor();
        }
        return valores;
    }

    public Jugador getJugadorActual() {
        return turno == 1 ? jugador1 : jugador2;
    }

    public int getTirosRestantes() {
        return tirosRestantes;
    }

    public void cambiarTurno() {
        turno = turno == 1 ? 2 : 1;
        tirosRestantes = 3; // Reseteamos los tiros para el siguiente jugador
    }

    public int calcularPuntaje(int categoria) {
        int[] valores = getValoresDados();
        Arrays.sort(valores);
        switch (categoria) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: // Números del 1 al 6
                int numero = categoria + 1;
                int suma = 0;
                for (int valor : valores) {
                    if (valor == numero) {
                        suma += numero;
                    }
                }
                return suma;
            case 6: // Escalera
                if (Arrays.equals(valores, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(valores, new int[]{2, 3, 4, 5, 6})) {
                    return 25;
                }
                return 0;
            case 7: // Full
                if ((valores[0] == valores[1] && valores[2] == valores[3] && valores[3] == valores[4]) ||
                        (valores[0] == valores[1] && valores[1] == valores[2] && valores[3] == valores[4])) {
                    return 30;
                }
                return 0;
            case 8: // Poker
                if ((valores[0] == valores[1] && valores[1] == valores[2] && valores[2] == valores[3]) ||
                        (valores[1] == valores[2] && valores[2] == valores[3] && valores[3] == valores[4])) {
                    return 40;
                }
                return 0;
            case 9: // Generala
                if (valores[0] == valores[1] && valores[1] == valores[2] && valores[2] == valores[3] && valores[3] == valores[4]) {
                    return 50;
                }
                return 0;
            case 10: // Doble Generala
                if (valores[0] == valores[1] && valores[1] == valores[2] && valores[2] == valores[3] && valores[3] == valores[4]) {
                    return 100;
                }
                return 0;
            default:
                return 0;
        }
    }
}