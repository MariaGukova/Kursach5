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
/*public class ServerConnection {

    public static LinkedList<ClientHandler> usersConnected = new LinkedList<>();
    private  int idUser=0;

    private ServerSocket server;

    public void startServer(){
        try {
            server = new ServerSocket(Constants.PORT);
            System.out.println("Сервер запустился ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connectNewClientInToServer(){
        Database dataBaseHandler = new Database();

        try {
            while (true) {
                Socket socket = server.accept();
                usersConnected.add(new ClientHandler(socket,dataBaseHandler,idUser++));
                System.out.println("Клиент  подключился !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAll(){
        try {

            server.close();
            System.out.println("Сервер остановился !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/

