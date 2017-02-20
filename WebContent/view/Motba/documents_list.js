function set_up_pagination(){
	
	var prev = "<li><a href='#'  class='prev disabled' onclick='prev()'><span aria-hidden='true'>&raquo;</span></a></li>";
	var next = "<li><a href='#'  class='next disabled' onclick='next()'><span aria-hidden='true'>&laquo;</span></a></li>"; 
	
	var active_content ="<li class='active'><a href='#'>id <span class='sr-only'>(current)</span></a></li>";
	var content="  <li><a href='#' onclick='go_to_page(id)'>id</a></li>";
	
	var inner="";
	for ( var index = 0; index < doc_search_page_count; index++) {
		if(index==doc_search_page_num){
			inner+=active_content.replace(new RegExp("id", 'g'), (index+1));
		}else{
			inner+=content.replace(new RegExp("id", 'g'), (index+1));
		}
	}
	return prev+inner+next;
}

function next(){
	if(doc_search_page_num == doc_search_page_count -1)
		return;
	go_to_page(doc_search_page_num+2);
}
function prev(){
	if(doc_search_page_num == 0)
		return;
	go_to_page(doc_search_page_num);
}


function go_to_page(page_num){
	page_num--;
	
	
	last_params_search.page=page_num;
	
	   
	superConnect(api_source + 'documents/search_motba' , last_params_search, function(documents_obj) {
		if(documents_obj.documents && documents_obj.documents.length > 0){
			documents_list = documents_obj.documents;
			 doc_search_size= documents_obj.res_size;
			 doc_search_page_num = documents_obj.page_num;
			 doc_search_page_size = documents_obj.page_size;
			 doc_search_page_count = documents_obj.page_count;
			
			loadFile ("Motba/documents_list.html", "html", ".bgcontent", function(){});
		}else if(documents_obj.documents && documents_obj.documents.length == 0) {
			$(".bgcontent").html("<strong>لا توجد مكاتبات</strong>");
		}
	 });
	
	
	
}

function show_doc(id){
	document_details = documents_list[id];
	loadFile ("Motba/details.html", "html", ".bgcontent", function(){
		details_back_tracker= come_from_search_list;
	});
}


function inner_table(documents){
	
	var inner_html = "";
	for (var i = 0; i < documents.length; i++) {
		var  document = documents[i];
		var unit = global_units_name[document.unit_id];
		if(document.unit_id==null || document.unit_id<=0)
			unit = '';
		var status = "";
		var tashira="";
		if (document.resp_unit != null && document.resp_unit != '') {
			var size = document.resp_unit.length;
			var index=0;
			while(index<size){
				status += 
					global_units_name[document.resp_unit[index].unit]
				+ "<br/>"
				index++
			}
		}
		if (document.tashira != null && document.tashira != '') {
			var link_to = document.parents.split("#");
			var size = document.tashira.length;
			var index=0;
			while(index<size){
				tashira += 
					global_Motb3aTashira_name[document.tashira[index++]]
				+ "<br/>"
				
			}
		}
		var t= document.trans_out_num;
		if(t==0)
			t='';
		
		var cc = document.doc_out_number==0?" ":document.doc_out_number;
		
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
		+ document.created_at 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ document.trans_out_num 
		+ "</td >"
		+ "<td style='white-space: nowrap; text-align: center; '>"
		+ document.trans_out_date 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ status
		+ "</td>"
		+ "<td style='text-align: center;'> <font color='green'>"
		+ tashira
		+ "</font></td>"
		+ "</tr>" ;
		
	}
	return inner_html;
}


$(document).ready(function() {
	
	$("#table_content").html(inner_table(documents_list));
	$("#paging").html(set_up_pagination());
	
});
