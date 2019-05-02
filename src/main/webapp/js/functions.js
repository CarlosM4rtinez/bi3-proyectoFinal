/* 
 * Carlos Martinez
 */

// Una vez cargado la pagina ejecutamos las acciones
$(document).ready(function(){
    // Consumimos los servicios por ajax de jquery
    $(document).on('submit','#form-mineria',function(e){
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
                $("#rta").html(data);
                // Volvemos habilitar el boton
                $("#btnform-mineria").prop("disabled", false);
            },
            error: function (e){
                $("#rta").html('<p align=left>'+e.responseText+'</p>');
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
                $("#rta").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function(data){
                $("#rta").html(data);
            },
            error: function (e){
                $("#rta").html('<p align=left><b>Datos del archivo:</b><br><br>'+e.responseText+'</p>');

            }
        });
        e.preventDefault();
    });
});

