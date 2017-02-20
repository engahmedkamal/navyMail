function refresh(){
	loadFile("Motba/search_form.html", "html", ".bgcontent", function() {});
}
$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true
		
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
	
	target_options = '<option value="">---  عرض على  ---</option>';
	units_options = '<option value="">---  الجهة الصادرة   ---</option>';
	status_options = '<option value="">-- الموقف --</option>';
	
	var size = Object.size(global_targets);
	for(var i=0; i<size ;i++ )
		target_options += '<option value="' + global_targets[i].id + '">' + global_targets[i].name + '</option>';
	
	size = Object.size(global_units);
	for(var i=0; i< size;i++ )
		units_options += '<option value="' + global_units[i].id + '">' + global_units[i].name + '</option>';
	
	size = Object.size(global_status);
	for(var i=0; i< size;i++ )
		status_options += '<option value="' + global_status[i].id + '">' + global_status[i].name + '</option>';
	
	$('#target').html(target_options);
	$('#units').html(units_options);
	$('#status').html(status_options);
	
	$('.chosen').chosen({
	    no_results_text: "لا يوجد",
		allow_single_deselect: true
	});
	

    var frm = $('#details_search_form');
    frm.submit(function (ev) {
	    var params = {};

	    params.document_title = $("#document_title").val();
	    params.status= $("#status").val();
	    params.target= $("#target").val();
	    params.unit= $("#units").val();
	  
	    var val = $("#number_val").val();
	    
	    var num =  $("#number_input").val();
	    var date_to=$("#date-to").val();
	    var date_from=$("#date-from").val();
	    
	    if(val==1){
	    // wared
	    params.document_number = num;
	    params.date_from= date_from;
	    params.date_to= date_to;
	    } else if (val==2){
	   	 //sader mo7awal
		    params.document_out_number=num;
		    params.date_from_sader= date_from;
		    params.date_to_sader= date_to;
	    } else{
	    	// sader 
	    	params.trans_out_num= num;
	    	params.date_from_trans= date_from;
	    	params.date_to_trans= date_to;
	    }
	   
    	superConnect(api_source + 'documents/search_motba' , params, function(documents_obj) {
			if(documents_obj.documents && documents_obj.documents.length > 0){
				 last_params_search = params;
				 documents_list = documents_obj.documents;
				 doc_search_size= documents_obj.res_size;
				 doc_search_page_num = documents_obj.page_num;
				 doc_search_page_size = documents_obj.page_size;
				 doc_search_page_count = documents_obj.page_count;
				loadFile ("Motba/documents_list.html", "html", ".bgcontent", function(){});
			}else if(documents_obj.documents && documents_obj.documents.length == 0) {
				$(".bgcontent").html("لا توجد مكاتبات");
			}
		 });
        ev.preventDefault();
    });
});