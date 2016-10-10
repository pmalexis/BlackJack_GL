package controller;

import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Connexion implements Runnable {

    BufferedReader in;
    PrintWriter out;
    Socket socket;
    
	public Connexion(Socket socket) {
        this.socket = socket;
    }

	public void run() {
		try {
            /* open flux */
            Scanner sc = new Scanner(System.in);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            String clientName;
            
            /* retrieve client name */
            clientName = in.readLine();
            /* creation of the object client */
            Client client = new Client(socket, in, out, clientName);
            Server.addClient(client);
            System.out.println(client.getName() + " join the server");
            
            /*while(in.read() != -1) {
                System.out.println("sdfsdf");
            }*/
            
            /*System.out.println(client.getName() + " left the server");
            Server.delClient(client);*/
            
            /*Thread clientServer = new Thread(new ClientServer(in, out, client));
            clientServer.start();*/
            
            
        } catch (IOException e) {
            System.err.println(e);
        } /*catch (InterruptedException e) {
            System.err.println(e);
        }*/
	}
}