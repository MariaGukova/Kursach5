package com.example.it;

import com.Server.ExstractProjects.ProjectProperty;
import com.Server.dataBase.Command;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.LinkedList;
import java.util.List;


public class ControllerUserWindow {

    @FXML
    private Button Exit;


    @FXML
    private TableView<ProjectProperty> projectsTable;
    @FXML
    private TableColumn<ProjectProperty, Integer> ID;

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

        tableProjectProperties.clear();// чтобы не добавлять каждый раз к существующему списку
        connectionTCP.writeObject(Command.READ);
        List<Project> projects = (List<Project>) connectionTCP.readObject();
        for (int i = 0; i < projects.size(); i++) {
            ProjectProperty e = new ProjectProperty(projects.get(i));
            tableProjectProperties.add(e);

        }
        projectsTable.setItems(tableProjectProperties);// устанавливаем значение обсёрвабл листа в таблицу



        Exit.setOnAction(event -> {

            Stage stage = (Stage)Exit.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("userChoose.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            stage.setTitle("Authorization window");
            primaryStage.setScene(scene);
            primaryStage.show();

        });

        Search.setOnAction(actionEvent -> {
            searchProject();
        });

    }
    private LinkedList<ProjectProperty> searchProject(){
        String search = TextFieldSearch.getText();
        System.out.println("1");
        LinkedList<ProjectProperty> projectSearches = new LinkedList<>();
        System.out.println("2");
        for(ProjectProperty project : tableProjectProperties){
            System.out.println("3");
            if(search.equals(project.getName())){
                System.out.println("4");
                projectSearches.add(project);
            }
            else {
               // Platform.runLater(() -> ex.setText("Such project doesn't exist"));

            }

        }
        return projectSearches;
    }

}
