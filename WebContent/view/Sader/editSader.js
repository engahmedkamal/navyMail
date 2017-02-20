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
		if(documentSader_details.id == tds[index1].getAttribute("doc_id"))
			found = 1;
	}
	
	if(found){
		alert("تربيط علي نفس المكاتبة أكثر من مرة ");
		return;
	}
	var params = {};
	params.id = documentSader_details.id;
	params.prev_doc_id = prev_doc_id;
	params.title = $.trim($("#title").val());
	params.security_type = $("#security").val();
	params.doc_num = $("#doc_num").val();
	params.title = $("#document_title").val();
	params.doc_date = $("#created_year").val() + '-'
			+ $("#created_month").val() + "-" + $("#created_day").val();
	params.units = $("#units").val();
	params.doc_type = $("#doctype").val();
	params.unit_ids = getSelectedOptions(document.getElementById("units_id"));
	
	params.file_save_num = $("#sub_file_name").val();
	params.sub_file_save_num = $("#file_name").val();
	params.tashira = $("#status").val();
	params.target_id = $("#target").val();
	params.change_year =" ";
	params.motaba_year =" ";
	if( params.doc_num <= 0){		
		alert("تأكد من إدخال رقم وارد صحيح");
		return;
	}
	
	if( params.title == '' ){
		alert("تأكد من إدخال العنوان صحيح");
		return;
	}
	console.debug(params);
	superConnect(api_source + 'SaderDocuments/edit', params, function(obj) {
		documentSader_details=obj[0];
		console.debug("asdasdkkjjkkk------"+params);
			loadFile("Sader/sader_details.html", "html", ".bgcontent", function() {});	
  });

}




