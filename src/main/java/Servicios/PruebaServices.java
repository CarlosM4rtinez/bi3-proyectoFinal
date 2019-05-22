/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio de pruebas
 * @author Carlos Martinez
 */
@Path("prueba")
public class PruebaServices {
    
    /**
     * Saluda a un usuario de manera decente
     * @param nombre el nombre del usuario a saludar
     * @return el saludo decente
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("saludo/{nombre}")
    public String saludo(@PathParam("nombre") String nombre) {
        return "Hola, "+nombre+".";
    }
}
