function print(n){
	 var params = {};
	 var d=new Date();
	 var date_to;
	 var date_from;
	  var datebymonth="1"+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
	  var datebyweek;
	  if(d.getDate()>7)
	    	datebyweek=(d.getDate()-7)+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
	else
		{
		var last = new Date(d.getTime() - (7 * 24 * 60 * 60 * 1000));
		var day =last.getDate();
		var month=last.getMonth()+1;
		var year=last.getFullYear();
		datebyweek=day +"-"+month+"-"+year;
		}
	  if(n==1){
	     date_to=$("#date-to").val();
	     date_from=$("#date-from").val();
	  }else if(n==2){
		  date_to=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
		  date_from=datebymonth;
	  }else{
		  date_to=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
		  date_from=datebyweek;
	  }
	    params.date_from= date_from;
	    params.date_to= date_to;
    
	   params.unit=$("#units").val();
	   params.type= $("#type").val();
	superConnect(api_source + 'motb3a_report/print' , params, function(documents_obj) {
		var win = window.open('http://'+host_name+':3000/MOTB3A.pdf', '_blank');
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