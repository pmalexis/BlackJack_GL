package controller;
import model.Player;
import java.io.PrintWriter;
import java.io.BufferedReader;

import java.net.Socket;

public class Client extends Player {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int id;
    
    public Client(Socket socket, BufferedReader in, PrintWriter out, String name) {
        super(name);
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = 0;
    }
    
    public Socket getSocket() { return socket; }
    public BufferedReader getIn() { return in; }
    public PrintWriter getOut() { return out; }
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    
}