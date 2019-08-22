package MineriaDatos.ArbolesDecision;

import MineriaDatos.DataMiningArboles;
import com.google.gson.Gson;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Modelo.Hijo;
import Modelo.JsonArboles;
import Modelo.NodoArbol;
import Modelo.Padre;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

/**
 *
 * @author CAMILO
 */
public class ArbolesDecisionMineria implements Serializable {

    //Lista para guardar la informacion del arbol y el JSON para D3.JS
    private ArrayList<String> listaRetorno = new ArrayList<>();

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    //Libreria Gson
    Gson gson = new Gson();

    // instanciamos el data mining
    private DataMiningArboles dataMining;

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String arbolRJ48(Instances data) {
        this.dataMining = new DataMiningArboles();
        try {

            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            J48 j48 = new J48();
            //clasificador de j48
            j48.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana
            Evaluation evalJ48 = new Evaluation(data);
            evalJ48.crossValidateModel(j48, data, 10, new Random(1));
            //Obtenemos el grafico del arbol generado por weka
            String[] arbol = j48.graph().split("\n");

            String resBay = "<b><center>Resultados RJ48</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<span class=\"text-success\">1. numero de instancias clasificadas: </span>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">2. porcentaje de instancias correctamente clasificadas: </span>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">3. numero de instancias correctamente clasificadas: </span>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">4. porcentaje de instancias incorrectamente clasificadas: </span>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">5. Numero de instancias incorrectamente clasificadas: </span>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">6. Media del error absoluto: </span>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">7." + evalJ48.toMatrixString("Matriz de confucion </span>").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            //jArbol.setArbol(arbol);

            String resPu = transformacionJ48JSON(arbol,1);

            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            return gson.toJson(listaRetorno);

        } catch (Exception ex) {
            // ex.printStackTrace();
            return "El error en RJ48 es: " + ex.getMessage();
        }
    }

