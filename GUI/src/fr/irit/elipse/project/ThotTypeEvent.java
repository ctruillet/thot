/**
* @author Clement Truillet (clement.Truillet@univ-tlse3.fr)
* @version 0.1 du 13/05/2019
*/

package fr.irit.elipse.project;

public enum ThotTypeEvent {
	Retranscription,    //Affichage d'une chaine de caractere
	Media, 				//Image (.png/.jpeg) + Son (.wav/.aif) + Video (.mov)
	Effet, 				//Lumieres/Traitement d'image -> Effets visuels
	Registre;			//Marqueur de positionnement dans le texte
}
