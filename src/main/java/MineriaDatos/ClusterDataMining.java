package MineriaDatos;

import MineriaDatos.Clustering.Canopy;
import MineriaDatos.Clustering.Cobweb;
import MineriaDatos.Clustering.EM;
import MineriaDatos.Clustering.SimpleKMeans;
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import weka.core.Instances;

/**
 * Esta clase realiza la mineria de los datos ingresados y envía los resultados
 * de los algoritmos según el seleccionado
 * 
 * @author Laura, Alejo, Mónica
 *
 */
public class ClusterDataMining implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia de la clase del algoritmo SimpleKMeans
	 */
	private SimpleKMeans simpleKMeans;

	/**
	 * Instancia de la clase del algoritmo EM
	 */
	private EM em;

	/**
	 * Instancia de la clase del algoritmo Canopy
	 */
	private Canopy canopy;

	/**
	 * Instancia de la clase del algoritmo Cobweb
	 */
	private Cobweb cobweb;

	/**
	 * Metodo que realiza la conversión del dataset ingresado y la mineria a estos
	 * datos según el algoritmo seleccionado
	 * 
	 * @param dataInput, Dataset ingresado
	 * @param algoritmo, Algoritmo seleccionado
	 * @return lista, lista de resultados del dataset según el algoritmo
	 *         seleccionado
	 */
	public ArrayList<String> dataMinning(String dataInput, int algoritmo, int numCluster) {
		StringReader sr = new StringReader(dataInput);
		BufferedReader br = new BufferedReader(sr);
		ArrayList<String> lista = new ArrayList<String>();

		try {
			Instances data;
			data = new Instances(br);

			// data.setClassIndex(data.numAttributes() - 1);
			br.close();
			String result = "";

			switch (algoritmo) {
			case 1:

				this.simpleKMeans = new SimpleKMeans(data, numCluster);
				lista.addAll(this.simpleKMeans.extraerResultados());
				break;
			case 2:

				this.em = new EM(data);
				lista.addAll(this.em.extraerResultados());

				break;
			case 3:

				this.canopy = new Canopy(data);
				lista.addAll(this.canopy.extraerResultados());

				break;
			case 4:

				this.cobweb = new Cobweb(data);
				lista.addAll(this.cobweb.extraerResultados());
				break;
			default:
				result = "Por favor seleccione un algoritmo!";
				lista.add(result);
			}
			return lista;
		} catch (IOException ex) {
			lista.add("El error es: " + ex.getMessage());
			return lista;
		}
	}

	/**
	 * Metodo que convierte el dataset a BufferedReader
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String convert(BufferedReader file) throws IOException {
		String temp;
		String text = "";
		while ((temp = file.readLine()) != null) {
			text = text + temp + "\n";
		}
		return text;
	}

}
