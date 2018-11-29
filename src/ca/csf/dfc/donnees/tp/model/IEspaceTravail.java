package ca.csf.dfc.donnees.tp.model;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ca.csf.dfc.donnees.tp.controller.IForme;

public interface IEspaceTravail   {

	public void setTaille(int p_x, int p_y);
	
	public void supprimer(IForme p_forme);
	
	public IForme verifierClick();
	
	public void draw(IForme p_forme);
	
	public void ajouterMouseMotionListener(MouseMotionListener p_MML);
	
	public void ajouterMouseListener(MouseListener p_ML);
}