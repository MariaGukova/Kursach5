package com.Server.ExtractUsers;

import com.Server.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
import com.example.it.User;

import java.net.Socket;
import java.util.List;

public class RequestHandlerUser implements Runnable {
    private final ConnectionTCP connectionTCP;

    public RequestHandlerUser(Socket socket) {
        connectionTCP = new ConnectionTCP(socket);//сокет соединения с клиентом
    }


    @Override
    public void run() {
        Database Users = new Database();
        while (true) {
            Command command = (Command) connectionTCP.readObject();
            System.out.println(command);
            switch (command) {
                case CREATE: {
                    User user = (User) connectionTCP.readObject();
                    Users.addUser(user);
                }
                break;
                case READ: {
                    List<User> users = null;
                    users = Users.getAllUsers();
                    connectionTCP.writeObject(users);
                }
                break;
                case DELETE: {
                    Integer id = (Integer) connectionTCP.readObject();
                    Users.deleteUserByID(id);
                }
                break;
                case UPDATE: {
                    User user = (User) connectionTCP.readObject();
                    Users.updateUser(user);
                }
                break;

                case EXIT: {
                    connectionTCP.close();
                    return;
                }
            }
        }
    }

}

