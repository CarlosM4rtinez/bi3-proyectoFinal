package Modelo;

import java.io.Serializable;

/**
 * clase encargada de la informacion del nodo
 * @author Carlos Martinez
 */
public class Nodo implements Serializable{
    
    /**
     * identificador del nodo
     */
    private int id;

    /**
     * Nombre del nodo
     */
    private String name;
    /**
     * Es el nombre atributo del nodo
     */
    private String atributo;
    /**
     * es el comparacion entre la clave y el valor
     */
    private String compara;
    /**
     * Es el valor que contiene el nodo
     */
    private String valor;
    
    // variables que no sabemos para que sirven en el grafo
    private boolean reflexive = false;

    public Nodo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isReflexive() {
        return reflexive;
    }

    public void setReflexive(boolean reflexive) {
        this.reflexive = reflexive;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getCompara() {
        return compara;
    }

    public void setCompara(String compara) {
        this.compara = compara;
    }
    
}
