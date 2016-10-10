package controller;
import model.Player;
import java.io.PrintWriter;
import java.io.BufferedReader;

import java.net.Socket;

public class Client extends Player {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    public Client(Socket socket, BufferedReader in, PrintWriter out, String name) {
        super(name);
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    
    public Socket getSocket() { return socket; }
    public BufferedReader getIn() { return in; }
    public PrintWriter getOut() { return out; }
    
}