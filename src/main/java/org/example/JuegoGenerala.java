package org.example;

public class JuegoGenerala {
    private Jugador[] jugadores;
    private int jugadorActual;
    private int tirosRestantes;
    private int[] valoresDados;

    public JuegoGenerala(String nombreJugador1, String nombreJugador2) {
        jugadores = new Jugador[2];
        jugadores[0] = new Jugador(nombreJugador1);
        jugadores[1] = new Jugador(nombreJugador2);
        jugadorActual = 0;
        tirosRestantes = 3;
        valoresDados = new int[5];
    }

    public Jugador getJugador(int indice) {
        if (indice >= 0 && indice < jugadores.length) {
            return jugadores[indice];
        } else {
            throw new IllegalArgumentException("Índice de jugador no válido");
        }
    }

    public Jugador getJugadorActual() {
        return jugadores[jugadorActual];
    }

    public int getTirosRestantes() {
        return tirosRestantes;
    }

    public void lanzarDados(boolean[] tirar) {
        for (int i = 0; i < valoresDados.length; i++) {
            if (tirar[i]) {
                valoresDados[i] = (int) (Math.random() * 6) + 1;
            }
        }
        tirosRestantes--;
    }

    public int[] getValoresDados() {
        return valoresDados;
    }

    public void cambiarTurno() {
        jugadorActual = (jugadorActual + 1) % 2;
        tirosRestantes = 3;
    }

    public int calcularPuntaje(int categoria) {
        int[] conteo = new int[6];
        for (int valor : valoresDados) {
            conteo[valor - 1]++;
        }

        switch (categoria) {
            case 0: // Escalera
                boolean escalera = (conteo[0] >= 1 && conteo[1] >= 1 && conteo[2] >= 1 && conteo[3] >= 1 && conteo[4] >= 1) ||
                        (conteo[1] >= 1 && conteo[2] >= 1 && conteo[3] >= 1 && conteo[4] >= 1 && conteo[5] >= 1);
                return escalera ? 25 : 0;
            case 1: // Full
                boolean trio = false, par = false;
                for (int c : conteo) {
                    if (c == 3) trio = true;
                    if (c == 2) par = true;
                }
                return (trio && par) ? 30 : 0;
            case 2: // Poker
                for (int c : conteo) {
                    if (c >= 4) return 40;
                }
                return 0;
            case 3: // Generala
                for (int c : conteo) {
                    if (c == 5) return 50;
                }
                return 0;
            case 4: // Unos
            case 5: // Doses
            case 6: // Treses
            case 7: // Cuatros
            case 8: // Cincos
            case 9: // Seises
                int numero = categoria - 3;
                return conteo[numero - 1] * numero;
            default:
                throw new IllegalArgumentException("Categoría no válida");
        }
    }
}

class Jugador {
    private String nombre;
    private int[] puntajes;
    private String[] categorias = {
            "Escalera", "Full", "Poker", "Generala", "1", "2", "3", "4", "5", "6"
    };

    public Jugador(String nombre) {
        this.nombre = nombre;
        puntajes = new int[categorias.length];
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getPuntajes() {
        return puntajes;
    }

    public String[] getCategorias() {
        return categorias;
    }

    public void setPuntaje(int categoria, int puntaje) {
        puntajes[categoria] = puntaje;
    }
}
