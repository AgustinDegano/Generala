package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGrafica extends JFrame {
    private JuegoGenerala juego;
    private JLabel[] dadosLabels;
    private JCheckBox[] tirarDadoCheckboxes;
    private JLabel turnoLabel;
    private JLabel tirosRestantesLabel;
    private JButton lanzarButton;
    private JButton cambiarTurnoButton;
    private JPanel puntajesPanel1;
    private JPanel puntajesPanel2;
    private JComboBox<String> categoriasComboBox;
    private JButton guardarPuntajeButton;

    public InterfazGrafica() {
        setTitle("Generala");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar el juego
        juego = new JuegoGenerala("Jugador 1", "Jugador 2");

        // Panel para los dados
        JPanel dadosPanel = new JPanel(new GridLayout(6, 1));
        dadosLabels = new JLabel[5];
        tirarDadoCheckboxes = new JCheckBox[5];
        for (int i = 0; i < 5; i++) {
            dadosLabels[i] = new JLabel();
            tirarDadoCheckboxes[i] = new JCheckBox("Tirar");
            dadosPanel.add(tirarDadoCheckboxes[i]);
            dadosPanel.add(dadosLabels[i]);
        }

        // Panel para el control de juego
        JPanel controlPanel = new JPanel();
        turnoLabel = new JLabel("Turno de: " + juego.getJugadorActual().getNombre());
        tirosRestantesLabel = new JLabel("Tiros restantes: " + juego.getTirosRestantes());
        lanzarButton = new JButton("Lanzar Dados");
        lanzarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] tirar = new boolean[5];
                for (int i = 0; i < 5; i++) {
                    tirar[i] = tirarDadoCheckboxes[i].isSelected();
                }
                if (juego.getTirosRestantes() > 0) {
                    juego.lanzarDados(tirar);
                    int[] valores = juego.getValoresDados();
                    for (int i = 0; i < 5; i++) {
                        dadosLabels[i].setText("DADO " + (i + 1) + ": " + valores[i]);
                    }
                    tirosRestantesLabel.setText("Tiros restantes: " + juego.getTirosRestantes());
                }
                if (juego.getTirosRestantes() == 0) {
                    lanzarButton.setEnabled(false);
                }
            }
        });

        cambiarTurnoButton = new JButton("Cambiar Turno");
        cambiarTurnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.cambiarTurno();
                turnoLabel.setText("Turno de: " + juego.getJugadorActual().getNombre());
                tirosRestantesLabel.setText("Tiros restantes: " + juego.getTirosRestantes());
                lanzarButton.setEnabled(true); // Habilitar botón de lanzar dados
                // Marcar todos los checkboxes para tirar dados
                for (int i = 0; i < 5; i++) {
                    tirarDadoCheckboxes[i].setSelected(true);
                }
                actualizarPuntajes();
            }
        });

        categoriasComboBox = new JComboBox<>(juego.getJugadorActual().getCategorias());
        guardarPuntajeButton = new JButton("Guardar Puntaje");
        guardarPuntajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int categoria = categoriasComboBox.getSelectedIndex();
                int puntaje = juego.calcularPuntaje(categoria);
                juego.getJugadorActual().setPuntaje(categoria, puntaje);
                actualizarPuntajes();
            }
        });

        controlPanel.add(turnoLabel);
        controlPanel.add(tirosRestantesLabel);
        controlPanel.add(lanzarButton);
        controlPanel.add(cambiarTurnoButton);
        controlPanel.add(categoriasComboBox);
        controlPanel.add(guardarPuntajeButton);

        // Panel para los puntajes
        JPanel puntajesPanel = new JPanel(new GridLayout(1, 2));
        puntajesPanel1 = new JPanel(new GridLayout(13, 2));
        puntajesPanel2 = new JPanel(new GridLayout(13, 2));

        actualizarPuntajes();

        puntajesPanel.add(puntajesPanel1);
        puntajesPanel.add(puntajesPanel2);

        add(dadosPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(puntajesPanel, BorderLayout.EAST);
    }

    private void actualizarPuntajes() {
        puntajesPanel1.removeAll();
        puntajesPanel2.removeAll();

        Jugador jugador1 = juego.getJugadorActual();
        Jugador jugador2 = juego.getJugadorActual();

        String[] categorias = jugador1.getCategorias();

        for (int i = 0; i < categorias.length; i++) {
            puntajesPanel1.add(new JLabel(categorias[i]));
            puntajesPanel1.add(new JLabel(String.valueOf(jugador1.getPuntajes()[i])));

            puntajesPanel2.add(new JLabel(categorias[i]));
            puntajesPanel2.add(new JLabel(String.valueOf(jugador2.getPuntajes()[i])));
        }

        puntajesPanel1.revalidate();
        puntajesPanel1.repaint();
        puntajesPanel2.revalidate();
        puntajesPanel2.repaint();
    }

    public static void main(String[] args) {
        InterfazGrafica ventana = new InterfazGrafica();
        ventana.setVisible(true);
    }
}
