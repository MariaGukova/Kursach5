package com.Server.ExstractProjects;

import com.Server.Command;
import com.Server.dataBase.Database;
import com.Server.server.ConnectionTCP;
import com.example.it.Project;

import java.net.Socket;
import java.util.List;


public class RequestHandler implements Runnable {
        private final ConnectionTCP connectionTCP;

        public RequestHandler(Socket socket) {
            connectionTCP = new ConnectionTCP(socket);//сокет соединения с клиентом
        }


        @Override
        public void run() {
            Database Projects = new Database();
            while (true) {
                Command command = (Command) connectionTCP.readObject();
                System.out.println(command);
                switch (command) {
                    case CREATE: {
                        Project project = (Project) connectionTCP.readObject();
                        Projects.addProject(project);
                    }
                    break;
                   case READ: {
                       List<Project> projects = null;
                       projects  = Projects.getAllProjects();
                       connectionTCP.writeObject(projects);
                    }
                    break;
                    case DELETE: {
                        Integer id = (Integer) connectionTCP.readObject();
                        Projects.deleteProjectByID(id);
                    }
                    break;
                    case UPDATE: {
                        Project project = (Project) connectionTCP.readObject();
                        Projects.updateProject(project);
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