/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import MineriaDatos.ReglasAsociacionDataMining;
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
import org.apache.commons.io.FilenameUtils;
/**
 * Clase encargada de los servicios de reglas de asociacion
 * @author Kevin Zapata & Carlos Martinez
 */
@Path("reglas-asociacion")
public class ReglasAsociacionServices extends Application{
    
    private ReglasAsociacionDataMining raDataMining = new ReglasAsociacionDataMining();

    /**
     * Servicio a consumir que apartir de un archivo .csv, .arf devuelve los datos analizados
     * dependiendo del algoritmo a ejecutar.
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
            return raDataMining.mineria(raDataMining.convertir(new BufferedReader(new InputStreamReader(file))), Integer.parseInt(algoritmo));
        }catch (IOException io) {
            return "Ups! ha ocurrido un error: "+io.getMessage();
        }
    }

}