    /**
     * Algoritmo de weka Arbol RandomTree
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RandomTree
     */
    public String arbolRandomTree(Instances data) {
        this.dataMining = new DataMiningArboles();
        try {
         
            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto random forest
            RandomTree rF = new RandomTree();
            //clasificador de j48

            rF.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(rF, data, 10, new Random(1));

            //Obtenemos el grafico del arbol generado por weka
            String[] arbol = rF.graph().split("\n");

            String resBay = "<b><center>Resultados Forest</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<b>1. numero de instancias clasificadas:<b>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<b>2. porcentaje de instancias correctamente clasificadas:</b>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<b>3. numero de instancias correctamente clasificadas:</b>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<b>4. porcentaje de instancias incorrectamente clasificadas:</b>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<b>5. Numero de instancias incorrectamente clasificadas:</b>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<b>6. Media del error absoluto:</b>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            // resBay = resBay + ("<b>7." + evalJ48.toMatrixString("Matriz de confucion").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            // jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError("Matriz de confucion").replace("\n", "<br>")));

            String resPu = transformacionJ48JSON(arbol,2);

            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            return gson.toJson(listaRetorno);

        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * Algoritomo de weka REPTree
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol REPTree
     */
    public String arbolREPTree(Instances data) {
        this.dataMining = new DataMiningArboles();
        try {

            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol REPTree
            REPTree rTree = new REPTree();

            rTree.buildClassifier(data);

            Evaluation evalRdTree = new Evaluation(data);
            evalRdTree.crossValidateModel(rTree, data, 10, new Random(1));

            //Obtenemos el grafico del arbol generado por weka
            String arbol[] = rTree.graph().split("\n");
            
            String resBay = "<b><center>Resultados LMT</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<span class=\"text-success\">1. numero de instancias clasificadas: </span>" + (int) evalRdTree.numInstances() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">2. porcentaje de instancias correctamente clasificadas: </span>" + format.format(evalRdTree.pctCorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">3. numero de instancias correctamente clasificadas: </span>" + (int) evalRdTree.correct() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">4. porcentaje de instancias incorrectamente clasificadas: </span>" + format.format(evalRdTree.pctIncorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">5. Numero de instancias incorrectamente clasificadas: </span>" + (int) evalRdTree.incorrect() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">6. Media del error absoluto: </span>" + format.format(evalRdTree.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">7." + evalRdTree.toMatrixString("Matriz de confucion </span>").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalRdTree.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalRdTree.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalRdTree.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalRdTree.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalRdTree.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            //jArbol.setArbol(evalJ48.toCumulativeMarginDistributionString());

            String resPu = transformacionJ48JSON(arbol,2);

            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            return gson.toJson(listaRetorno);

        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * Metodo para obtener el JSON que se enviara al arbol d3.js
     *
     * @param dataGr grafico en formato string que retorna el arbol
     * @return JSON que se enviara a d3.js
     */
    public String transformacionJ48JSON(String[] data, int inicio) {

        List<Padre> listaPadres = new ArrayList<>();
        List<Hijo> listaHijos = new ArrayList<>();

        for (int i = 0; i < data.length - inicio; i++) {
            //Verificamos la data que tenga hijos
            boolean palabraSplit = data[i].matches(".*->.*");
            //IF para crear la lista de padres
            if (palabraSplit == false) {
                if (i >= inicio) {//definimos los padres
                    //Obtenemos id del padre y el nombre 
                    String[] identificador = data[i].split("label");
                    //Nombre final del padre
                    String nombreNodo[] = identificador[1].split("\"");

                    //Objeto padre
                    Padre p = new Padre();
                    p.setId(identificador[0].substring(0, identificador[0].length() - 2));//Identificador del padre
                    p.setNombre(nombreNodo[1]);// nombre del padre
                    listaPadres.add(p);
                    //System.out.println("Padre -> " + identificador[0].substring(0, identificador[0].length()-2) +" nombre: "+ nombreNodo[1]); 
                }
            } 
            //De locontrario creamos la lista de hijos
            else {
                if (i > inicio) {//definimos los hijos
                    //Obtenemos id del padre y el valor de decision de este
                    String[] identificador = data[i].split("->");
                    //Obtenemos id del hijo 
                    String[] idHijo = identificador[1].split("label");
                    //Valor del padre
                    String nombreNodo[] = identificador[1].split("\"");

                    //Objeto hijo
                    Hijo h = new Hijo();
                    h.setIdP(identificador[0]);//Identificador del padre
                    h.setIdH(idHijo[0].substring(0, idHijo[0].length() - 2));
                    h.setNombre(nombreNodo[1]);// nombre del padre
                    listaHijos.add(h);
                    //System.out.println("Padre -> " +identificador[0] + " - Hijo: "+ idHijo[0].substring(0, idHijo[0].length()-2) +" - nombre: "+ nombreNodo[1]); 
                }
            }
        }
        String armadoArbol = armarArbol(listaPadres, listaHijos);
        return armadoArbol;
    }

    /**
     * Metodo que se encarga de armar la estructura del arbol y retornar el JSON
     * @param listPadre
     * @param listHijo
     * @return 
     */
    private String armarArbol(List<Padre> listPadre, List<Hijo> listHijo) {
        List<NodoArbol> listNodo = new ArrayList<>();
        for (int i = 0; i < listPadre.size(); i++) {
            NodoArbol objNodo = new NodoArbol();
            //Se crea el nodo principal el padre de todos
            if (i == 0) {
                objNodo.setName(listPadre.get(i).getNombre());
                objNodo.setNodo(listPadre.get(i).getId());
                objNodo.setChildren(new ArrayList<NodoArbol>());
            }
            //Se crean los hijos de cada nodo
            if (i > 0 && i < listPadre.size()) {
                objNodo.setName(listHijo.get(i - 1).getNombre()+" - " +listPadre.get(i).getNombre());
                objNodo.setNodo(listHijo.get(i - 1).getIdH());
                objNodo.setChildren(new ArrayList<NodoArbol>());
                NodoArbol nodoPadre = obtenerPadre(listHijo.get(i - 1).getIdP(), listNodo);
                nodoPadre.getChildren().add(objNodo);               
            }
            //Se agre cada hijo a la lista de nodos
            listNodo.add(objNodo);            
        }
        //System.out.println(gson.toJson(listNodo.get(0)));
        return gson.toJson(listNodo.get(0));
    }

    /**
     * Metodo para obtiener el nodo padre de una clase hija
     *
     * @param nombrePadre nombre del nodo padre que se desea obtener
     * @param nodos lista de nodos donde se desea buscar
     * @return el nodo padre
     */
    private NodoArbol obtenerPadre(String nombrePadre, List<NodoArbol> nodos) {
        for (NodoArbol nodo : nodos) {
            if (nodo.getNodo().equals(nombrePadre)) {
                return nodo;
            }
        }
        return null;
    }

}
