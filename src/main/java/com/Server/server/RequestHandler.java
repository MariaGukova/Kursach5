package com.Server.server;

import com.Server.Command;
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
            ProjectRepository projectRepository = new ProjectRepository();
            while (true) {
                Command command = (Command) connectionTCP.readObject();
                System.out.println(command);
                switch (command) {
                    case CREATE: {
                        Project project = (Project) connectionTCP.readObject();
                        projectRepository.addProject(project);
                    }
                    break;
                    case READ: {
                        List<Project> projects = projectRepository.getAllProjects();
                        connectionTCP.writeObject(projects);
                    }
                    break;
                    case UPDATE: {
                        Project project = (Project) connectionTCP.readObject();
                        projectRepository.updateProject(project);
                    }
                    break;
                    case DELETE: {
                        Integer id = (Integer) connectionTCP.readObject();
                        projectRepository.deleteProjectByID(id);
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