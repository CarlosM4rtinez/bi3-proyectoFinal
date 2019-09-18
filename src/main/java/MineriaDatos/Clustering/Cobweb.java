package MineriaDatos.Clustering;

import java.util.ArrayList;  

import weka.core.Instances;

/**
 * Clase que extrae los resultados del algorimo Cobweb
 * 
 * @author Laura, Alejo, Mónica
 *
 */
public class Cobweb {

	private SimpleKMeans simpleKMeans;

	/**
	 * Instancia de los datos
	 */
	private Instances data;

	public Cobweb() {

	}

	public Cobweb(Instances data) {
		this.data = data;
	}

	/**
	 * Método que extrae los clusters resultantes del algoritmo Cobweb y uso del
	 * algoritmo SimpleKMeans para extraer los resultados enviando el número de clusters
	 * 
	 * @param data
	 * @param resultado
	 * @return respuesta, String de datos según el resultado que se quiere obtener
	 */
	/**
	 * Método que extrae los clusters resultantes del algoritmo Cobweb y uso del
	 * algoritmo SimpleKMeans para extraer los resultados enviando el número de clusters
	 * 
	 * @return lista de resultados
	 */
	public ArrayList<String> extraerResultados() {
		
		try {
			weka.clusterers.Cobweb
			cw = new weka.clusterers.Cobweb();
			cw.buildClusterer(data);
			simpleKMeans = new SimpleKMeans(data, cw.numberOfClusters());

			return simpleKMeans.extraerResultados();

		} catch (Exception ex) {
			ArrayList<String> lista = new ArrayList<String>();
			lista.add("El error es: " + ex.getMessage());
			return lista;
		}
	}

}
