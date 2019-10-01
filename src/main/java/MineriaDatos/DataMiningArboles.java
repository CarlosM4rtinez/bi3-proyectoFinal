package MineriaDatos;

import MineriaDatos.ArbolesDecision.ArbolesDecisionMineria;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DecimalFormat;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;

/**
 *
 * @author CAMILO
 */
public class DataMiningArboles implements Serializable {

    // Algoritmos de Reglas de Asociacion
    ArbolesDecisionMineria raDataMining = new ArbolesDecisionMineria();

    /**
     * Aplica la mineria de datos para el algoritmo especifico
     *
     * @param datos conjunto de datos a analizar
     * @param tipoAlgoritmo el tipo de algoritmo a ejecutar para el conjunto de
     * datos
     * @return
     */
    public String mineria(String datos, int tipoAlgoritmo,float coincidencia,int semilla) {
        StringReader sr = new StringReader(datos);
        BufferedReader br = new BufferedReader(sr);
        try {
            Instances data;
            //Definimos el objeto que contiene los datos a clasificar
            data = new Instances(br);
            //Seleccionamos cual sera el atributo clase, el cual
            //es el total de atributos -1
            data.setClassIndex(data.numAttributes() - 1);
            //cerramos el objeto buffer
            br.close();
            String resultado = "";
            //Obtenemos resultados dependiendo del algoritmo
            switch (tipoAlgoritmo) {
                case 1:
                    // Ejecuta el algoritmo de apriori
                    resultado = raDataMining.arbolRJ48(data,coincidencia,semilla);
                    break;

                case 2:
                    // Ejecuta el algoritmo de randomforest
                    resultado = raDataMining.arbolRandomTree(data,semilla);
                    break;
                    
                case 3:
                    //Ejecuta el algoritmo RandomTree
                    resultado = raDataMining.arbolREPTree(data,semilla);
                    break;
                default:
            }
            return resultado;
        } catch (IOException ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * define el encabezado de un conjunto de datos
     *
     * @param data los datos con las isntancias
     * @return el encabezado del conjunto de datos
     */
    public String encabezado(Instances data) {
        String descripcion = "<b>Atributo clase:</b> " + data.attribute(data.numAttributes() - 1).name() + "<br>";
        descripcion += "<b>Posibles valores:</b><ul align='left'>";
        for (int z = 0; z < data.attribute(data.numAttributes() - 1).numValues(); z++) {
            descripcion += "<li>" + data.attribute(data.numAttributes() - 1).value(z) + "</li>";
        }
        return descripcion + "</ul>";
    }

    /**
     * Saca la informacion del archivo y la guarda como texto
     *
     * @param file el archivo a extraerle la informacion
     * @return toda la informacion del archivo en una variable string
     * @throws IOException
     */
    public String convertir(BufferedReader file) throws IOException {
        String temp;
        String cadena = "";
        while ((temp = file.readLine()) != null) {
            cadena = cadena + temp + "\n";
        }
        return cadena;
    }
}
