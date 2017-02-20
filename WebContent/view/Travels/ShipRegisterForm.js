function goBack(){
	loadFile("Travels/Home.html", "html", ".bgcontent", function() {});
}

function insert_new_ship(){
	var ship_data = {} ; 
	ship_data.ship_name = $("#ship_name").val() ;
	ship_data.ship_type = $("#ship_type").val() ;
	ship_data.unit_id = $("#ships_options").find("option:selected").attr('value');
	var ship_obj = {name:ship_data.ship_name,ship_type:ship_data.ship_type,unit_id:ship_data.unit_id};
	console.debug(ship_obj) ; 
	ship.push(ship_obj) ; 
	superConnect(api_source + 'ships/insert',ship_data, function(obj) {	 
		// loadFile("Travels/ShipRegisterForm.html", "html", ".bgcontent", function() {});
	showShips() ; 
	});
}

var inner_html = "" ; 
function showShips(){
	var index = 1;
	for ( var row = 0; row < Object.size(ship); row++) {
	inner_html += "<tr style='font-size:20px;color:black;text-align:center;'>"
	+ "<td style='font-size:20px;color:black;text-align:center;'>"
	+  index++
	+ "</td >"
	+ "<td style='font-size:20px;color:black;text-align:center;'>"
	+  ship[row].name
	+ "</td>"
	+ "<td style='font-size:20px;color:black;text-align:center;'>"
	+  ship[row].ship_type
	+ "</td>"
	+ "<td style='font-size:20px;color:black;text-align:center;'>"	
	+  internal_units_name[ship[row].unit_id]
	+ "</td>"
	+ "</tr>" ;
	}
	$("#ships_table_content").html(inner_html);
}


$(document).ready(function(){
	
	var internal_units_options = '<option value="">--الوحدة البحرية---</option>';
	size = Object.size(internal_units);
	for ( var i = 0; i < size; i++)
		internal_units_options += '<option value="' + internal_units[i].id
				+ '">' + internal_units[i].name + '</option>';
	
	$('#ships_options').html(internal_units_options);
	showShips() ; 

})  
