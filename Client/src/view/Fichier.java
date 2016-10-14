package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Fichier {

	public static BufferedReader getFichier(String fichier) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("res/tools/" + fichier));
			return br;
		}		
		catch (Exception e){
			System.err.println("Fichier non trouve, il a donc ete cree");
			new File("res/tools/" + fichier);
		}
		return null;
	}
}
