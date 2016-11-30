package view.ihm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Fichier {

	/*
	 * return bufferedReader from the name of the file who is give
	 */
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
	
	/*
	 * return a arraylist<string> from the file who is give
	 */
	public static ArrayList<String> getLignes(BufferedReader br) {
		ArrayList<String> al = new ArrayList<String>();
		
		String str = "";
		try {
			while( (str = br.readLine()) != null ) {
				al.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return al;
	}
	
	/*
	 * return money from the name who is give
	 */
	public static int getMoney(String name) {
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		int retour = 5555;

		for(int i=0;i<al.size();i++)
			if(al.get(i).split("-")[0].equals(name))
				retour = Integer.parseInt(al.get(i).split("-")[1].trim());
		
		return retour;
	}
	
	/*
	 * change money from the name who is give
	 */
	public static void setMoney(String name, int money) {
		if(name.equals("Joueur")) return;
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		FileWriter ffw;

		int cpt = 0;
		for(int i=0;i<al.size();i++)
			if(al.get(i).split("-")[0].equals(name))
				cpt = i;
		
		try {
			ffw = new FileWriter("res/tools/joueurs.txt");
			for(int i=0;i<al.size();i++) {
				if(i==cpt) ffw.write(name + "-" + money + " \r\n");
				else ffw.write(al.get(i) + " \r\n");
			}
			ffw.close();
		} catch (IOException ex) {}
	}

	/*
	 * add a player in the file joueurs.txt
	 */
	public static void add(String str) {
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		FileWriter ffw;
		try {
			ffw = new FileWriter("res/tools/joueurs.txt");
			for(int i=0;i<al.size();i++) {
				ffw.write(al.get(i) + " \r\n");
			}
			ffw.write(str.toUpperCase() + "-5500");
			ffw.close();
		} catch (IOException ex) {}
	}
	
	/*
	 * remove a player in the file joueurs.txt
	 */
	public static void remove(String str) {
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		FileWriter ffw;
		try {
			ffw = new FileWriter("res/tools/joueurs.txt");
			for(int i=0;i<al.size();i++) {
				if(!str.equals(al.get(i).split("-")[0]))
					ffw.write(al.get(i) + " \r\n");
			}
			ffw.close();
		} catch (IOException e) {}
	}
}
