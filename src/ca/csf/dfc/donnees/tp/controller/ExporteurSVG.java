/**
 * 
 */
package ca.csf.dfc.donnees.tp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ca.csf.dfc.donnees.tp.model.*;

/**
 * Classe qui gère l'exportation sous format SVG un espace de travail.
 * @author JBrazeau
 *
 */
public class ExporteurSVG implements IExporteur{
	static private ExporteurSVG m_Instance = null;
	
	/**
     * Constructeur par défaut.
     * Associe l'instance à m_Instance.
     */
	private ExporteurSVG() { 
		ExporteurSVG.m_Instance = this;
	}
	
	/**
	 * Retourne l'instance existante ou nouvellement créée.
     * @return l'instance
	 */
	static public ExporteurSVG getInstance() {
		if(ExporteurSVG.m_Instance == null) {
			new ExporteurSVG();
		}
		
		return ExporteurSVG.m_Instance;
	}
	
	// EXPORTATION
	
	/**
	 * Exporte l'espace de travail dans un fichier format SVG.
	 * @param p_EspaceTravail L'espace de travail.
	 */
	public void Exporter(IEspaceTravail p_EspaceTravail) 
	{
		PrintWriter doc = null;
		try 
		{
			Integer hauteurEspace = p_EspaceTravail.getHauteur();
			Integer largeurEspace = p_EspaceTravail.getLargeur();
			
			doc = creationPrintWriterSVGParJFileChooser();
			doc.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			doc.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \r\n" + 
					"  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
			doc.println("<svg height=\""+ hauteurEspace +"\" width=\"" + largeurEspace +"\" version=\"1.1\" " + 
					"xmlns=\"http://www.w3.org/2000/svg\">");
			
			ecritureFormatSVGDesFormes(doc, p_EspaceTravail);
			
			doc.println("</svg>");	
		}
		catch(IOException exp) 
		{
			System.err.println("Erreur d'écriture : " + exp);
		}
		catch(NullPointerException exp) 
		{
			System.err.println("Erreur, référence inexistante : " + exp);
		}
		finally
		{
			if(doc != null) {
				doc.close();
			}
		}
	}
	
	/**
     * Permet d'avoir un PrintWriter avec l'extension svg par l'entremise d'un JFileChooser
     * @return Le PrintWriter
     * @throws IOException Si le JFIleChooser est annulé ou le nom de fichier est d'un format inadéquat.
     */
	private PrintWriter creationPrintWriterSVGParJFileChooser() throws IOException 
	{
		PrintWriter fileWriter = null;
    	JFileChooser chooser = new JFileChooser();
    		FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichiers svg (*.svg)","svg");
    		chooser.setFileFilter(filtre);
    		
    	// Windows
    	/*  chooser.setCurrentDirectory(new File("/home/%username%/Documents"));
   		int valRetournee = chooser.showOpenDialog(null); */
    		
    	int valRetournee = chooser.showSaveDialog(chooser.getParent());
    	
    	if (valRetournee == JFileChooser.APPROVE_OPTION) 
    	{
    		String nomFichier = chooser.getSelectedFile().toString();
    		if(!nomFichier.endsWith(".svg")){
    			nomFichier += ".svg";
    		}
    		
    	     fileWriter = new PrintWriter(new File(nomFichier), "UTF-8");
    	}
    	
    	return fileWriter;
	}

	/**
	 * S'occupe de l'écriture des formes de l'espace de travail lors de l'exportation SVG. 
	 * @param p_Doc Le PrintWriter de l'exportation en cours. 
	 * @param p_EspaceTravail L'espace de travail exporté.
	 */
	private void ecritureFormatSVGDesFormes(PrintWriter p_Doc, IEspaceTravail p_EspaceTravail) {
		
		for(IForme forme: p_EspaceTravail) {

			if(forme.GetForme() == "oval") 

			{
				ecritureFormatSVGDeOvale(p_Doc, (Oval)forme);
			}
			else if(forme.GetForme() == "rectangle") 
			{
				ecritureFormatSVGDeRectangle(p_Doc, (Rectangle)forme);
			}
			else if(forme.GetForme() == "ligne") 
			{
				ecritureFormatSVGDeLigne(p_Doc, forme);
			}
		}
	}

