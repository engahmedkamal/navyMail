function goBack(){
	loadFile("Travels/FirstPhaseRegister.html", "html", ".bgcontent", function() {});
}

function ResetPassengersData(){
	Passengers_Array = [] ; 
	military_num_array = [] ; 
	Passengers_Number = 0;
}

function CollectStudentsTravelMainCommanderData(){
	// Collect Main Commander Data
	var MainCommanderData = {} ; 
		MainCommanderData.military_num =$("#travel_qa2ed_ID").val() ; 
		MainCommanderData.rank_id = $("#travel_qa2ed_rank").find("option:selected").attr('value');
		MainCommanderData.unit_id = $("#travel_qa2ed_unit").find("option:selected").attr('value');
		MainCommanderData.name = $("#travel_qa2ed_name").val()  ;
		MainCommanderData.seniority_num = "" ;
		MainCommanderData.job_desc = 1 ;// أساسى
		MainCommanderData.notes = "قائد المأمورية" ;
		MainCommanderData.security_status = 1 ;
	return MainCommanderData ; 
}


function CollectJointExchangeTravelMainCommanderData(){
	// Collect Main Commander Data
	var data = {} ; 
		data.military_num =$("#travel_qa2ed_tashkeel_ID").val() ; 
		data.rank_id = $("#travel_qa2ed_tashkeel_rank").find("option:selected").attr('value');
		data.unit_id = $("#travel_qa2ed_tashkeel_unit").find("option:selected").attr('value');
		data.name = $("#travel_qa2ed_tashkeel_name").val()  ;
		data.seniority_num = "" ;
		data.job_desc = 1 ;// أساسى
		data.notes = "" ;
		data.security_status = 1 ;
	return data ;
}


function CollectJointExchangeTravelSecondCommanderData(){
	// Collect Second Commander Data
	var data = {} ; 
		data.military_num =$("#travel_ra2es_tashkeel_ID").val();
		data.rank_id = $("#travel_ra2es_tashkeel_rank").find("option:selected").attr('value'); 
		data.name = $("#travel_ra2es_tashkeel_name").val();
		data.seniority_num = "" ;
		data.unit_id = $("#travel_ra2es_tashkeel_unit").find("option:selected").attr('value');
		data.job_desc = 1 ;// أساسى
		data.notes = "" ;
		data.security_status = 1 ;
	return data ;
}

function PushIntoPassengersArr(DataObj) {
	
	military_num_array.push(DataObj.military_num) ;
	Passengers_Number += 1 ; 
	Passengers_Array[Passengers_Number-1]=[ , ,DataObj.military_num,DataObj.rank_id,DataObj.name,
	                                        DataObj.seniority_num,DataObj.unit_id,DataObj.job_desc,
	                                        DataObj.security_status,DataObj.notes];
	// We need To Insert Them into Traveler Missions Also 
}

function InsertJointExchangeTravel(){
	if (!CheckAllRegisteredData())
		return;
	
	var MainCommanderData =  CollectJointExchangeTravelMainCommanderData(); 
	var SecondCommanderData = CollectJointExchangeTravelSecondCommanderData(); 
	var TravelerParams = {}; 
	console.debug("Before Inserting Main Commander Data") ; 
	console.debug(MainCommanderData) ; 
	console.debug(SecondCommanderData) ; 
	// ----------------- Problem Here --------------------- 
	superConnect(api_source + 'travelers/insert',MainCommanderData, function(obj) {
		console.debug("Before Inserting Second Commander Data") ; 
		superConnect(api_source + 'travelers/insert',SecondCommanderData, function(obj) {			
			console.debug("Before Inserting Whole Mission Data") ; 
			console.debug(missions_params) ; 
			superConnect(api_source + 'missions/insert', missions_params, function(obj0) {
				current_mission_id = obj0 ;
				console.debug("Current Inserted Mission ID") ; 
				console.debug(current_mission_id) ; 
				TravelerParams.mission_id = current_mission_id ; 
				TravelerParams.traveler_id = MainCommanderData.military_num ; 
				TravelerParams.traveler_ship = $("#travel_qa2ed_tashkeel_ship").find("option:selected").attr('value') ; 
				TravelerParams.traveler_position = 1 ; 	 // أساسى
				TravelerParams.traveler_note ="قائد التشكيل";
				console.debug("Before Inserting Main Commander as a traveler on the current mission") ; 
				superConnect(api_source + 'travelers_missions/insert',TravelerParams, function(obj) {
					TravelerParams.traveler_id = SecondCommanderData.military_num ;
					TravelerParams.traveler_ship = $("#travel_ra2es_tashkeel_ship").find("option:selected").attr('value') ; 
					TravelerParams.traveler_position = 1 ; 	 // أساسى
					TravelerParams.traveler_note ="رئيس أركان التشكيل";
					console.debug("Before Inserting Second Commander as a traveler on the current mission") ; 
					superConnect(api_source + 'travelers_missions/insert',TravelerParams, function(obj) {
					// 	ResetPassengersData() ;
						console.debug("Before Pushing The Two Commander into the Passengers Array") ; 
						PushIntoPassengersArr(MainCommanderData) ; 
						PushIntoPassengersArr(SecondCommanderData) ;
						loadFile("Travels/TravelersRegisterForms.html", "html", ".bgcontent", function() {
						});		
					});
				});
			});
		});
	});

}

