package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class InterfazGrafica extends Application {

    private JuegoGenerala juego;
    private Label[] dadosLabels;
    private CheckBox[] tirarDadoCheckboxes;
    private Label turnoLabel;
    private Label tirosRestantesLabel;
    private Button lanzarButton;
    private Button cambiarTurnoButton;
    private VBox puntajesPanel1;
    private VBox puntajesPanel2;
    private ComboBox<String> categoriasComboBox;
    private Button guardarPuntajeButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Generala");

        juego = new JuegoGenerala("Jugador 1", "Jugador 2");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // Panel para los dados
        GridPane dadosPanel = new GridPane();
        dadosPanel.setHgap(10);
        dadosPanel.setVgap(10);

        dadosLabels = new Label[5];
        tirarDadoCheckboxes = new CheckBox[5];
        for (int i = 0; i < 5; i++) {
            dadosLabels[i] = new Label("DADO " + (i + 1) + ": ");
            tirarDadoCheckboxes[i] = new CheckBox("Tirar");
            tirarDadoCheckboxes[i].getStyleClass().add("stylish-checkbox");
            dadosPanel.add(tirarDadoCheckboxes[i], 0, i);
            dadosPanel.add(dadosLabels[i], 1, i);
        }

        // Panel para el control de juego
        turnoLabel = new Label("Turno de: " + juego.getJugadorActual().getNombre());
        tirosRestantesLabel = new Label("Tiros restantes: " + juego.getTirosRestantes());
        lanzarButton = new Button("Lanzar Dados");
        lanzarButton.setOnAction(e -> lanzarDados());
        cambiarTurnoButton = new Button("Cambiar Turno");
        cambiarTurnoButton.setOnAction(e -> cambiarTurno());

        categoriasComboBox = new ComboBox<>();
        categoriasComboBox.getItems().addAll(juego.getJugadorActual().getCategorias());
        guardarPuntajeButton = new Button("Guardar Puntaje");
        guardarPuntajeButton.setOnAction(e -> guardarPuntaje());

        HBox controlPanel = new HBox(10, turnoLabel, tirosRestantesLabel, lanzarButton, cambiarTurnoButton, categoriasComboBox, guardarPuntajeButton);

        // Panel para los puntajes
        HBox puntajesPanel = new HBox(10);
        puntajesPanel1 = new VBox(5);
        puntajesPanel2 = new VBox(5);

        puntajesPanel.getChildren().addAll(puntajesPanel1, puntajesPanel2);

        actualizarPuntajes();

        root.getChildren().addAll(dadosPanel, controlPanel, puntajesPanel);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/styles.css")))).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void lanzarDados() {
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
            lanzarButton.setDisable(true);
        }
    }

    private void cambiarTurno() {
        juego.cambiarTurno();
        turnoLabel.setText("Turno de: " + juego.getJugadorActual().getNombre());
        tirosRestantesLabel.setText("Tiros restantes: " + juego.getTirosRestantes());
        lanzarButton.setDisable(false);
        for (int i = 0; i < 5; i++) {
            tirarDadoCheckboxes[i].setSelected(true);
        }
        actualizarPuntajes();
    }

    private void guardarPuntaje() {
        int categoria = categoriasComboBox.getSelectionModel().getSelectedIndex();
        int puntaje = juego.calcularPuntaje(categoria);
        juego.getJugadorActual().setPuntaje(categoria, puntaje);
        actualizarPuntajes();
    }

    private void actualizarPuntajes() {
        puntajesPanel1.getChildren().clear();
        puntajesPanel2.getChildren().clear();

        Jugador jugador1 = juego.getJugadorActual();
        Jugador jugador2 = juego.getJugadorActual();

        String[] categorias = jugador1.getCategorias();

        for (int i = 0; i < categorias.length; i++) {
            puntajesPanel1.getChildren().add(new Label(categorias[i]));
            puntajesPanel1.getChildren().add(new Label(String.valueOf(jugador1.getPuntajes()[i])));

            puntajesPanel2.getChildren().add(new Label(categorias[i]));
            puntajesPanel2.getChildren().add(new Label(String.valueOf(jugador2.getPuntajes()[i])));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
