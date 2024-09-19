
package com.example.dropthenumber.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static com.example.dropthenumber.App.COL_SIZE;
import static com.example.dropthenumber.App.ROW_SIZE;



public class Graphic {

    static final int CELL_SIZE = 100;

    public final Label[][] cells = new Label[ROW_SIZE][COL_SIZE];

    // GridPane per mostrare la matrice
    private GridPane gridPane;

    private StackPane scorePane;

    // Label per visualizzare lo score
    private Label scoreLabel;

    public Graphic(Stage primaryStage) {

        //GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Score
        scoreLabel = new Label();
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setText("Score: 0");

        //stackPane for score
        scorePane = new StackPane(scoreLabel);
        scorePane.setMinSize(530, 80);
        scorePane.setPrefSize(530, 80);
        scorePane.setMaxSize(530, 80);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setStyle("-fx-background-color: #90EE90; "
                + "-fx-border-color: #DADADA; "
                + "-fx-border-radius: 20; "
                + "-fx-background-radius: 20; "
                + "-fx-padding: 20;");


        // Creazione del layout principale con BorderPane
        BorderPane root = new BorderPane();
        root.setTop(scorePane);
        BorderPane.setAlignment(scorePane, Pos.CENTER);
        BorderPane.setMargin(scorePane, new Insets(20, 0, 20, 0)); // Aggiunge spazio sopra e sotto lo score
        root.setCenter(gridPane);
        root.setStyle("-fx-background-color: #2E2E2E;");

        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                // Crea un rettangolo con bordo
                Rectangle border = new Rectangle(CELL_SIZE, CELL_SIZE);
                border.setArcHeight(20);
                border.setArcWidth(20);
                border.setFill(Color.WHITE);
                border.setStroke(Color.BLACK);

                // Crea una Label
                Label label = new Label("");
                label.setFont(Font.font("Arial", FontWeight.BOLD, 28));
                label.setTextFill(Color.WHITE);
                label.setAlignment(Pos.CENTER);
                label.setMinSize(90, 90); // Dimensioni minime
                label.setPrefSize(90, 90); // Dimensioni preferite
                label.setMaxSize(90, 90); // Dimensioni massime
                label.setStyle("-fx-font: 28 arial; -fx-font-weight: bold; ");

                cells[row][col] = label;

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(border, label);

                gridPane.add(stackPane, col, row);
            }
        }


        /*cells[ROW_SIZE - 1][0].setText("8");
        cells[ROW_SIZE - 1][1].setText("2");
        cells[ROW_SIZE - 1][2].setText("332");
        cells[ROW_SIZE - 1][3].setText("644");
        cells[ROW_SIZE - 1][4].setText("325");

        cells[ROW_SIZE - 2][0].setText("163");
        cells[ROW_SIZE - 2][1].setText("823");
        cells[ROW_SIZE - 2][2].setText("165");
        cells[ROW_SIZE - 2][3].setText("323");
        cells[ROW_SIZE - 2][4].setText("642");

        cells[ROW_SIZE - 3][0].setText("82");
        cells[ROW_SIZE - 3][1].setText("163");
        cells[ROW_SIZE - 3][2].setText("84");
        cells[ROW_SIZE - 3][3].setText("136");
        cells[ROW_SIZE - 3][4].setText("323");

        cells[ROW_SIZE - 4][0].setText("8");
        cells[ROW_SIZE - 4][1].setText("156");
        cells[ROW_SIZE - 4][2].setText("83");
        cells[ROW_SIZE - 4][3].setText("166");
        cells[ROW_SIZE - 4][4].setText("327");

        cells[ROW_SIZE - 5][0].setText("22");
        cells[ROW_SIZE - 5][1].setText("43");

        cells[ROW_SIZE - 5][3].setText("156");
        cells[ROW_SIZE - 5][4].setText("3273");*/

        Scene scene = new Scene(root, 600, 800);
        primaryStage.setTitle("Drop the Number");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private String getBackgroundColor(int value) {
        switch (value) {

            case 2:
                return "#FF1493";
            case 4:
                return "#00FF00";
            case 8:
                return "#87CEFA";
            case 16:
                return "#6A5ACD";
            case 32:
                return "#FF4500";
            case 64:
                return "#C71585";
            case 128:
                return "#D3D3D3";
            case 256:
                return "#FFA07A";
            case 512:
                return "#FFFF00";
            case 1024:
                return "#48D1CC";
            case 2048:
                return "#7FFF00";

            default:
                return "#FAEBD7";
        }
    }


    public int[][] getMatrix() {

        int[][] copiedMatrix = new int[ROW_SIZE][COL_SIZE];

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (cells[i][j].getText().isEmpty()) {
                    copiedMatrix[i][j] = 0;
                } else {
                    copiedMatrix[i][j] = Integer.parseInt(cells[i][j].getText());
                }

            }
        }

        return copiedMatrix;
    }

    public void setCell(int i, int j, int value) {
        if (value != 0) {
            cells[i][j].setText(String.valueOf(value));
            String backgroundColor = getBackgroundColor(value);
            cells[i][j].setStyle("-fx-background-color: " + backgroundColor + "; " // Sfondo dinamico
                    + "-fx-border-color: #DADADA; " // Colore del bordo
                    + "-fx-border-radius: 12; " // Bordi arrotondati
                    + "-fx-background-radius: 12; " // Bordi arrotondati
                    + "-fx-padding: 10;"); // Spazio interno alla cella
        } else {
            cells[i][j].setText("");
            cells[i][j].setStyle("-fx-background-color: #FFFFFF ");
        }
    }

    public void updateScoreLabel(int partialScore) {
        String numberOnly= scoreLabel.getText().replaceAll("[^0-9]", "");
        int totalSum = partialScore + Integer.parseInt(numberOnly);
        scoreLabel.setText("Score: " + totalSum);
    }

    public void gameOver() {
        // Creazione di un nuovo layout per mostrare solo l'immagine del game over
        StackPane gameOverPane = new StackPane();
        gameOverPane.setStyle("-fx-background-color: #2E2E2E;");

        Image gameOverImage = new Image(getClass().getResource("/images/gameover.png").toString());

        ImageView imageView = new ImageView(gameOverImage);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        Button closeButton = new Button("Chiudi");
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        closeButton.setTextFill(Color.WHITE);
        closeButton.setStyle("-fx-background-color: #f55b5b; -fx-border-radius: 30; -fx-background-radius: 30;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #FF0000; -fx-border-radius: 30; -fx-background-radius: 30;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #f55b5b; -fx-border-radius: 30; -fx-background-radius: 30;"));

        HBox hboxButton = new HBox(30);
        hboxButton.setAlignment(Pos.CENTER);
        hboxButton.getChildren().add(closeButton);

        VBox vbox = new VBox(100);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(imageView, hboxButton);

        // Diamo funzione al pulsante di chiusura
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        gameOverPane.getChildren().add(vbox);

        Scene gameOverScene = new Scene(gameOverPane, 600, 800);

        Stage stage = (Stage) scoreLabel.getScene().getWindow();

        stage.setScene(gameOverScene);
        stage.show();
    }


}
