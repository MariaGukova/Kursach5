package com.example.it;

import com.Server.ExtractUsers.UserProperty;
import com.Server.dataBase.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
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
import java.util.LinkedList;
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
    void initialize() throws IOException {
        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));

        ID.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        surname.setCellValueFactory(cellValue -> cellValue.getValue().surnameProperty());
        login.setCellValueFactory(cellValue -> cellValue.getValue().loginProperty());
        password.setCellValueFactory(cellValue -> cellValue.getValue().passwordProperty());


        Add.setOnAction(actionEvent -> {

            tableUserProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            connectionTCP.writeObject(Command.READ1);
            //Database db = new Database();
            //List<User> users = db.getAllUsers();
            List<User> users = (List<User>) connectionTCP.readObject();
            for (int i = 0; i < users.size(); i++) {
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


            //Database db = new Database();
            //db.addUser(user);
            connectionTCP.writeObject(Command.CREATE1);
            connectionTCP.writeObject(user);
        });
        Search.setOnAction(actionEvent -> {
            searchUser();
        });

       /* Update.setOnAction(event -> {
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


                Database db = new Database();
                db.updateUser(user);
                connectionTCP.writeObject(Command.UPDATE1);
                connectionTCP.writeObject(user);
            } catch (NullPointerException e) {

            }
        });
*/

        Delete.setOnAction(event -> {
            try {
                int id = usersTable.getSelectionModel().getSelectedItem().getId();
                Database db = new Database();
                db.deleteUserByID(id);
                connectionTCP.writeObject(Command.DELETE1);
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
    private LinkedList<UserProperty> searchUser(){
        String search = TextFieldSearch.getText();
        System.out.println("1");
        LinkedList<UserProperty> userSearches = new LinkedList<>();
        System.out.println("2");
        for(UserProperty user : tableUserProperties){
            System.out.println("3");
            if(search.equals(user.getName())){
                System.out.println("4");
                userSearches.add(user);
            }
            else {
                //Platform.runLater(() -> ex.setText("Such project doesn't exist"));

            }

        }
        return userSearches;
    }


}
