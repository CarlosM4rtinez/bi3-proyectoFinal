/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos;

import Modelo.Link;
import Modelo.Nodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weka.associations.Apriori;
import weka.associations.FPGrowth;
import weka.associations.Item;
import weka.associations.Tertius;
import weka.associations.tertius.Rule;
import weka.associations.tertius.SimpleLinkedList;
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
    public String apriori (Instances data) throws Exception{
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
            rta += "<li align='left'>Si ";
            // Sacamos los valores del si para construir los nodos
            Collection<Item> premises = a.getAssociationRules().getRules().get(i).getPremise();
            for(Item item: premises){
                rta += "<b>"+this.replaceAttribute(item.getAttribute().name())+" "+this.replaceCompara(item.getComparisonAsString())+" "+item.getItemValueAsString()+"</b>";
                rta += " y ";
            }
            rta = rta.substring(0, rta.length()-3); // Eliminamos el ultimo " y " de la cadena
            rta += "</b><br>Entonces <b>";
            // Sacamos los valores del Entonces para construir los nodos
            Collection<Item> consequences = a.getAssociationRules().getRules().get(i).getConsequence();
            for(Item item: consequences){
                rta += this.replaceAttribute(item.getAttribute().name())+" "+this.replaceCompara(item.getComparisonAsString())+" "+item.getItemValueAsString();
            }
            rta += "</b> con un <font color='green'><b>"+(int)(a.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100)
                +  "%</b></font> de posibilidad.<br><br></li>";
        }
        return rta+"</ol>";
    }
    
    /**
     * Remplaza caracteres del nombre del atributo
     * @param a el atributo a remplazarle los atributos
     * @return el atributo limpio sin caracteres conectado las palabras
     */
    public String replaceAttribute(String a){
        return a.replaceAll("-", " "); // Eliminamos los guiones del atributo
    }
    /**
     * Remplaza los caracteres de = y otros para mejorar la interpretacion de las reglas de asociacion
     * @param c la cadena de comparacion 
     * @return la cadena de compracion con datos mas entendibles
     */
    public String replaceCompara(String c){
        return c.replaceAll("=", "es"); // Remplazamos "=" por "es" para mayor interpretacion
    }
    
    /**
     * Convierte los resultados de las reglas de asociacion en json formato D3.js
     * @param data las instancias del archivo
     * @return lista para ser pasada a formato json
     * @throws java.lang.Exception
     */
    public List<ArrayList> aprioriGraphData(Instances data) throws Exception{
        // La lista con las listas de nodos y links a retornar
        List<ArrayList> listas = new ArrayList<>();
        //Creamos el objeto de asociacion por apriori
        Apriori a = new Apriori();
        //Creamos el descriptivo apriori con los datos
        a.buildAssociations(data);
        // Lista de nodos
        List<Nodo> nodes = new ArrayList<>();
        // Lista de enlaces de los nodos
        List<Link> links = new ArrayList<>();
        for (int i = 0; i < a.getAssociationRules().getRules().size(); i++) {
            // construimos el link
            Link link = new Link();
            // Sacamos los valores del si para construir los nodos
            Collection<Item> premises = a.getAssociationRules().getRules().get(i).getPremise();
            // nos indica el numero del si y el entonces
            int sec = 1;            
            for(Item item: premises){
                Nodo nodo = new Nodo();
                nodo.setAtributo(this.replaceAttribute(item.getAttribute().name()));
                nodo.setCompara(this.replaceCompara(item.getComparisonAsString()));
                nodo.setValor(item.getItemValueAsString());
                // Validamos si ya existe nodo
                Nodo n = this.buscarNodo(nodo, nodes);
                //if(n == null){
                    nodo.setId(nodes.size());
                    nodo.setName("R"+(i+1)+":"+sec+". Si "+nodo.getAtributo()+" "+nodo.getCompara()+" "+nodo.getValor());
                    // Guardamos el nodo en la lista de nodes
                    nodes.add(nodo);
                //}
                if(link.getSource() == null){
                    // Asignamos el nodo anterior
                    link.setSource("nodes["+nodo.getId()+"]");
                }else{
                    // Asignamos el nodo siguiente y guardamos el link
                    link.setTarget("nodes["+nodo.getId()+"]");
                    link.setConector("Y");
                    links.add(link);
                    link = new Link(); // Como se agrego, reiniciamos el link
                }
                sec++;
            }
            // Conectamos el si con el entonces en el grapho
            if(link.getSource() == null){
                link.setSource("nodes["+(nodes.size()-1)+"]");
            }
            // Sacamos los valores del Entonces para construir los nodos
            Collection<Item> consequences = a.getAssociationRules().getRules().get(i).getConsequence();
            for(Item item: consequences){
               Nodo nodo = new Nodo();
                nodo.setAtributo(this.replaceAttribute(item.getAttribute().name()));
                nodo.setCompara(this.replaceCompara(item.getComparisonAsString()));
                nodo.setValor(item.getItemValueAsString());
                // Validamos si ya existe nodo
                Nodo n = this.buscarNodo(nodo, nodes);
                //if(n == null){
                    nodo.setId(nodes.size());
                    nodo.setName("R"+(i+1)+":"+sec+". Entonces "+nodo.getAtributo()+" "+nodo.getCompara()+" "+nodo.getValor()+", "+(+(int)(a.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100)+"%."));
                    // Guardamos el nodo en la lista de nodes
                    nodes.add(nodo);
                //}
                // creamos el link
                if(link.getSource() == null){
                    // Asignamos el nodo anterior
                    link.setSource("nodes["+nodo.getId()+"]");
                }else{
                    // Asignamos el nodo siguiente y guardamos el link
                    link.setTarget("nodes["+nodo.getId()+"]");
                    link.setConector("Entonces");
                    links.add(link);
                    link = new Link(); // Como se agrego, reiniciamos el link
                }
                sec++;
            }
        }
        // Agregamos la lista de nodes y links a listas
        listas.add(0, (ArrayList) nodes);
        listas.add(1, (ArrayList) links);
        return listas;     
    }
    /**
     * Validamos si un nodo ya existe en la lista de nodos
     * @param n el nodo a validar
     * @param nodos la lista de nodos
     * @return el nodo encontrado, de lo contrario null
     */
    public Nodo buscarNodo(Nodo n, List<Nodo> nodos){
        for(int i=0; i<nodos.size(); i++){
            // Validamos si la clave y valor son iguales
            Nodo s = nodos.get(i);
            if(n.getAtributo().equals(s.getAtributo()) && n.getValor().equals(s.getValor()) && n.getCompara().equals(s.getCompara())){
                return nodos.get(i);
            }
        }
        return null;
    }
    /**
     * Devuelve una tabla html de todos los nodos para ser mostrada en el front
     * @param nodos la lista de nodos a mostrar en la tabla html
     * @return string de la tabla html
     */
    public String tablaNodosHTML(List<Nodo> nodos){
        String filas = "";
        for (Nodo n : nodos) {
            filas += "<tr>\n" +
                    "      <th scope='row'>"+n.getId()+"</th>\n" +
                    "      <td>"+n.getName()+"</td>\n" +
                    "      <td>"+n.getAtributo()+"</td>\n" +
                    "      <td>"+n.getCompara()+"</td>\n" +
                    "      <td>"+n.getValor()+"</td>\n" +
                    "</tr>\n";
        }
        String tabla = "<table class='table table-bordered'>\n" +
                        "  <thead>\n" +
                        "    <tr>\n" +
                        "      <th scope='col'>#</th>\n" +
                        "      <th scope='col'>Nombre</th>\n" +
                        "      <th scope='col'>Atributo</th>\n" +
                        "      <th scope='col'>Compara</th>\n" +
                        "      <th scope='col'>Valor</th>\n" +
                        "    </tr>\n" +
                        "  </thead>\n" +
                        "  <tbody>\n" +
                        filas+
                        "  </tbody>\n" +
                        "</table>";
        return tabla;
    }
    
    public String FPGrowth (Instances data){
    
        try {

           //Creamos el objeto de asociacion por FPGrwoth
            FPGrowth a = new FPGrowth();
            //Se rebajan el numero de reglas ya que por defecto son 100
            //Se sacan las 10 MEJORES reglas que haya encontrado el modelo
          //  a.setNumRules(10);
            //Creamos el descriptivo apriori con los datos
            a.buildAssociations(data);
            //Se cargan los resultados de la asociacion apriori
            String rta = "<b><center>Resultados de asociacion FPGrowth</center></b>"
                    + "========<br>"
                    + "El modelo de FPGrowth generado indica los siguientes resultados"
                    + "<br>===========<br><ol>";
             
             //Obtenemos resultados;
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
    
    public String Tertius (Instances data){
    
        try {
            //Creamos el objeto de asociacion por FPGrwoth
            Tertius a = new Tertius();
            //Creamos el descriptivo apriori con los datos
            a.buildAssociations(data);
            //Se cargan los resultados de la asociacion apriori
            String rta = "<b><center>Resultados de asociacion Tertius</center></b>"
                    + "========<br>"
                    + "El modelo de Tertius generado indica los siguientes resultados"
                    + "<br>===========<br><ol>";
            //Obtenemos resultados
            SimpleLinkedList.LinkedListIterator resultados = a.getResults().iterator();
            while(resultados.hasNext()){
                String[] datos = ((Rule) resultados.next()).toString().split(" ==> ");
                rta += "<b>"+datos[1]+" </b><br>";
            }

            
//                rta = rta+"<li align='left'><b>"+resultados+"<br>"
//                    + " .<br><br></li>";
                
             return rta+"</ol>";
             
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    
    }
    
}
