$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true
		
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
    });
