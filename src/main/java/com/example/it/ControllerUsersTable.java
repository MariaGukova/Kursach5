package com.example.it;

import com.Server.ExtractUsers.UserProperty;
import com.Server.dataBase.Command;
import com.Server.server.ConnectionTCP;
import com.example.it.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
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
    private TableColumn<UserProperty,Integer> ID;

    @FXML
    private Button Search;

    @FXML
    private TextField TextFieldSearch;

    @FXML
    private Button Update;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableColumn<UserProperty,String> login;

    @FXML
    private TableColumn<UserProperty,String> name;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<UserProperty,String> password;

    @FXML
    private TableView<UserProperty> usersTable;

    @FXML
    private TableColumn<UserProperty,String> surname;

    private ConnectionTCP connectionTCP;
    private static ObservableList<UserProperty> tableUserProperties = FXCollections.observableArrayList();// вызовет конструктор 0


    @FXML
    void initialize() {
        
        try {
            connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ID.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        surname.setCellValueFactory(cellValue -> cellValue.getValue().surnameProperty());
        login.setCellValueFactory(cellValue -> cellValue.getValue().loginProperty());
        password.setCellValueFactory(cellValue -> cellValue.getValue().passwordProperty());


        Add.setOnAction(actionEvent -> {

            System.out.println(" !1");
            tableUserProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            System.out.println("меня");
            connectionTCP.writeObject(Command.READ);
            System.out.println("ебет");
            List<User> users = (List<User>) connectionTCP.readObject();
            System.out.println("курсовая");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(" !");
                UserProperty e = new UserProperty(users.get(i));
                tableUserProperties.add(e);

            }
            usersTable.setItems(tableUserProperties);// устанавливаем значение обсёрвабл листа в таблицу

        });



        Edit.setOnAction(event -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();

            nameField.setText("");
            surnameField.setText("");
            loginField.setText("");
            passwordField.setText("");


            User user = new User(
                    name,
                    surname,
                    login,
                    password);

            connectionTCP.writeObject(Command.CREATE);
            connectionTCP.writeObject(user);
        });

        Update.setOnAction(event -> {
            try {
                User user = usersTable.getSelectionModel().getSelectedItem().toUser();

                String name = nameField.getText();
                if (!name.isEmpty()) {
                    user.setName(name);
                }
                String surname = surnameField.getText();
                if (!surname.isEmpty()) {
                    user.setSurname(surname);
                }
                String login  = loginField.getText();
                if (!login.isEmpty()) {
                    user.setLogin(login);
                }
                String password = passwordField.getText();
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }

                nameField.setText("");
                loginField.setText("");
                surnameField.setText("");
                passwordField.setText("");


                connectionTCP.writeObject(Command.UPDATE);
                connectionTCP.writeObject(user);
            } catch (NullPointerException e) {

            }
        });


        Delete.setOnAction(event -> {
            try {
                int id = usersTable.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETE);
                connectionTCP.writeObject(id);
            } catch (NullPointerException e) {// если 0

            }
        });

        Exit.setOnAction(event -> {
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);

        });
    }

}
