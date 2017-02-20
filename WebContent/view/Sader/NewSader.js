function set_doc_follow(document) {
	inner = '<div doc_id="'
			+ document.id
			+ '" class="alert alert-success"><button type="button" onclick="remove_doc_follow(this)" class="close" data-dismiss="alert">'
			+ '<i class="icon-remove"></i>'
			+ '</button><strong> ربط على </strong> ' + document.doc_number
			+ " " + document.title + '<br></div>';
	$("#doc_follow").append(inner);
	$("#document_title").val(document.title);
}
function IsNumeric(input){
    return (input - 0) == input;
}
function selcetDocument(id) {
	set_doc_follow(documents_tarbets[id]);
	$("#close_modal").click();
}
function refresh_scanners(){
	var id = $('#folder').find("option:selected").attr('value');
	set_image_container(id, 1 << 25);
}
var scanner_arr = ["HP Scanjet-5590","HP Officejet Pro X476dw","HP digital Sender"];
function getSelectedOptions(element) {

    // you are here because your browser doesn't have the HTML5 selectedOptions
    var opts = element.options;
    var selectedOptions = "";
    for(var i = 0; i < opts.length; i++) {
         if(opts[i].selected) {
             selectedOptions+= opts[i].value+" ";
         }
    }
    return selectedOptions;
}

function create_new_document() {
	var count = $("#doc_follow div").length;
	var prev_doc_id = '';
	var tds = $("#doc_follow div");
	for ( var index = 0; index < count; index++) {
		prev_doc_id += tds[index].getAttribute("doc_id") + ',';
	}
	var params = {};
	console.debug("fuck badea"+$("#created_day").val().length);
	params.folder_id = $("#folder").val();
	params.prev_doc_id = prev_doc_id;
	params.img_no = $("#image_num").val();
	params.security = $("#security").val();
	params.doc_num = $("#doc_num").val();
	params.title = $("#document_title").val();
	params.doc_date = $("#created_year").val() + '-'
			+ ($("#created_month").val().length == 1 ? '0' + $("#created_month").val() : $("#created_month").val()) + "-" 
			+ ($("#created_day").val().length == 1 ? '0' + $("#created_day").val() : $("#created_day").val());
	params.units = $("#units").val();
	params.doc_type = $("#doctype").val();
	params.units_ids = getSelectedOptions(document.getElementById("units_id"));
	params.file_save_num = $("#sub_file_name").val();
	params.sub_file_save_num = $("#file_name").val();
	params.tashira = $("#status").val();
	params.target_id = $("#target").val();
	
	params.change_year =" ";
	params.motaba_year =" ";
	console.debug(params);
//	params.change_year = $("#change_year").val() + '-'
//	+ $("#change_month").val() + "-" + $("#change_day").val();
//	params.motaba_year = $("#motaba_year").val() + '-'
//	+ $("#motaba_month").val() + "-" + $("#motaba_day").val();
	if (params.title.indexOf('/') != -1) {
		alert("عنوان المكاتبة لا يجب أن يحتوى على العلامة / إستخدم - بدلا منها للتواريخ");
		return;
	}
	if( params.doc_num <= 0 ){		
		alert("تأكد من إدخال البيانات صحيحة");
		return;
	}
	
	if( params.title == '' ){
		alert("تأكد من إدخال العنوان صحيح");
		return;
	}
	
	if(!IsNumeric(params.doc_num)){
		alert("تأكد من إدخال الرقم الصادر صحيح");
		return;
	}

	superConnect(api_source + 'SaderDocuments/new', params, function(obj) {
		if(obj.error!=1)
			loadFile("Sader/NewSader.html", "html", ".bgcontent", function() {});
		else
			{
			alert("تم تسجيل هذه المكاتبة من قبل");
			return;
			}
	});

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

	var d = new Date();
	var cur_day = d.getDate();
	var cur_month = d.getMonth() + 1;
	var cur_year = d.getFullYear();

	var data=set_up_date(cur_day, cur_month, cur_year);

	$("#created_year").html(data.years);
	$("#created_month").html(data.months);
	$("#created_day").html(data.days);
//	$("#motaba_year").html(data.years);
//	$("#motaba_month").html(data.months);
//	$("#motaba_day").html(data.days);
//	$("#change_year").html(data.years);
//	$("#change_month").html(data.months);
//	$("#change_day").html(data.days);
	var units_options = '<option value="">---  الجهة الصادرة   ---</option>';


	size = Object.size(global_saderunits);
	for ( var i = 0; i < size; i++)
		units_options += '<option value="' + global_saderunits[i].id
				+ '">' + global_saderunits[i].name + '</option>';

	$('#units').html(units_options);
	var units_options = '<option value="">---  الجهة الصادرة   ---</option>';

	console.debug(global_submain);
	size = Object.size(global_saderunits);
	for ( var i = 0; i < size; i++)
		units_options += '<option value="' + global_saderunits[i].id
				+ '">' + global_saderunits[i].name + '</option>';

	$('#units_id').html(units_options);
	
	var saved_file_option='<option value="">---  ملف الحفظ الرئيسى   ---</option>';
	size = Object.size(global_submain);
	for ( var i = 0; i < size; i++)
		saved_file_option += '<option value="' + global_submain[i].id
				+ '">' + global_submain[i].name + '</option>';

	$('#file_name').html(saved_file_option);
	var sub_saved_file_option='<option value="">---    ملف الحفظ الفرعى  ---</option>';
	size = Object.size(global_sub_br);
	for ( var i = 0; i < size; i++)
		sub_saved_file_option += '<option value="' + global_sub_br[i].id
				+ '">' + global_sub_br[i].name + '</option>';

	$('#sub_file_name').html(sub_saved_file_option);
	$('#image_num').on("input",function() {
		var id = $('#folder').find("option:selected")
				.attr('value');
		var value = $(this).val();
		if (value == "" || value == null)
			value = 1 << 25;
		set_image_container(id, value);
	});
	superConnect(api_source + 'master/scanner',params,function(obj) {
		var size = Object.size(obj);
		$("#image_num").val(size);
	});
	set_image_container(selected_scanner, 1 << 25);
	$('#folder').change(function() {
		selected_scanner = $(this).find("option:selected").attr('value');
		set_image_container(selected_scanner, 1 << 25);
	});
	loadFile("tarbet_form.html", "html", "#tarbet_panel",function() {});
	
	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
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
			units_options = '<option value="" selected> -- الجهة  -- </option><option value="0">داخلي</option><option value="1">خارجي</option>';
		$('#doctype').html(units_options);
	});
	var tashira ='<option value="' +"-1"
	+ '">' + "التوقيع" + '</option>';;
	size = Object.size(global_tashira);
	for ( var i = 1; i < size; i++)
		tashira += '<option value="' + global_tashira[i].id
				+ '">' + global_tashira[i].name + '</option>';

	$('#status').html(tashira);
});


