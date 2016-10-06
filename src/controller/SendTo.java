package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

public class SendTo implements Runnable {

    private Socket socket;
    private String message;
	
	public SendTo(String message, Socket socket) {
        this.message = message;
        this.socket = socket;
	}

	public void run() {		  
		 
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();
        
        } catch (IOException e) {
            System.err.println(e);
        }
			  
	}
}
