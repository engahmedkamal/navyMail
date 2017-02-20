function init(){
	var script="";
	script+="<div class='row'>"
		+"<br/><div class='col-md-1'></div><div class='col-md-3'><select class='form-control'  id='created_year'></select></div>"
		+"<div class='col-md-4'><select class='form-control'   id='created_month'></select></div>"
		+"<div class='col-md-2'><select class='form-control'   id='created_day'></select></div></div>";
	$('#Sader_units').html(script);
	var d = new Date();
	var cur_day = d.getDate();
	var cur_month = d.getMonth() + 1;
	var cur_year = d.getFullYear();
	var data = set_up_date(cur_day, cur_month, cur_year);
	$("#created_year").html(data.years);
	$("#created_month").html(data.months);
	$("#created_day").html(data.days);	
}

function open_modal_form(){
	var e = document.getElementById("myModal");
	
	if(e.style.display == 'block')
		e.style.display = 'none';
	else
		e.style.display = 'block';
}
window.onload=function(){
	
}
$(document).ready(function() {
	init();
//	  var frm = $('#tarbet_form');
//	  //document.getElementById("docNumber").focus();
//	  //$("#docNumber").focus("");
//	  frm.submit(function (ev) {
//		
//	   });
});