/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos.ReglasAsociacion;

import Modelo.Link;
import Modelo.Nodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weka.associations.FPGrowth;
import weka.associations.Item;
import weka.core.Instances;

/**
 * Algoritmo de Mineria de datos FPGrowth de reglas de asociacion
 * @author Carlos Martinez
 */
public class FPGrowthAlgoritmoRA implements Serializable{
    
    ReglasAsociacion ra;

    public FPGrowthAlgoritmoRA(ReglasAsociacion ra) {
        this.ra = ra;
    }

    /**
     * Ejecuta el algoritmo
     * @param data instancias necesarias para la ejecucion del algoritmo
     * @return string con los resultados
     * @throws Exception 
     */
    public String FPGrowth (Instances data) throws Exception{
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
        //Obtenemos resultados
        for (int i = 0; i < a.getAssociationRules().getRules().size(); i++) {
            rta += "<li align='left'>Si ";
            // Sacamos los valores del si para construir los nodos
            Collection<Item> premises = a.getAssociationRules().getRules().get(i).getPremise();
            for(Item item: premises){
                rta += "<b>"+this.ra.replaceAttribute(item.getAttribute().name())+" "+this.ra.replaceCompara(item.getComparisonAsString())+" "+item.getItemValueAsString()+"</b>";
                rta += " y ";
            }
            rta = rta.substring(0, rta.length()-3); // Eliminamos el ultimo " y " de la cadena
            rta += "</b><br>Entonces <b>";
            // Sacamos los valores del Entonces para construir los nodos
            Collection<Item> consequences = a.getAssociationRules().getRules().get(i).getConsequence();
            for(Item item: consequences){
                rta += this.ra.replaceAttribute(item.getAttribute().name())+" "+this.ra.replaceCompara(item.getComparisonAsString())+" "+item.getItemValueAsString();
            }
            rta += "</b> con un <font color='green'><b>"+(int)(a.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100)
                +  "%</b></font> de posibilidad.<br><br></li>";
        }
        return rta+"</ol>";    
    }
    
     /**
     * Convierte los resultados de las reglas de asociacion en json formato D3.js
     * @param data las instancias del archivo
     * @return lista para ser pasada a formato json
     * @throws java.lang.Exception
     */
    public List<ArrayList> FPGrowthGraphData(Instances data) throws Exception{
        // La lista con las listas de nodos y links a retornar
        List<ArrayList> listas = new ArrayList<>();
         //Creamos el objeto de asociacion por apriori
         FPGrowth a = new FPGrowth();
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
                nodo.setAtributo(this.ra.replaceAttribute(item.getAttribute().name()));
                nodo.setCompara(this.ra.replaceCompara(item.getComparisonAsString()));
                nodo.setValor(item.getItemValueAsString());
                // Validamos si ya existe nodo
                Nodo n = this.ra.buscarNodo(nodo, nodes);
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
                    link.setSource("nodes["+nodo.getId()+"]");
                }
                sec++;
            }
            // Conectamos el  entonces en si en el grapho
            if(link.getSource() == null){
                link.setSource("nodes["+(nodes.size()-1)+"]");
            }else{
                
            }
            // Sacamos los valores del Entonces para construir los nodos
            Collection<Item> consequences = a.getAssociationRules().getRules().get(i).getConsequence();
            for(Item item: consequences){
               Nodo nodo = new Nodo();
                nodo.setAtributo(this.ra.replaceAttribute(item.getAttribute().name()));
                nodo.setCompara(this.ra.replaceCompara(item.getComparisonAsString()));
                nodo.setValor(item.getItemValueAsString());
                // Validamos si ya existe nodo
                Nodo n = this.ra.buscarNodo(nodo, nodes);
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
}
