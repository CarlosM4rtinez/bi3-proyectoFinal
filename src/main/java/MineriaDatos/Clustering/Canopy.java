package MineriaDatos.Clustering;

import java.util.ArrayList;

import weka.core.Instances;

/**
 * Clase que extrae los resultados del algorimo Canopy
 * 
 * @author Laura, Alejo, Mónica
 *
 */
public class Canopy {

	private SimpleKMeans simpleKMeans;

	/**
	 * Instancia de los datos
	 */
	private Instances data;

	public Canopy() {

	}

	public Canopy(Instances data) {
		this.data = data;
	}

	/**
	 * Método que extrae los datos resultantes del algoritmo Canopy
	 * 
	 * @return lista de resultados
	 */
	public ArrayList<String> extraerResultados() {
		
		try {
			weka.clusterers.Canopy canopy = new weka.clusterers.Canopy();

			canopy.setNumClusters(-1);
			canopy.buildClusterer(data);

			simpleKMeans = new SimpleKMeans(data, canopy.numberOfClusters());
			return simpleKMeans.extraerResultados();

		} catch (Exception ex) {
			ArrayList<String> lista = new ArrayList<String>();
			lista.add("El error es: " + ex.getMessage());
			return lista;
		}

	}

}
