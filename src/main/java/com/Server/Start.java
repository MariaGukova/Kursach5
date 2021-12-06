package com.Server;

import com.Server.server.ServerApplication;

/*public class Start {

    public static void main(String[] args) {
        ServerConnection serverConnection = new ServerConnection();
        serverConnection.startServer();
        serverConnection.connectNewClientInToServer();
        serverConnection.closeAll();
    }
}*/
class ServerMain {
    public static void main(String[] args) {
        new ServerApplication().run();
    }
}
