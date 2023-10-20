package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}
		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Et si la voiture est déjà dans un garage ?
		if(this.estDansUnGarage()){
			throw new Exception("Déjà dans ce garage");
		}
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);

	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
		if(!myStationnements.isEmpty()) {
			int dernierGarage = myStationnements.size() - 1;
			Stationnement s = myStationnements.get(dernierGarage);
			if (s.estEnCours()) {
				s.terminer();
			} else {
				throw new Exception("La voiture est déjà sortie");
			}
		}
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> visites = new HashSet<Garage>();
		for(Stationnement s : myStationnements) {
			Garage g = s.getGarage();
			visites.add(g);
		}
		return visites;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// Vrai si le dernier stationnement est en cours
		if(myStationnements.size()==0) {
			return false;
		} else {
			int dernierStationnement = myStationnements.size() - 1;
			Stationnement s = myStationnements.get(dernierStationnement);
			return s.estEnCours();
		}
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
		// Utiliser les méthodes toString() de Garage et Stationnement
		Set<Garage> visites = this.garagesVisites();
		for(Garage g : visites){
			out.append(g.toString()+" \n ");
			for(Stationnement s : myStationnements) {
				if(s.getGarage().equals(g)){
					out.append(s.toString() + " \n ");
				}
			}
		}
	}

}
