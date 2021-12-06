package com.example.it;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class ControllerUserWindow {
    private ObservableList<Project> listProjects= FXCollections.observableArrayList();

    @FXML
    private Button Exit;


    @FXML
    private TableView<Project> userTable;
    @FXML
    private TableColumn<Project, Integer> ID;

    @FXML
    private Button Search;

    @FXML
    private TextField TextFieldSearch;

    @FXML
    private TableColumn<Project,String> cost;

    @FXML
    private TableColumn<Project,String> customer;

    @FXML
    private TableColumn<Project,String> deadline;

    @FXML
    private TableColumn<Project,String> name;


    @FXML
    void initialize() {

        Exit.setOnAction(event -> {

            Stage stage = (Stage)Exit.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("authorization.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            stage.setTitle("Authorization window");
            primaryStage.setScene(scene);
            primaryStage.show();

        });

    }






    /*try {
               initProjects(Client.interactionsWithServer.allProjects());
           } catch (IOException e) {
               e.printStackTrace();
           }*/
    private void initProjects(ArrayList<Project> projects){

        listProjects.addAll(projects);

        ID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
        customer.setCellValueFactory(new PropertyValueFactory<Project, String>("customer"));
        cost.setCellValueFactory(new PropertyValueFactory<Project, String>("cost"));
        deadline.setCellValueFactory(new PropertyValueFactory<Project, String>("deadline"));

        userTable.setItems(listProjects);
    }

}
