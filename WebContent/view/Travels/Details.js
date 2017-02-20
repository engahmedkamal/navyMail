function ResetPassengersData(){
	Passengers_Array = [] ; 
	military_num_array = [] ; 
	Passengers_Number = 0;
}

function LoadOfficersForm(){
	loadFile("Travels/OfficersTravelersRegister.html", "html", ".bgcontent", function() {});
}

function LoadAssistantsForm(){
	loadFile("Travels/AssistantsTravelersRegister.html", "html", ".bgcontent", function() {});
}

function LoadStudentsForm(){
	loadFile("Travels/StudentsTravelersRegister.html", "html", ".bgcontent", function() {});
}

function ShowOfficersForm(){
	var travel_params  = {} ; 
	travel_params.mission_id = current_mission_id ;
	travel_params.ship_id = GlobalShipEntryID ; 
	superConnect(api_source + 'travelers_missions/search/Officers', travel_params, function(obj) {
		console.debug("Returned Officers Object") ; 
		console.debug(obj) ; 
		if (obj == null || Object.size(obj) == 0){
			ResetPassengersData() ; 
			alert("لا يوجد بيانات مسجلة للضباط") ; 
			LoadOfficersForm() ; 
			return ; 
		}
		ResetPassengersData() ; 
		Passengers_Number = Object.size(obj) ; 
		for (var i = 0;i < Object.size(obj) ;i++){	
			Passengers_Array[i]=new Array(10);
			var curObj = obj[i];
			
			military_num_array[i] =  curObj.military_num +'' ;
			Passengers_Array[i][2] = curObj.military_num;
			Passengers_Array[i][3] = curObj.rank_id;
			Passengers_Array[i][4] = curObj.name;
			Passengers_Array[i][5] = curObj.seniority_num;
			Passengers_Array[i][6] = curObj.unit_id;
			Passengers_Array[i][7] = curObj.position;
			Passengers_Array[i][8] = curObj.security_status;
			if(curObj.notes_mission==null||curObj.notes_mission=='')
			{
				console.debug("aaa"+curObj.notes_mission+"aa");
				//Passengers_Array[i][9] = curObj.notes_mission;
			     Passengers_Array[i][9] = "";
			
			}else
				//Passengers_Array[i][9] = '';
				Passengers_Array[i][9] = curObj.notes_mission;
		
		}
			LoadOfficersForm() ; 
	}) ; 
}

function ShowStudentsForm(){
	var travel_params  = {} ; 
	travel_params.mission_id = current_mission_id ; 
	travel_params.ship_id = GlobalShipEntryID ; 
	superConnect(api_source + 'travelers_missions/search/Students', travel_params, function(obj) {
		if (obj == null || Object.size(obj) == 0){
			ResetPassengersData() ; 
			alert("لا يوجد بيانات مسجلة للطلبة") ;
			LoadStudentsForm() ; 
			return ; 
		}
		ResetPassengersData() ; 
		Passengers_Number = Object.size(obj) ; 
		
		for (var i = 0;i < Object.size(obj) ;i++){	
			Passengers_Array[i]=new Array(10);
			var curObj = obj[i];
			console.debug(curObj);
			military_num_array[i] = curObj.military_num +'';
			Passengers_Array[i][2] = curObj.military_num;
			Passengers_Array[i][3] = curObj.rank_id;
			Passengers_Array[i][4] = curObj.name;
			if(curObj.notes_mission==null||curObj.notes_mission=='')
			{
				console.debug("aaa"+curObj.notes_mission+"aa");
				//Passengers_Array[i][9] = curObj.notes_mission;
			     Passengers_Array[i][9] = "";
			
			}else
				//Passengers_Array[i][9] = '';
				Passengers_Array[i][9] = curObj.notes_mission;
			}
			LoadStudentsForm() ; 
	}) ; 
}

function ShowAssistantsForm(){
	var travel_params  = {} ; 
	travel_params.mission_id = current_mission_id ; 
	travel_params.ship_id = GlobalShipEntryID ; 
	superConnect(api_source + 'travelers_missions/search/Assistants', travel_params, function(obj) {
		if (obj == null || Object.size(obj) == 0){
			ResetPassengersData() ; 
			alert("لا يوجد بيانات مسجلة لصف الضباط") ;
			LoadAssistantsForm() ; 
			return ; 
		}
		ResetPassengersData() ; 
		Passengers_Number = Object.size(obj) ; 
		for (var i = 0;i < Object.size(obj) ;i++){	
			Passengers_Array[i]=new Array(10);
			var curObj = obj[i];
			//console.debug("aaa"+curObj.notes_mission+"aa");
			military_num_array[i] = curObj.military_num +'';
			Passengers_Array[i][2] = curObj.military_num;
			Passengers_Array[i][3] = curObj.rank_id;
			Passengers_Array[i][4] = curObj.name;
			Passengers_Array[i][6] = curObj.unit_id;
			Passengers_Array[i][7] = curObj.position;
			Passengers_Array[i][8] = curObj.security_status;
			//Passengers_Array[i][9] = curObj.notes_mission;
			
			if(curObj.notes_mission==null||curObj.notes_mission=='')
			{
				console.debug("aaa"+curObj.notes_mission+"aa");
				//Passengers_Array[i][9] = curObj.notes_mission;
			     Passengers_Array[i][9] = "";
			
			}else
				//Passengers_Array[i][9] = '';
				Passengers_Array[i][9] = curObj.notes_mission;
		}
			LoadAssistantsForm() ; 
	}) ; 
}
function GetShipsOptions(ShipArray) {
    var selectedOptions = "";
    for(var i = 0; i < ShipArray.length - 1; i++) 
    	selectedOptions+= "<option value='"+ShipArray[i]+"' >"+ ship_name[ShipArray[i]]+"</option>";
    console.debug("Selected Ships:"+ selectedOptions) ; 
    return selectedOptions;
}


