function changeCombo(v) {
    if (v == 1) {
        $("#grups").show();
    } else {
        $("#grups").hide();
    }
}
/**
 * Returna el valor de una variable por get
 * @param {String} key Nombre del parametro get
 * @returns si se encuentra retorna el valor de la variable, de lo contrario null
 * @author Johnny Alexander Salazar
 * @version 0.1
*/
function getUrlParametro(key){
    var url = decodeURIComponent(window.location.search.substring(1)),
        parametros = url.split('&'),
        claves;
    for (var i = 0; i < parametros.length; i++) {
        claves = parametros[i].split('=');
        if (claves[0] === key) {
            // retornamos el valor
            return claves[1];
        }
    }
    return null;
}
// Una vez cargado la pagina ejecutamos las acciones
$(document).ready(function(){
    $("#grups").hide();
    // Validamos si hay un algoritmo seleccionado
    var algoritmo = getUrlParametro('algoritmo');
    // agregamos la clase active al menu
    $("#algoritmo"+algoritmo).addClass("active");
    // asignamos el valor al select de opciones
    $("#algoritmo").val(algoritmo);
    changeCombo(algoritmo);
    // Cambiamos el menu, dependiendo de la opcion seleccionada
    $("body").on("change","#algoritmo", function(){
        var algoritmo = $(this).val();
        // Eliminamos el active en el menu del anterior algoritmo
        $("#algoritmo1,#algoritmo2,#algoritmo3,#algoritmo4").removeClass("active");
        // Asignamos el active en el menu del algoritmo seleccionado 
        $("#algoritmo"+algoritmo).addClass("active");
    });
    $("#algoritmo").change(function() {
        changeCombo($(this).val());
    });
});

