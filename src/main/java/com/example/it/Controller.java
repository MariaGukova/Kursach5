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


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Admin;

    @FXML
    private Button User;


    @FXML
    void initialize() {

        Admin.setOnAction(event -> {
                    Stage stage = (Stage) Admin.getScene().getWindow();
                    stage.close();

                    Stage primaryStage = new Stage();
                    Parent path = null;
                    try {
                        path = FXMLLoader.load(getClass().getResource("AdminAuthorization.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(path);
                    stage.setTitle("Authorization window");
                    primaryStage.setScene(scene);
                    primaryStage.show();
        });

        User.setOnAction(event -> {
            Stage stage = (Stage)User.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("authorization.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            stage.setTitle("Authorization window");
            primaryStage.setScene(scene);
            primaryStage.show();

        });

    }


    /*public void openNewWindow (Button button, String path, String title) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource(path));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public void clickAdmin(ActionEvent actionEvent) {
        try {
            openNewWindow (Admin, "AdminChoose.fxml", "Admin window");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
