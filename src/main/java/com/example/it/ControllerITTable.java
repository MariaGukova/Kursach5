package com.example.it;

import com.Server.dataBase.Command;
import com.Server.server.ConnectionTCP;
import com.Server.ExstractProjects.ProjectProperty;
import com.example.it.model.Project;
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

public class ControllerITTable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit;

    @FXML
    private TableColumn<ProjectProperty,Integer> ID;

    @FXML
    private Button Search;

    @FXML
    private TextField TextFieldSearch;

    @FXML
    private TableColumn<ProjectProperty,String> cost;

    @FXML
    private TableColumn<ProjectProperty,String> customer;

    @FXML
    private TableColumn<ProjectProperty,String> deadline;

    @FXML
    private TableColumn<ProjectProperty,String> name;

    @FXML
    private TableView<ProjectProperty> projectsTable;
    @FXML
    private TextField costField;

    @FXML
    private TextField customerField;

    @FXML
    private TextField deadlineField;

    @FXML
    private TextField nameField;

    @FXML
    private Button Update;

    @FXML
    private Button read;

    @FXML
    private Button Delete;

    @FXML
    private Button Edit;


    private ConnectionTCP connectionTCP;
    private static ObservableList<ProjectProperty> tableProjectProperties = FXCollections.observableArrayList();// вызовет конструктор 0

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
        customer.setCellValueFactory(cellValue -> cellValue.getValue().costProperty());
        cost.setCellValueFactory(cellValue -> cellValue.getValue().customerProperty());
        deadline.setCellValueFactory(cellValue -> cellValue.getValue().deadlineProperty());


        read.setOnAction(actionEvent -> {

            System.out.println(" !1");
            tableProjectProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            System.out.println("меня");
            connectionTCP.writeObject(Command.READ);
            System.out.println("ебет");
            List<Project> projects = (List<Project>) connectionTCP.readObject();
            System.out.println("курсовая");
            for (int i = 0; i < projects.size(); i++) {
                System.out.println(" !");
                ProjectProperty e = new ProjectProperty(projects.get(i));
                tableProjectProperties.add(e);

            }
            projectsTable.setItems(tableProjectProperties);// устанавливаем значение обсёрвабл листа в таблицу

        });

        Edit.setOnAction(event -> {
            String name = nameField.getText();
            String customer = customerField.getText();
            String cost = costField.getText();
            String deadline = deadlineField.getText();

            nameField.setText("");
            customerField.setText("");
            costField.setText("");
            deadlineField.setText("");


            Project project = new Project(
            name,
            customer,
            cost,
            deadline);

            connectionTCP.writeObject(Command.CREATE);
            connectionTCP.writeObject(project);
        });

        Update.setOnAction(event -> {
            try {
                    Project project = projectsTable.getSelectionModel().getSelectedItem().toProject();

                String name = nameField.getText();
                if (!name.isEmpty()) {
                    project.setName(name);
                }
                String customer = customerField.getText();
                if (!customer.isEmpty()) {
                    project.setCustomer(customer);
                }
                String cost  = costField.getText();
                if (!cost.isEmpty()) {
                    project.setCost(cost);
                }
                String deadline = deadlineField.getText();
                if (!deadline.isEmpty()) {
                    project.setDeadline(deadline);
                }

                nameField.setText("");
                costField.setText("");
                customerField.setText("");
                deadlineField.setText("");


                connectionTCP.writeObject(Command.UPDATE);
                connectionTCP.writeObject(project);
            } catch (NullPointerException e) {

            }
        });
        Delete.setOnAction(event -> {
            try {
                int id = projectsTable.getSelectionModel().getSelectedItem().getId();
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



