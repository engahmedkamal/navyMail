function goBack(){
	loadFile("Travels/MissionsSearchForm.html", "html", ".bgcontent", function() {});
}


var i = 0 ; 
var inner_html = "";

function inner_table(travels){
		
		if (i ==  Object.size(travels)){
			$("#travels_table_content").html(inner_html);
			return; 
		}
		
		var  travel = travels[i];
		var travel_type = travel.type_id ; 
		var travel_type_tag = (travel_type == 1)? "تدريب مشترك" : "رحلة طلبة" ; 
		var travel_nashat_type = travel.name ; 
		var travel_date_from = travel.date_from; 
		var travel_date_to = travel.date_to ;  
		var travel_related_doc_id = travel.document_id ; 
		var traveler_params = {} ;	
		traveler_params.military_num = travel.commander_id ; 
		superConnect(api_source + 'travelers/search', traveler_params, function(traveler_obj) {	

			if (traveler_obj == null || Object.size(traveler_obj) == 0){
				alert("لا يوجد مسافرين ");
				return ; 
			}
			var travel_commander_name = traveler_obj[0].name ;
			var travel_commander_rank_name = rank_name[traveler_obj[0].rank_id] ; 
			if (travel_related_doc_id != null && travel_related_doc_id != '') {
				var link_to = travel_related_doc_id.split("#");
				var links = "<a href='javascript:void(0);' onclick='show_parent_doc(\"/rest/documents/"
						+ link_to[0]
						+ "\")'> "
						+ link_to[1]
						+ " "
						+ link_to[2]
						+ "</a><br/>";
			}
			
			inner_html += "<tr onclick='show_travel(" + i + ',' + travel.id + ")'>"
			+ "<td style='text-align: center;'>"
			+  travel_type_tag 
			+ "</td >"
			+ "<td style='text-align: center;'>"
			+  travel_nashat_type
			+ "</td>"
			+ "<td style='text-align: center;'>"
			+ travel_date_from
			+ "</td>"
			+ "<td style='text-align: center;'>"	
			+ travel_date_to
			+ "</td>"
			+ "<td style='text-align: center;'>"
			+ travel_commander_rank_name + '/' + travel_commander_name
			+ "</td>"
			+ "<td style='text-align: center;'>"
			+  links
			+ "</td>"
			+ "</tr>" ;
			i++ ; 
			inner_table(travels);
			});		
	

}
function show_travel(travel_index,travel_id){
	travel_index_list = travel_index ; 
	current_mission_id = travel_id ;
	loadFile("Travels/Details.html", "html", ".bgcontent", function() {});
}

$(document).ready(function() {
	inner_table(travels_list)
});