package controller;

import java.net.Socket;

public class Player {
    
    private Socket socket;
    private String name;
    
    public Player(Socket socket) {
        this.socket = socket;
        this.name = null;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() { return name; }
    public Socket getSocket() { return socket; }
    
}