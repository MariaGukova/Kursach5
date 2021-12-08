package com.example.it;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserChoose {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Analysis;

    @FXML
    private Button ProjectsTable;
    @FXML
    private Button Exit;

    @FXML
    void initialize() {
        Analysis.setOnAction(actionEvent -> {

            Stage stage = (Stage) Analysis.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("analysis.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();

        });

        ProjectsTable.setOnAction(actionEvent -> {

            Stage stage = (Stage) ProjectsTable.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("userWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        Exit.setOnAction(actionEvent -> {
            Stage stage = (Stage) ProjectsTable.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("userChoose.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

}
