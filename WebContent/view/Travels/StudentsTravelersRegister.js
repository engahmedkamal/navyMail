function print_travelers_report(){
	var report_params = {} ; 
	report_params.mission_id = current_mission_id ; 
	report_params.type = 3 ; 
	report_params.ship_id = GlobalShipEntryID ; 
	report_params.mission_type = (ma2morya_type == 1)?(2):(1) ; 
	superConnect(api_source + 'Report/Print', report_params, function(obj) {
		var win = window.open('http://'+host_name+':3000/StudentsReport.pdf', '_blank');
		win.focus();
	});
}

function goBack(){
	if (Traveler_Back_Button == 2)
		loadFile("Travels/Details.html", "html", ".bgcontent", function() {});
	else
		loadFile("Travels/TravelersRegisterForms.html", "html", ".bgcontent", function() {});
}



function Push_Passengers_Array() {
	
	var military_mumber = "<input  type='number' id='m_num' autofocus class='form-control'>";
	var rank_select = "<select id='traveler_rank' class='form-control'>" + passengers_ranks + "</select>";
	var passenger_name = "<input id='traveler_name' class='form-control'>";
	var notes = "<input class='form-control' id='traveler_add_notes' >";
		
	Passengers_Number += 1 ; 
	
	Passengers_Array.push([ , ,military_mumber,rank_select,passenger_name, , , , ,notes]);
	
	ViewPassengersTable();
}

function ViewPassengersTable() {
	var index = 1;
	var NewPassenger = "";
	for ( var row = 0; row < Passengers_Number; row++) {
		var remove_tag = (row != Passengers_Number-1)?'<a target="_blank" href="javascript:void(0)" class="btn btn-danger" onClick=removetraveler('+ row + ')><i class="icon-remove red"></i></a>':"" ; 
		var rank_name_tag =  (row != Passengers_Number-1) ? rank_name[Passengers_Array[row][3]] : Passengers_Array[row][3] ; 
		var notes='';
		if(Passengers_Array[row][9]!='null')
			notes=Passengers_Array[row][9];
		
		NewPassenger += "<tr style='font-size:16px;color:black;text-align:center;'>"
				+ "<td>"
				+ remove_tag
				+ "</td>"
				+ "<td>"
				+ index++
				+ "</td>"
				+ "<td >"
				+ Passengers_Array[row][2]
				+ "</td>"
				+ "<td>"
				+ rank_name_tag
				+ "</td>"
				+ "<td style='white-space: nowrap;text-align: center;'>"
				+ Passengers_Array[row][4]
				+ "</td>"
				+ "<td>"
				+notes
				+ "</td>" 
				+ "</tr>";

	}
	
	
	$("#Passengers_Body").html(NewPassenger);
	$("#m_num").bind('keypress',function(e){
		var index = military_num_array.indexOf($("#m_num").val()) ; //
		if(e.keyCode == 13){
				if(index != -1){
					alert("هذا الرقم العسكرى تم تسجيله من قبل");
					return ;
				}
				var rankid = $("#traveler_rank").find("option:selected").attr('value');
				if (rankid == -1){
					alert("تأكد من إدخال جميع البيانات"); 
					return ;
				}
				military_num_array.push($("#m_num").val()) ;
			
				Passengers_Array[Passengers_Number-1]=[ , ,$("#m_num").val(),rankid,$("#traveler_name").val(), , , , ,$("#traveler_add_notes").val()];
				var data = Passengers_Array[Passengers_Number-1] ;	
				var traverlers_array = {} ; 
				traverlers_array.military_num = data[2] ; 
				traverlers_array.rank_id = data[3] ;
				traverlers_array.name = data[4] ;
				traverlers_array.seniority_num = ' ' ;
				traverlers_array.unit_id = 100 ; // dummy unit id for Students 
				traverlers_array.job_desc = ' ' ;
				traverlers_array.notes = data[9] ;
				traverlers_array.security_status = ' ' ;
				if( $("#traveler_add_notes").val().indexOf('/')!=-1||$("#traveler_add_notes").val().indexOf('0')!=-1)
				{
				alert("عنوان المكاتبة لا يجب أن يحتوى على العلامات / أو . ");
				return;
				}
				superConnect(api_source + 'travelers/insert',traverlers_array, function(obj) {
					var params = {};
					params.traveler_id = data[2] ;
					params.mission_id=current_mission_id; // Need to be changed ( why mission_id default value = 0 ) ( Gives me an error in the console of the eclipse when Inserting new array )
					params.traveler_ship = GlobalShipEntryID;
					params.traveler_position = 1;

					superConnect(api_source + 'travelers_missions/insert',params, function(obj) {
						if (obj == null || obj.error == 1){
							alert("هذه الرقم العسكرى تم تسجيله على سجم إخر في نفس المأمورية") ; 
						}else{
							Push_Passengers_Array();
						}

					});
				});
				
				// add_traverler_to_database(Passengers_Array[Passengers_Number-1]);
				
				// Push_Passengers_Array();
		    }
		
	});	
	$("#traveler_add_notes").bind('keypress',function(e){
		var index = military_num_array.indexOf($("#m_num").val()) ; //
		if(e.keyCode == 13){
				if(index != -1){
					alert("هذا الرقم العسكرى تم تسجيله من قبل");
					return ;
				}
				var rankid = $("#traveler_rank").find("option:selected").attr('value');
				if (rankid == -1){
					alert("تأكد من إدخال جميع البيانات"); 
					return ;
				}
				if( $("#traveler_add_notes").val().indexOf('/')!=-1||$("#traveler_add_notes").val().indexOf('0')!=-1)
				{
				alert("عنوان المكاتبة لا يجب أن يحتوى على العلامات / أو . ");
				return;
				}
				military_num_array.push($("#m_num").val()) ;
			
				Passengers_Array[Passengers_Number-1]=[ , ,$("#m_num").val(),rankid,$("#traveler_name").val(), , , , ,$("#traveler_add_notes").val()];
				var data = Passengers_Array[Passengers_Number-1] ;	
				var traverlers_array = {} ; 
				traverlers_array.military_num = data[2] ; 
				traverlers_array.rank_id = data[3] ;
				traverlers_array.name = data[4] ;
				traverlers_array.seniority_num = ' ' ;
				traverlers_array.unit_id = 100 ; // dummy unit id for Students 
				traverlers_array.job_desc = ' ' ;
				traverlers_array.notes = data[9] ;
				traverlers_array.security_status = ' ' ;
				superConnect(api_source + 'travelers/insert',traverlers_array, function(obj) {
					var params = {};
					params.traveler_id = data[2] ;
					params.mission_id=current_mission_id; // Need to be changed ( why mission_id default value = 0 ) ( Gives me an error in the console of the eclipse when Inserting new array )
					params.traveler_ship = GlobalShipEntryID;
					superConnect(api_source + 'travelers_missions/insert',params, function(obj) {
						if (obj == null || obj.error == 1){
							alert("هذه الرقم العسكرى تم تسجيله على سجم إخر في نفس المأمورية") ; 
						}else{
							Push_Passengers_Array();
						}

					});
				});
				
				// add_traverler_to_database(Passengers_Array[Passengers_Number-1]);
				
				// Push_Passengers_Array();
		    }
		
	});	
	$("#m_num").on("input", function() {
		Search_Traveler_By_Id();
	});
	
	

}	