	/**
	 * Écrit une forme de type Ovale dans le fichier SVG.
	 * @param p_Doc Le PrintWriter de l'exportation en cours.
	 * @param p_Ovale L'ovale exporté.
	 */
	private void ecritureFormatSVGDeOvale(PrintWriter p_Doc, Oval p_Ovale) {
		Integer cx = p_Ovale.GetX(); 
		Integer cy = p_Ovale.GetY();
		Integer rx = p_Ovale.GetLargeur()/2;
		Integer ry = p_Ovale.GetHauteur()/2;
		//Style (couleurs rgb)
		Integer fillRed     = p_Ovale.GetRemplissage() != null ? p_Ovale.GetRemplissage().getRed() : 0; 
		Integer fillGreen   = p_Ovale.GetRemplissage() != null ? p_Ovale.GetRemplissage().getGreen() : 0;
		Integer fillBlue    = p_Ovale.GetRemplissage() != null ? p_Ovale.GetRemplissage().getBlue() : 0;
		double fillOpacity = p_Ovale.GetRemplissage() != null ? 1.0 : 0.0;
		Integer strokeRed   = p_Ovale.GetCouleur().getRed();
		Integer strokeGreen = p_Ovale.GetCouleur().getGreen();
		Integer strokeBlue  = p_Ovale.GetCouleur().getBlue();
		Integer strokeWidth = p_Ovale.GetTrait();
		
		p_Doc.println("	<ellipse cx=\""+ cx +"\" cy=\""+ cy +"\" rx=\""+ rx +"\" ry=\""+ ry +"\"\r"
				    + " style=\"fill:rgb("+ fillRed +", "+ fillGreen +", "+ fillBlue+ ");"
				    	     + "fill-opacity:" + fillOpacity + ";"
				    		 + "stroke:rgb("+ strokeRed +", "+ strokeGreen +", "+ strokeBlue +");"
				    		 + "stroke-width:"+ strokeWidth +"\" />");
	}
	
	/**
	 * Écrit une forme de type Rectangle dans le fichier SVG.
	 * @param p_Doc Le PrintWriter de l'exportation en cours.
	 * @param p_Rectangle Le rectangle exporté.
	 */
	private void ecritureFormatSVGDeRectangle(PrintWriter p_Doc, Rectangle p_Rectangle) {
		Integer x      = p_Rectangle.GetX(); 
		Integer y      = p_Rectangle.GetY();
		Integer width  = p_Rectangle.GetLargeur();
		Integer height = p_Rectangle.GetHauteur();
		//Style (couleurs rgb)
		Integer fillRed     = p_Rectangle.GetRemplissage() != null ? p_Rectangle.GetRemplissage().getRed() : 0; 
		Integer fillGreen   = p_Rectangle.GetRemplissage() != null ? p_Rectangle.GetRemplissage().getGreen() : 0;
		Integer fillBlue    = p_Rectangle.GetRemplissage() != null ? p_Rectangle.GetRemplissage().getBlue() : 0;
		double 	fillOpacity =  p_Rectangle.GetRemplissage() != null ? 1.0 : 0.0;
		Integer strokeRed   = p_Rectangle.GetCouleur().getRed();
		Integer strokeGreen = p_Rectangle.GetCouleur().getGreen();
		Integer strokeBlue  = p_Rectangle.GetCouleur().getBlue();
		Integer strokeWidth = p_Rectangle.GetTrait();
		
		p_Doc.println("	<rect x=\""+ x +"\" y=\""+ y +"\" width=\""+ width +"\" height=\""+ height +"\" "
			     + " style=\"fill:rgb("+ fillRed +", "+ fillGreen +", "+ fillBlue+ ");"
			     		  + "fill-opacity:" + fillOpacity + ";"
	    		          + "stroke:rgb("+ strokeRed +", "+ strokeGreen +", "+ strokeBlue +");"
	    		          + "stroke-width:"+ strokeWidth +"\" />");
	}
	
	/**
	 * Écrit une forme de type Ligne dans le fichier SVG.
	 * @param @param p_Doc Le PrintWriter de l'exportation en cours.
	 * @param p_Ligne La ligne exportée.
	 */
	private void ecritureFormatSVGDeLigne(PrintWriter p_Doc, IForme p_Ligne) {
		Integer x1 = p_Ligne.GetX();
		Integer y1 = p_Ligne.GetY();
		Integer x2 = x1 + p_Ligne.GetLargeur();
		Integer y2 = y1 + p_Ligne.GetHauteur();
		//Style
		Integer strokeRed   = p_Ligne.GetCouleur().getRed();
		Integer strokeGreen = p_Ligne.GetCouleur().getGreen();
		Integer strokeBlue  = p_Ligne.GetCouleur().getBlue();
		Integer strokeWidth = p_Ligne.GetTrait();
		
		p_Doc.println("	<line x1=\""+ x1 +"\" y1=\""+ y1 +"\" x2=\""+ x2 +"\" y2=\""+ y2 +"\" "
				    + " style=\"stroke:rgb("+ strokeRed +", "+ strokeGreen +", "+ strokeBlue +");" 
				            +  "stroke-width:"+ strokeWidth +"\" />");
	}


}
