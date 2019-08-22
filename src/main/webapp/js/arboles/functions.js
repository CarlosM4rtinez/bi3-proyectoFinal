// Una vez cargado la pagina ejecutamos las acciones
$(document).ready(function () {
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
                $("#rta").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function (data) {
                $("#rta").html(data[0]);
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
            },
            error: function (e) {
                $("#rta").html('<p align=left>' + e.responseText + '</p>');
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
                $("#rtaArb").html();
                $("#rta").html('<div class="lds-ring"><div></div><div></div><div></div><div></div></div>');
            },
            success: function (data) {
                $("#rtaArb").html();
                $("#rta").html(data[0]);
            },
            error: function (e) {
                $("#rta").html('<p align=left><b>Datos del archivo:</b><br><br>' + e.responseText + '</p>');
            }
        });
        e.preventDefault();
    });

});

