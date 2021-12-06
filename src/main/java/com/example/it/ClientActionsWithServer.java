package com.example.it;

import java.io.*;
import java.net.Socket;

public class ClientActionsWithServer {

    private static BufferedReader acceptMessage;
    private  static BufferedWriter sendMessage;

    public ClientActionsWithServer(Socket clientSocket) {
        try {
            acceptMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sendMessage = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


   /* public ArrayList<Project> allProjects() throws IOException {

        LinkedList<Project> projects = new LinkedList<>();

        int sizeList = Integer.parseInt(acceptMessage.readLine());
        for(int i=0;i<sizeList;i++){
            addProjectInList(acceptMessage.readLine(), projects);
        }
        return projects;
    }
    private void addProjectInList(String string, LinkedList<Project> projects){
        String[] product;
        product = string.split(" ");
        projects.add(new Project(Integer.parseInt(product[0]),product[1],product[2],product[3],product[4]));

    }*/

}