function removetraveler(index) {
		if (Object.size(Passengers_Array) == 1) {
			remove_traverler_from_database(military_num_array[0]);
			Passengers_Array = [];
			military_num_array=[];
			ViewPassengersTable();
			return;
		}
		Passengers_Array.splice(index, 1);
		remove_traverler_from_database(military_num_array[index]);
		military_num_array.splice(index,1); 
		Passengers_Number-- ; 
		ViewPassengersTable();
	}
	
function Search_Traveler_By_Id()
	{
		var traveler_params = {}; 
		traveler_params.military_num = $("#m_num").val() ; 
		superConnect(api_source + 'travelers/search', traveler_params, function(obj) {
			if (obj == null || Object.size(obj) == 0 || obj.error == 1 ||  obj[0].military_num == 0){
				$("#traveler_rank").html(passengers_ranks) ; 
				$("#traveler_name").val("") ;
				$("#traveler_add_notes").val("") ;
				return ; 
			}
			
			traveler_rank = '<option value='+obj[0].rank_id+' selected>'+ rank_name[obj[0].rank_id] + '</option>' ; 
			traverler_unit = '<option value='+obj[0].unit_id+' selected>'+ internal_units_name[obj[0].unit_id] + '</option>' ;
	
			$("#traveler_rank").html(traveler_rank) ; 
			$("#traveler_name").val(obj[0].name) ;
			$("#traveler_unit").html(traverler_unit) ;
			// $("#traveler_position").val(obj[0].job_description) ; 
			$("#traveler_security_status").val(obj[0].security_status) ;
			$("#traveler_add_notes").val(obj[0].notes) ;
			
			});	
	}
	
function add_traverler_to_database(data){

	}

function remove_traverler_from_database(m_num){
		var params = {};
		params.traveler_id = m_num ;
		params.mission_id = current_mission_id;
		superConnect(api_source + 'travelers_missions/remove', params, function(obj) {
		});
	}	

passengers_units = '<option value="-1">---  الوحدة   ---</option>';
passengers_ranks = '<option value="-1">--الرتبة---</option>';
var global_ship_name = ship_name[GlobalShipEntryID] ; 


$(document).ready(function() {
	size = Object.size(internal_units);
	for ( var i = 0; i < size; i++)
		passengers_units += '<option value="' + internal_units[i].id
				+ '">' + internal_units[i].name + '</option>';
	
	size = Object.size(rank);
	for ( var i = 0; i < size; i++)
		if (rank[i].rank_type == 3)
			passengers_ranks += '<option value="' + rank[i].id
					+ '">' + rank[i].rank_name + '</option>';
	
	if (ma2morya_type == 1){
		$('#Ma2morya_name').html("تسجيل بيانات طاقم مأمورية التدريب المشترك" + " " + "(" + nashat_type +  ")" + " " +  nashat_name + " على متن " + global_ship_name);
		} else {
		$('#Ma2morya_name').html("تسجيل بيانات طاقم مأمورية رحلة الطلبة" +  " (" + nashat_type + ") " +  nashat_name + " على متن " + global_ship_name);
		}

	
	Push_Passengers_Array();

});
