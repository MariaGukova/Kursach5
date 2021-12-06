package com.Server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("The multi-threaded server has started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection established\n" +
                        "IP:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                new Thread(String.valueOf(new RequestHandler(clientSocket))).start();// обработчик клиентских запросов
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


