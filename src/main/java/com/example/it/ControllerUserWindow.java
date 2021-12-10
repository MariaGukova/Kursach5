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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class ControllerUserWindow {

    @FXML
    private Button Exit;


    @FXML
    private TableView<ProjectProperty> projectsTable;
    @FXML
    private TableColumn<ProjectProperty, Integer> ID;


    @FXML
    private Button read;

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
                } else if (projectProperty.getCustomer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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


        Exit.setOnAction(event -> {

            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);

        });



    }

}
