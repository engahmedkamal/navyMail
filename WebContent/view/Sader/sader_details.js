function edit() {
	loadFile("Sader/editSader.html", "html", ".bgcontent", function() {
	});
}
var inner_html;


function goback(){
	if(Object.size(stack)==1){
		documentSader_list = stack.pop();
		loadFile("Sader/sader_details.html", "html", ".bgcontent", function() {
		});
		
	}else if( details_back_tracker == come_from_search_list ) {
		loadFile ("Sader/document_sader_search.html", "html", ".bgcontent", function(){});
	}else{
		loadFile ("Sader/search_sader_form.html", "html", ".bgcontent", function(){});
	}
}

function load_doc_imgs( doc_title) {
	var images = '<ul class="row ace-thumbnails">';

	var img_html = '<li> <a href="=====" target="_blank"  title="mytitle">'
			+ ' <img  style="width: 100%; height: 70%;" src="====="> <div class="text">  </div> </a> </li>';
	console.debug(documentSader_details);
	var a = documentSader_details.imgs;
	
	for ( var i = 0; i < a.length; i++) {
		images += img_html.replace(
				/=====/g,
				a[i].replace(first_part_of_image_path, server_path)
						+ "?cache=" + Math.random()).replace("mytitle",
				doc_title);
	}
	
	$("#document-images").append(images);
}

//function print() {
//	var params = {};
//	params.id = documentSader_details.id;
//	console.debug("Hello"+documentSader_list.id+params.id);
//	superConnect(api_source + 'SaderDocuments/print', params, function(obj) {
//		var win = window.open('http://'+host_name+':3000/tmp.pdf', '_blank');
//		win.focus();
//		
//	});
//
//}
function print() {
	var params = {};
	params.id = documentSader_details.id;
	console.debug("Hello"+documentSader_list.id+params.id);
	superConnect(api_source + 'SaderDocuments/print', params, function(obj) {
		var win = window.open('http://'+host_name+':3000/tmp.pdf', '_blank');
		win.focus();
		
	});

}
function init_doc_details() {
	
	$('#target_id').html(global_targets_name[documentSader_details.target_id]);
	$('#status_id').html(global_status_name[documentSader_details.status_id]);
	$('#file_save_number').html(global_submain_name[documentSader_details.sub_file_save_num]);
	$('#sub_file_save_number').html(global_sub_br_name[documentSader_details.file_save_num]);
	
	if(documentSader_details.unit_id!= null && documentSader_details.unit_id > 0)
		$('#unit_id').html(global_saderunits_name[documentSader_details.unit_id]);

	if (documentSader_details.doc_type == 2)
		$('#doc_type').html("داخلي");
	else
		$('#doc_type').html("خارجي");
	
	
	switch (documentSader_details.security_type){
		case 0:
			$('#security_id').html("سرى");
			break;
		case 1:
			$('#security_id').html("سرى للغاية");
			break;
		case 2:
			$('#security_id').html("سرى جدا");
			break;
		case 3:
			$('#security_id').html("سرى و شخصى");
			break;
			default: $('#security_id').html(" ");
	}
	
	$("#title").html(documentSader_details.title);
	$("#doc_number").html(documentSader_details.doc_number);
	console.debug("a7aaa"+documentSader_details.doc_number);
	$("#doc_date").html(documentSader_details.doc_date);
	$("#finish_date").html(documentSader_details.finish_date);
	$("#start_date").html(documentSader_details.start_date);
	$("#tashira").html(global_tashira_name[documentSader_details.tashira]);


	var links="";
	if (documentSader_details.parents != null && documentSader_details.parents != '') {
		var link_to = documentSader_details.parents.split("#");
		var size = link_to.length;
		var index=0;
		while(index<size){
		links += "<a href='javascript:void(0);' onclick='show_parent_doc(\"/rest/documents/"
			+ link_to[index++]
			+ "\")'> "
			+ link_to[index++]
			+ " "
			+ link_to[index++]+ "</a><br/>";
		}
	}
	
	
	$("#parents").html(links);
	
	load_doc_imgs(documentSader_details.title);
	
	
	
	
	var params = {};
	params.doc_num = documentSader_details.doc_number;
	params.doc_date = documentSader_details.doc_date;
	var unitsader={};
	unitsader=documentSader_details.units_sader;

		inner_html="";
		for ( var row = 0; row < Object.size(unitsader); row++){
			inner_html += "<tr style='font-size:16px;color:black;text-align:center;'>"
				+ "<td style='text-align: center;'>"
				+  global_saderunits_name[unitsader[row]]
			+ "</td>"
			+ "</tr>" ;
		}
	
	$("#units_body").html(inner_html);
}


$(document).ready(function() {
	$('#print').sidr();
	init_doc_details();
});
