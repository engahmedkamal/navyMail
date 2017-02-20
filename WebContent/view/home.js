function navyAdminReady() {
	$(document).ready(function() {
		stack = [];
		loadFile("search_form.html", "html", ".bgcontent",function() {});
		loadFile("FastSearchForTablet/currentStatus.html", "html", "#elmawkef",function() {});

		$('#simple-menu').sidr();
	
		var opened = false;
		$('#currentStatus').on("click",function() {
			if (opened == false) {
				$("#right-btn-img").attr("src","../assets/css/images/prev.png");
				opened = true;
			} else {
				$("#right-btn-img").attr("src","../assets/css/images/next.png");
				opened = false;
			}
		});

		$("#simple-menu").on("click", function() {
			write_cookie("login_id", "");
			write_cookie("login_name", "");
			window.location.assign("/view/index.html");
		});
		var frm = $('#mini_search_form');
		frm.submit(function(ev) {
			start_search();
			ev.preventDefault();
		});
		
		$("#mini_search_input").keydown(function(e) {

			switch (e.which) {
				case 40: // left
					if (cur_doc_index != 0) {
						cur_doc_index--;
						document_details = documents_list[cur_doc_index];
						loadFile("details.html","html",".bgcontent",function() {});
					}
					break;
				case 38: // right
					if (cur_doc_index != documents_list.length - 1) {
						cur_doc_index++;
						document_details = documents_list[cur_doc_index];
						loadFile("details.html","html",".bgcontent",function() {});
					}
					break;
				default:
					return;
				}
			});
/*		$("#mini_search_input").on("input", function() {
			start_search();
		});*/
	});
};

function start_search() {
	stack = [];
	var mini_search_val = $("#mini_search_input").val();
	var mini_search_params = {};
	if (/^\d+$/.test(mini_search_val)) {
		mini_search_params.document_number = mini_search_val;
	} else {
		mini_search_params.document_title = mini_search_val;
	}

	superConnect(api_source + 'documents/search', mini_search_params, function(
			documents_obj) {
		if (documents_obj.documents && documents_obj.documents.length > 0) {
			documents_list = documents_obj.documents;
			cur_doc_index = 0;
			document_details = documents_obj.documents[cur_doc_index];
			loadFile("details.html", "html", ".bgcontent", function() {
				details_back_tracker = come_from_fast_search;
			});
		} else if (documents_obj.documents
				&& documents_obj.documents.length == 0) {
			$(".bgcontent").html("لا توجد مكاتبات");
		}
	});
}

function start_search_with_param(target, status, doc_type) {
	start_search_with_param_data=[target,status,doc_type];
	var params = {};
	params.target = target;
	params.status = status;
	params.doc_type = doc_type;
	params.page_size = curr_page_size;

	current_view_tabletFastsearch = '';
	
	if( target == 1)
		current_view_tabletFastsearch += ' السيد القائد';
	else
		current_view_tabletFastsearch += ' السيد رئيس الأركان';
	
	if( status == 1)
		current_view_tabletFastsearch += ' تحت التجهيز';
	else
		current_view_tabletFastsearch += ' علي التابلت';

	var b = doc_type+'a';
	if( b == '0a')
		current_view_tabletFastsearch += ' (داخلى)';
	else if( b == '1a')
		current_view_tabletFastsearch += ' (خارجى)';
	else
		current_view_tabletFastsearch += ' (خارجى/داخلى)';
		
	superConnect(api_source + 'documents/search', params, function(documents_obj) {
		if (documents_obj.documents && documents_obj.documents.length > 0) {
			last_params_search = params;
			documents_list = documents_obj.documents;
			 doc_search_size= documents_obj.res_size;
			 doc_search_page_num = documents_obj.page_num;
			 doc_search_page_size = documents_obj.page_size;
			 doc_search_page_count = documents_obj.page_count;
			loadFile("FastSearchForTablet/list.html", "html", ".bgcontent", function() {});
		} else if (documents_obj.documents
				&& documents_obj.documents.length == 0) {
			loadFile("notfound.html", "html", ".bgcontent", function() {});
		}
	});
	stack = [];
}

function open_motb3a_form(){
	stack = [];
	loadFile("Motba/Motb3a.html", "html", ".bgcontent", function() {});
}
function open_create_ward_form(){
	stack = [];
	loadFile("new_wared_document.html", "html", ".bgcontent", function() {});
}
function open_create_sader_form(){
	stack = [];
	loadFile("Sader/NewSader.html", "html", ".bgcontent", function() {});
}
function go_to_eltezmat_register()
{
	stack = [];
	loadFile("Eltezamat/eltezamat_form.html", "html", ".bgcontent", function() {});
	}
function open_search_ward_form(){
	stack = [];
	loadFile("search_form.html", "html", ".bgcontent", function() {});
}

function open_search_sader_form(){
	stack = [];
	loadFile("Sader/sader_search_form.html", "html", ".bgcontent", function() {});
}

function go_to_travels_register() {
	stack = [];
	loadFile("Travels/Home.html", "html", ".bgcontent", function() {});
}
function go_to_index() {
	stack = [];
	window.location.assign("index2.html");
}


function open_eltezamat(id){
	stack = [];
	current_view_eltezamat= id;
	loadFile("Eltezamat/eltezamat_form.html", "html", ".bgcontent", function() {});
}

function pull(){
	stack = [];
	loadFile("FastSearchForTablet/pull.html", "html", ".bgcontent", function() {});
}