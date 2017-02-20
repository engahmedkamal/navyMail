function save_edited_document() {
	var params = {};
	params.id = document_details.id;
	params.prev_doc_id = document_details.prev_doc_id;
	
	params.title = document_details.title;
	params.doc_num = document_details.doc_number;

	params.created_at = document_details.created_at;

	params.doc_out_num = document_details.doc_out_number;
	params.doc_out_date = document_details.doc_out_date;

	params.unit_id = document_details.unit_id;
	params.doc_type = document_details.doc_type;
	params.target_id = document_details.target_id;
	params.status_id = document_details.status_id;
	params.notes = document_details.notes;
	params.tashira = getSelectedOptions(document.getElementById("motb3a"));
	params.tashira_quad = getSelectedOptions(document.getElementById("motb3aQuad"));
	params.resp_unit = getSelectedOptions(document.getElementById("Sader_units"));
	params.trans_out_num = $("#trans_number").val();
	params.trans_out_date = $("#trans_year").val() + '-' + $("#trans_month").val()
			+ "-" + $("#trans_day").val();
	
	if(params.trans_out_num == 0){
		params.trans_out_num = '';
		params.trans_out_date= '';
	}	
	if( params.title == '' ){
		alert("تأكد من إدخال العنوان صحيح");
		return;
	}

	var target = params.target_id ;
	var status = params.status_id ;	
	document.getElementById("editDoc").style.visibility = "hidden";
	superConnect(api_source + 'documents/edit', params, function(obj) {
			document_details = obj[0];
			loadFile("details.html", "html", ".bgcontent", function() {});
  });
}
///////////////////////////////////////
function init_doc_details() {
	console.debug(document_details);
	$("#title").attr("placeholder", document_details.title).val(
			document_details.title).focus().blur();
	$("#trans_number").attr("placeholder", document_details.trans_out_num)
	.val(document_details.trans_out_num).focus().blur();
	var motb3a_options='';
	var sader_units_options = "";
	var sader={};
	sader=document_details.resp_unit;
	var z=0;
	size = Object.size(global_units);
	for ( var i = 0; i < size; i++){
		if(global_units[i].id==document_details.units_resp[z]){
			sader_units_options += '<option value=' + global_units[i].id + ' selected="selected">'
			+ global_units[i].name + '</option>';
			z++;
			continue;
		}
		sader_units_options += '<option value=' + global_units[i].id + '>'
		+ global_units[i].name + '</option>';
	}
	size = Object.size(global_Motb3aTashira);
	console.debug(global_Motb3aTashira);
	var x=0;
	for ( var i = 0; i < size; i++){
		if(document_details.tashira[x] == global_Motb3aTashira[i].id ){
			motb3a_options += '<option value=' + global_Motb3aTashira[i].id + ' selected="selected">'
			+ global_Motb3aTashira[i].name + '</option>';
			x++;
			continue;
		}
		motb3a_options += '<option value=' + global_Motb3aTashira[i].id + '>'
		+ global_Motb3aTashira[i].name+ '</option>';
	}
	$('#motb3a').html(motb3a_options);
	var e=0;
	var motb3a_options2="";
	for ( var i = 0; i < size; i++){
		if(document_details.tashira_qaud[e] == global_Motb3aTashira[i].id ){
			motb3a_options2 += '<option value=' + global_Motb3aTashira[i].id + ' selected="selected">'
			+ global_Motb3aTashira[i].name + '</option>';
			e++;
			continue;
		}
		motb3a_options2 += '<option value=' + global_Motb3aTashira[i].id + '>'
		+ global_Motb3aTashira[i].name+ '</option>';
	}
	$('#motb3aQuad').html(motb3a_options2);
	$('#Sader_units').html(sader_units_options);
	if (document_details.trans_out_date != null) {
		var date = document_details.trans_out_date.split("-");
		var data = set_up_date(date[2],date[1], date[0]);
		$("#trans_year").html(data.years);
		$("#trans_month").html(data.months);
		$("#trans_day").html(data.days);
	}else{
		var d = new Date();
		var cur_day = d.getDate();
		var cur_month = d.getMonth() + 1;
		var cur_year = d.getFullYear();
		var data = set_up_date(cur_day, cur_month, cur_year);
		$("#trans_year").html(data.years);
		$("#trans_month").html(data.months);
		$("#trans_day").html(data.days);
	}
}
$(document).ready(function() {
	init_doc_details();
	$('.chosen_tashira').chosen({
		no_results_text : "لا يوجد",
		allow_single_deselect : true
	});
});
function getSelectedOptions(element) {
    var opts = element.options;
    var selectedOptions = "";
    var check=false;
    for(var i = 0; i < opts.length; i++) 
         if(opts[i].selected) {
             selectedOptions+= opts[i].value+" ";
             check=true;
         }
    if(check)
    return selectedOptions;
    else
    	return "-1";
}
