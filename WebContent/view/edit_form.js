function set_image_container(res) {
	
	var images = '';
	
	var inner1 = '<div class="inner"><span> #### </span>'
			+ '<div class="buttons-controls"><a href="#" class="btnRemoveImg"> <i class="icon-remove red"></i> </a>'
			+ '<a href="#edit_img_modal" role="button" data-toggle="modal" id="btnEditImage" onclick="edit_img_panel(this)">'
			+ '<i class="icon-pencil"></i> </a><a href="#edit_img_modal" role="button" data-toggle="modal" onclick="btnInsertImg(this);"> '
			+ '<i class="icon-reply icon-only"></i></a> </div></div>';
	
	var inner2 = '<div class="inner"><span> #### </span>'
		+ '<div class="buttons-controls">'
		+ '<a href="#edit_img_modal" role="button" data-toggle="modal" id="btnEditImage" onclick="edit_img_panel(this)">'
		+ '<i class="icon-pencil"></i> </a><a href="#edit_img_modal" role="button" data-toggle="modal" onclick="btnInsertImg(this);"> '
		+ '<i class="icon-reply icon-only"></i></a> </div></div>';

	var inner ;
	
	if( res.length == 1 )
		inner = inner2;
	else
		inner = inner1;
	
	var img_html = '<li style="float: right;"> <div>  <img  style="width: 240px; height: 290px;" src="====="> <div class="text"> '
			+ inner + ' </div>  </li>';

	for ( var i = 0; i < res.length; i++) {
		images += img_html.replace(/=====/g,
				res[i].replace(first_part_of_image_path, server_path)+"?cache=" + Math.random());
		images = images.replace('####', 'i0' + (i + 1) + '.jpg');
	}
	
	$("#images_container").append(images);

}

function selcetDocument(id) {
	set_doc_follow(documents_tarbets[id]);
	$("#close_modal").click();
}

function save_edited_document() {
	var count = $("#doc_follow div").length;

	var prev_doc_id = '';
	var tds = $("#doc_follow div");
	for ( var index = 0; index < count; index++) {
		prev_doc_id += tds[index].getAttribute("doc_id") + ',';
	}
	
	var found = 0;
	for ( var index1 = 0; index1 < count; index1++) {
		for ( var index2 = index1 + 1 ; index2 < count; index2++) {
			if(tds[index2].getAttribute("doc_id")==tds[index1].getAttribute("doc_id"))
				found = 1;
		}
	}

	for ( var index1 = 0; index1 < count; index1++) {
		if(document_details.id == tds[index1].getAttribute("doc_id"))
			found = 1;
	}
	
	if(found){
		alert("تربيط علي نفس المكاتبة أكثر من مرة ");
		return;
	}
	
	var params = {};
	params.id = document_details.id;
	params.prev_doc_id = prev_doc_id;
	
	params.title = $.trim($("#title").val());
	params.doc_num = $("#doc_number").val();

	params.created_at = $("#created_year").val() + '-'
			+ $("#created_month").val() + "-" + $("#created_day").val();

	params.doc_out_num = $("#sader_number").val();
	params.doc_out_date = $("#sader_year").val() + '-'
			+ $("#sader_month").val() + "-" + $("#sader_day").val();

	params.unit_id = global_units[$("#units").val()].id;
	params.doc_type = $("#doc_type").val();
	params.target_id = $("#target").val();
	params.status_id = $("#status").val();
	params.notes = $("#notes").val();
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
		

	
	
	if( params.doc_num <= 0){		
		alert("تأكد من إدخال رقم وارد صحيح");
		return;
	}
	
	if( params.title == '' ){
		alert("تأكد من إدخال العنوان صحيح");
		return;
	}

	var target = params.target_id ;
	var status = params.status_id ;

	if( ( target == 3 || target == 4 ) && ( status == 1 || status == 2 ) ){
		alert("الموقف غير صحيح");
		return;
	}	
	
	document.getElementById("editDoc").style.visibility = "hidden";
	params.status_id = 4;
	superConnect(api_source + 'documents/edit', params, function(obj) {
		params.status_id = $("#status").val();
		superConnect(api_source + 'documents/edit', params, function(obj2) {
			document_details = obj2[0];
			loadFile("details.html", "html", ".bgcontent", function() {});
			setStatus();
		});
  });
}

function init_doc_follow() {

	if (document_details.parents == null || document_details.parents == '')
		return;

	var details = document_details.parents;

	var data = details.split('#');


	var inner="";
	var size = data.length;
	var index=0;
	while(index<size){
		inner += '<div doc_id="' + data[index++]
			+ '" class="alert alert-success"><button type="button"  class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + data[index++] + " " + data[index++]
			+ '<br></div>';
	}

	$("#doc_follow").append(inner);

}

