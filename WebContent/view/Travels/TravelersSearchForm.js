function goBack(){
	loadFile("Travels/MainSearchForm.html", "html", ".bgcontent", function() {});
}

function editTraveler(){
	
}

function start_search_for_travelers() {
	var traveler_params = {}; 
	traveler_params.military_num = $("#traveler_ID").val() ;
	superConnect(api_source + 'travelers/allMissions', traveler_params, function(travels_obj) {
		if (travels_obj && travels_obj.length > 0) {
			travels_list = travels_obj; 
			loadFile("Travels/List.html", "html", ".bgcontent", function() {});
		} else if (travels_obj
				&& travels_obj.length == 0) {
			$(".bgcontent").html("<p style='text-align: center;'>لا توجد سفريات</p>");
		}
	});		
}

function search_traveler_by_id() {
	var traveler_params = {}; 
	travelers_units = '<option value="-1">---  الوحدة   ---</option>';
	travelers_ranks = '<option value="-1">--الرتبة---</option>';
	travelers_sec_status = '<option value="-1">--الموقف الأمنى---</option>';
	
	size = Object.size(internal_units);
	for ( var i = 0; i < size; i++)
		travelers_units += '<option value="' + internal_units[i].id
				+ '">' + internal_units[i].name + '</option>';
	
	size = Object.size(rank);
	for ( var i = 0; i < size; i++)
		travelers_ranks += '<option value="' + rank[i].id
				+ '">' + rank[i].rank_name + '</option>';
	
		$("#traveler_rank").html(travelers_ranks) ;
		$("#traveler_unit").html(travelers_units) ;
		$("#traveler_sec_status").html(travelers_sec_status) ;
		$("#traveler_name").val("");
	/////////////////////////////////////////////////	
	var traveler_params = {}; 
	traveler_params.military_num = $("#traveler_ID").val() ;
	superConnect(api_source + 'travelers/search', traveler_params, function(obj) {
		if (obj == null || Object.size(obj) == 0 || obj.error == 1)
			return ; 
		traveler_rank = '<option>'+ rank_name[obj[0].rank_id] + '</option>' ;
		traveler_unit = '<option>'+ internal_units_name[obj[0].unit_id] + '</option>' ;
		
		if (obj[0].security_status == 1)
			traveler_sec_status = '<option selected value ='+1+'>'+ 'لا يوجد' + '</option>' ; 
		else if (obj[0].security_status == 2)
			traveler_sec_status = '<option selected value ='+2+'>'+ 'يوجد' + '</option>' ;
		else 
			traveler_sec_status = '<option selected value ='+0+'>'+ ' ' + '</option>' ; 
		
		$("#traveler_name").val(obj[0].name) ; 
		
		$("#traveler_rank").html(traveler_rank) ; 
		$("#traveler_unit").html(traveler_unit) ;
		$("#traveler_sec_status").html(traveler_sec_status) ;
		});			
}

$(document).ready(function() {
	
	travelers_units = '<option value="-1">---  الوحدة   ---</option>';
	travelers_ranks = '<option value="-1">--الرتبة---</option>';
	travelers_sec_status = '<option value="-1">--الموقف الأمنى---</option>';
	
	size = Object.size(internal_units);
	for ( var i = 0; i < size; i++)
		travelers_units += '<option value="' + internal_units[i].id
				+ '">' + internal_units[i].name + '</option>';
	
	size = Object.size(rank);
	for ( var i = 0; i < size; i++)
		travelers_ranks += '<option value="' + rank[i].id
				+ '">' + rank[i].rank_name + '</option>';
	
		$("#traveler_rank").html(travelers_ranks) ;
		$("#traveler_unit").html(travelers_units) ;
		$("#traveler_sec_status").html(travelers_sec_status) ;
	
		$("#traveler_ID").on("input", function() {
			search_traveler_by_id(); 
		});
});