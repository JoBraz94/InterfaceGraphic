package ca.csf.dfc.donnees.tp.controller;

import java.awt.Color;

public class Ligne extends Forme{

	
	public Ligne(int p_X, int p_Y, int p_Hauteur, int p_Largeur, int p_Trait, Color p_Couleur, Color p_Remplissage) {
		
		this.m_X = p_X;
		this.m_Y = p_Y;
		this.m_Hauteur = p_Hauteur;
		this.m_Largeur = p_Largeur;
		this.m_Trait = p_Trait;
		this.m_Couleur = p_Couleur;
		this.m_Remplissage = p_Remplissage;
		
	}
	
	public Ligne(IForme p_Ligne) {
		this.m_X = p_Ligne.GetX();
		this.m_Y = p_Ligne.GetY();
		this.m_Hauteur = p_Ligne.GetHauteur();
		this.m_Largeur = p_Ligne.GetLargeur();
		this.m_Trait = p_Ligne.GetTrait();
		this.m_Couleur = p_Ligne.GetCouleur();
		this.m_Remplissage = p_Ligne.GetRemplissage();
	}
	
	@Override
	public Boolean isclicked(int p_X, int p_Y) {
		
		int x1 = this.m_X;
		int y1 = this.m_Y;
		int x2 = (x1 + this.m_Largeur);
		int y2 = (y1 - this.m_Hauteur);
			
		Boolean retour = false;		
		
		double a = ((y2-y1)/(x2-x1));		
		double b = -((a*x2)-y2);
	
		
		
		if((y1 == y2) && ((p_Y >= (y1 - 20)) && (p_Y <= (y1 + 20)))) {
			
			if(((p_X >= (x1 - 20)) && (p_X <= (x2 + 20)))) {
				retour = true;
			}
			
		}
		else if((x1 == x2) && ((p_X >= (x1 - 20)) && (p_X <= (x1 + 20)))) {
			
			if(((p_Y >= (y1 - 20)) && (p_Y <= (y2 + 20)))) {
				retour = true;
			}
						
		}
		else if ((this.m_Hauteur > 0) && ((p_X >= x1)&&(p_X <= x2))&&((p_Y >= y2)&&(p_Y <= y1))) {
			
			if((p_Y >= ((a * p_X + b) - 20)) && (p_Y <= ((a * p_X + b) + 20))) {
				retour = true;
			}
			
		}
		else if ((this.m_Hauteur < 0) && ((p_X >= x1)&&(p_X <= x2))&&((p_Y <= y2)&&(p_Y >= y1))) {
			
			if((p_Y >= ((a * p_X + b) - 20)) && (p_Y <= ((a * p_X + b) + 20))) {
				retour = true;
			}
			
		}
				
		
		

		return retour;
		
	}

	@Override
	public void Deplacer(int p_X, int p_Y) {
		this.setX(p_X);
		this.setY(p_Y);		
	}

	@Override
	public void Modifier(int p_Largeur, int p_Hauteur) {
		this.setLargeur(p_Largeur);
		this.setHauteur(p_Hauteur);	
	}

	@Override
	public String GetForme() {
		return "ligne";
	}

	@Override
	public int GetX() {
		return this.m_X;
	}

	@Override
	public int GetY() {
		return this.m_Y;
	}

	@Override
	public int GetHauteur() {
		return this.m_Hauteur;
	}

	@Override
	public int GetLargeur() {
		return this.m_Largeur;
	}

	@Override
	public int GetTrait() {
		return this.m_Trait;
	}

	@Override
	public Color GetCouleur() {
		return this.m_Couleur;
	}

	@Override
	public Color GetRemplissage() {
		return this.m_Remplissage;
	}

	@Override
	public int compareTo(IForme o) {
		int retour = -1;
		
		if ((this.m_X == o.GetX()) && (this.m_Y == o.GetY()) &&
				(this.m_Hauteur == o.GetHauteur()) && (this.m_Largeur == o.GetLargeur()) &&
				(this.m_Trait == o.GetTrait()) && (this.m_Couleur == o.GetCouleur()) &&
				(this.m_Remplissage == o.GetRemplissage()) && (this.GetForme() == o.GetForme())) {	
			
			retour = 0;
		}		
		return retour;
	}

	@Override
	protected void setX(int p_X) {
		this.m_X = p_X;
	}

	@Override
	protected void setY(int p_Y) {
		this.m_Y = p_Y;	
	}

	@Override
	protected void setHauteur(int p_Hauteur) {
		this.m_Hauteur = p_Hauteur;	
	}

	@Override
	protected void setLargeur(int p_Largeur) {
		this.m_Largeur = p_Largeur;
	}

	@Override
	protected void setTrait(int p_Trait) {
		this.m_Trait = p_Trait;
		
	}

	@Override
	protected void setCouleur(Color p_Couleur) {
		this.m_Couleur = p_Couleur;	
	}

	@Override
	protected void setRemplissage(Color p_Remplissage) {
		this.m_Remplissage = p_Remplissage;
		
	}

	@Override
	public IForme GetCopie() {
		return new Ligne(this);
  }
  
	public void ModifierCouleur(Color p_couleur) {
		this.m_Couleur = p_couleur;
		
	}

	@Override
	public void ModifierTrait(int p_trait) {
		this.m_Trait = p_trait;
		
	}

	@Override
	public void ModifierRemplisage(Color p_couleur) {
		this.m_Remplissage = p_couleur;
	}
}
