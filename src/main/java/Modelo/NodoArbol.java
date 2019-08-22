package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author CAMILO
 */
public class NodoArbol implements Serializable{
    
    private String name;
    private String nodo;
    private ArrayList<NodoArbol> children;
    
   /** public Nodo(String nodo) {
        this.nodo = nodo;
    }*/

    public NodoArbol() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public ArrayList<NodoArbol> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<NodoArbol> children) {
        this.children = children;
    }
    
    

}
