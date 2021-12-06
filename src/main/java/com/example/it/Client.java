package com.example.it;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static ClientActionsWithServer interactionsWithServer;

    public void connectToServer() {
        try {
            Socket clientSocket = new Socket(Constants.HOST, Constants.PORT);
            interactionsWithServer = new ClientActionsWithServer(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}