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
    private JPanel puntajesPanel;
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
        JPanel dadosPanel = new JPanel(new GridLayout(6, 2));
        dadosLabels = new JLabel[5];
        tirarDadoCheckboxes = new JCheckBox[5];
        for (int i = 0; i < 5; i++) {
            dadosLabels[i] = new JLabel("DADO " + (i + 1) + ": ", SwingConstants.CENTER);
            dadosLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
            dadosLabels[i].setForeground(Color.RED);
            tirarDadoCheckboxes[i] = new JCheckBox("Tirar");
            tirarDadoCheckboxes[i].setSelected(true);
            dadosPanel.add(dadosLabels[i]);
            dadosPanel.add(tirarDadoCheckboxes[i]);
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
        puntajesPanel = new JPanel(new GridLayout(13, 3));

        actualizarPuntajes();

        add(dadosPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(puntajesPanel, BorderLayout.EAST);
    }

    private void actualizarPuntajes() {
        puntajesPanel.removeAll();

        Jugador jugador1 = juego.getJugador(0);
        Jugador jugador2 = juego.getJugador(1);

        String[] categorias = jugador1.getCategorias();

        puntajesPanel.add(new JLabel("Categoría"));
        puntajesPanel.add(new JLabel(jugador1.getNombre()));
        puntajesPanel.add(new JLabel(jugador2.getNombre()));

        for (int i = 0; i < categorias.length; i++) {
            puntajesPanel.add(new JLabel(categorias[i]));
            puntajesPanel.add(new JLabel(String.valueOf(jugador1.getPuntajes()[i])));
            puntajesPanel.add(new JLabel(String.valueOf(jugador2.getPuntajes()[i])));
        }

        puntajesPanel.revalidate();
        puntajesPanel.repaint();
    }

    public static void main(String[] args) {
        InterfazGrafica ventana = new InterfazGrafica();
        ventana.setVisible(true);
    }
}
