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
import java.util.List;
import weka.associations.Tertius;
import weka.associations.tertius.Rule;
import weka.associations.tertius.SimpleLinkedList;
import weka.core.Instances;

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
     * @param data instancias necesarias para el proceso
     * @return resultado de las reglas de asociacion
     */
    public String Tertius (Instances data) throws Exception{
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
        int i= 0;
        while(resultados.hasNext()){
            String[] datos = ((Rule) resultados.next()).toString().split(" ==> "); // 0: condiciones si u o, 1: entonces
        }
        return rta+"</ol>"; 
    }
    /**
     * Convierte los resultados de las reglas de asociacion en json formato D3.js
     * @param data las instancias del archivo
     * @return lista para ser pasada a formato json
     * @throws java.lang.Exception
     */
    public List<ArrayList> tertiusGraphData(Instances data) throws Exception{
        // La lista con las listas de nodos y links a retornar
        List<ArrayList> listas = new ArrayList<>();
        //Creamos el objeto de asociacion por FPGrwoth
        Tertius a = new Tertius();
        //Creamos el descriptivo apriori con los datos
        a.buildAssociations(data);
        // Lista de nodos
        List<Nodo> nodes = new ArrayList<>();
        // Lista de enlaces de los nodos
        List<Link> links = new ArrayList<>();
        //Obtenemos resultados
        SimpleLinkedList.LinkedListIterator resultados = a.getResults().iterator();
        int sec = 1; 
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
                    Nodo nodo = this.crearNodoTertius(condiciones[j], "Si", sec, nodes.size());
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
                Nodo nodo = this.crearNodoTertius(stringCondiciones, "Si", sec, nodes.size());
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
                    Nodo nodo = this.crearNodoTertius(entonces[j], "O", sec, nodes.size());
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
                Nodo nodo = this.crearNodoTertius(stringEntonces, "Entonces", sec, nodes.size());
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
    public Nodo crearNodoTertius(String cadena, String conector, int sec, int sizeNodes){
        String[] partes = cadena.split(" = ");
        Nodo nodo = new Nodo();
        nodo.setAtributo(this.ra.replaceAttribute(partes[0]));
        nodo.setCompara(this.ra.replaceCompara("="));
        nodo.setValor(partes[1]);
        nodo.setId(sizeNodes);
        nodo.setName("R"+(sizeNodes+1)+":"+sec+". "+conector+" "+nodo.getAtributo()+" "+nodo.getCompara()+" "+nodo.getValor());
        System.out.println(nodo.getName());
        return nodo;
    }
}
