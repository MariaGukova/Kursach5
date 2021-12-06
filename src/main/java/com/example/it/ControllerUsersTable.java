package com.example.it;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUsersTable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Add;

    @FXML
    private Button Delete;

    @FXML
    private Button Edit;

    @FXML
    private Button Exit;

    @FXML
    private TableColumn<?, ?> ID;

    @FXML
    private Button Search;

    @FXML
    private TextField TextFieldSearch;

    @FXML
    private Button Update;

    @FXML
    private TableColumn<?, ?> cost;

    @FXML
    private TextField costField;

    @FXML
    private TableColumn<?, ?> customer;

    @FXML
    private TextField customerField;

    @FXML
    private TableColumn<?, ?> deadline;

    @FXML
    private TextField deadlineField;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<?> projectsTable;

    @FXML
    void initialize() {
        Exit.setOnAction(event -> {
            Stage stage = (Stage) Exit.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("AdminChoose.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

}
