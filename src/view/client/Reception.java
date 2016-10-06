package view.client;

import java.io.BufferedReader;
import java.io.IOException;

public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	
	public Reception(BufferedReader in){
		
		this.in = in;
	}
	
	public void run() {
		
        try {
            while((message = in.readLine()) != null){
                System.out.println(message);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
	}
    
}
