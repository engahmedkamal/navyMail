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
		console.debug(obj) ;
		if (obj == null || Object.size(obj) == 0){
			ResetPassengersData() ; 
			alert("لا يوجد بيانات مسجلة للطلبة") ;
			LoadStudentsForm() ; 
			return ; 
		}
		ResetPassengersData() ; 
		console.debug("sjahfjklhgjklashgjlksd");
		Passengers_Number = Object.size(obj) ; 
		military_num_array = [];
		for (var i = 0;i < Object.size(obj) ;i++){	
			Passengers_Array[i]=new Array(10);
			var curObj = obj[i];
			console.debug(curObj);
			military_num_array[i] = curObj.military_num +'';
			Passengers_Array[i][2] = curObj.military_num;
			Passengers_Array[i][3] = curObj.rank_id;
			Passengers_Array[i][4] = curObj.name;
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
		military_num_array = [];
		for (var i = 0;i < Object.size(obj) ;i++){	
			Passengers_Array[i]=new Array(10);
			var curObj = obj[i];
			military_num_array[i] = curObj.military_num +'';
			Passengers_Array[i][2] = curObj.military_num;
			Passengers_Array[i][3] = curObj.rank_id;
			Passengers_Array[i][4] = curObj.name;
			Passengers_Array[i][6] = curObj.unit_id;
			Passengers_Array[i][7] = curObj.position;
			Passengers_Array[i][8] = curObj.security_status;
			Passengers_Array[i][9] = curObj.notes_mission;
		}
			LoadAssistantsForm() ; 
	}) ; 
}

$(document).ready(function() {
	// do nothing 
	$("#ship_type").html(GlobalSelectedShipsIDs);
	console.debug("GlobalSelectedShipsIDs:" + GlobalSelectedShipsIDs) ; 
	GlobalShipEntryID = $("#ship_type").find("option:selected").attr('value');
	
	$('#ship_type').change(function() {
		GlobalShipEntryID = $("#ship_type").find("option:selected").attr('value');
		console.debug("GlobalShipEntryID:" + GlobalShipEntryID) ; 
	}); 

	Traveler_Back_Button = 1 ; 
	if (ma2morya_type == 1){ // Joint Exercise
		var e2 = document.getElementById("ShowStudentsForm") ;
		e2.style.display = 'none';}
});