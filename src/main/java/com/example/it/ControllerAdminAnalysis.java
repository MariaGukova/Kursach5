package com.example.it;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdminAnalysis {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit;

    @FXML
    private PieChart pcCost;
    @FXML
    private Label label;




    @FXML
    void initialize() {

       /* //connectionTCP.writeObject(Command.READ2);
        label.setText("");
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList (new ArrayList<>());
        list.add( new PieChart.Data(Database.getCost));
        pcCost.setData(list);
*/
       //loadData();

        Exit.setOnAction(actionEvent -> {
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

   /* public void loadData(String cost){
        Database db = new Database();
        Project project = new Project();
        project.setCost(cost);

        ResultSet result = db.getAllCost(project);
        //ArrayList<String> cost = new ArrayList<String>();
    }*/

}
