package Servicios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Clase que configura la aplicacion para el uso de servicios
 * @author Kevin Zapata & Carlos Martinez
 */
@javax.ws.rs.ApplicationPath("servicios")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    /**
     * Agrega los servicios a los resources
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Servicios.ClusterService.class);
        resources.add(Servicios.DataMiningArbolesServices.class);
        resources.add(Servicios.DataMiningServices.class);
        resources.add(Servicios.PruebaServices.class);
    }
    
}