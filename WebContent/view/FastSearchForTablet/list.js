function set_up_pagination(){
	var prev = "<li><a href='#'  class='prev disabled' onclick='prev()'><span aria-hidden='true'>&raquo;</span></a></li>";
	var next = "<li><a href='#'  class='next disabled' onclick='next()'><span aria-hidden='true'>&laquo;</span></a></li>"; 
	var active_content = "<li class='active'><a href='#'>id <span class='sr-only'>(current)</span></a></li>";
	var content = "<li><a href='#' onclick='go_to_page(id)'>id</a></li>";
	var inner = "";
	for ( var index = 0; index < doc_search_page_count; index++) {
		if(index == doc_search_page_num){
			inner +=active_content.replace(new RegExp("id", 'g'), (index+1));
		}else{
			inner +=content.replace(new RegExp("id", 'g'), (index+1));
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
	superConnect(api_source + 'documents/search' , last_params_search, function(documents_obj) {
		if(documents_obj.documents && documents_obj.documents.length > 0){
		  	documents_list = documents_obj.documents;
			 doc_search_size= documents_obj.res_size;
			 doc_search_page_num = documents_obj.page_num;
			 doc_search_page_size = documents_obj.page_size;
			 doc_search_page_count = documents_obj.page_count;
			
			loadFile ("FastSearchForTablet/list.html", "html", ".bgcontent", function(){});
		}else if(documents_obj.documents && documents_obj.documents.length == 0) {
			$(".bgcontent").html("لا توجد مكاتبات");
		}
	 });
}

function show_doc(id){
	document_details = documents_list[id];
	loadFile ("details.html", "html", ".bgcontent", function(){
		details_back_tracker = come_from_tablet_list;
	});
}


function inner_table(documents){
	
	var inner_html = "";
	for (var i = 0; i < documents.length; i++) {
		var document = documents[i];
		var unit = global_units_name[document.unit_id];
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

		var t = '';
		if( document.doc_type==0)
			t='داخلى';
		else
			t='خارجى';
		
		var c = document.doc_out_number;
		if(c==0)
			c=' ';
		inner_html += "	<tr  class='document-item'>"
		+ "<td><center><label class='inline'><input id=" + i + " type='checkbox' class='ace'>" +
				"<span class='lbl'></span></label></center></td>"
		+ "<td style='text-align: center;'>"
		+ document.doc_number 
		+ "</td >"
		+ "<td style='text-align: center;'>"
		+ document.title 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ c
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ t
		+ "</td>"
		+ "<td style='white-space: nowrap; text-align: center; '>"
		+ document.created_at 
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ status
		+ "</td>"
		+ "<td style='text-align: center;'>"
		+ links
		+ "</td>" 
		+ "<td style='text-align: center;'>"
		+ "<a class='btn btn-success btn-block' target='_blank' href='javascript:void(0)'  onclick='show(" + i + ")';> عرض</a>"
		+ "<a class='btn btn-primary btn-block' target='_blank'  onclick='edit(" + i + ")';>تعديل</a>"
		+ "</td>"
		+ "</tr>" ;
		
	}
	return inner_html;
}
function go(){
	var q = start_search_with_param_data;
	start_search_with_param(q[0], q[1], q[2]);
}

function show(id){
	document_details = documents_list[id];
	loadFile ("details.html", "html", ".bgcontent", function(){
		details_back_tracker = come_from_tablet_list;
	});
}

function edit(id) {
	document_details = documents_list[id];
	loadFile("edit_form.html", "html", ".bgcontent", function() {
	});
}

$(document).ready(function() {
	var isSelected1 = "";
	var isSelected2 = "";
	var isSelected3 = "";
	
	if(curr_page_size==20)
		isSelected1 = "selected";
	else if(curr_page_size==50)
		isSelected2 = "selected";
	else
		isSelected3 = "selected";

	
	var page_size_options = "<option value='20'"+isSelected1+">20</option>"
							+"<option value='50'"+isSelected2+">50</option>"
							+"<option value='100'"+isSelected3+">100</option>";

	$("#pageSize").html(page_size_options);
	$("#table_content").html(inner_table(documents_list));
	$("#paging").html(set_up_pagination());
	
	document.getElementById("title").innerHTML = current_view_tabletFastsearch;
	if(current_view_tabletFastsearch.indexOf("قائد", 0)!=-1)
		document.getElementById("MAINTITLE").style.color= "red";
	else
		document.getElementById("MAINTITLE").style.color= "green";
		

	$('#pageSize').change(function() {
		curr_page_size = $(this).find("option:selected").attr('value');
		console.debug(curr_page_size);
		go();
	
	});
	
	
	target_options = '<option value="">---  عرض على  ---</option>';
	status_options = '<option value="">- الموقف -</option>';

	var size = Object.size(global_targets);
	for ( var i = 0; i < size; i++)
		target_options += '<option value="'
				+ global_targets[i].id + '">'
				+ global_targets[i].name + '</option>';

	size = Object.size(global_status);
	for ( var i = 0; i < size; i++)
		status_options += '<option value="'
				+ global_status[i].id + '">'
				+ global_status[i].name + '</option>';

	$('#target').html(target_options);
	$('#status').html(status_options);

	
	
	//check/uncheck all messages
	$('#id-toggle-all').removeAttr('checked').on('click', function() {
		if(this.checked) {
			Inbox.select_all();
		}
		else
		 	Inbox.select_none();
	});

	var Inbox = {
	select_all : function() {
		$('.document-item input[type=checkbox]').each(function() {
			this.checked = true;
		});

	},
	select_none : function() {
		$('.document-item input[type=checkbox]').removeAttr('checked').closest('.document-item').removeClass('selected');
		$('#id-toggle-all').get(0).checked = false;
		}
	};


	$('#onTabletTable').dataTable({
		"aoColumns" : [{
			"bSortable" : false
		}, null, null, null, null, null, {
			"bSortable" : false
		}]
	});

	
	
	$('#toshown').click(function() {
		var status= $("#status").val();
		var target = $("#target").val();
		
		if( ( target == 3 || target == 4 ) && ( status == 1 || status == 2 ) ){
			alert("لا يجوز التحويل");
			return;
		}	
		
		if( ( target == 0 )|| ( status == 0) ){
			alert("لا يجوز التحويل");
			return;
		}
		var da5ly  = 0;
		var khargy = 0;
		var message = "تحويل عدد مكاتبات";

		$('.document-item input[type=checkbox]').each(function() {
			if (this.checked == true){
				var index = this.id;
				if( documents_list[index].doc_type==0)
					da5ly++;
				else
					khargy++;
			}		
		});
		message +="(  خارجي = " + khargy + ")(  داخلي = " + da5ly + ")";
		var r = confirm(message);
		
		$('.document-item input[type=checkbox]').each(function() {
			if (this.checked == true){
				var params ={};
				var index = this.id;
				params.id= documents_list[index].id;

				params.target_id = target;
				params.status_id = status;
				
				if(r){
					superConnect(api_source + 'documents/editStatus' , params, function(documents_obj) {
						go();
						setStatus();
					});
				}
			}
		});
	});

});
