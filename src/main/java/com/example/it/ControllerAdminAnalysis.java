package com.example.it;

import com.Server.dataBase.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerAdminAnalysis {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit;

    @FXML
    private Button Import;

    @FXML
    private PieChart pcLevel;

    private ConnectionTCP connectionTCP;

    @FXML
    void initialize() throws IOException {

        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));
        operatePieChart();
        Import.setOnAction(actionEvent -> {
            try {
                Database.filewriter();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Exit.setOnAction(actionEvent -> {
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);

        });

    }

    private void operatePieChart() {
        PieChartUtils pieChartUtils = new PieChartUtils(pcLevel);
        connectionTCP.writeObject(Command.READ4);
        List<Project> projects =(List<Project>)connectionTCP.readObject();

        for (int i = 0; i < projects.size(); i++) {
            pieChartUtils.addData(projects.get(i).getName(), Double.parseDouble(projects.get(i).getLevel()));
        }
        // Отображение графика
        pieChartUtils.showChart();
        // Цвет области данных можно изменить только после отображения значка
        pieChartUtils.setDataColor(0, "purple");
        pieChartUtils.setDataColor(1, "green");
        pieChartUtils.setDataColor(2, "blue");
        pieChartUtils.setDataColor(3, "yellow");
       // pieChartUtils.setDataColor(4, "pink");

        // Устанавливаем цвет ряда данных круговой диаграммы
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(0, "purple");
        hashMap.put(1, "green");
        hashMap.put(2, "blue");
        hashMap.put(3, "yellow");
       // hashMap.put(4, "pink");
        // Устанавливаем цвет и ориентацию легенды
        pieChartUtils.setLegendColor(hashMap);
        pieChartUtils.setLegendSide("Right");


        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 32 arial;");

        pcLevel.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(data.getPieValue() + "%");
                }));
    }


    class PieChartUtils {
        private PieChart pcLevel;
        private ObservableList<PieChart.Data> pieChartData;

        PieChartUtils(PieChart pieChart) {
            pieChartData = FXCollections.observableArrayList();
            this.pcLevel = pieChart;
        }

        void addData(String name, double value) {
            pieChartData.add(new PieChart.Data(name, value));
        }

        void showChart() {
            pcLevel.setData(pieChartData);
        }

        void setDataColor(int index, String color) {
            pieChartData.get(index).getNode().setStyle("-fx-background-color: " + color);
        }

        void setMarkVisible(boolean b) {
            pcLevel.setLabelsVisible(b);
        }

        void setLegendColor(HashMap<Integer, String> colors) {
            String setColor = "";
            for (Map.Entry<Integer, String> entry : colors.entrySet()) {
                int index = entry.getKey();
                String color = entry.getValue();
                setColor = setColor.concat("CHART_COLOR_" + (index + 1) + ":" + color + ";");
            }
            pcLevel.setStyle(setColor);
        }

        void setLegendSide(String side) {
            if (side.equalsIgnoreCase("top"))
                pcLevel.setLegendSide(Side.TOP);
            else if (side.equalsIgnoreCase("bottom"))
                pcLevel.setLegendSide(Side.BOTTOM);
            else if (side.equalsIgnoreCase("left"))
                pcLevel.setLegendSide(Side.LEFT);
            else {
                pcLevel.setLegendSide(Side.RIGHT);
            }
        }

        private void setTitle(String title) {
            pcLevel.setTitle(title);
        }
    }
}

