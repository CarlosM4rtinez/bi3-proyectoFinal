package Modelo;

import java.io.Serializable;

/**
 * Clase que representa el cluster
 * 
 * @author Laura, Alejo, Monica
 *
 */
public class Cluster implements Serializable {
	
	private String nombre;
	
	private double tamano;
	
	public Cluster() {
		// TODO Auto-generated constructor stub
	}

	public Cluster(String nombre, double tamano) {
		super();
		this.nombre = nombre;
		this.tamano = tamano;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getTamano() {
		return tamano;
	}

	public void setTamano(double tamano) {
		this.tamano = tamano;
	}
	
	

}
