package view.client;

import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

public class Client {

	public static void main(String[] args) {
		try {
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter your name bellow");
            String name = sc.nextLine();
            
            /* open flux */
            Socket socket = new Socket("localhost",1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));;
            
            /* send client name */
            out.println(name);
            out.flush();
            
            Thread emission = new Thread(new Emission(out));
            Thread reception = new Thread(new Reception(in));
            emission.start();
            reception.start();
            
            
        } catch (IOException e) {
            System.err.println(e);
        }
	}
}