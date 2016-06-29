package Joueur;


public class classJoueur 
{
	private String nom;
	private classBateau porteavion;
	private classBateau croiseur;
	private classBateau contretorpilleur;
	private classBateau sousmarin;
	private classBateau torpilleur;
	
	public classJoueur(String nom, classBateau porteavion, classBateau croiseur, classBateau contretorpilleur,
			classBateau sousmarin, classBateau torpilleur) {
		super();
		this.nom = nom;
		this.porteavion = porteavion;
		this.croiseur = croiseur;
		this.contretorpilleur = contretorpilleur;
		this.sousmarin = sousmarin;
		this.torpilleur = torpilleur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public classBateau getPorteavion() {
		return porteavion;
	}

	public void setPorteavion(classBateau porteavion) {
		this.porteavion = porteavion;
	}

	public classBateau getCroiseur() {
		return croiseur;
	}

	public void setCroiseur(classBateau croiseur) {
		this.croiseur = croiseur;
	}

	public classBateau getContretorpilleur() {
		return contretorpilleur;
	}

	public void setContretorpilleur(classBateau contretorpilleur) {
		this.contretorpilleur = contretorpilleur;
	}

	public classBateau getSousmarin() {
		return sousmarin;
	}

	public void setSousmarin(classBateau sousmarin) {
		this.sousmarin = sousmarin;
	}

	public classBateau getTorpilleur() {
		return torpilleur;
	}

	public void setTorpilleur(classBateau torpilleur) {
		this.torpilleur = torpilleur;
	}	
}
