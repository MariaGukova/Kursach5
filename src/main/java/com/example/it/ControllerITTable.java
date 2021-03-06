package com.example.it;

import com.Server.ExstractProjects.ProjectProperty;
import com.Server.dataBase.Command;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TextField TextFieldSearch;


    @FXML
    private TableColumn<ProjectProperty,String> level;

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
    private TextField levelField;

    @FXML
    private TextField deadlineField;

    @FXML
    private TextField nameField;

    @FXML
    private Button read;

    @FXML
    private Button Delete;

    @FXML
    private Button Edit;

    @FXML
    private Label ex;

    @FXML
    private TextField idField;

    @FXML
    private Button update;

    private ConnectionTCP connectionTCP;
    private static ObservableList<ProjectProperty> tableProjectProperties = FXCollections.observableArrayList();// вызовет конструктор 0

    @FXML
    void initialize() throws IOException {

        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));


        ID.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        customer.setCellValueFactory(cellValue -> cellValue.getValue().costProperty());
        cost.setCellValueFactory(cellValue -> cellValue.getValue().customerProperty());
        deadline.setCellValueFactory(cellValue -> cellValue.getValue().deadlineProperty());
        level.setCellValueFactory(cellValue -> cellValue.getValue().levelProperty());


        FilteredList<ProjectProperty> filteredData = new FilteredList<>(tableProjectProperties, p -> true);
        TextFieldSearch.textProperty().addListener((ObservableList, oldValue, newValue) -> {
        filteredData.setPredicate(projectProperty -> {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }
        String lowerCaseFilter = newValue.toLowerCase();
        if (projectProperty.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
            return true; // Filter matches first name.
        } else if (projectProperty.getCost().toLowerCase().indexOf(lowerCaseFilter) != -1) {
            return true; // Filter matches last name.
        }
            return false; // Does not match.
        });
        SortedList<ProjectProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(projectsTable.comparatorProperty());
        projectsTable.setItems(sortedData);
        });



        read.setOnAction(actionEvent -> {

            tableProjectProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            connectionTCP.writeObject(Command.READ);
            List<Project> projects = (List<Project>) connectionTCP.readObject();
            for (int i = 0; i < projects.size(); i++) {
                ProjectProperty e = new ProjectProperty(projects.get(i));
                tableProjectProperties.add(e);

            }
            projectsTable.setItems(tableProjectProperties);// устанавливаем значение обсёрвабл листа в таблицу

        });
        update.setOnAction(actionEvent -> {
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
                String cost = costField.getText();
                if (!cost.isEmpty()) {
                    project.setCost(cost);
                }
                String deadline = deadlineField.getText();
                if (!deadline.isEmpty()) {
                    project.setDeadline(deadline);
                }
                String level = levelField.getText();
                if (!level.isEmpty()) {
                    project.setLevel(level);
                }

                idField.setText("");
                nameField.setText("");
                customerField.setText("");
                costField.setText("");
                deadlineField.setText("");
                levelField.setText("");

                connectionTCP.writeObject(Command.UPDATE);
                connectionTCP.writeObject(project);
            } catch (NullPointerException e) {

            }
        });

        Edit.setOnAction(event -> {
            Integer id = Integer.valueOf(idField.getText());
            String name = nameField.getText();
            String customer = customerField.getText();
            String cost = costField.getText();
            String deadline = deadlineField.getText();
            String level = levelField.getText();

            idField.setText("");
            nameField.setText("");
            customerField.setText("");
            costField.setText("");
            deadlineField.setText("");
            levelField.setText("");

            Project project = new Project(
                    id,
            name,
            customer,
            cost,
            deadline, level);

            connectionTCP.writeObject(Command.CREATE);
            connectionTCP.writeObject(project);
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





