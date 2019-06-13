/*
 * @author : Clement Truillet (clement.truillet@univ-tlse3.fr)
 * @date : 15/05/2019
 */

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	protected class ThotFilterText extends FileFilter{
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".txt")
					|| f.getName().endsWith(".md"));
		}
		
		public String getDescription() {
			return "Fichiers texte";
		}
		
	}
	
	protected class ThotFilterAudio extends FileFilter{
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".wov")
					|| f.getName().endsWith(".mp3")
					|| f.getName().endsWith(".aif"));
		}
		
		public String getDescription() {
			return "Fichiers Audio";
		}
		
	}
	
	protected class ThotFilterVideo extends FileFilter{
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().endsWith(".mov")
					|| f.getName().endsWith(".avi")
					|| f.getName().endsWith(".m2ts")
					|| f.getName().endsWith(".ts")
					|| f.getName().endsWith(".mkv")
					|| f.getName().endsWith(".mp4"));
		}
		
		public String getDescription() {
			return "Fichiers Video";
		}
		
	}
	
	protected class ThotFilterPicture extends FileFilter{
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
	
	
	protected class ThotFilterMedia extends FileFilter{
		ThotFilterAudio audio = new ThotFilterAudio();
		ThotFilterVideo video = new ThotFilterVideo();
		ThotFilterPicture picture = new ThotFilterPicture();
		public boolean accept(File f) {

			return audio.accept(f) == true || video.accept(f) == true || picture.accept(f);
		}

		public String getDescription() {
			return "media";
		}

	}

	//Méthodes
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
				System.out.println("Pas d'importType spécifié");
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
	
	public File getFile() {
		return (this.fichier);
	}
	
	public String getFileName() {
		return ((this.fichier==null)?null:this.fichier.getName());
	}
	
	public String getFilePath() {
		return ((this.fichier==null)?null:this.fichier.getPath());
	}
	
	public importType getImportType() {
		return (this.typeImport);
	}
	
	public void setImportType(importType it) {
		this.typeImport = it;
	}
}
