package com;

import com.Server.server.ConnectionTCP;
import com.Server.server.ProjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
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

    @FXML
    void initialize() {
        System.out.println(" !1");
        try {
            System.out.println("меня");
            connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));
            System.out.println("ебет");
        } catch (IOException e) {
            System.out.println("курсовая");
            e.printStackTrace();

            System.exit(-1);
        }

        System.out.println(" !");


        Exit.setOnAction(event -> {
            Stage stage = (Stage) Exit.getScene().getWindow();
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

        });

    }

}
/*
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

        System.out.println(" !");

        ID.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        customer.setCellValueFactory(cellValue -> cellValue.getValue().costProperty());
        cost.setCellValueFactory(cellValue -> cellValue.getValue().customerProperty());
        deadline.setCellValueFactory(cellValue -> cellValue.getValue().deadlineProperty());

        System.out.println("1 !");
        read.setOnAction(event -> {
            tableProjectProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            List<Project> projects = (List<Project>) connectionTCP.readObject();
            for (int i = 0; i < projects.size(); i++) {
                ProjectProperty e = new ProjectProperty(projects.get(i));
                tableProjectProperties.add(e);

            }
            projectsTable.setItems(tableProjectProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });
        System.out.println("2 !");


        Edit.setOnAction(event -> {
            String name = nameField.getText();
            String customer = customerField.getText();
            String cost = costField.getText();
            String deadline = deadlineField.getText();

            nameField.setText("");
            costField.setText("");
            customerField.setText("");
            deadlineField.setText("");

            Project project = new Project(name,
                    customer,
                    cost,
                    deadline);

            connectionTCP.writeObject(Command.CREATE);
            connectionTCP.writeObject(project);
        });
        System.out.println("3 !");


        Exit.setOnAction(event -> {
            Stage stage = (Stage) Exit.getScene().getWindow();
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

        });

    }


}*/
        /*Update.setOnAction(event -> {
            try {
                initProjects(Client.interactionsWithServer.allProjects());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            initProjects(Client.interactionsWithServer.allProjects());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Search.setOnAction(event -> {
            initProjects(searchProject());

        });
        Delete.setOnAction(event -> {
            int count = projectsTable.getSelectionModel().getSelectedCells().get(0).getRow();

            Client.interactionsWithServer.deleteProject(listProjects.get(count).getId());
            try {
                initProjects(Client.interactionsWithServer.allProjects());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initProjects(LinkedList<Project> projects){

        listProjects.clear();
        listProjects.addAll(projects);

        ID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
        customer.setCellValueFactory(new PropertyValueFactory<Project, String>("customer"));
        cost.setCellValueFactory(new PropertyValueFactory<Project, String>("cost"));
        deadline.setCellValueFactory(new PropertyValueFactory<Project, String>("deadline"));

        projectsTable.setItems(listProjects);
    }
    private void initTextField(){
        nameField.setText("");
        customerField.setText("");
        costField.setText("");
        deadlineField.setText("");

    }

    private LinkedList<Project> searchProject(){
        String search = searchText.getText();
        LinkedList<Project> projectSearches = new LinkedList<>();
        for(Project project : listProjects){
            if(search.equals(project.getName())){
                project.add(project);
            }

        }
        return projectSearches;
    }*/