function set_doc_follow(document) {
	inner = '<div doc_id="'
			+ document.id
			+ '" class="alert alert-success"><button type="button"  class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + document.doc_number
			+ " " + document.title + '<br></div>';
	$("#doc_follow").append(inner);
}

function init_doc_details() {

	init_doc_follow();
	set_image_container(document_details.imgs, document_details.title);
	console.debug(document_details);

	$("#title").attr("placeholder", document_details.title).val(
			document_details.title).focus().blur();

	$("#doc_number").attr("placeholder", document_details.doc_number).val(
			document_details.doc_number).focus().blur();

	var date = document_details.created_at.split("-");
	var data = set_up_date(date[2], date[1], date[0]);
	$("#created_year").html(data.years);
	$("#created_month").html(data.months);
	$("#created_day").html(data.days);

	$("#notes").html(document_details.notes);
	$("#sader_number").attr("placeholder", document_details.doc_out_number)
			.val(document_details.doc_out_number).focus().blur();
	
	$("#trans_number").attr("placeholder", document_details.trans_out_num)
	.val(document_details.trans_out_num).focus().blur();

	if (document_details.doc_out_date != null) {
		var date = document_details.doc_out_date.split("-");
		var data = set_up_date(date[2], date[1], date[0]);
		$("#sader_year").html(data.years);
		$("#sader_month").html(data.months);
		$("#sader_day").html(data.days);
	}

	var units_options = "";
	var target_options ="";
	var status_options = "";
	var motb3a_options='';
	var sader_units_options = "";
	var size = Object.size(global_targets);
	for ( var i = 0; i < size; i++){
		if(document_details.target_id==global_targets[i].id){
			target_options += '<option value=' + global_targets[i].id + ' selected="selected">'
			+ global_targets[i].name + '</option>';
				continue;
		}
		target_options += '<option value=' + global_targets[i].id + '>'
		+ global_targets[i].name + '</option>';
		
	}

	size = Object.size(global_units);
	for ( var i = 0; i < size; i++){
		if(global_units[i].id ==document_details.unit_id ){
			units_options += '<option value=' + i + ' selected="selected">'
			+ global_units[i].name + '</option>';
			continue;
		}
		units_options += '<option value=' + i + '>'
		+ global_units[i].name + '</option>';
		
	}
	var sader={};
	sader=document_details.resp_unit;
	
	var z=0;
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

	size = Object.size(global_status);
	for ( var i = 0; i < size; i++){
		if(document_details.status_id == global_status[i].id ){
			status_options += '<option value=' + global_status[i].id + ' selected="selected">'
			+ global_status[i].name + '</option>';
			continue;
		}
		status_options += '<option value=' + global_status[i].id + '>'
		+ global_status[i].name + '</option>';
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
	$('#units').html(units_options);
	$('#target').html(target_options);
	$('#status').html(status_options);
	$('#motb3a').html(motb3a_options);
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

	var doc_type_options = '';
	
	if (document_details.doc_type == 0)
		doc_type_options = '<option value="0" selected="selected">داخلي</option> <option value="1">خارجي</option> ';
	else
		doc_type_options = '<option value="0" >داخلي</option> <option value="1" selected="selected">خارجي</option> ';
		
	$('#doc_type').html(doc_type_options);

}

$(document).ready(function() {
	loadFile("tarbet_form.html", "html", "#tarbet_panel", function() {});
	loadFile("edit_img.html", "html", "#edit_panel", function() {});

	init_doc_details();

	$(".btnRemoveImg").click( function(event) {
		event.preventDefault();
		
		img_li = $(this).parent().parent().parent().parent()
				.parent();
		img_loc = $(img_li).find("img").attr("src");
		img_loc = img_loc.replace(img_loc.substring(img_loc
				.indexOf("?"), img_loc.length), "");
		
		
		
		if (confirm("هل انت متاكد من المسح؟؟")) {
			var params = {};
			params.id = document_details.id;
			params.title = document_details.doc_number + ' ' + document_details.title;
			params.path = img_loc;
			superConnect(api_source + 'image/delete', params, function(obj) {
			  	document_details = obj[0];
				loadFile("edit_form.html", "html", ".bgcontent", function() {});
			});
		}
	});

	$('.chosen_tashira').chosen({
		no_results_text : "لا يوجد",
		allow_single_deselect : true
	});

	$('#units').change(function() {
		var id = $(this).find("option:selected").attr('value');
		var val = global_units[id].unit_type;
		if(val==0)
			units_options = '<option value="0" selected>داخلي</option><option value="1">خارجي</option>';
		else if(val==1) 
			units_options = '<option value="0">داخلي</option><option value="1" selected>خارجي</option>';
		else
			units_options = '<option value="" selected> -- الجهة  -- </option><option value="0">داخلي</option><option value="1">خارجي</option>';

		$('#doc_type').html(units_options);
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
