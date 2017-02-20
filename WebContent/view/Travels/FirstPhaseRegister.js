function goBack(){
	loadFile("Travels/Home.html", "html", ".bgcontent", function() {});
}

$(document).ready(function() {
	
	$('.date-picker').datepicker( {
		autoclose : true,
		language : 'ar-EG',
		isRTL : true

	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});

 
	var tadreeb_missions_options ;   
	var students_missions_options  ;
	size = Object.size(missions_type);
	for ( var i = 0; i < size; i++) {
		if (missions_type[i].type == 1) {
			tadreeb_missions_options += '<option value="' + missions_type[i].id + '">'+ missions_type[i].name + '</option>';
		}else {
			students_missions_options += '<option value="' + missions_type[i].id + '">'+ missions_type[i].name + '</option>';
		}
	}

	$('#nashat_type').html(tadreeb_missions_options);
	ma2morya_type = 1 ; // joint Exercise 

	var id =  1 ; // joint Exercise 
	missions_params.type_id = id ;

	$('#ma2morya_type').change(function(){
		id = $(this).find("option:selected").attr('value');
		missions_params.type_id = id ; 
		ma2morya_type = $("#ma2morya_type").val();
		if (ma2morya_type == 1)
			$('#nashat_type').html(tadreeb_missions_options);
		else
			$('#nashat_type').html(students_missions_options);
	});
});

function go_to_register_second_phase() {
	loadFile("Travels/SecondPhaseRegister.html","html", ".bgcontent", function(){});
}

function create_new_ma2morya() {
	nashat_name = $("#nashat_name").val();
	var id =$("#nashat_type").val(); 
	nashat_type = missions_type_name[id] ;
	missions_params.activity_name = $("#nashat_name").val(); 
	missions_params.name = missions_type_name[$("#nashat_type").val()] ;
	go_to_register_second_phase() ;
}
	