$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true
		
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
    });


function goBack(){
	loadFile("Travels/MainSearchForm.html", "html", ".bgcontent", function() {});
}

function start_search_for_travels() {
	
	var travelsParams = {};
	travelsParams.type_id = $('#ma2morya_type').val();
	travelsParams.date_from = $("#date-from ").val();
	travelsParams.date_to = $("#date-to").val() ;

	superConnect(api_source + 'missions/search', travelsParams, function(travels_obj) {
		if (travels_obj && travels_obj.length > 0) {
			travels_list = travels_obj; 
			loadFile("Travels/List.html", "html", ".bgcontent", function() {});
		} else if (travels_obj
				&& travels_obj.length == 0) {
			$(".bgcontent").html("<p style='text-align: center;'>لا توجد سفريات</p>");
		}
	});	
}

$(document).ready(function() {
$('#ma2morya_type').change(function(){
	id = $(this).find("option:selected").attr('value');
	missions_params.type_id = id ;
});	
});
