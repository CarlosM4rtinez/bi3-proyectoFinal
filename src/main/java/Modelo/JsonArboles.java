/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author CAMILO
 */
public class JsonArboles implements Serializable{
    
    private int numeroInstancias;
    
    private String instanciasCorrectas;
    
    private int numeroInstanciasCorrectas;
    
    private String porcentInstanciasCorrectas;
    
    private int numeroInstanciasIncorrectas;
    
    private String mediaError;
    
    private String matrizConju;
    
    private String arbol;

    public JsonArboles(int numeroInstancias, String instanciasCorrectas, int numeroInstanciasCorrectas, String porcentInstanciasCorrectas, int numeroInstanciasIncorrectas, String mediaError, String matrizConju, String arbol) {
        this.numeroInstancias = numeroInstancias;
        this.instanciasCorrectas = instanciasCorrectas;
        this.numeroInstanciasCorrectas = numeroInstanciasCorrectas;
        this.porcentInstanciasCorrectas = porcentInstanciasCorrectas;
        this.numeroInstanciasIncorrectas = numeroInstanciasIncorrectas;
        this.mediaError = mediaError;
        this.matrizConju = matrizConju;
        this.arbol = arbol;
    }

    public JsonArboles() {
    }

    public int getNumeroInstancias() {
        return numeroInstancias;
    }

    public void setNumeroInstancias(int numeroInstancias) {
        this.numeroInstancias = numeroInstancias;
    }

    public String getInstanciasCorrectas() {
        return instanciasCorrectas;
    }

    public void setInstanciasCorrectas(String instanciasCorrectas) {
        this.instanciasCorrectas = instanciasCorrectas;
    }

    public int getNumeroInstanciasCorrectas() {
        return numeroInstanciasCorrectas;
    }

    public void setNumeroInstanciasCorrectas(int numeroInstanciasCorrectas) {
        this.numeroInstanciasCorrectas = numeroInstanciasCorrectas;
    }

    public String getPorcentInstanciasCorrectas() {
        return porcentInstanciasCorrectas;
    }

    public void setPorcentInstanciasCorrectas(String porcentInstanciasCorrectas) {
        this.porcentInstanciasCorrectas = porcentInstanciasCorrectas;
    }

    public int getNumeroInstanciasIncorrectas() {
        return numeroInstanciasIncorrectas;
    }

    public void setNumeroInstanciasIncorrectas(int numeroInstanciasIncorrectas) {
        this.numeroInstanciasIncorrectas = numeroInstanciasIncorrectas;
    }

    public String getMediaError() {
        return mediaError;
    }

    public void setMediaError(String mediaError) {
        this.mediaError = mediaError;
    }

    public String getMatrizConju() {
        return matrizConju;
    }

    public void setMatrizConju(String matrizConju) {
        this.matrizConju = matrizConju;
    }

    public String getArbol() {
        return arbol;
    }

    public void setArbol(String arbol) {
        this.arbol = arbol;
    }
    
    
    
}