function InsertStudentsTravel(){
	if (!CheckAllRegisteredData())
		return;
	// insert commander
	var MainCommanderData = {} ; 
	var TravelerParams = {} ; 
	MainCommanderData = CollectStudentsTravelMainCommanderData() ; 
		superConnect(api_source + 'travelers/insert',MainCommanderData, function(obj) {	
					superConnect(api_source + 'missions/insert', missions_params, function(obj) {
						current_mission_id = obj ;
						TravelerParams.mission_id = current_mission_id ; 
						TravelerParams.traveler_id = MainCommanderData.military_num ; 
						TravelerParams.traveler_ship = $("#travel_qa2ed_ship").find("option:selected").attr('value') ; 
						TravelerParams.traveler_position = 1 ; 	 // أساسى
						superConnect(api_source + 'travelers_missions/insert',TravelerParams, function(obj) {
							ResetPassengersData() ;
							PushIntoPassengersArr(MainCommanderData) ; 
							loadFile("Travels/TravelersRegisterForms.html", "html", ".bgcontent", function() { });
						});
					});
			
		});
	
}

function InsertMissionData(){
	var prev_doc_id = '';
	if ($("#doc_follow div")[0])
		prev_doc_id = $("#doc_follow div")[0].getAttribute("doc_id") ;
	
	missions_params.document_id = prev_doc_id ; 
	missions_params.date_from = $("#travel_date-from").val();
	missions_params.date_to = $("#travel_date-to").val();
	missions_params.confimation_no = $("#tasdeq_number").val();
	missions_params.confimation_date = $("#tasdeq_date").val();
	missions_params.ships = getSelectedOptions(document.getElementById("ships")) ;
	missions_params.ports = getSelectedOptions(document.getElementById("ports")) ;
	
	GlobalDateFrom = missions_params.date_from ;
	GlobalDateTo = missions_params.date_to ; 
	
	if (!CheckAllRegisteredData()){
		alert("تأكد من إدخال جميع البيانات");
		return ;
	}
	
	if (ma2morya_type == 1){ // Joint Exercise 
		missions_params.commander_id = $("#travel_qa2ed_tashkeel_ID").val() ; 
		missions_params.second_commander_id = $("#travel_ra2es_tashkeel_ID").val() ;
		InsertJointExchangeTravel();
	}
	else{	// Students Travel
		missions_params.commander_id = $("#travel_qa2ed_ID").val() ;
		InsertStudentsTravel();
	}
			
}


function CheckAllRegisteredData(){
	if (missions_params.document_id == "" || missions_params.date_from == "" || missions_params.date_to == "" || 
		missions_params.confimation_no == "" || missions_params.confimation_date == "" || 
		missions_params.commander_id == "" || missions_params.ships == "" || missions_params.ports == "") 
			return false ; 
	return true ; 
}

function goto_Travels_Reports() {
	InsertMissionData() ;
	if (CheckAllRegisteredData())
		loadFile("Travels/Reports.html", "html", ".bgcontent", function() {
		});
}

function show_tarbet_button(){
	var tarbet = document.getElementById("wared_tarbet");
	tarbet.style.visibility="visible" ; 	
}

