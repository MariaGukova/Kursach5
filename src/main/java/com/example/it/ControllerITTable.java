package com.example.it;

import com.Server.ExstractProjects.ProjectProperty;
import com.Server.dataBase.Command;
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
    void initialize() throws IOException {

        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));


        ID.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        customer.setCellValueFactory(cellValue -> cellValue.getValue().costProperty());
        cost.setCellValueFactory(cellValue -> cellValue.getValue().customerProperty());
        deadline.setCellValueFactory(cellValue -> cellValue.getValue().deadlineProperty());


        Search.setOnAction(actionEvent -> {
            searchProject();
        });
/*
            System.out.println("1");
            FilteredList<ProjectProperty> filteredData = new FilteredList<>(tableProjectProperties, p -> true);
            System.out.println("2");
            TextFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("3");
                filteredData.setPredicate(projectProperty -> {
                    System.out.println("4");
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        System.out.println("5");
                        return true;
                    }
                    System.out.println("6");

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    System.out.println("7");

                    if (projectProperty.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        System.out.println("8");
                        return true; // Filter matches first name.
                    } else if (projectProperty.getCost().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            SortedList<ProjectProperty> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(projectsTable.comparatorProperty());
            projectsTable.setItems(sortedData);

        });*/


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

        /*Update.setOnAction(event -> {
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
                customerField.setText("");
                costField.setText("");
                deadlineField.setText("");


                connectionTCP.writeObject(Command.UPDATE);
                connectionTCP.writeObject(project);
            } catch (NullPointerException e) {

            }
        });*/
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
    private LinkedList<ProjectProperty> searchProject(){
        String search = TextFieldSearch.getText();
        LinkedList<ProjectProperty> projectSearches = new LinkedList<>();
        for(ProjectProperty project : tableProjectProperties){
            if(search.equals(project.getName())){
                projectSearches.add(project);
            }

        }
        return projectSearches;
    }

}



