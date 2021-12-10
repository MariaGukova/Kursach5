package com.Server.ExstractProjects;

import com.Server.dataBase.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
import com.example.it.model.Customer;
import com.example.it.model.Otchet;
import com.example.it.model.Project;
import com.example.it.model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class RequestHandler implements Runnable {
        private final ConnectionTCP connectionTCP;
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;

        public RequestHandler(Socket socket) {
            System.out.println("Я тут есть");
            connectionTCP = new ConnectionTCP(socket);//сокет соединения с клиентом
            this.socket = socket;
            run();

        }

        @Override
        public void run() {

            Database Projects = new Database();
            Database Users = new Database();
            Database Otchets = new Database();
            Database Customers = new Database();

            while (true) {

                Command command = (Command) connectionTCP.readObject();
                System.out.println(command);
                switch (command) {
                    case CREATE: {
                        Project project = (Project) connectionTCP.readObject();
                        Projects.addProject(project);
                    }
                    break;
                    case CREATE1:
                    {
                        User user = (User) connectionTCP.readObject();
                        Users.addUser(user);
                    }
                    break;
                    case READ: {
                        List<Project> projects = null;
                        projects = Projects.getAllProjects();
                        connectionTCP.writeObject(projects);
                    }
                    break;
                    case READ1: {
                        List<User> users = null;
                        users = Users.getAllUsers();
                        connectionTCP.writeObject(users);
                    }
                    break;
                    case READ2: {
                        List<Otchet> otchets = null;
                        otchets = Otchets.getOtchet();
                        connectionTCP.writeObject(otchets);
                    }
                    break;
                    case READ3: {
                        List<Customer> customers = null;
                            customers = Customers.getCustomers();
                        connectionTCP.writeObject(customers);
                    }
                    break;

                    case READ4: {
                        List<Project> projects = null;
                        projects = Projects.getAllLevel();
                        connectionTCP.writeObject(projects);
                    }
                    break;
                    case DELETE: {
                        Integer id = (Integer) connectionTCP.readObject();
                        Projects.deleteProjectByID(id);
                    }
                    break;
                    case DELETE1: {

                        Integer id = (Integer) connectionTCP.readObject();
                        Users.deleteUserByID(id);
                    }
                    break;
                    case UPDATE: {
                        Project project = (Project) connectionTCP.readObject();
                        Projects.updateProject(project);
                    }
                    break;

                    case UPDATE1: {

                        User user = (User) connectionTCP.readObject();
                        Users.updateUser(user);
                    }
                    break;
                    case EXIT: {
                        connectionTCP.close();
                        return;
                    }

                }

            }}

}
