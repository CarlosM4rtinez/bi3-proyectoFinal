/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos.ReglasAsociacion;

import Modelo.AjustesRA;
import Modelo.Link;
import Modelo.Nodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import weka.associations.Tertius;
import weka.associations.tertius.Rule;
import weka.associations.tertius.SimpleLinkedList;

/**
 * Algoritmo de Mineria de datos Tertius de reglas de asociacion
 * @author Carlos Martinez
 */
public class TertiusAlgoritmoRA implements Serializable{
    
    ReglasAsociacion ra;

    public TertiusAlgoritmoRA(ReglasAsociacion ra) {
        this.ra = ra;
    }
    
    /**
     * Ejecuta el algoritmo de tertius
     * @param ajustes contiene los datos necesarios para la ejecucion del algoritmo
     * @return resultado de las reglas de asociacion
     */
    public String Tertius (AjustesRA ajustes) throws Exception{
        //Creamos el objeto de asociacion por FPGrwoth
        Tertius a = new Tertius();
        a.setConfirmationValues(ajustes.getNumeroReglas()); // Asignamos el numero de reglas
        a.setConfirmationThreshold(ajustes.getPorcentajeAceptacion());
        a.setNumberLiterals(4);
        //Creamos el descriptivo apriori con los datos
        a.buildAssociations(ajustes.getData());
        //Se cargan los resultados de la asociacion apriori
        String rta = "<b><center>Resultados de asociacion Tertius</center></b>"
                + "========<br>"
                + "El modelo de Tertius generado indica los siguientes resultados"
                + "<br>===========<br><ol>";
        //Obtenemos resultados
        SimpleLinkedList.LinkedListIterator resultados = a.getResults().iterator();
        int i= 0;
        while(resultados.hasNext()){
            Rule regla = (Rule) resultados.next();
            String[] datos = regla.toString().split(" ==> "); // 0: condiciones si u o, 1: entonces
            rta += "<li>"+regla.toString()+" </li>";
            rta += "</b> con un <font color='green'><b>"+(int)(regla.getConfirmation()*100)
                +  "%</b></font> de posibilidad.<br><br></li>";
        }
        return rta+"</ol>"; 
    }
    /**
     * Convierte los resultados de las reglas de asociacion en json formato D3.js
     * @param ajustes contiene los datos necesarios para la ejecucion del algoritmo
     * @return lista para ser pasada a formato json
     * @throws java.lang.Exception
     */
    public List<ArrayList> tertiusGraphData(AjustesRA ajustes) throws Exception{
        // La lista con las listas de nodos y links a retornar
        List<ArrayList> listas = new ArrayList<>();
        //Creamos el objeto de asociacion por FPGrwoth
        Tertius a = new Tertius();
        a.setConfirmationValues(ajustes.getNumeroReglas()); // Asignamos el numero de reglas
        a.setConfirmationThreshold(ajustes.getPorcentajeAceptacion());
        a.setNumberLiterals(4);
        //Creamos el descriptivo apriori con los datos
        a.buildAssociations(ajustes.getData());
        // Lista de nodos
        List<Nodo> nodes = new ArrayList<>();
        // Lista de enlaces de los nodos
        List<Link> links = new ArrayList<>();
        //Obtenemos resultados
        SimpleLinkedList.LinkedListIterator resultados = a.getResults().iterator();
        int sec = 1; 
        int numeroRegla = 1;
        while(resultados.hasNext()){
            String[] datos = ((Rule) resultados.next()).toString().split(" ==> "); // 0: condiciones si u o, 1: entonces
            // construimos el link
            Link link = new Link();
            // Sacamos los valores del si para construir los nodos
            String stringCondiciones = datos[0];
            // validamos si la cadena contiene mas de una condicion
            if(stringCondiciones.contains(" and ")){
                // sacamos cada una de las condiciones por separado
                String[] condiciones = stringCondiciones.split(" and ");
                for (int j = 0; j < condiciones.length; j++) {
                    Nodo nodo = this.crearNodoTertius(condiciones[j], "Si", sec, nodes.size(), 0, numeroRegla);
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
            }else{
                // sacamos la unica condicion
                Nodo nodo = this.crearNodoTertius(stringCondiciones, "Si", sec, nodes.size(), 0, numeroRegla);
                // Guardamos el nodo en la lista de nodes
                nodes.add(nodo);
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
            }
            // Sacamos los valores del ENTONCES para construir los nodos
            String stringEntonces = datos[1];
            // validamos si la cadena contiene mas de una condicion
            if(stringEntonces.contains(" or ")){
                // sacamos cada una de las condiciones por separado
                String[] entonces = stringEntonces.split(" or ");
                for (int j = 0; j < entonces.length; j++) {
                    Nodo nodo = this.crearNodoTertius(entonces[j], "O", sec, nodes.size() , 0, numeroRegla);
                    // Guardamos el nodo en la lista de nodes
                    nodes.add(nodo);
                    if(link.getSource() == null){
                        // Asignamos el nodo anterior
                        link.setSource("nodes["+nodo.getId()+"]");
                    }else{
                        // Asignamos el nodo siguiente y guardamos el link
                        link.setTarget("nodes["+nodo.getId()+"]");
                        link.setConector("Entonces");
                        links.add(link);
                        link = new Link(); // Como se agrego, reiniciamos el link
                        link.setSource("nodes["+nodo.getId()+"]");
                    }
                    sec++;
                }
            }else{
                // sacamos la unica condicion
                Nodo nodo = this.crearNodoTertius(stringEntonces, "Entonces", sec, nodes.size(), ((Rule) resultados.next()).getConfirmation(), numeroRegla);
                // Guardamos el nodo en la lista de nodes
                nodes.add(nodo);
                if(link.getSource() == null){
                    // Asignamos el nodo anterior
                    link.setSource("nodes["+nodo.getId()+"]");
                }else{
                    // Asignamos el nodo siguiente y guardamos el link
                    link.setTarget("nodes["+nodo.getId()+"]");
                    link.setConector("O");
                    links.add(link);
                    link = new Link(); // Como se agrego, reiniciamos el link
                    link.setSource("nodes["+nodo.getId()+"]");
                }
                sec++;
            }
            sec = 1;
            numeroRegla++;
        }
        // Agregamos la lista de nodes y links a listas
        listas.add(0, (ArrayList) nodes);
        listas.add(1, (ArrayList) links);
        return listas;   
    }
    /**
     * construye un nodo para el algoritmo tertius
     * @param cadena
     * @param conector
     * @param sec
     * @param sizeNodes
     * @return 
     */
    public Nodo crearNodoTertius(String cadena, String conector, int sec, int sizeNodes, double porcentaje, int numeroRegla){
        String[] partes = cadena.split(" = ");
        Nodo nodo = new Nodo();
        nodo.setAtributo(this.ra.replaceAttribute(partes[0]));
        nodo.setCompara(this.ra.replaceCompara("="));
        nodo.setValor(partes[1]);
        nodo.setId(sizeNodes);
        if(conector.equalsIgnoreCase("Entonces")){
            nodo.setName("R"+numeroRegla+":"+sec+". "+conector+" "+nodo.getAtributo()+" "+nodo.getCompara()+" "+nodo.getValor()+", "+(int)(porcentaje*100)+"%.");
        }else{
            nodo.setName("R"+numeroRegla+":"+sec+". "+conector+" "+nodo.getAtributo()+" "+nodo.getCompara()+" "+nodo.getValor());
        }
        return nodo;
    }
}
