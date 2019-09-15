/**
* @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
* @version 0.7 du 15/09/2019
* 
*/

package fr.irit.elipse.project;

/**
 * 
 * Enumeration des types d'evenements possible pour un mot-balise
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 *
 */
public enum ThotTypeEvent {
	Retranscription,    //Affichage d'une chaine de caractere
	Media, 				//Image (.png/.jpeg) + Son (.wav/.aif) + Video (.mov)
	Effet, 				//Lumieres/Traitement d'image -> Effets visuels
	Registre,			//Marqueur de positionnement dans le texte
	Autre
}