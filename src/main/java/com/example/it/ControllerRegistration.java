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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerRegistration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Register;

    @FXML
    private Button ex;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldUserName;

    @FXML
    private TextField textFieldUserSurname;


    @FXML
    void initialize() {

        Register.setOnAction(event -> {

            String login = textFieldLogin.getText().trim();
            String password = textFieldPassword.getText().trim();
            String name = textFieldUserName.getText().trim();
            String surname = textFieldUserSurname.getText().trim();
            if (!login.equals("") && !password.equals("") && !name.equals("") && !surname.equals("") ) {
                try {
                    AddUser(login,password,name,surname);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }

        });

        ex.setOnAction(event -> {
            Stage stage = (Stage) ex.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("authorization.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();

        });

}

    private void AddUser(String login, String password, String name, String surname) throws SQLException {
        Database db = new Database();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        db.getUserRegistration(user);

        try {
            Stage stage = (Stage) Register.getScene().getWindow();
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

        catch (Exception e){
            //Platform.runLater(() -> ex.setText("Such user exists"));
            shake userLoginAnim = new shake(textFieldLogin);
            shake userPasswordAnim = new shake(textFieldPassword);
            shake userNameAnim = new shake(textFieldUserName);
            shake userSurnameAnim = new shake(textFieldUserSurname);

            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();
            userNameAnim.playAnim();
            userSurnameAnim.playAnim();
        };
    }

}
