/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos;

import Modelo.Nodo;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

/**
 * Funciones genericas de Mineria de datos
 * @author Carlos Martinez
 */
public class DataMining implements Serializable{
    
    // Algoritmos de Reglas de Asociacion
    ReglasAsociacionDataMining raDataMining = new ReglasAsociacionDataMining();
    
    //define el formato para los decimales
    DecimalFormat formato = new DecimalFormat("#.##");
    
    /**
     * Aplica la mineria de datos para el algoritmo especifico
     * @param datos conjunto de datos a analizar
     * @param tipoAlgoritmo el tipo de algoritmo a ejecutar para el conjunto de datos
     * @return 
     */
    public String[] mineria (String datos, int tipoAlgoritmo) throws Exception, IOException{
        StringReader sr = new StringReader(datos);
        BufferedReader br = new BufferedReader(sr);
        String[] rta = new String[4];
        try {
            Instances data;
            //Definimos el objeto que contiene los datos a clasificar
            data = new Instances(br);
            //Variable de retorno para definir si un algoritmo recibe atrubitos de tipo " Class"
            int tipo=0;
            //cerramos el objeto buffer
            br.close();
            //Obtenemos resultados dependiendo del algoritmo
            switch (tipoAlgoritmo){
                case 1:
                    //Tipo 1 Apriori
                     tipo=1;
                    final Gson gson = new Gson();
                    // Ejecuta el algoritmo de apriori y guardamos el resultado en la respuesta
                    rta[0] = encabezado(data,tipo)+"\n"+raDataMining.apriori(data);
                    // informacion necesaria para construir el grapho en el frontend con D3.js
                    // obtenemos las listas de nodos y links
                    List<ArrayList> listas = raDataMining.aprioriGraphData(data);
                    // Obtenemos los nodos
                    List<Nodo> nodos = listas.get(0);
                    // guardamos los nodos del grapho en la respuesta
                    rta[1] = gson.toJson(nodos);
                    // Guardamos los links del grapho en la respuesta
                    rta[2] = gson.toJson(listas.get(1));
                   // Mandamos la tabla html de los nodos
                    rta[3] = raDataMining.tablaNodosHTML(nodos);
                    break;
                case 2:
                    //Tipo 2 (FTGrowth) --- NO RECIBE ATRIBUTOS DE TIPO CLASS
                    tipo=2;
                    final Gson gsonFP = new Gson();
                   // Ejecuta el algoritmo de FPGrowth y guardamos el resultado en la respuesta
                    rta[0] = encabezado(data,tipo)+"\n"+raDataMining.FPGrowth(data);
                    // informacion necesaria para construir el grapho en el frontend con D3.js
                    // obtenemos las listas de nodos y links
                    List<ArrayList> listasFP = raDataMining.aprioriGraphData(data);
                    // Obtenemos los nodos
                    List<Nodo> nodosFP = listasFP.get(0);
                    // guardamos los nodos del grapho en la respuesta
                    rta[1] = gsonFP.toJson(nodosFP);
                    // Guardamos los links del grapho en la respuesta
                    rta[2] = gsonFP.toJson(listasFP.get(1));
                   // Mandamos la tabla html de los nodos
                    rta[3] = raDataMining.tablaNodosHTML(nodosFP);
                    break;
                case 3:
                    //Tipo 3 (Tertius)
                    tipo=3;
                    //Seleccionamos cual sera el atributo clase, el cual
                    //es el total de atributos -1
                    data.setClassIndex(data.numAttributes() - 1);
                  //  resultado = raDataMining.Tertius(data);
                    break;
                default:
            }
        } catch (IOException ex) {
            rta[0] = "Ha ocurrido un error!:\n"+ex.getMessage();
        }
        return rta;
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
        descripcion= "<b>Este metodo no recibe atributos de tipo ´´Clase´´</b><ul align='left'>";
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
        String cadena="";
        while ((temp = file.readLine()) != null){
            cadena = cadena+temp+ "\n";
        }
        return cadena;
    }
}
