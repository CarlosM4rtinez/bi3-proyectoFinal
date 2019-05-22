package Modelo;

import java.io.Serializable;

/**
 * clase encargada de enlazar los nodos 
 * @author Carlos Martinez
 */
public class Link implements Serializable{
    
    /**
     * es la posicion del nodo inicial
     */
    private String source;
    /**
     * Es la posicion del nodo final
     */
    private String target;
    /**
     * el conector solo puede ser: Si, o Entonces
     */
    private String conector;
    /* variables que no sabemos para que sirven en el grafo */
    private boolean left = false;
    private boolean right = true;

    public Link() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getConector() {
        return conector;
    }

    public void setConector(String conector) {
        this.conector = conector;
    }
}
