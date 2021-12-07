package com.example.it;

import com.Server.dataBase.Database;
import com.animation.shake;
import com.example.it.model.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerAdminAuthorization {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Login;

    @FXML
    private TextField Password;

    @FXML
    private Button Back;

    @FXML
    private Button SignIn;


    @FXML
    void initialize() {
        SignIn.setOnAction(event -> {
            String login = Login.getText().trim();
            String password = Password.getText().trim();
            if (!login.equals("") && !password.equals("")) {
                try {
                    AdminExist (login,password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }
        } );

        Back.setOnAction(event -> {
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private void AdminExist(String login, String password) throws SQLException {

        Database db = new Database();
        Admin admin = new Admin();
        admin.setLogin(login);
        admin.setPassword(password);
        ResultSet result = db.getAdminAuthorization(admin);

        int counter = 0;
        while (result.next()){
            counter++;
        }
        if (counter >= 1) {
            Stage stage = (Stage) SignIn.getScene().getWindow();
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
        }

        else{
            shake adminLoginAnim = new shake(Login);
            shake  adminPasswordAnim = new shake(Password);
            adminLoginAnim.playAnim();
            adminPasswordAnim.playAnim();

            System.out.println("Incorrect login or password !");
        };
    };

}


