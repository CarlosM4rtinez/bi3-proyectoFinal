package MineriaDatos.Clustering;

import java.util.ArrayList;  

import weka.core.Instances;

/**
 * Clase que extrae los resultados del algorimo EM
 * 
 * @author Laura, Alejo, Mónica
 *
 */
public class EM {

	private SimpleKMeans simpleKMeans;

	/**
	 * Instancia de los datos
	 */
	private Instances data;

	public EM() {
	}

	public EM(Instances data) {
		this.data = data;
	}

	
	/**
	 * Método que extrae los clusters resultantes del algoritmo EM y uso del
	 * algoritmo SimpleKMeans para extraer los resultados enviando el número de clusters
	 * 
	 * @return lista de resultados
	 */
	public ArrayList<String> extraerResultados() {

		try {
			weka.clusterers.EM em = new weka.clusterers.EM();

			em.setNumClusters(-1);
			em.setOptions(new String[] { "-S", "1" });
			em.buildClusterer(data);

			simpleKMeans = new SimpleKMeans(data, em.numberOfClusters());
			return simpleKMeans.extraerResultados();

		} catch (Exception ex) {
			ArrayList<String> lista = new ArrayList<String>();
			lista.add("El error es: " + ex.getMessage());
			return lista;
		}
	}

}