function init_doc_follow() {

	if (documentSader_details.parents == null || documentSader_details.parents == '')
		return;

	var details = documentSader_details.parents;

	var data = details.split('#');


	var inner="";
	var size = data.length;
	var index=0;
	while(index<size){
		inner += '<div doc_id="' + data[index++]
			+ '" class="alert alert-success"><button type="button" onclick="remove_doc_follow(this)" class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + data[index++] + " " + data[index++]
			+ '<br></div>';
	}

	$("#doc_follow").append(inner);

}
function set_doc_follow(document) {
	inner = '<div doc_id="'
			+ document.id
			+ '" class="alert alert-success"><button type="button" onclick="remove_doc_follow(this)" class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + document.doc_number
			+ " " + document.title + '<br></div>';
	$("#doc_follow").append(inner);
}
function init_doc_details() {

	init_doc_follow();
	set_image_container(documentSader_details.imgs, documentSader_details.title);

	$("#document_title").attr("placeholder", documentSader_details.title).val(
			documentSader_details.title).focus().blur();
	$("#doc_num").attr("placeholder", documentSader_details.doc_number).val(
			documentSader_details.doc_number).focus().blur();

	var date = documentSader_details.doc_date.split("-");
	var data = set_up_date(date[2], date[1], date[0]);

	$("#created_year").html(data.years);
	$("#created_month").html(data.months);
	$("#created_day").html(data.days);
	var doc_type_options="";
	switch (documentSader_details.security_type){
		case 0:
			doc_type_options = '<option value="0" selected="selected">سرى</option> <option value="1">سرى للغاية</option> <option value="2">سرى جدا</option><option value="3">سرى و شخصى</option>';
			break;
		case 1:
			doc_type_options = '<option value="0"">سرى</option> <option value="1"  selected="selected>سرى للغاية</option> <option value="2">سرى جدا</option><option value="3">سرى و شخصى</option>';
			break;
		case 2:
			doc_type_options = '<option value="0">سرى</option> <option value="1">سرى للغاية</option> <option value="2"  selected="selected">سرى جدا</option><option value="3">سرى و شخصى</option>';
			break;
		case 3:
			doc_type_options = '<option value="0">سرى</option> <option value="1">سرى للغاية</option> <option value="2">سرى جدا</option><option value="3"  selected="selected">سرى و شخصى</option>';
			break;
		default: doc_type_options = '<option value="0"">سرى</option> <option value="1">سرى للغاية</option> <option value="2">سرى جدا</option><option value="3">سرى و شخصى</option>';
		break;
	}
	var doc_type="";
	if (documentSader_details.doc_type == 2)
		doc_type = '<option value="2" selected="selected">داخلي</option> <option value="1">خارجي</option> ';
	else
		doc_type = '<option value="2" >داخلي</option> <option value="1" selected="selected">خارجي</option> ';
	var target_type="";
	if (documentSader_details.target_id == 2)
		target_type = '<option value="0">التأشيرة</option><option value="2" selected="selected">السيد رئيس الأركان</option> <option value="1">السيد القائد</option> ';
	else if (documentSader_details.target_id == 1)
		target_type = '<option value="0">التأشيرة</option><option value="2">السيد رئيس الأركان</option> <option value="1" selected="selected">السيد القائد</option> ';
	else
		target_type = '<option value="0" selected="selected">التأشيرة</option><option value="2" >السيد رئيس الأركان</option> <option value="1">السيد القائد</option> ';
	$("#doctype").html(doc_type);
	$("#security").html(doc_type_options);
	$("#target").html(target_type);
	var units_option="";
	size = Object.size(global_saderunits);
	for ( var i = 0; i < size; i++){
		if(documentSader_details.unit_id==global_saderunits[i].id){
			units_option += '<option value=' + global_saderunits[i].id + ' selected="selected">'
			+ global_saderunits[i].name + '</option>';
				continue;
		}
		units_option += '<option value="' + global_saderunits[i].id
				+ '">' + global_saderunits[i].name + '</option>';
	}
	$('#units').html(units_option);
	var x=0;
	var units_options="";
	size = Object.size(global_saderunits);
	for ( var i = 0; i < size; i++){
		if(documentSader_details.units_sader[x]==global_saderunits[i].id){
			units_options += '<option value=' + global_saderunits[i].id + ' selected="selected">'
			+ global_saderunits[i].name + '</option>';
			x++;
				continue;
		}
		units_options += '<option value="' + global_saderunits[i].id
				+ '">' + global_saderunits[i].name + '</option>';
		console.debug(global_saderunits[i].id);
	}
	
	$('#units_id').html(units_options);
	var saved_file_option='<option value="">---  ملف الحفظ الرئيسى   ---</option>';
	size = Object.size(global_submain);
	for ( var i = 0; i < size; i++){
		if(documentSader_details.sub_file_save_num==global_submain[i].id){
			saved_file_option += '<option value=' + global_submain[i].id + ' selected="selected">'
			+ global_submain[i].name + '</option>';
				continue;
		}
		saved_file_option += '<option value="' + global_submain[i].id
				+ '">' + global_submain[i].name + '</option>';
	}

	$('#file_name').html(saved_file_option);
	var sub_saved_file_option='<option value="">---    ملف الحفظ الفرعى  ---</option>';
	size = Object.size(global_sub_br);
	
	for ( var i = 0; i < size; i++){
		
		if(documentSader_details.file_save_num==global_sub_br[i].id){
			sub_saved_file_option += '<option value=' + global_sub_br[i].id + ' selected="selected">'
			+ global_sub_br[i].name + '</option>';
				continue;
		}
		sub_saved_file_option += '<option value="' + global_sub_br[i].id
				+ '">' + global_sub_br[i].name + '</option>';
	}

	$('#sub_file_name').html(sub_saved_file_option);
	var tashira ='<option value="' +"-1"
	+ '">' + "التوقيع" + '</option>';;
	size = Object.size(global_tashira);
	for ( var i = 1; i < size; i++){
		if(documentSader_details.tashira==global_tashira[i].id){
			tashira += '<option value=' + global_tashira[i].id + ' selected="selected">'
			+ global_tashira[i].name + '</option>';
				continue;
		}
		tashira += '<option value="' + global_tashira[i].id
				+ '">' + global_tashira[i].name + '</option>';
	}
	$('#status').html(tashira);
	
}
$(document).ready(function() {
	loadFile("tarbet_form.html", "html", "#tarbet_panel", function() {});
	loadFile("Sader/edit_img.html", "html", "#edit_panel", function() {});
	$('#print').sidr();
	init_doc_details();
	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
	});
	$(".btnRemoveImg").click( function(event) {
		event.preventDefault();
		
		img_li = $(this).parent().parent().parent().parent()
				.parent();
		img_loc = $(img_li).find("img").attr("src");
		img_loc = img_loc.replace(img_loc.substring(img_loc
				.indexOf("?"), img_loc.length), "");
		if (confirm("هل انت متاكد من المسح؟؟")) {
			var params = {};
			params.id = documentSader_details.id;
			params.path = img_loc;
			superConnect(api_source + 'image/deleteSader', params, function(obj) {
				documentSader_details = obj[0];
				loadFile("Sader/editSader.html", "html", ".bgcontent", function() {});
			});
		}
	});
	$('#units').change(function() {
		var id = $(this).find("option:selected").attr('value');
		//console.debug(global_saderunits) ; 
		var val = global_saderunits_unit_type[id];
		if(val==2)
			units_options = '<option value="2" selected>داخلي</option><option value="1">خارجي</option>';
		else if(val==1) 
			units_options = '<option value="2">داخلي</option><option value="1" selected>خارجي</option>';
		else
			units_options = '<option value="-1" selected> -- الجهة  -- </option><option value="0">داخلي</option><option value="1">خارجي</option>';
		$('#doctype').html(units_options);
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
