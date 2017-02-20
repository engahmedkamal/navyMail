function print(){
	 var date_to;
	 var date_from;
	 var params = {};
	   	 params.unit=$("#units").val();   
	   	 date_to=$("#date-to").val();
	     date_from=$("#date-from").val();
	     params.date_from= date_from;
	     params.date_to= date_to;
	superConnect(api_source + 'units_report/print' , params, function(documents_obj) {
		var win = window.open('http://'+host_name+':3000/units.pdf', '_blank');
		win.focus()
	 });
}
$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true
	});
	var units_options = '<option value="">---  الجهة الصادرة   ---</option>';
	size = Object.size(global_units);
	for(var i=0; i< size;i++ )
		units_options += '<option value="' + global_units[i].id + '">' + global_units[i].name + '</option>';
	$('#units').html(units_options);
	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
	});
});