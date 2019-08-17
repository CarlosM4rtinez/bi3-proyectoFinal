/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos;

import MineriaDatos.ReglasAsociacion.ReglasAsociacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DecimalFormat;
import weka.core.Instances;

/**
 * Funciones genericas de Mineria de datos
 * @author Carlos Martinez
 */
public class DataMining implements Serializable{
    
    // Algoritmos de Reglas de Asociacion
    ReglasAsociacion raDataMining = new ReglasAsociacion(this);
    
    //define el formato para los decimales
    DecimalFormat formato = new DecimalFormat("#.##");
    
    /**
     * Aplica la mineria de datos para el algoritmo especifico
     * @param datos conjunto de datos a analizar
     * @param tipoAlgoritmo el tipo de algoritmo a ejecutar para el conjunto de datos
     * @param tipoClasificacion el tipo de clasificacion de algoritmos a usar
     * @return resultados del proceso de analisis
     */
    public String[] mineria (String datos, int tipoAlgoritmo, int tipoClasificacion) throws Exception, IOException{
        StringReader sr = new StringReader(datos);
        BufferedReader br = new BufferedReader(sr);
        Instances data;
        //Definimos el objeto que contiene los datos a clasificar
        data = new Instances(br);
        //cerramos el objeto buffer
        br.close();
        // Deinimos que clasificacion de algoritmos ejecutar
        switch (tipoClasificacion){
            case 1:
                // Algoritmos de Reglas de Asociacion
                return this.raDataMining.aplicar(data, tipoAlgoritmo);
            case 2:
                // Algoritmos de Arboles de Decision
            case 3:
                // Algoritmos de Cloustering
            default:
        }
        return null;
    }
    /**
     * define el encabezado de un conjunto de datos
     * @param data los datos con las isntancias
     * @param tipo el tipo de algoritmo que ha ingresado
     * @return el encabezado del conjunto de datos
     */
    public String encabezado(Instances data, int tipo){
        String descripcion ="<b>Atributo clase:</b> "+data.attribute(data.numAttributes()-1).name()+"<br>";
        //Si el tipo es igual a 2 (FTGrowth)
        if(tipo==2){
            descripcion= "<b>Este metodo no recibe atributos de tipo Â´Â´ClaseÂ´Â´</b><ul align='left'>";
        }
        descripcion += "<b>Posibles valores:</b><ul align='left'>";
        for (int z = 0; z < data.attribute(data.numAttributes()-1).numValues(); z++) {
            descripcion += "<li>"+data.attribute(data.numAttributes()-1).value(z)+"</li>";
        }
        return descripcion+"</ul>";
    }
    /**
     * Saca la informacion del archivo y la guarda como texto
     * @param file el archivo a extraerle la informacion
     * @return toda la informacion del archivo en una variable string
     * @throws IOException 
     */
    public String convertir (BufferedReader file) throws IOException{
        String temp;
        String cadena = "";
        while ((temp = file.readLine()) != null){
            cadena = cadena+temp+ "\n";
        }
        return cadena;
    }
}