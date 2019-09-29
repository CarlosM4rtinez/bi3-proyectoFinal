/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import MineriaDatos.DataMining;
import Modelo.AjustesRA;
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
 * Clase encargada de los servicios de reglas de asociacion
 * @author Kevin Zapata & Carlos Martinez
 */
@Path("mineria")
public class DataMiningServices extends Application{
    
    private DataMining dataMining = new DataMining();

    /**
     * Servicio a consumir que apartir de un archivo .csv, .arf devuelve los datos analizados
     * dependiendo del algoritmo a ejecutar.
     * @param reglas la cantidad de reglas de generar
     * @param algoritmo el algoritmo que se ejecutara para analizar el archivo
     * @param file el archivo con el conjunto de datos a analizar (.csv, .arf)
     * @param fileDetail detalles del archivo  que contiene los datos
     * @return json con los resultados del analisis del algoritmo ejecutado
     */
    @POST
    @Path("consumir")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String consumir(@FormDataParam("reglas") int reglas, @FormDataParam("reglas") int porcentaje, @FormDataParam("algoritmo") String algoritmo, @FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition fileDetail){
        try {
            final Gson gson = new Gson();
            // Deinimos los ajustes para la ejecucion del algoritmo
            AjustesRA a = new AjustesRA();
            a.setDatosArchivo(dataMining.convertir(new BufferedReader(new InputStreamReader(file))));
            a.setAlgoritmo(Integer.parseInt(algoritmo));
            a.setNumeroReglas(reglas);
            a.setPorcentajeAceptacion(porcentaje/100); // valores de 0 a 1: ejemplo: 0.95, 0.99, 0.78
            // Pasamos a analizar el archivo usando la mineria de datos.
            return gson.toJson(dataMining.mineria(a,1));
        }catch (IOException io) {
            return "<div class='alert alert-danger'><b>Ups! ha ocurrido un error:</b><br>"+io.getMessage()+"</div>";
        }catch (Exception e){
            return "<div class='alert alert-danger'><b>Ups! ha ocurrido un error:</b><br>"+e.getMessage()+"</div>";
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
            if(fileDetail.getFileName().toLowerCase().contains(".arff") || fileDetail.getFileName().toLowerCase().contains(".csv") ){
                // Obtenemos la informacion del archivo
                return dataMining.convertir(new BufferedReader(new InputStreamReader(file)));
            }else{
                return "<div class='alert alert-danger'><b>Ups! ha ocurrido un error:</b><br>Seleccione un archivo valido: csv o arff.</div>";
            }
        }catch (IOException io) {
            return "<div class='alert alert-danger'><b>Ups! ha ocurrido un error:</b><br>"+io.getMessage()+"</div>";
        }catch (Exception e){
            return "<div class='alert alert-danger'><b>Ups! ha ocurrido un error:</b><br>"+e.getMessage()+"</div>";
        }
    }

}