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
            Apriori a = new Apriori();
            //Creamos el descriptivo apriori con los datos
            a.buildAssociations(data);
            //Se cargan los resultados de la asociacion apriori
            String rta = "<b><center>Resultados de asociacion Apriori</center></b>"
                    + "========<br>"
                    + "El modelo de asociacion generado indica los siguientes resultados"
                    + "<br>===========<br><ol>";
            //Obtenemos resultados
            for (int i = 0; i < a.getAssociationRules().getRules().size(); i++) {
                rta = rta+"<li align='left'>Si <b>"+a.getAssociationRules().getRules().get(i).getPremise().toString()
                         +"</b><br>Entonces <b>"+a.getAssociationRules().getRules().get(i).getConsequence().toString()
                         + "</b> con un <font color='green'><b>"+(int)(a.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100)
                         +"%</b></font> de posibilidad.<br><br></li>";
            }
            return rta+"</ol>";
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }
    
}
