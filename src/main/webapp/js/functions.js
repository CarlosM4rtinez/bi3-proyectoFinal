/* 
 * Carlos Martinez
 */

// Una vez cargado la pagina ejecutamos las acciones
$(document).ready(function(){
    // Consumimos los servicios por ajax de jquery
    $(document).on('submit','#form-mineria',function(e){
        // ocultamos el conjunto de datos
        $("#collapseCardExample").removeClass("show");
        // Ocultamos el grapho, por si ya se habia mostrado
        $("#graph").parent().hide();
        jQuery.ajax({
            url: $(this).attr("action"),
            data: new FormData($(this)[0]),
            cache: false,
            contentType: false,
            processData: false,
            method: $(this).attr("method"),
            type: $(this).attr("method"),
            enctype: $(this).attr("enctype"),
            beforeSend: function(data){
                // Deshabilitamos el boton submit
                $("#btnform-mineria").prop("disabled", true);
                $("#rta").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function(data){
                // pasamos los nodos del grapho
                nodes = JSON.parse(data[1]);
                // Pasamos los links del grapho
                var enlaces = data[2].replace(/"nodes/g, "nodes");
                enlaces = enlaces.replace(/]"/g, ']');
                links = eval(enlaces);
                // Iniciamos las variables para el grapho
                iniciarVariables($("#collapseCardExample5").width()-30,400,'#graph');
                // Iniciamos el grapho
                restart();
                // Mostramos el grapho
                $("#graph").parent().show();
                // Mostramos el expandir grapho
                $("#expandir").show();
                // Ingresamos la informacion de la tabla
                $("#tabledata").html('<div class="grapho"><div id="grapho"></div>'+data[3]+'</div>');
                // Ingresamos el resumen del analisis
                $("#resumendata").html('<div class="rules"><h5>Interpretacion</h5><p align=left>'+data[0]+'</p></div>');
                // Volvemos habilitar el boton
                $("#btnform-mineria").prop("disabled", false);
            },
            error: function (e){
                $("#graph,#tabledata,#resumendata").html('<p align=left>'+e.responseText+'</p>');
                // Volvemos habilitar el boton
                $("#btnform-mineria").prop("disabled", false);

            }
        });
        e.preventDefault();
    });
    
    // Obtenemos la informacion del archivo seleccionado y la mostramos
    $(document).on('change','#file',function(e){
        jQuery.ajax({
            url: $(this).data("action"),
            data: new FormData($("#form-mineria")[0]),
            cache: false,
            contentType: false,
            processData: false,
            method: $("#form-mineria").attr("method"),
            type: $("#form-mineria").attr("method"),
            enctype: $("#form-mineria").attr("enctype"),
            beforeSend: function(data){
                $("#conjuntodatos").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function(data){
                $("#conjuntodatos").html(data);
            },
            error: function (e){
                $("#conjuntodatos").html('<p align=left><b>Datos del archivo:</b><br><br>'+e.responseText+'</p>');

            }
        });
        e.preventDefault();
    });
    /**
     * Expande al grapho
     */
    $("body").on("click", "#expandir", function(e){
        // Iniciamos las variables para el grapho GRANDE
        iniciarVariables($(window).width(),$(window).height(),'#graphBIG');
        // Iniciamos el grapho GRANDE
        restart();
        var boton = "<span id='close' class='close'>&#10006;</span>";
        $("#graphBIG").addClass("fixed");
        $("#graphBIG").append(boton);
    });
    /**
     * Cerrar el expandido del grapho
     */
    $("body").on("click", "#close", function(e){
        $("#graph").html("");
        // Iniciamos las variables para el grapho
        iniciarVariables($("#collapseCardExample5").width()-30,400,'#graph');
        // Iniciamos el grapho
        restart();
        $("#graphBIG").removeClass("fixed").html("");
        $(this).remove();
    });
});



