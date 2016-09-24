package cartes;

public class MoteurBlackjack {

	private final static int NB_CARDS_BY_HANDS_START = 2;
	
	private int nb_players;
	
	private Paquet paquet; //Le paquet de carte
	
	private Carte[][] hands; //la main des joueurs + banquier
	
	private Jeton[][] jetonsTable; //savoir combien les joueurs ont misé sur la table
	
	/*
	 * Constructor
	 */
	public MoteurBlackjack(int nb_players) {
		this.paquet = new Paquet();
		this.nb_players = nb_players;
		
		this.initAll();
	}
	
	/*
	 * initialization of the class
	 */
	public void initAll() {
		this.hands       = new Carte[ (this.nb_players*2) + 1][10];
		this.jetonsTable = new Jeton[ (this.nb_players) ][9];
	
		this.initPaquet();
	}
	
	/*
	 * initialization of the paquet
	 */
	public boolean initPaquet() {
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carreau, Couleur.Coeur};
		
		for(int nb_pack=0;nb_pack<1;nb_pack++) //nb pack
			for(int i=0;i<4;i++) // 4 couleurs
				for(int j=1;j<14;j++) { // 13 cartes
					paquet.addTop(new Carte(j, tabCouleur[i]));
				}
			
		return true;
	}
	
	/*
	 * distribution
	 */
	public boolean distribution() {
		this.paquet.mixCards();
		
		for(int i=0;i<MoteurBlackjack.NB_CARDS_BY_HANDS_START;i++) 
			for(int j=0;j< (this.hands.length - this.nb_players) ;j++) {
				this.hands[j][i] = this.paquet.dropTop();
			}
		
		return true;
	}
	
	/*
	 * a turn 
	 */
	public boolean hit(int player_now) {
		
		for(int i=0;i<this.hands[0].length;i++)
			if(this.hands[player_now][i] == null) {
				this.hands[player_now][i] = this.paquet.dropTop();
				break;
			}
		
		return true;
	}
	
	/*
	 * double jeton
	 */
	public boolean double_(int player_now) {
		
		this.hit(player_now);
		//this.jetonsTable[player_now] = this.jetonsTable[player_now] * 2;
		
		return true;
	}
	
	/*
	 * split 
	 */
	public boolean split(int player_now) {
		
		if( this.canSplit(player_now) ) 
		{	
			this.hands[player_now + this.nb_players][0] = this.hands[player_now][1];
			this.hands[player_now + this.nb_players][1] = this.paquet.dropTop();
			this.hands[player_now][1]                   = this.paquet.dropTop();
					
			return true;
		}
		
		return false;
	} 
	
	/*
	 * 
	 */
	public boolean assurance(int player_now) {
		
		if(this.hands[0][0].getHauteur() == 1) {
			
			return true;
		}
		
		return false;
	}
	
	/*
	 * 
	 */
	public boolean blackjack(int player_now) {
		int n = 0;
		
		if(this.hands[player_now][0].getHauteur() == 1) n += 11;
		else n += this.hands[player_now][0].getHauteur() > 10 ? 10 : this.hands[player_now][0].getHauteur();
		
		if(this.hands[player_now][1].getHauteur() == 1) n += 11;
		else n += this.hands[player_now][1].getHauteur() > 10 ? 10 : this.hands[player_now][1].getHauteur();
		
		return n == 21;
	}
	
	public boolean canSplit(int player_now) {
		return (this.hands[player_now][0].getHauteur()>10?10:this.hands[player_now][0].getHauteur()) 
				== (this.hands[player_now][1].getHauteur()>10?10:this.hands[player_now][1].getHauteur());
	}
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public Paquet getPaquet() {
		return this.paquet;
	}
	
	public Carte[][] getHands() {
		return this.hands;
	}
	
	public String getHand(int player_now) {
		String s = "";
		
		if(player_now == 0) s+= "Banquier : ";
		else s+= (player_now>this.nb_players?"Joueur BIS " +(player_now-this.nb_players):"Joueur " + player_now) + " : ";
		
		for(int j=0;j<this.hands[0].length;j++)
			if(this.hands[player_now][j] != null)
				s += (player_now == 0 && j > 0 ? "--------- | " : this.hands[player_now][j] + " | " ) ;
		
		return s;
	}
	
	public int getValeurJoueur(int i) {
		int n  = 0;
		int as = 0;
		
		for(int j=0;j<this.hands[0].length;j++)
			if(this.hands[i][j] != null) 
				if(this.hands[i][j].getHauteur() == 1) as++;
				else n += (this.hands[i][j].getHauteur()>10?10:this.hands[i][j].getHauteur());
		
		if(as > 0) n++;
		
		return n;
	}
	
	/* --------------------------- *
	 *   SET - change the values
	 * --------------------------- */
	public void setJetonTable(int player_now, Jeton valeur_jeton) {
	}
	
	/*
	 * return string ...
	 */
	public String toString() {
		String s = "";
	
		for(int i=0;i<this.hands.length;i++) {
			for(int j=0;j<this.hands[0].length;j++) {
				if(j==0) {
					if(i==0) s+= "Banquier : ";
					else s+= "Joueur " + i + " : ";
				}
				if(this.hands[i][j] != null)
					s += this.hands[i][j] + " | ";
			}
			s += "\n";
		}
		
		return s;
	}
}
