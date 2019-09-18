package Servicios;

import MineriaDatos.ClusterDataMining;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.Application;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que expone los servicios del sistema
 * 
 * @author Laura, Alejo, Mónica
 *
 */
@Path("cluster")
public class ClusterService extends Application {

	/**
	 * Instancia de la clase de mineria de data para Clustering
	 */
	private ClusterDataMining dataMining = new ClusterDataMining();

	/**
	 * Obtiene el dataset
	 * 
	 * @param file       el archivo con el conjunto de datos (.csv, .arf)
	 * @param fileDetail detalles del dataset
	 * @return json con la informacion del archivo
	 */
	@POST
	@Path("dataset")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String getInformacion(@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			// Convierte el dataset a string
			String dataset = dataMining.convert(new BufferedReader(new InputStreamReader(file)));
			return dataset;
		} catch (IOException io) {
			return "error: " + io.getMessage();
		}
	}

	/**
	 * Servicio que recibe un dataset .csv, .arf que devuelve los datos analizados
	 * del mismo según el algoritmo seleccionado
	 * 
	 * @param algoritmo,  Algoritmo a procesar en el dataset
	 * @param file,       dataset a analizar (.csv, .arf)
	 * @param fileDetail, detalles del dataset
	 * @return Json con los resultados del analisis de los datos según el algoritmo
	 *         seleccionado
	 */
	@POST
	@Path("procesar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String consumir(@FormDataParam("algoritmo") String algoritmo, @FormDataParam("numCluster") String numClusters, @FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			final Gson gson = new Gson();
			// Realiza la mineria de clustering al dataset
			
			String resultado = gson.toJson(dataMining.dataMinning(
					dataMining.convert(new BufferedReader(new InputStreamReader(file))), Integer.parseInt(algoritmo), Integer.parseInt(numClusters)));
			return resultado;
		} catch (IOException io) {
			return "error: " + io.getMessage();
		}
	}

}