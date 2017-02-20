function IsNumeric(input){
    return (input - 0) == input;
}

function create_new_document() {
	var count = $("#doc_follow div").length;

	var prev_doc_id = '';
	var tds = $("#doc_follow div");
	for ( var index = 0; index < count; index++) 
		prev_doc_id += tds[index].getAttribute("doc_id") + ',';
	
	var found = 0;
	for ( var index1 = 0; index1 < count; index1++) 
		for ( var index2 = index1 + 1 ; index2 < count; index2++) 
			if( tds[index2].getAttribute("doc_id") == tds[index1].getAttribute("doc_id") )
				found = 1;
	
	if(found){
		alert("تربيط علي نفس المكاتبة أكثر من مرة ");
		return;
	}
	
	var params = {};

	params.folder_id = $("#folder").val();
	params.prev_doc_id = prev_doc_id;
	params.img_no = $("#image_num").val();
	params.title =  $.trim($("#document_title").val());
	params.doc_num = $("#doc_num").val();
	params.created_at = $("#created_year").val() + '-' + ($("#created_month").val().length == 1 ? '0' + $("#created_month").val() : $("#created_month").val()) + "-" 
	+ ($("#created_day").val().length == 1 ? '0' + $("#created_day").val() : $("#created_day").val());
	params.doc_out_num = $("#sader_num").val();
	params.doc_out_date = $("#sader_year").val() + '-' + $("#sader_month").val() + "-" + $("#sader_day").val();
	params.unit_id = $("#units").val();
	params.doc_type = $("#doctype").val();
	params.target_id = $("#target").val();
	params.status_id = $("#status").val();

	if (params.title.indexOf('/') != -1||params.title.indexOf('.')!=-1) {
		alert("عنوان المكاتبة لا يجب أن يحتوى على العلامات / أو . ");
		return;
	}
	
	if( params.img_no <= 0 ){
		alert("تأكد من إدخال صور للمكاتبة");
		return;
	}

	if( params.doc_num <= 0 || params.target_id == 0 || params.status_id == 0 ){		
		alert("تأكد من إدخال البيانات صحيحة");
		return;
	}
	
	if( params.title == '' ){
		alert("تأكد من إدخال العنوان صحيح");
		return;
	}
	
	if(!IsNumeric(params.doc_num)){
		alert("تأكد من إدخال الرقم الوارد صحيح");
		return;
	}

	var target = params.target_id ;
	var status = params.status_id ;

	if( ( target == 3 || target == 4 ) && ( status == 1 || status == 2 ) ){
		alert("الموقف غير صحيح");
		return;
	}	
	
	

	document.getElementById("newDoc").disabled=true;
	superConnect(api_source + 'documents/new', params, function(obj) {
		console.debug("test"+obj);
		if(obj.error!=1){
			loadFile("new_wared_document.html", "html", ".bgcontent", function() {});
			setStatus();
		}
		else
			{
			alert("تم تسجيل هذه المكاتبة من قبل");
			document.getElementById("newDoc").disabled=false;
			return;
			}
	});
}

function refresh_scanners(){
	var id = $('#folder').find("option:selected").attr('value');
	set_image_container(id, 1 << 25);
}

function set_doc_follow(document) {
	inner = '<div doc_id="'
			+ document.id
			+ '" class="alert alert-success"><button type="button"  class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>' + '</button><strong> ربط على </strong> ' 
			+ document.doc_number + " " + document.title + '<br></div>';
	$("#doc_follow").append(inner);
	$("#document_title").val(document.title);
	
}

function selcetDocument(id) {
	set_doc_follow(documents_tarbets[id]);
	$("#close_modal").click();
}