function selcetDocument(id) {
	var d= documents_tarbets[id];
	inner = '<div doc_id="'+ d.id
			+ '" class="alert alert-success"><button type="button" class="close" data-dismiss="alert" onClick="show_tarbet_button()">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + d.doc_number+ " " + d.title + '<br></div>';
	$("#doc_follow").append(inner);
	$("#close_modal").click();
	var tarbet = document.getElementById("wared_tarbet");
	tarbet.style.visibility="hidden" ; 
}

function search_qa2ed_by_id() {
	var traveler_params = {}; 
	traveler_params.military_num = $("#travel_qa2ed_ID").val() ; 
	superConnect(api_source + 'travelers/search', traveler_params, function(qa2ed_obj) {
		if (qa2ed_obj == null || Object.size(qa2ed_obj) == 0 || qa2ed_obj.error == 1 || qa2ed_obj[0].military_num ==0){
			$('#travel_qa2ed_unit').html(internal_units_options);
			$('#travel_qa2ed_rank').html(rank_options);
			$("#travel_qa2ed_name").val("") ; 

			return ; 
		}
		qa2ed_rank = '<option selected value ='+qa2ed_obj[0].rank_id+'>'+rank_name[qa2ed_obj[0].rank_id] + '</option>' ;
		qa2ed_unit = '<option selected value ='+qa2ed_obj[0].unit_id+'>'+internal_units_name[qa2ed_obj[0].unit_id] + '</option>' ;
		$("#travel_qa2ed_name").val(qa2ed_obj[0].name) ; 
		$("#travel_qa2ed_rank").html(qa2ed_rank) ; 
		$("#travel_qa2ed_unit").html(qa2ed_unit) ;
		return qa2ed_obj ;
		});	 
}
			
function search_qa2ed_tashkeel_by_id() {
	var traveler_params = {} ;	
	traveler_params.military_num = $("#travel_qa2ed_tashkeel_ID").val() ; 
	superConnect(api_source + 'travelers/search', traveler_params, function(qa2ed_eltashkeel_obj) {
		if (qa2ed_eltashkeel_obj == null || Object.size(qa2ed_eltashkeel_obj) == 0 || qa2ed_eltashkeel_obj.error == 1 ||  qa2ed_eltashkeel_obj[0].military_num ==0){
			$('#travel_qa2ed_tashkeel_unit').html(internal_units_options);
			$('#travel_qa2ed_tashkeel_rank').html(rank_options);
			$("#travel_qa2ed_tashkeel_name").val("") ; 

			return ; 
		}
		qa2ed_tashkeel_rank = '<option selected value ='+qa2ed_eltashkeel_obj[0].rank_id+'>'+ rank_name[qa2ed_eltashkeel_obj[0].rank_id] + '</option>' ; 
		qa2ed_tashkeel_unit = '<option selected value ='+qa2ed_eltashkeel_obj[0].unit_id+'>'+ internal_units_name[qa2ed_eltashkeel_obj[0].unit_id] + '</option>' ;
		$("#travel_qa2ed_tashkeel_name").val(qa2ed_eltashkeel_obj[0].name) ; 
		$("#travel_qa2ed_tashkeel_rank").html(qa2ed_tashkeel_rank) ; 
		$("#travel_qa2ed_tashkeel_unit").html(qa2ed_tashkeel_unit) ;
		return qa2ed_eltashkeel_obj ;
	});	
 
}
	
function search_ra2es_tashkeel_by_id() {
	var traveler_params = {} ;
	traveler_params.military_num = $("#travel_ra2es_tashkeel_ID").val() ;
	superConnect(api_source + 'travelers/search', traveler_params, function(ra2es_tashkeel_obj) {
		if (ra2es_tashkeel_obj == null || Object.size(ra2es_tashkeel_obj) == 0 || ra2es_tashkeel_obj.error == 1 || ra2es_tashkeel_obj[0].military_num ==0){
			$('#travel_ra2es_tashkeel_unit').html(internal_units_options);
			$('#travel_ra2es_tashkeel_rank').html(rank_options);
			$("#travel_ra2es_tashkeel_name").val("") ; 
			return ; 
		}
		ra2es_tashkeel_rank = '<option selected value ='+ra2es_tashkeel_obj[0].rank_id+'>'+ rank_name[ra2es_tashkeel_obj[0].rank_id] + '</option>' ; 
		ra2es_tashkeel_unit = '<option selected value ='+ra2es_tashkeel_obj[0].unit_id+'>'+ internal_units_name[ra2es_tashkeel_obj[0].unit_id] + '</option>' ;
		$("#travel_ra2es_tashkeel_name").val(ra2es_tashkeel_obj[0].name) ; 
		$("#travel_ra2es_tashkeel_rank").html(ra2es_tashkeel_rank) ; 
		$("#travel_ra2es_tashkeel_unit").html(ra2es_tashkeel_unit) ;
		return ra2es_tashkeel_obj ;
	});

}