function initMa2moryaDetails(){
	var  travel = travels_list[travel_index_list];
	ma2morya_type = travel.type_id ; 
	nashat_type = travel.name ; 
	nashat_name = travel.activity_name ; 
	var travel_type = travel.type_id ; 
	var travel_type_tag = (travel_type == 1)? "تدريب مشترك" : "رحلة طلبة" ; 
	var travel_nashat_type = travel.name ; // مرجان او سنة تانيه او تالته وهكذا 
	var travel_nashat_name = travel.activity_name ; // رحلة عام 2015 
	var travel_date_from = travel.date_from; 
	var travel_date_to = travel.date_to ;  
	var travel_related_doc_id = travel.document_id ; 
	var travel_confirmation_no = travel.confirmation_no  ; 
	var travel_confirmation_date = travel.confirmation_date ;  
	var travel_ports = travel.ports ; 
	var travel_ships = travel.ships  ;
	travel_ports = travel_ports.split(' ');
	travel_ships = travel_ships.split(' ');
	
	var traveler_params = {} ;	
	traveler_params.military_num = travel.commander_id ; 
	superConnect(api_source + 'travelers/search', traveler_params, function(traveler_obj) {
		if (traveler_obj == null || Object.size(traveler_obj) == 0)
			return ; 
		
		var travel_commander_name = traveler_obj[0].name ;
		var travel_commander_rank_name = rank_name[traveler_obj[0].rank_id] ; 

		
		var traveler_params = {} ;
		traveler_params.military_num = travel.second_commander_id ; 
		superConnect(api_source + 'travelers/search', traveler_params, function(traveler_obj) {
			if (traveler_obj == null || Object.size(traveler_obj) == 0)
				return ; 
			if( traveler_obj.error!=1){
			var travel_second_commander_name = traveler_obj[0].name ;
			var travel_second_commander_rank_name = rank_name[traveler_obj[0].rank_id] ; 
			}
		$('#ma2morya_type').html(travel_type_tag);
		$('#nashat_type').html(travel_nashat_type);
		$('#nashat_name').html(travel_nashat_name);
		$('#date_from').html(travel_date_from);
		$('#date_to').html(travel_date_to);
		GlobalDateFrom = travel_date_from ;
		GlobalDateTo = travel_date_to ; 
		$('#confirmation_no').html(travel_confirmation_no);
		$('#confirmation_date').html(travel_confirmation_date);
		$('#ma2morya_commander').html(travel_commander_rank_name + '/' + travel_commander_name);
		$('#ma2morya_ships').html(get_ships(travel_ships));
		$('#ma2morya_ports').html(get_ports(travel_ports)); 
		GlobalSelectedShipsIDs = GetShipsOptions(travel_ships) ; 
		$("#ship_type").html(GlobalSelectedShipsIDs);
		GlobalShipEntryID = $("#ship_type").find("option:selected").attr('value');
		if (ma2morya_type == 1)
			$('#ma2morya_second_commander').html(travel_second_commander_rank_name + '/' + travel_second_commander_name);
		else 
			$('#ma2morya_second_commander').html(" ");

		
		if (travel_related_doc_id != null && travel_related_doc_id != '') {
			var link_to = travel_related_doc_id.split("#");
			var links = "<a href='javascript:void(0);' onclick='show_parent_doc(\"/rest/documents/"
					+ link_to[0]
					+ "\")'> "
					+ link_to[1]
					+ " "
					+ link_to[2]
					+ "</a><br/>";
			$("#ma2morya_related_doc").html(links);
		}
		

	});	
	});
}

function get_ports(ports_array){ 
	var ports = "" ; 
	var size = Object.size(ports_array)-1;
	for ( var i = 0; i < size; i++)
		ports += port_name[ports_array[i]]+ "،" ; 
	return ports ; 
}
	
function get_ships(ships_array){ 
	var ships = ""; 
	var size = Object.size(ships_array)-1; 
	for ( var i = 0; i < size; i++)
		ships += ship_name[ships_array[i]]+ "،"  ;
	return ships ; 
}

$(document).ready(function(){
	$('#ship_type').change(function() {
		GlobalShipEntryID = $("#ship_type").find("option:selected").attr('value');
		console.debug("GlobalShipEntryID:" + GlobalShipEntryID) ; 
	}); 
	Traveler_Back_Button = 2 ; 
	initMa2moryaDetails() ; 
	if (ma2morya_type == 1){ // Joint Exercise
		var e2 = document.getElementById("ShowStudentsForm") ;
		e2.style.display = 'none';}
}) ; 