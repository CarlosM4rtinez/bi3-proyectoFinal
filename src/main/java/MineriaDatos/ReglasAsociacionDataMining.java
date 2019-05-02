/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DecimalFormat;
import weka.associations.Apriori;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Clase encargada de usar la libreria de weka para el analisis de datos 
 * usando los algoritmos de reglas de asociacion
 * @author Carlos Martinez
 */
public class ReglasAsociacionDataMining implements Serializable{
    
    //define el formato para los decimales
    DecimalFormat formato = new DecimalFormat("#.##");
    
    /**
     * Aplica la mineria de datos para el algoritmo especifico
     * @param datos conjunto de datos a analizar
     * @param tipoAlgoritmo el tipo de algoritmo a ejecutar para el conjunto de datos
     * @return 
     */
    public String mineria (String datos, int tipoAlgoritmo){
        StringReader sr = new StringReader(datos);
        BufferedReader br = new BufferedReader(sr);
        try {
            Instances data;
            //Definimos el objeto que contiene los datos a clasificar
            data = new Instances(br);
            //Seleccionamos cual sera el atributo clase, el cual
            //es el total de atributos -1
            data.setClassIndex(data.numAttributes() - 1);
            //cerramos el objeto buffer
            br.close();
            String resultado = "";
            //Obtenemos resultados dependiendo del algoritmo
            switch (tipoAlgoritmo){
                case 1:
                    // Ejecuta el algoritmo de apriori
                    resultado = apriori(data);
                    break;
                default:
            }
            return encabezado(data)+"\n"+resultado;
        } catch (IOException ex) {
            return "El error es: " + ex.getMessage();
        }
    }
    
    /**
     * define el encabezado de un conjunto de datos
     * @param data los datos con las isntancias
     * @return el encabezado del conjunto de datos
     */
    public String encabezado(Instances data){
        String descripcion = "<b>El atributo clase seleccionado es: "+data.attribute(data.numAttributes()-1).name() + "</b>";
        descripcion += " <b>con posibles valores</b>";
        for (int z = 0; z < data.attribute(data.numAttributes()-1).numValues(); z++) {
            descripcion += "<b>" + data.attribute(data.numAttributes()-1).value(z) + "</b>";
        }
        return descripcion;
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
    
  /**
   * Metodo de Reglas de Asociacion: clasificacion Apriori
   * @param data conjunto de instancias con los datos
   * @return el resultado del analisis del algoritmo de apriori
   */  
    public String apriori (Instances data){
        try {
            //Creamos el objeto de asociacion por apriori
            Apriori aso = new Apriori();
            //Creamos el descriptivo apriori con los datos
            aso.buildAssociations(data);
            //Se cargan los resultados de la asociacion apriori
            String resApriori = "<br><b><center>Resultados de asociacion "
                    + " apriori</center></b>========<br>El modelo de asociacion "
                    + "generado indica los siguientes resultados:"
                    + "<br>===========<br>";
            //Obtenemos resultados
            for (int i = 0; i < aso.getAssociationRules().getRules().size(); i++) {
                resApriori = resApriori + "<b>" + (1 + 1) + ". Si</>"
                        + aso.getAssociationRules().getRules().get(i).getPremise().toString();
                resApriori = resApriori + " <b>Entonces"
                        + aso.getAssociationRules().getRules().get(i).getConsequence().toString();
                resApriori = resApriori + " <b>Con un "
                        + (int) (aso.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100)
                        + "% de posibilidad<br>";
            }
            return resApriori;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }
    
}
