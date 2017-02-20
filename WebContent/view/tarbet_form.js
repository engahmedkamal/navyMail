function inner_table(documents){
	
	var inner_html = "";
	for (var i = 0; i < documents.length; i++) {
		var  document = documents[i];
		var status = global_status_name[document.status_id];
		
		inner_html += "	<tr>"
		+ "<td style='text-align: center;'>"
		+ document.doc_number 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ document.title 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ document.created_at 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ document.doc_out_number 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ status
		+ "<td style='text-align: center;'> <a href= 'javascript:void(0)' onclick='selcetDocument("+i+");'  class='btn btn-warning' >  إختيار </a> </td>"
		+ "</tr>" ;
		
	}
	return inner_html;
}

function open_modal_form(){
	var e = document.getElementById("modal-form");
	
	if(e.style.display == 'block')
		e.style.display = 'none';
	else
		e.style.display = 'block';
}
window.onload=function(){
	
}
$(document).ready(function() {
	  var frm = $('#tarbet_form');
	  //document.getElementById("docNumber").focus();
	  //$("#docNumber").focus("");
	  frm.submit(function (ev) {
		  	var mini_search_params = {};
		    mini_search_params.doc_num = $("#docNumber").val();
		    mini_search_params.doc_out_num = $("#docOutNumber").val();
		    
	    	superConnect(api_source + 'documents/minisearch' , mini_search_params, function(documents_obj) {
	    		console.debug(documents_obj);
				if(documents_obj && documents_obj.length > 0){
					documents_tarbets = documents_obj;
					$("#inner-modal-content").html(inner_table(documents_tarbets));
				}else if(documents_obj && documents_obj.length == 0) {
					$("#inner-modal-content").html("لا توجد مكاتبات");
				}
			 });
			ev.preventDefault();
	   });
});