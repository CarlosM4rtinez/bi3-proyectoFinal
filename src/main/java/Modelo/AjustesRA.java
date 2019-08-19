/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import weka.core.Instances;

/**
 *
 * @author Carlos Martinez
 */
public class AjustesRA implements Serializable{
    
    private String datosArchivo;
    private Instances data;
    private int algoritmo;
    private int numeroReglas;
    private double porcentajeAceptacion; // valores de 0 a 1: ejemplo: 0.95, 0.99, 0.78

    public AjustesRA() {
    }
    
    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }

    public int getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(int algoritmo) {
        this.algoritmo = algoritmo;
    }

    public int getNumeroReglas() {
        return numeroReglas;
    }

    public void setNumeroReglas(int numeroReglas) {
        this.numeroReglas = numeroReglas;
    }

    public double getPorcentajeAceptacion() {
        return porcentajeAceptacion;
    }

    public void setPorcentajeAceptacion(double porcentajeAceptacion) {
        this.porcentajeAceptacion = porcentajeAceptacion;
    }    

    public String getDatosArchivo() {
        return datosArchivo;
    }

    public void setDatosArchivo(String datosArchivo) {
        this.datosArchivo = datosArchivo;
    }
 
}
