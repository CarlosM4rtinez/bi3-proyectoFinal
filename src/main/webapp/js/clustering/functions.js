/* 
 * Carlos Martinez
 */

// Una vez cargado la pagina ejecutamos las acciones
$(document)
		.ready(

				function() {
					// Consumimos los servicios por ajax de jquery
					$(document)
							.on(
									'submit',
									'#form-mineria',
									function(e) {
										jQuery
												.ajax({
													url : $(this)
															.attr("action"),
													data : new FormData(
															$(this)[0]),
													cache : false,
													contentType : false,
													processData : false,
													method : $(this).attr(
															"method"),
													type : $(this).attr(
															"method"),
													enctype : $(this).attr(
															"enctype"),
													beforeSend : function(data) {
														// Deshabilitamos el
														// boton submit
														$("#btnform-mineria")
																.prop(
																		"disabled",
																		true);
														$(
																"#atributos, #clusters, #analisis")
																.html(
																		'<center><div class="lds-ring"><div></div><div></div><div></div><div></div></div></center>');

													},
													success : function(data) {

														$("#atributos").html(
																data[0]);

														$("#clusters").html(
																data[1]);

														$("#analisis").html(
																data[2]);

														graficar(data[3]);

														// Volvemos habilitar el
														// boton
														$("#btnform-mineria")
																.prop(
																		"disabled",
																		false);
													},
													error : function(e) {

														$(
																"#atributos, #clusters, #analisis")
																.html(
																		'<p align=left>'
																				+ e
																				+ '</p>');

													}
												});
										e.preventDefault();
									}

							);

					$(document).ready();

					$(document)
							.on(
									'change',
									'#file',
									function(e) {
										jQuery
												.ajax({
													url : $(this)
															.data("action"),
													data : new FormData(
															$("#form-mineria")[0]),
													cache : false,
													contentType : false,
													processData : false,
													method : $("#form-mineria")
															.attr("method"),
													type : $("#form-mineria")
															.attr("method"),
													enctype : $("#form-mineria")
															.attr("enctype"),
													beforeSend : function(data) {
														$("#dataset")
																.html(
																		'<center><div class="lds-ring"><div></div><div></div><div></div><div></div></div></center>');
													},
													success : function(data) {
														$("#dataset")
																.html(data);
													},
													error : function(e) {
														$("#dataset")
																.html(
																		'<p align=left><b>Datos:</b><br><br>'
																				+ e.responseText
																				+ '</p>');

													}
												});
										e.preventDefault();
									});

				}

		);

function graficar(data) {
							
							$("#graficaClusters").html('');
					
					        dataset = JSON.parse(data);
					        	/*{
					            "children": [{"Name":"Olives","Count":4319},
					                {"Name":"Tea","Count":4159},
					                {"Name":"Mashed Potatoes","Count":2583},
					                {"Name":"Boiled Potatoes","Count":2074},
					                {"Name":"Milk","Count":1894},
					                {"Name":"Chicken Salad","Count":1809},
					                {"Name":"Vanilla Ice Cream","Count":1713},
					                {"Name":"Cocoa","Count":1636},
					                {"Name":"Lettuce Salad","Count":1566},
					                {"Name":"Lobster Salad","Count":1511},
					                {"Name":"Chocolate","Count":1489},
					                {"Name":"Apple Pie","Count":1487},
					                {"Name":"Orange Juice","Count":1423},
					                {"Name":"American Cheese","Count":1372},
					                {"Name":"Green Peas","Count":1341},
					                {"Name":"Assorted Cakes","Count":1331},
					                {"Name":"French Fried Potatoes","Count":1328},
					                {"Name":"Potato Salad","Count":1306},
					                {"Name":"Baked Potatoes","Count":1293},
					                {"Name":"Roquefort","Count":1273},
					                {"Name":"Stewed Prunes","Count":1268}]
					        };*/
					        // JSON.parse(data)+""

					        var diameter = 600;
					        var color = d3.scaleOrdinal(d3.schemeCategory20);

					        var bubble = d3.pack(dataset)
					            .size([diameter, diameter])
					            .padding(1.5);

					        var svg = d3.select("#graficaClusters")
					            .append("svg")
					            .attr("width", diameter)
					            .attr("height", diameter)
					            .attr("class", "bubble");

					        var nodes = d3.hierarchy(dataset)
					            .sum(function(d) { return d.Count; });

					        var node = svg.selectAll(".node")
					            .data(bubble(nodes).descendants())
					            .enter()
					            .filter(function(d){
					                return  !d.children
					            })
					            .append("g")
					            .attr("class", "node")
					            .attr("transform", function(d) {
					                return "translate(" + d.x + "," + d.y + ")";
					            });

					        node.append("title")
					            .text(function(d) {
					                return d.Name + ": " + d.Count;
					            });

					        node.append("circle")
					            .attr("r", function(d) {
					                return d.r;
					            })
					            .style("fill", function(d,i) {
					                return color(i);
					            });

					        node.append("text")
					            .attr("dy", ".2em")
					            .style("text-anchor", "middle")
					            .text(function(d) {
					                return d.data.Name.substring(0, d.r / 3);
					            })
					            .attr("font-family", "sans-serif")
					            .attr("font-size", function(d){
					                return d.r/5;
					            })
					            .attr("fill", "white");

					        node.append("text")
					            .attr("dy", "1.3em")
					            .style("text-anchor", "middle")
					            .text(function(d) {
					                return d.data.Count;
					            })
					            .attr("font-family",  "Gill Sans", "Gill Sans MT")
					            .attr("font-size", function(d){
					                return d.r/5;
					            })
					            .attr("fill", "white");

					        d3.select(self.frameElement)
					            .style("height", diameter + "px");

							
						}