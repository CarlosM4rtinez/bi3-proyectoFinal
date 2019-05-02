/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos;

import java.io.Serializable;
import weka.associations.Apriori;
import weka.core.Instances;

/**
 * Clase encargada de usar la libreria de weka para el analisis de datos 
 * usando los algoritmos de reglas de asociacion
 * @author Carlos Martinez
 */
public class ReglasAsociacionDataMining implements Serializable{
    
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
                resApriori = resApriori + "<b>" + (i + 1) + ". Si</>"
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
