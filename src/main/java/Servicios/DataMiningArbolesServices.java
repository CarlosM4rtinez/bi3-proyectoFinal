package Servicios;

import MineriaDatos.DataMiningArboles;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;


/**
 *
 * @author CAMILO
 */
@Path("servicios-Web")
public class DataMiningArbolesServices extends Application {

     private DataMiningArboles dataMining = new DataMiningArboles();

    /**
     * Metodo para devolver los datos analizados de acuerdo a los archivos que se ingresan (.arff o .csv)
     * @param algoritmo el algoritmo que se ejecutara para analizar el archivo
     * @param file el archivo con el conjunto de datos a analizar (.csv, .arf)
     * @param fileDetail detalles del archivo  que contiene los datos
     * @return json con los resultados del analisis del algoritmo ejecutado
     */
    @POST
    @Path("consumir")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String consumir(@FormDataParam("algoritmo") String algoritmo, @FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition fileDetail){
        try {
            // Pasamos a analizar el archivo usando la mineria de datos.
            return dataMining.mineria(dataMining.convertir(new BufferedReader(new InputStreamReader(file))), Integer.parseInt(algoritmo));
        }catch (IOException io) {
            return "Se ha presentado un error: "+io.getMessage();
        }
    }
    /**
     * Obtiene la informacion de un archivo
     * @param file el archivo con el conjunto de datos (.csv, .arf) para obtener la informacion
     * @param fileDetail detalles del archivo  que contiene los datos
     * @return json con la informacion del archivo
     */
    @POST
    @Path("informacion")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String getInformacion(@FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition fileDetail){
        try {
            // Obtenemos la informacion del archivo
            return dataMining.convertir(new BufferedReader(new InputStreamReader(file)));
        }catch (IOException io) {
            return "Se ha presentado un error: "+io.getMessage();
        }
    }

}
