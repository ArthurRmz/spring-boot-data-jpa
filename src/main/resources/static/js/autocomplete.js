$(document).ready(()=>{
	console.log('Ready');
	buscador_productos();
});

const buscador_productos = () =>{
	$("#buscar_producto").autocomplete({
		source: (request,response)=>{
			$.ajax({
				url: "/producto/cargar-productos/"+request.term,
				dataType: "json",
				data: {
					term: request.term,
				},
				success: (data)=>{
					response($.map(data, (item)=>{
						return {
							value: item.id,
							label: item.nombre,
							precio: item.precio
						};
					}));
				},
			});
		},
		select: (event,ui)=>{
			//$("#buscar_producto").val(ui.item.label);
			let id = ui.item.value;
			let precio = ui.item.precio;
			let nombre = ui.item.label;
			
			if(itemsHelper.hasProducto(id)){
				itemsHelper.incrementaCantidad(id,precio);
				return false;
			}
			let linea = $("#plantillaItemsFactura").html();
			linea = linea.replace(/{ID}/g, id);
			linea = linea.replace(/{NOMBRE}/g, nombre);
			linea = linea.replace(/{PRECIO}/g, precio);
			$("#cargarItemProductos tbody").append(linea)
			itemsHelper.calcularImporte(id, precio, 1);
			return false;
		}
	});
	$("form").submit(()=>{
		$("#plantillaItemsFactura").remove();
		return;
	})
};

const itemsHelper = {
	calcularImporte: (id,precio,cantidad)=>{
		$("#total_importe_"+id).html(parseInt(precio)*parseInt(cantidad));
		itemsHelper.calcularGranTotal();
	},
	
	hasProducto: (id)=>{
		let resultado = false;
		let ids = $('input[name="item_id[]"]');
		let i = 0;
		for(i=1;i<ids.length;i++){
			if(parseInt(id) === parseInt(ids[i].value)){
				resultado = true;
				break;
			}
		}
		return resultado;
	},
	
	incrementaCantidad: (id,precio)=>{
		let cantidad = $("#cantidad_"+id).val()?parseInt($("#cantidad_"+id).val()) : 0;
		$("#cantidad_"+id).val(++cantidad);
		itemsHelper.calcularImporte(id,precio,cantidad);
	},
	
	eliminarLineaFactura: (id)=>{
		$("#row_"+id).remove();
		itemsHelper.calcularGranTotal();
	},
	
	calcularGranTotal: ()=>{
		let total = 0;
		
		let importes = $('span[id^="total_importe_"]');
		
		for(i=1;i<importes.length;i++){
			total+=parseInt(importes[i].innerHTML);
		}
		console.log(total);
		
		$("#gran_total").html(total);
	}
};