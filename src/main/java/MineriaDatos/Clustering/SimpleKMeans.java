package MineriaDatos.Clustering;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Modelo.Cluster;
import weka.core.Instances;

/**
 * Clase que extrae los resultados del algorimo SimpleKMeans
 * 
 * @author Laura, Alejo, Mónica
 *
 */
public class SimpleKMeans implements Serializable {

	/**
	 * Instancia de los datos
	 */
	private Instances data;

	/**
	 * Resultado que se quiere sacar de los datos
	 */
	private int numClusters;

	public SimpleKMeans() {

	}

	public SimpleKMeans(Instances data, int numClusters) {
		this.data = data;
		this.numClusters = numClusters;
	}

	/**
	 * Método que extrae los datos resultantes del algoritmo SimpleKMeans
	 * 
	 * @param data
	 * @param resultado
	 * @return respuesta, String de datos según el resultado que se quiere obtener
	 */
	public ArrayList<String> extraerResultados() {
		ArrayList<String> lista = new ArrayList<String>();
		boolean clusterDefinido = false;
		if (numClusters != 0) {
			clusterDefinido = true;
		} else {
			// Se define 1 cluster por defecto en caso de que el usuario no seleccione
			// ningún número de grupos
			numClusters = 2;
		}

		try {
			weka.clusterers.SimpleKMeans algortim = new weka.clusterers.SimpleKMeans();
			algortim.setNumClusters(numClusters);
			algortim.buildClusterer(data);
			algortim.setPreserveInstancesOrder(true);

			// Agrega los atributos de los datos
			lista.add(extraerAtributos());

			// Agrega los clusters definidos por el algoritmo
			if (clusterDefinido) {
				lista.add(extraerClusters(algortim));
			} else {
				lista.add("Por defecto se instancian dos grupos!" + extraerClusters(algortim));
			}

			// Agrega la tabla de análisis de weka como resultado del algoritmo
			if (clusterDefinido) {
				lista.add(extraerAnalisis(algortim));
			} else {
				lista.add("Por defecto se instancian dos grupos!" + extraerAnalisis(algortim));
			}

			// Agrega el Json de la grafica
			lista.add(generarJsonGrafica(algortim));

			return lista;
		} catch (Exception ex) {
			lista.add("El error es: " + ex.getMessage());
			return lista;
		}
	}

	/**
	 * Metodo que extrae los atributos del dataset
	 * 
	 * @return
	 */
	private String extraerAtributos() {
		String atributos = "<b><center>Resultados de cluster SimpleKMeans</center></b>";
		atributos += "<br><b>Todos los Atributos:</b><ul align='left'>";

		for (int z = 0; z < data.numAttributes(); z++) {
			atributos += "<li>" + data.attribute(z).name() + "</li>";
		}

		atributos += "<br><b align='left'>Atributos de Clase </b><br>";
		for (int z = 0; z < data.attribute(data.numAttributes() - 1).numValues(); z++) {
			atributos += "<li>" + data.attribute(data.numAttributes() - 1).value(z) + "</li>";
		}

		return atributos;
	}

	/**
	 * Metodo que extrae los grupos (clusters) del dataset
	 * 
	 * @return
	 */
	private String extraerClusters(weka.clusterers.SimpleKMeans k) {
		String clusters = "<b><center>Se han generado los siguientes resultados</center></b>"
				+ "<br><b align='left'>Numero de Clusters: " + k.getNumClusters() + "<b><br>"
				+ "<br><b align='left'>Clasificación por caracteristicas:<b><br><br><br>";

		for (int i = 0; i < k.getClusterCentroids().size(); i++) {
			clusters = clusters + "<li align='left'> Cluster <b>" + i + "</b>: <b>"
					+ k.getClusterCentroids().get(i).toString() + "</b></li><br>";
		}

		clusters = clusters + "<br>Clasificación por tamaño: <br><ol>";
		for (int i = 0; i < k.getClusterCentroids().size(); i++) {
			clusters = clusters + "<li align='left'> Cluster <b>" + i + "</b> con un Tamaño de <b>"
					+ k.getClusterSizes()[i] + "</b></li>";
		}
		clusters += "</ol>";
		return clusters;
	}

	/**
	 * Metodo que extrae la tabla del análisis de weka del dataset
	 * 
	 * @return
	 */
	private String extraerAnalisis(weka.clusterers.SimpleKMeans k) {
		String tabla = "<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
				+ "<thead>\r\n" + "<tr>\r\n" + "<th>Cluster Nro</th>\r\n";
		for (int i = 0; i < data.numAttributes(); i++) {

			tabla += "<th>" + data.attribute(i).name() + "</th>\r\n";
		}

		tabla += "</tr>\r\n" + "</thead>";

		for (int z = 0; z < k.getClusterCentroids().size(); z++) {
			tabla += "<tr>\r\n";

			tabla += "<td> Cluster #" + z + "\n (" + k.getClusterSizes()[z] + ")</td>";

			String string = k.getClusterCentroids().get(z).toString();
			String[] parts = string.split(",");
			for (int j = 0; j < parts.length; j++) {
				tabla += "<td>" + parts[j] + "</td>";
			}

			tabla += "<tr>\r\n";

		}

		tabla += "</tr>\r\n";

		return tabla;
	}

	private String generarJsonGrafica(weka.clusterers.SimpleKMeans k) {
		JSONObject grafica = new JSONObject();

		try {

			ArrayList<Cluster> listClusters = new ArrayList<Cluster>();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < k.getNumClusters(); i++) {

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("Name", "Cluster #" + i);
				jsonObject.put("Count", k.getClusterSizes()[i]);

				jsonArray.put(jsonObject);

			}
			
			grafica.put("children", jsonArray);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grafica.toString();

	}

}
