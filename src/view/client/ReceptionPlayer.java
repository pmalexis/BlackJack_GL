package view.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.Player;

public class ReceptionPlayer implements Runnable {

	private ArrayList<Player> allPlayer;
	private Socket socket;
	
	public ReceptionPlayer(Socket socket) {
		allPlayer = new ArrayList<Player>();
		this.socket = socket;
	}
	
	public void run() {
		
		while(true) {
			System.out.println("zefzef");
			ObjectInputStream iin;
			try {
				Thread.sleep(1000);
				iin = new ObjectInputStream(socket.getInputStream());
				ArrayList<Player> allPlayer = (ArrayList<Player>) iin.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			for(Player player : allPlayer) {
				System.out.println(player.getName());
			}
			
			System.out.println("bite");
			
		}
		
	}
}
