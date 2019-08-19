/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineriaDatos.ReglasAsociacion;

import MineriaDatos.DataMining;
import Modelo.AjustesRA;
import Modelo.Nodo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

/**
 * Clase encargada de usar la libreria de weka para el analisis de datos 
 * usando los algoritmos de reglas de asociacion
 * @author Carlos Martinez
 */
public class ReglasAsociacion implements Serializable{
        
    DataMining dm;
    AprioriAlgoritmoRA aprioriRA = new AprioriAlgoritmoRA(this);
    FPGrowthAlgoritmoRA FPGrowthRA = new FPGrowthAlgoritmoRA(this);
    TertiusAlgoritmoRA tertiusRA = new TertiusAlgoritmoRA(this);

    public ReglasAsociacion(DataMining dm) {
        this.dm = dm;
    }
    
    /**
     * Aplica un algoritmo de regla de asociacion
     * @param ajustes contiene los ajustes necesarios para la aplicacion del algoritmo
     * @return vector con inormacion de los resultados del algoritmo
     * @throws Exception
     * @throws IOException 
     */
    public String[] aplicar (AjustesRA ajustes) throws Exception, IOException{
        //Variable de retorno para definir si un algoritmo recibe atrubitos de tipo " Class"
        int tipo = 0;
        final Gson gson = new Gson(); // clase para pasar objetos a JSON
        String[] rta = new String[4];
        switch (ajustes.getAlgoritmo()){
            case 1:
                // Algoritmo de Apriori
                tipo = 1;
                // Ejecuta el algoritmo de apriori y guardamos el resultado en la respuesta
                rta[0] = this.dm.encabezado(ajustes.getData(),tipo)+"\n"+this.aprioriRA.apriori(ajustes);
                // informacion necesaria para construir el grapho en el frontend con D3.js
                // obtenemos las listas de nodos y links
                List<ArrayList> listas = this.aprioriRA.aprioriGraphData(ajustes);
                // Obtenemos los nodos
                List<Nodo> nodos = listas.get(0);
                // guardamos los nodos del grapho en la respuesta
                rta[1] = gson.toJson(nodos);
                // Guardamos los links del grapho en la respuesta
                rta[2] = gson.toJson(listas.get(1));
               // Mandamos la tabla html de los nodos
                rta[3] = this.tablaNodosHTML(nodos);
                break;
            case 2:
                // Algoritmo FTGrowth (NO RECIBE ATRIBUTOS DE TIPO CLASS)
                tipo=2;
               // Ejecuta el algoritmo de FPGrowth y guardamos el resultado en la respuesta
                rta[0] = this.dm.encabezado(ajustes.getData(),tipo)+"\n"+this.FPGrowthRA.FPGrowth(ajustes);
                // informacion necesaria para construir el grapho en el front-end con D3.js
                // obtenemos las listas de nodos y links
                List<ArrayList> listasFP = this.FPGrowthRA.FPGrowthGraphData(ajustes);
                // Obtenemos los nodos
                List<Nodo> nodosFP = listasFP.get(0);
                // guardamos los nodos del grapho en la respuesta
                rta[1] = gson.toJson(nodosFP);
                // Guardamos los links del grapho en la respuesta
                rta[2] = gson.toJson(listasFP.get(1));
               // Mandamos la tabla html de los nodos
                rta[3] = this.tablaNodosHTML(nodosFP);
                break;
            case 3:
                //Tipo 3 (Tertius)
                tipo=3;
                 // Ejecuta el algoritmo de apriori y guardamos el resultado en la respuesta
                rta[0] = this.dm.encabezado(ajustes.getData(),tipo)+"\n"+this.tertiusRA.Tertius(ajustes);
                // informacion necesaria para construir el grapho en el frontend con D3.js
                // obtenemos las listas de nodos y links
                List<ArrayList> listasTer = this.tertiusRA.tertiusGraphData(ajustes);
                // Obtenemos los nodos
                List<Nodo> nodosTer = listasTer.get(0);
                // guardamos los nodos del grapho en la respuesta
                rta[1] = gson.toJson(nodosTer);
                // Guardamos los links del grapho en la respuesta
                rta[2] = gson.toJson(listasTer.get(1));
               // Mandamos la tabla html de los nodos
                rta[3] = this.tablaNodosHTML(nodosTer);
                break;
            default:
        }
    return rta;
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
    
}