function getSelectedOptions(element) {
    var opts = element.options;
    var selectedOptions = "";
    for(var i = 0; i < opts.length; i++) 
         if(opts[i].selected) 
             selectedOptions+= opts[i].value+" ";
    return selectedOptions;
}

function getSelectedOptions2(element) {
    var opts = element.options;
    var selectedOptions = "";
    for(var i = 0; i < opts.length; i++) 
         if(opts[i].selected) 
             selectedOptions+= "<option value='"+opts[i].value+"' >"+ opts[i].text+"</option>";
    return selectedOptions;
}


var internal_units_options = '<option value="">--الوحدة البحرية---</option>';
var rank_options = '<option value="">--الرتبة---</option>';

$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true

	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
	loadFile("../view/tarbet_form.html", "html",
			"#tarbet_panel", function() {
			});
		
	$("#travel_qa2ed_tashkeel_ID").on("input", function() {
		search_qa2ed_tashkeel_by_id(); 
	});
	
	$("#travel_ra2es_tashkeel_ID").on("input", function() {
		search_ra2es_tashkeel_by_id(); 
	});
	
	$("#travel_qa2ed_ID").on("input", function() {
		search_qa2ed_by_id(); 
	});
	
	$('#ships').change(function() {
		var option=getSelectedOptions2(document.getElementById("ships"));
		GlobalSelectedShipsIDs = option ; 
		$("#travel_qa2ed_tashkeel_ship").html(option);
		$("#travel_ra2es_tashkeel_ship").html(option);
		$("#travel_qa2ed_ship").html(option);
		console.debug(option) ; 
	}); 
	
	var ship_options = '';
	var port_options = '';
	size = Object.size(ship);
	for ( var i = 0; i < size; i++)
		ship_options += '<option value="' + ship[i].id
				+ '">' + ship[i].name + '</option>';
	
	size = Object.size(port);
	for ( var i = 0; i < size; i++)
		port_options += '<option value="' + port[i].id
				+ '">' + port[i].name + '</option>';
	
	
	size = Object.size(internal_units);
	for ( var i = 0; i < size; i++)
		internal_units_options += '<option value="' + internal_units[i].id
				+ '">' + internal_units[i].name + '</option>';
	
	size = Object.size(rank);;
	for ( var i = 0; i < size; i++)
		rank_options += '<option value="' + rank[i].id
				+ '">' + rank[i].rank_name + '</option>';
	
	$('#travel_qa2ed_unit').html(internal_units_options);
	$('#travel_qa2ed_tashkeel_unit').html(internal_units_options);
	$('#travel_ra2es_tashkeel_unit').html(internal_units_options);
	
	$('#travel_qa2ed_rank').html(rank_options);
	$('#travel_qa2ed_tashkeel_rank').html(rank_options);
	$('#travel_ra2es_tashkeel_rank').html(rank_options);
	
	$('#ships').html(ship_options);
	$('#ports').html(port_options);
	
	if (ma2morya_type == 1)    // Joint Exercise
	{
		$('#Ma2morya_name').html("تدريب مشترك" + " " + "(" + nashat_type +  ")" + " " +  nashat_name);
		var e = document.getElementById("travel_qa2ed_tashkeel");
		var e1 = document.getElementById("travel_ra2es_tashkeel");
		if(e.style.display == 'block' && e1.style.display == 'block'){
			e.style.display = 'none';
			e1.style.display = 'none';
		}else{
			e.style.display = 'block';
			e1.style.display = 'block';
		}
	}else // Students Travel
	{
		$('#Ma2morya_name').html("رحلة طلبة" +  " (" + nashat_type + ") " +  nashat_name);
		var e = document.getElementById("travel_qa2ed_re7la");
		if(e.style.display == 'block')
		e.style.display = 'none';
		else
		e.style.display = 'block';
	}

	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
	});
	
});
