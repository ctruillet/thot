/**
* @author Clement Truillet (clement.Truillet@univ-tlse3.fr)
* @version 0.1 du 13/05/2019
*/

package fr.irit.elipse.project;

public enum ThotTypeEvent {
	RETRANSCRIPTION,    //Affichage d'une chaine de caractere
	MEDIA, 				//Image (.png/.jpeg) + Son (.wav/.aif) + Video (.mov)
	EFFET, 				//Lumieres/Traitement d'image -> Effets visuels
	REGISTRE;			//Marqueur de positionnement dans le texte
}
