/**
 * 
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 * @version 0.7 du 15/09/2019
 */

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Fenetre d'import des fichiers
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 *
 */
public class ThotImportFrame extends JFrame{
	//Attribut
	protected JFileChooser fenetre;
	protected File fichier;
	protected String parentDirectory;
	protected importType typeImport;
	
	//Constructeur
	public ThotImportFrame(String directory) {
		this.fenetre = new JFileChooser();
		this.typeImport = importType.ALL;
		this.parentDirectory = directory;
	}
	
	//Enum
	enum importType {
		ALL,
		TEXT,
		AUDIO,
		VIDEO,
		PICTURE,
		MEDIA
	}
	
	//Inner Class
	
	/**
	 * Classe interne à ThotImportFrame
	 * @see ThotImportFrame
	 */
	protected class ThotFilterText extends FileFilter{

		/**
		 * @param f File
		 * Retourne vrai si le File f est accepté par ThotFilterText
		 * @return boolean
		 * @see File
		 * @see ThotFilterText
		 */
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".txt")
					|| f.getName().endsWith(".md"));
		}
		
		/**
		 * @return String
		 */
		public String getDescription() {
			return "Fichiers texte";
		}
		
	}
	
	/**
	 * Classe interne à ThotImportFrame
	 * @see ThotImportFrame
	 */
	protected class ThotFilterAudio extends FileFilter{
		
		/**
		 * @param f File
		 * Retourne vrai si le File f est accepté par ThotFilterAudio
		 * @return boolean
		 * @see File
		 * @see ThotFilterAudio
		 */
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".wov")
					|| f.getName().endsWith(".mp3")
					|| f.getName().endsWith(".aif"));
		}
		
		/**
		 * @return String
		 */
		public String getDescription() {
			return "Fichiers Audio";
		}
		
	}
	
	/**
	 * Classe interne à ThotImportFrame
	 * @see ThotImportFrame
	 */
	protected class ThotFilterVideo extends FileFilter{
		
		/**
		 * @param f File
		 * Retourne vrai si le File f est accepté par ThotFilterVideo
		 * @return boolean
		 * @see File
		 * @see ThotFilterVideo
		 */
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".mov")
					|| f.getName().endsWith(".avi")
					|| f.getName().endsWith(".m2ts")
					|| f.getName().endsWith(".ts")
					|| f.getName().endsWith(".mkv")
					|| f.getName().endsWith(".mp4"));
		}
		
		/**
		 * @return String
		 */
		public String getDescription() {
			return "Fichiers Video";
		}
		
	}
	
	/**
	 * Classe interne à ThotImportFrame
	 * @see ThotImportFrame
	 */
	protected class ThotFilterPicture extends FileFilter{
		
		/**
		 * @param f File
		 * Retourne vrai si le File f est accepté par ThotFilterPicture
		 * @return boolean
		 * @see File
		 * @see ThotFilterPicture
		 */
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".png")
					|| f.getName().endsWith(".gif")
					|| f.getName().endsWith(".jpg")
					|| f.getName().endsWith(".jpeg"));
		}
		
		public String getDescription() {
			return "Images";
		}
		
	}
	
	/**
	 * Classe interne à ThotImportFrame
	 * @see ThotImportFrame
	 */
	protected class ThotFilterMedia extends FileFilter{
		ThotFilterAudio audio = new ThotFilterAudio();
		ThotFilterVideo video = new ThotFilterVideo();
		ThotFilterPicture picture = new ThotFilterPicture();
		
		/**
		 * @param f File
		 * Retourne vrai si le File f est accepté par ThotFilterMedia
		 * @return boolean
		 * @see File
		 * @see ThotFilterAudio
		 * @see ThotFilterVideo
		 * @see ThotFilterPicture
		 * @see ThotFilterMedia
		 */
		public boolean accept(File f) {
			return audio.accept(f) == true || video.accept(f) == true || picture.accept(f);
		}

		/**
		 * @return String
		 */
		public String getDescription() {
			return "media";
		}

	}

	//Méthodes
	
	/**
	 * @param parent Component 
	 * Ouvre la fenetre d'import de fichier
	 */
	public void openFrame(Component parent) {
		this.fenetre.setMultiSelectionEnabled(false);
		this.fenetre.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		this.fenetre.setAcceptAllFileFilterUsed(false);				//Supprimer le filtre "All"
		
		switch (this.typeImport) {
			case TEXT:
				this.fenetre.addChoosableFileFilter(new ThotFilterText());
				break;
				
			case AUDIO:
				this.fenetre.addChoosableFileFilter(new ThotFilterAudio());
				break;
				
			case VIDEO:
				this.fenetre.addChoosableFileFilter(new ThotFilterVideo());
				break;
				
			case PICTURE:
				this.fenetre.addChoosableFileFilter(new ThotFilterPicture());
				break;
			case MEDIA:
				this.fenetre.addChoosableFileFilter(new ThotFilterMedia());
				break;
			default:
				System.out.println("Pas d'importType sp�cifi�");
				this.fenetre.setAcceptAllFileFilterUsed(true);
				
		}
				
		if(this.fenetre.showDialog(parent, "Importer") == JFileChooser.APPROVE_OPTION) {
			this.fichier = this.fenetre.getSelectedFile();
			
			try {
				Files.copy(this.fichier.toPath(), Paths.get(this.parentDirectory,this.getFileName()));
			} catch(Exception ignored) {
				
			}
			
			this.fichier = new File((Paths.get(this.parentDirectory,this.getFileName())).toString());

		}
	}
	
	/**
	 * @return File
	 * @see File
	 */
	public File getFile() {
		return (this.fichier);
	}
	
	/**
	 * Retourne le nom du fichier
	 * @return String
	 */
	public String getFileName() {
		return ((this.fichier==null)?null:this.fichier.getName());
	}
	
	/**
	 * Retourne le chemin du fichier
	 * @return String
	 */
	public String getFilePath() {
		return ((this.fichier==null)?null:this.fichier.getPath());
	}
	
	/**
	 * Retourne le type d'importation
	 * @return importType
	 * @see importType
	 */
	public importType getImportType() {
		return (this.typeImport);
	}
	
	/**
	 * @param it importType
	 * @see importType
	 */
	public void setImportType(importType it) {
		this.typeImport = it;
	}
}
