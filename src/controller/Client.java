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
    private boolean over;
    
    public Client(Socket socket, BufferedReader in, PrintWriter out, String name) {
        super(name);
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = 0;
        this.over = false;
    }
    
    public Socket getSocket() { return socket; }
    public BufferedReader getIn() { return in; }
    public PrintWriter getOut() { return out; }
    public int getId() { return id; }
    public boolean getOver() { return over; }
    
    public void setId(int id) { this.id = id; }
    public void setOver(boolean b) { this.over = b; }
    
}