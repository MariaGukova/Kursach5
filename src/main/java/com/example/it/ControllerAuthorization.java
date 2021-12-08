package com.example.it;

import com.Server.dataBase.Database;
import com.animation.shake;
import com.example.it.model.User;
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

public class ControllerAuthorization {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Login;

    @FXML
    private TextField Password;

    @FXML
    private Button Registration;

    @FXML
    private Button SignIn;



    @FXML
    void initialize() {


        SignIn.setOnAction(event -> {
            String login = Login.getText().trim();
            String password = Password.getText().trim();
            if (!login.equals("") && !password.equals("")) {
                try {
                    UserExist (login,password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }
        } );

        Registration.setOnAction(event -> {
            Stage stage = (Stage) Registration.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("registration.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private void UserExist(String login, String password) throws SQLException {

        Database db = new Database();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        ResultSet result = db.getUserAuthorization(user);

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
                path = FXMLLoader.load(getClass().getResource("userChoose.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        else{
            shake  userLoginAnim = new shake(Login);
            shake  userPasswordAnim = new shake(Password);
            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();

            System.out.println("Incorrect login or password !");
        };
    };

    /*public void openNewWindow (Button button, String window){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Parent path = null;
        try {
            path = FXMLLoader.load(getClass().getResource(window));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(path);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }*/

}