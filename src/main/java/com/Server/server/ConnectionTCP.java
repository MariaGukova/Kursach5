package com.Server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTCP {
    private Socket socket ;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ConnectionTCP(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection happened");
        } catch (IOException e) {
            throw new RuntimeException("can't initialise", e);
        }
    }

    public void writeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object readObject() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);// не требует обработки
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
