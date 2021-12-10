
package com.example.it;

import com.Server.ExstractOtchet.OtchetProperty;
import com.Server.dataBase.Command;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Otchet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.Server.dataBase.Database.filewriterOtchet;

public class ControllerUserOtchet {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Exit;

    @FXML
    private TableColumn<OtchetProperty, String> deadline;

    @FXML
    private TableColumn<OtchetProperty, String> name;

    @FXML
    private TableView<OtchetProperty> otchetTable;

    @FXML
    private TableColumn<OtchetProperty, String> participants;

    @FXML
    private Button read;

    @FXML
    private TableColumn<OtchetProperty, String> risks;
    @FXML
    private Button otchet;


    private ConnectionTCP connectionTCP;
    private static ObservableList<OtchetProperty> tableOtchetProperties = FXCollections.observableArrayList();// вызовет конструктор 0

    @FXML
    void initialize() throws IOException {
        connectionTCP = new ConnectionTCP(new Socket("localhost", 8888));


        name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        risks.setCellValueFactory(cellValue -> cellValue.getValue().risksProperty());
        participants.setCellValueFactory(cellValue -> cellValue.getValue().participantsProperty());
        deadline.setCellValueFactory(cellValue -> cellValue.getValue().deadlineProperty());


        read.setOnAction(actionEvent -> {

            tableOtchetProperties.clear();// чтобы не добавлять каждый раз к существующему списку
            connectionTCP.writeObject(Command.READ2);
            List<Otchet> otchets = (List<Otchet>) connectionTCP.readObject();
            for (int i = 0; i < otchets.size(); i++) {
                OtchetProperty e = new OtchetProperty(otchets.get(i));
                tableOtchetProperties.add(e);

            }
            otchetTable.setItems(tableOtchetProperties);// устанавливаем значение обсёрвабл листа в таблицу

        });
        otchet.setOnAction(actionEvent -> {
            try {
                filewriterOtchet();
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

}
