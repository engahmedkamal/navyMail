function reload(){
	loadFile ("Sader/sader_search_form.html", "html", ".bgcontent", function(){});

}
$(document).ready(function() {
	$('.date-picker').datepicker({
		autoclose : true,
		language : 'ar-EG',
		isRTL : true
		
	}).next().on(ace.click_event, function() {
		$(this).prev().focus();
	});
	
	units_options = '<option value="">---  الجهة الصادرة   ---</option>';
	size = Object.size(global_units);
	for(var i=0; i< size;i++ )
		units_options += '<option value="' + global_units[i].id + '">' + global_units[i].name + '</option>';
	
	$('#units').html(units_options);
	
	

    var frm = $('#details_search_form');
    frm.submit(function (ev) {
	    var params = {};

	    params.document_title = $("#document_title").val();
	    params.document_number = $("#number_input").val();
	    var date_to=$("#date-to").val();
	    var date_from=$("#date-from").val();
	    params.date_from= date_from;
	    params.date_to= date_to;
	    params.unit= $("#units").val();
	    params.doc_type = $("#doctype").val();

	    console.debug(params);
	    
    	superConnect(api_source + 'SaderDocuments/sadersearch' , params, function(documents_obj) {
    	    console.debug(documents_obj);
    	    
			if(documents_obj.documents && documents_obj.documents.length > 0){
				
				 last_params_search_sader = params;
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
    	
        ev.preventDefault();
    });
});