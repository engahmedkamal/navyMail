function goBack(){
	loadFile("Travels/Home.html", "html", ".bgcontent", function() {});
}

function insert_new_port(){
	var port_data = {} ; 
	port_data.port_name = $("#port_name").val() ;
	var port_obj = {name:port_data.port_name};
	port.push(port_obj) ; 
	superConnect(api_source + 'ports/insert',port_data, function(obj) {	 
		
	showPorts() ; 
	});
}

function showPorts(){
	var inner_html = "" ; 
	var index = 1;
	for ( var row = 0; row < Object.size(port);) {
		var total ="<td style='text-align: center;'>"
		+  index++
		+ "</td >"
		+ "<td style='text-align: center;'>"
		+  port[row++].name
		+ "</td>" ;
		if(row < Object.size(port)){
			total += "<td style='text-align: center;'>"
			+ index++
			+"</td>"
			+ "<td style='text-align: center;'>"
			+  port[row++].name
			+"</td>";
		}
	inner_html += "<tr style='font-size:20px;color:black;text-align:center;'>"
	+ total
	+ "</tr>" ;
	}
	
	$("#ports_table_content").html(inner_html);
}


$(document).ready(function(){
	showPorts() ; 
})  
