/* 
 * Carlos Martinez
 */
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
    // Validamos si hay un algoritmo seleccionado
    var algoritmo = getUrlParametro('algoritmo');
    // agregamos la clase active al menu
    $("#arboles"+algoritmo).addClass("active");
    // asignamos el valor al select de opciones
    $("#algoritmo").val(algoritmo);
    // Consumimos los servicios por ajax de jquery
    $(document).on('submit', '#formulario', function (e) {
        jQuery.ajax({
            url: $(this).attr("action"),
            data: new FormData($(this)[0]),
            cache: false,
            contentType: false,
            processData: false,
            method: $(this).attr("method"),
            type: $(this).attr("method"),
            enctype: $(this).attr("enctype"),
            beforeSend: function (data) {
                // Deshabilitamos el boton submit
                $("#btnform-mineria").prop("disabled", true);
//                $("#resumendata,#resultadoJSON,#rtaArb").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function (data) {
                // pintamos el json
                $("#resultadoJSON").html(data[1]);
                // pintamos el resumen estadistico
                $("#resumendata").html(data[0]);
                // Volvemos habilitar el boton
                $("#btnform-mineria").prop("disabled", false);
                // Pintamos el arbol
                var treeData = JSON.parse(data[1]);
                root = treeData;
                root.x0 = height / 2;
                root.y0 = 0;
                update(root);
                // end arbol
                console.log(data);
                // mostramos el arbol
                $("#rtaArb").show();
            },
            error: function (e) {
                $("#resumendata,#resultadoJSON,#rtaArb").html('<p align=left>' + e.responseText + '</p>');
                // Volvemos habilitar el boton
                $("#btnform-mineria").prop("disabled", false);
                console.log(e.responseText);
            }
        });
        e.preventDefault();
    });

    // Obtenemos la informacion del archivo seleccionado y la mostramos
    $(document).on('change', '#file', function (e) {
        jQuery.ajax({
            url: $(this).data("action"),
            data: new FormData($("#formulario")[0]),
            cache: false,
            contentType: false,
            processData: false,
            method: $("#formulario").attr("method"),
            type: $("#formulario").attr("method"),
            enctype: $("#formulario").attr("enctype"),
            beforeSend: function (data) {   
                $("#conjuntodatos").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function (data) {
                $("#conjuntodatos").html(data[0]);
            },
            error: function (e) {
                $("#conjuntodatos").html('<p align=left><b>Datos del archivo:</b><br><br>' + e.responseText + '</p>');
            }
        });
        e.preventDefault();
    });
    // Cambiamos el menu, dependiendo de la opcion seleccionada
    $("body").on("change","#algoritmo", function(){
        var algoritmo = $(this).val();
        // Eliminamos el active en el menu del anterior algoritmo
        $("#arboles1,#arboles2,#arboles3").removeClass("active");
        // Asignamos el active en el menu del algoritmo seleccionado 
        $("#arboles"+algoritmo).addClass("active");
    });
});

