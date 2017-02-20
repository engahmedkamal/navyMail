function set_up_pagination(){
	
	var prev = "<li><a href='#'  class='prev disabled' onclick='prev()'><span aria-hidden='true'>&raquo;</span></a></li>";
	var next = "<li><a href='#'  class='next disabled' onclick='next()'><span aria-hidden='true'>&laquo;</span></a></li>"; 
	
	var active_content ="<li class='active'><a href='#'>id <span class='sr-only'>(current)</span></a></li>";
	var content="  <li><a href='#' onclick='go_to_page(id)'>id</a></li>";

	var inner="";
	for ( var index = 0; index < sader_search_page_count; index++) {
		if(index==sader_search_page_num){
			inner+=active_content.replace(new RegExp("id", 'g'), (index+1));
		}else{
			inner+=content.replace(new RegExp("id", 'g'), (index+1));
		}
	}
	return prev+inner+next;
}
function go_to_page(page_num){
	page_num--;
	last_params_search_sader.page=page_num;
	superConnect(api_source + 'SaderDocuments/sadersearch' , last_params_search_sader, function(documents_obj) {
		if(documents_obj.documents && documents_obj.documents.length > 0){
			 documentSader_list = documents_obj.documents;
			 sader_search_size= documents_obj.res_size;
			 sader_search_page_num = documents_obj.page_num;
			 sader_search_page_size = documents_obj.page_size;
			 sader_search_page_count = documents_obj.page_count;
			 console.debug("jhjh");
			 console.debug(sader_search_page_count);
			 loadFile ("Sader/document_sader_search.html", "html", ".bgcontent", function(){});
		}else if(documents_obj.documents && documents_obj.documents.length == 0) {
			$(".bgcontent").html("لا توجد مكاتبات");
		}
	 });
}
function next(){
	if(sader_search_page_num == sader_search_page_count -1)
		return;
	go_to_page(sader_search_page_num+2);
}
function prev(){
	if(sader_search_page_num == 0)
		return;
	go_to_page(sader_search_page_num);
}


function show_doc(id){
	console.debug
	documentSader_details = documentSader_list[id];
	loadFile ("Sader/sader_details.html", "html", ".bgcontent", function(){
		details_back_tracker= come_from_search_list;
	});
}


function inner_table(documents){
	
	var inner_html = "";
	for (var i = 0; i < documents.length; i++) {
		var  document = documents[i];
		console.debug(document);
		var ta2shira=(document.target_id == 1)?("السيد القائد"):((document.target_id == 2)?("السيد رئيس الأركان"):"");
		var unit = global_saderunits_name[document.unit_id];
		console.debug(unit);
		var mawqef="";
		if(document.tashira!=-1)
		 mawqef=global_tashira_name[document.tashira];
		console.debug(document.tashira);
		if(document.unit_id==null || document.unit_id<=0)
			unit = '';
		var status = global_status_name[document.status_id];
		var links="";
		if (document.parents != null && document.parents != '') {
			var link_to = document.parents.split("#");
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
		var t= document.trans_out_num;
		if(t==0)
			t='';
		inner_html += "	<tr onclick='show_doc(" + i + ")'>"
		+ "<td style='text-align: center;'>"
		+ document.doc_number
		+ "</td >"
		+ "<td style='text-align: center;'>"
		+ document.title 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ unit
		+ "</td>"
		+ "<td style='white-space: nowrap; text-align: center; '>"
		+ document.doc_date 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ ta2shira
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ mawqef
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ links
		+ "</td>"
		+ "</tr>" ;
		
	}
	return inner_html;
}


$(document).ready(function() {
	console.debug("$$$$");
	console.debug(sader_search_page_count);
	//console.debug(documentSader_list);
	$("#table_content").html(inner_table(documentSader_list));
	$("#paging").html(set_up_pagination());
	
});