function set_image_container(folder_id, max_img) {
	var params = {};
	params.folder_id = folder_id;
	superConnect(api_source + 'master/scanner',params,function(obj) {
		var img_html = '<li style="float: right;"> <a href="=====" data-lightbox="documnet" class="cboxElement" title="mytitle"> ' +
			 '<img  style="width: 200px; height: 230px;" src="====="> <div class="text"> '
				+ '<div class="inner"> #### </div></div> </a>  </li>';
		$(".ace-thumbnails li").remove();
		var size = Object.size(obj);
		var end = Math.min(size, max_img);
		for ( var i = 0; i < end; i++) {
			var path = obj[i].replace(first_part_of_image_path, server_path);
			var name = obj[i].substring(obj[i].lastIndexOf('/') + 1);
			var images = img_html.replace(/=====/g, path+ "?cache=" + Math.random()).replace('####', name);
			$(".ace-thumbnails").append(images);
		}
		$("#image_num").val(end);

	});

}

var scanner_arr = ["HP Scanjet-5590","HP Officejet Pro X476dw","HP digital Sender"];

$(document).ready(function() {
	
	var scanner_option ='';
	for ( var i = 1; i <= 3; i++){
		if(selected_scanner==i)
			scanner_option+="<option value='"+i+"' selected>" + scanner_arr[i-1]+"</option>";
		else
			scanner_option+="<option value='"+i+"'>" + scanner_arr[i-1]+"</option>";
	}
	
	$('#folder').html(scanner_option);
	
	var params = {};
	params.folder_id = 1;
	superConnect(api_source + 'master/scanner',params,function(obj) {
		var size = Object.size(obj);
		$("#image_num").val(size);
	});
	
	var d = new Date();
	var cur_day = d.getDate();
	var cur_month = d.getMonth() + 1;
	var cur_year = d.getFullYear();

	var data = set_up_date(cur_day, cur_month, cur_year);
	
	$("#created_year").html(data.years);
	$("#created_month").html(data.months);
	$("#created_day").html(data.days);
	$("#sader_year").html(data.years);
	$("#sader_month").html(data.months);
	$("#sader_day").html(data.days);

	
	target_options = '<option value="0">---  عرض على  ---</option>';
	units_options = '<option value="0">---  الجهة الصادرة   ---</option>';
	var status_options = '';

	var size = Object.size(global_targets);
	for ( var i = 0; i < size; i++)
		target_options += '<option value="'
				+ global_targets[i].id + '">' + global_targets[i].name + '</option>';

	size = Object.size(global_units);
	for ( var i = 0; i < size; i++)
		units_options += '<option value="' + global_units[i].id
				+ '">' + global_units[i].name + '</option>';

	size = Object.size(global_status);
	for ( var i = 0; i < size; i++){
		status_options += '<option value="'
				+ global_status[i].id + '">' + global_status[i].name + '</option>';
	}

	$('#target').html(target_options);
	$('#units').html(units_options);
	$('#status').html(status_options);
	units_options = '<option value=""> -- الجهة  -- </option><option value="0">داخلي</option><option value="1">خارجي</option>';
	$('#doctype').html(units_options);

	$('#units').change(function() {
		var id = $(this).find("option:selected").attr('value');
		var val = global_units_type[id].unit_type;
		if(val==0)
			units_options = '<option value="0" selected>داخلي</option><option value="1">خارجي</option>';
		else if(val==1) 
			units_options = '<option value="0">داخلي</option><option value="1" selected>خارجي</option>';
		else
			units_options = '<option value="" selected> -- الجهة  -- </option><option value="0">داخلي</option><option value="1">خارجي</option>';
		$('#doctype').html(units_options);
	});

	set_image_container(selected_scanner, 1 << 25);
	$('#folder').change(function() {
		selected_scanner = $(this).find("option:selected").attr('value');
		set_image_container(selected_scanner, 1 << 25);
	});

	$('#target').change(function() {
		var id = $(this).find("option:selected").attr('value');
		if(id == 4){
			size = Object.size(global_status);
			var soptions = '<option value="'
				+ global_status[size - 2 ].id + '">' + global_status[size - 2].name + '</option>';
			$('#status').html(soptions);
		}else{
			$('#status').html(status_options);
		}
	});
	
	$('#image_num').on("input",function() {
		var id = $('#folder').find("option:selected").attr('value');
		var value = $(this).val();
		if (value == "" || value == null)
			value = 1 << 25;
		set_image_container(id, value);
	});
	
	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
	});
	
	loadFile("tarbet_form.html", "html", "#tarbet_panel",function() {});
});