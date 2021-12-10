package com.example.it;

import com.Server.dataBase.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Customer;
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

public class Controlleranalysis {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit;

    @FXML
    private Button Import;

    @FXML
    private PieChart pcCustomer;

    private ConnectionTCP connectionTCP;

    @FXML
    void initialize() throws IOException {

        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));
        operatePieChart();
        Import.setOnAction(actionEvent -> {
            try {
                Database.filewriterCustomer();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        Exit.setOnAction(event -> {
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);

        });

    }

    private void operatePieChart() {

        PieChartUtils pieChartUtils = new PieChartUtils(pcCustomer);
        connectionTCP.writeObject(Command.READ3);
        List<Customer> customers =(List<Customer>)connectionTCP.readObject();

        for (int i = 0; i < customers.size(); i++) {
            pieChartUtils.addData(customers.get(i).getName(), Double.parseDouble(customers.get(i).getNumber()));
        }
        // Отображение графика
        pieChartUtils.showChart();
        // Цвет области данных можно изменить только после отображения значка
        pieChartUtils.setDataColor(0, "purple");
        pieChartUtils.setDataColor(1, "green");


        // Устанавливаем цвет ряда данных круговой диаграммы
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(0, "purple");
        hashMap.put(1, "green");

        // Устанавливаем цвет и ориентацию легенды
        pieChartUtils.setLegendColor(hashMap);
        pieChartUtils.setLegendSide("Right");


        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 32 arial;");

        pcCustomer.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(data.getPieValue() + "%");
                }));


    }

    static class PieChartUtils
    {
        private PieChart pcCustomer;
        private ObservableList<PieChart.Data> pieChartData;
        PieChartUtils(PieChart pieChart)
        {
            pieChartData = FXCollections.observableArrayList();
            this.pcCustomer = pieChart;
        }
        void addData(String name, double value)
        {
            pieChartData.add(new PieChart.Data(name, value));
        }
        void showChart()
        {
            pcCustomer.setData(pieChartData);
        }
        void setDataColor(int index, String color)
        {
            pieChartData.get(index).getNode().setStyle("-fx-background-color: "+ color);
        }
        private void setMarkVisible(boolean b)
        {
            pcCustomer.setLabelsVisible(b);
        }
        void setLegendColor(HashMap<Integer, String> colors)
        {
            String setColor = "";
            for(Map.Entry<Integer, String> entry:colors.entrySet())
            {
                int index = entry.getKey();
                String color = entry.getValue();
                setColor = setColor.concat("CHART_COLOR_" + (index+1) + ":" + color+";");
            }
            pcCustomer.setStyle(setColor);
        }
        void setLegendSide(String side)
        {
            if(side.equalsIgnoreCase("top"))
                pcCustomer.setLegendSide(Side.TOP);
            else if(side.equalsIgnoreCase("bottom"))
                pcCustomer.setLegendSide(Side.BOTTOM);
            else if(side.equalsIgnoreCase("left"))
                pcCustomer.setLegendSide(Side.LEFT);
            else{
                pcCustomer.setLegendSide(Side.RIGHT);
            }
        }
        private void setTitle(String title)
        {
            pcCustomer.setTitle(title);
        }
    }


}
