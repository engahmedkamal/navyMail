function goBack() {
	loadFile("Travels/Home.html", "html", ".bgcontent", function() {
	});
}

function editTraveler() {
	var traveler_params = {};
	traveler_params.military_num = $("#traveler_ID").val() ;
	traveler_params.rank_id = $("#traveler_rank").val() ;
	traveler_params.name = $("#traveler_name").val() ;
	traveler_params.seniority_num = $("#traveler_seniority").val() ;
	traveler_params.unit_id = $("#traveler_unit").val() ;
	traveler_params.job_desc = $("#traveler_ID").val() ;
	traveler_params.notes = $("#traveler_notes").val();
	traveler_params.security_status = $("#traveler_sec_status").val() ;
	superConnect(api_source + 'travelers/update', traveler_params, function(obj) {
			loadFile("Travels/EditTraveler.html", "html", ".bgcontent", function() {});	
  });
}

// function start_search_for_travelers() {
// var traveler_params = {};
// traveler_params.military_num = $("#traveler_ID").val() ;
// superConnect(api_source + 'travelers/allMissions', traveler_params,
// function(travels_obj) {
// if (travels_obj && travels_obj.length > 0) {
// travels_list = travels_obj;
// loadFile("Travels/List.html", "html", ".bgcontent", function() {});
// } else if (travels_obj
// && travels_obj.length == 0) {
// $(".bgcontent").html("<p style='text-align: center;'>لا توجد سفريات</p>");
// }
// });
// }

function search_traveler_by_id() {
	var traveler_params = {};
	travelers_units = '<option value="-1">---  الوحدة   ---</option>';
	travelers_ranks = '<option value="-1">--الرتبة---</option>';
	travelers_sec_status = '<option value="-1">--الموقف الأمنى---</option>';

	$("#traveler_rank").html(travelers_ranks);
	$("#traveler_unit").html(travelers_units);
	$("#traveler_sec_status").html(travelers_sec_status);
	$("#traveler_name").val("");
	$("#traveler_notes").val("");
	$("#traveler_seniority").val("");
	traveler_params.military_num = $("#traveler_ID").val();
	superConnect(api_source + 'travelers/search', traveler_params,
			function(obj) {
				if (obj == null || Object.size(obj) == 0 || obj.error == 1)
					return;
				// traveler_rank = '<option>'+ rank_name[obj[0].rank_id] +
				// '</option>' ;
				// traveler_unit = '<option>'+
				// internal_units_name[obj[0].unit_id] + '</option>' ;
				var size = Object.size(internal_units);

				for (var i = 0; i < size; i++) {
					if (internal_units[i].id == obj[0].unit_id) {
						travelers_units += '<option selected value="'
								+ internal_units[i].id + '">'
								+ internal_units[i].name + '</option>';
						continue;
					}
					// console.debug(internal_units[i].id+" "+obj[0].unit_id);
					travelers_units += '<option value="' + internal_units[i].id
							+ '">' + internal_units[i].name + '</option>';
				}
				size = Object.size(rank);
				for (var i = 0; i < size; i++) {
					if (rank[i].id == obj[0].rank_id) {
					travelers_ranks += '<option selected value="'
								+ rank[i].id + '">'
								+ rank[i].rank_name + '</option>';
						continue;
					}
					travelers_ranks += '<option value="' + rank[i].id + '">'
							+ rank[i].rank_name + '</option>';
				}
				if (obj[0].security_status == 1)
					traveler_sec_status = '<option selected value =' + 1 + '>'
							+ 'لا يوجد' + '</option>'
							+ '<option value="2">---  يوجد   ---</option>';
				else if (obj[0].security_status == 2)
					traveler_sec_status = '<option selected value =' + 2 + '>'
							+ 'يوجد' + '</option>'
							+ '<option value="1">---  لا يوجد   ---</option>';
				else
					traveler_sec_status = '<option selected value =' + 0 + '>'
							+ ' ' + '</option>';
				$("#traveler_name").val(obj[0].name);
				$("#traveler_notes").val(obj[0].notes);
				$("#traveler_seniority").val(obj[0].seniority_num);
				$("#traveler_rank").html(travelers_ranks);
				$("#traveler_unit").html(travelers_units);
				$("#traveler_sec_status").html(traveler_sec_status);
				
			});
}

$(document).ready(
				function() {

					travelers_units = '<option value="-1">---  الوحدة   ---</option>';
					travelers_ranks = '<option value="-1">--الرتبة---</option>';
					travelers_sec_status = '<option value="-1">--الموقف الأمنى---</option>';

					size = Object.size(internal_units);
					for (var i = 0; i < size; i++)
						travelers_units += '<option value="'
								+ internal_units[i].id + '">'
								+ internal_units[i].name + '</option>';

					size = Object.size(rank);
					for (var i = 0; i < size; i++)
						travelers_ranks += '<option value="' + rank[i].id
								+ '">' + rank[i].rank_name + '</option>';

					$("#traveler_rank").html(travelers_ranks);
					$("#traveler_unit").html(travelers_units);
					$("#traveler_sec_status").html(travelers_sec_status);

					$("#traveler_ID").on("input", function() {
						search_traveler_by_id();
					});
				});