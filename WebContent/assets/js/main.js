var host_name = "100.100.100.253";
var api_source = '/rest/';
var loggined = false;
var login_id = "";
var login_name = "";
var privilege_id=""; // Code Bassiouny
var curr_page_size = 20;
var arryPublic={};
/************************* back herf for details html ********************************/
var come_from_search_list = 1;
var come_from_tablet_list = 2;
var come_from_fast_search = 3;

var details_back_tracker;
/*************************************************************************************/

var last_params_search_sader;
var sader_search_size;
var sader_search_page_num;
var sader_search_page_size;
var sader_search_page_count;
var first_part_of_image_path =	'/media/backup/navyadmin/public';

var server_path = 'http://' + host_name + ':3000';


var start_search_with_param_data = {};
var current_view_eltezamat = 1;

var current_view_tabletFastsearch = '';

var type = "autos";
var myobj = "";
var myprams = get_parameters();

var crossDomain = false;

var eltezamat_ka2ed= [];
var eltezamat_arkan=[];
var documents_tarbets;
var document_details=null;
var documents_list;
var documentSader_list;
var documentSader_details;
var cur_doc_index = 0;

var last_params_search;
var doc_search_size;
var doc_search_page_num;
var doc_search_page_size;
var doc_search_page_count;

var selected_scanner = 1 ;
///////////////////////////// Travels Variables Declarations ////////////////////////////////////
var internal_units_name = {};
var rank_name = {};
var port_name = {}; 
var ship_name ={}; 
var ship_type = {} ; 
var missions_type = {}; 
var missions_type_name = {}; 
var internal_units = {}; 
var rank = {}; 
var port = []; 
var ship  = [];
var ma2morya_type ; 
var nashat_name ; 
var nashat_type ; 
var missions_params = {} ;
var travels_list ;
var travel_index_list ;
var Passengers_Array = [];
var Passengers_Number = 0;
var military_num_array = [] ; 
var current_mission_id = 0;
///////////////////////////// Travels Variables Declarations ////////////////////////////////////
var GlobalShipEntryID ; // For on Ship Registeration 
var GlobalSelectedShipsIDs ;
var GlobalPortID ; 
var GlobalDateFrom ; 
var GlobalDateTo ; 
var Traveler_Back_Button = 1 ; 

var global_status_name = {};
var global_targets_name = {};
var global_units_name = {};
var global_Motb3aTashira_name = {};
var global_saderunits_name = {};
var global_tashira_name={};
var global_submain_name={};
var global_sub_br_name={};
var global_saderunits_unit_type = {};
var global_status = {};
var global_targets = {};
var global_units = {};
var global_units_type = {};

var global_saderunits = {};
var global_tashira={};
var global_submain={};
var global_sub_br={};
var global_Motb3aTashira={};

var global_saved_files = {};
var global_saved_files_name= {};



init();

function get_domain() {
	var domain = "";
	return domain;
}
// for back button in the detail.html
var stack = [];

function show_parent_doc(doc_url) {
	if(document_details!=null )
		stack.push(document_details);  
	superConnect(doc_url, {}, function(documents_obj) {
		if (documents_obj[0]) {
			document_details = documents_obj[0];
			loadFile("details.html", "html", ".bgcontent", function() {});
		} else if (documents_obj.documents
				&& documents_obj.documents.length == 0) {
			$(".bgcontent").html("لا توجد مكاتبات");
		}
	});
	console.debug(stack);

}

function setStatus(){
	superConnect(api_source + 'tablet/status' , {}, function(obj) {
		
		$("#kaed_da5ly").html(obj[0]);
		$("#kaed_khargy").html(obj[1]);
		$("#kaed_total").html(obj[0] + obj[1]);

		$("#kaed_da5ly_tab").html(obj[2]);
		$("#kaed_khargy_tab").html(obj[3]);
		$("#total_kaed_tab").html(obj[2]+obj[3]);
		
		$("#arkan_da5ly").html(obj[4]);
		$("#arkan_khargy").html(obj[5]);
		$("#arkan_total").html(obj[4]+obj[5]);
		
		$("#arkan_da5ly_tab").html(obj[6]);
		$("#arkan_khargy_tab").html(obj[7]);
		$("#total_arkan_tab").html(obj[6]+obj[7]);
		
	});
}


function init() {
	if (window.location.href == "http://" + host_name + ":8080/")
		window.location.assign("/view/index.html");
	login_id = read_cookie('login_id');
	if (login_id && login_id != "") {
		loggined = true;
		login_name = read_cookie('login_name');
		privilege_id = read_cookie('privilege_id'); // Code Samir
		if (!not_index())
			window.location.assign("/view/home.html");
	} else if (not_index())
		window.location.assign("/view/index.html");

	superConnect(api_source + 'master/target', {}, function(obj) {
		$.each(obj, function(key, value) {
			global_targets_name[value.id] = value.name;
			global_targets[key] = value;
		});
		////////////////////////////////////////MoTb3a///////////////////////
		superConnect(api_source + 'motba/tashira', {}, function(obj) {
			$.each(obj, function(key, value) {
			//	console.debug("asddddddddddddd");
				global_Motb3aTashira_name[value.id] = value.name;
				global_Motb3aTashira[key] = value;
			});
///////////////////////////// Travels SuperConnect  /////////////////////////////////////////////
		superConnect(api_source + 'internal_units/show', {}, function(obj) {
			$.each(obj, function(key, value) {
				internal_units_name[value.id] = value.name;
				internal_units[key] = value;
			});
/////////////////////////////////////////////////////////////////////////////////////////////////
			superConnect(api_source + 'rank_degrees/show', {}, function(obj) {
				$.each(obj, function(key, value) { 
					rank_name[value.id] = value.rank_name;
					rank[key] = value;
				});
/////////////////////////////////////////////////////////////////////////////////////////////////
				superConnect(api_source + 'ports/show', {}, function(obj) {
					$.each(obj, function(key, value) { 
						port_name[value.id] = value.name;
						port[key] = value;
					});
/////////////////////////////////////////////////////////////////////////////////////////////////
					superConnect(api_source + 'ships/show', {}, function(obj) {
						$.each(obj, function(key, value) { 
							ship_name[value.id] = value.name;
							ship[key] = value;
							ship_type[value.id] = value.ship_type;
						});
/////////////////////////////////////////////////////////////////////////////////////////////////
						superConnect(api_source + 'missions_types/show', {}, function(obj) {
							$.each(obj, function(key, value) { 
								missions_type_name[value.id] = value.name;
								missions_type[key] = value;

							});
							
///////////////////////////////////////SADER///////////////////////////////////////////////////////////////
							superConnect(api_source + 'master/tashira', {}, function(obj) {
								$.each(obj, function(key, value) {
									global_tashira_name[value.id] = value.name;
									global_tashira[key] = value;
									
								});
								superConnect(api_source + 'master/sub_main', {}, function(obj) {
									$.each(obj, function(key, value) {
										global_submain_name[value.id] = value.name;
										global_submain[key] = value;
									});
									superConnect(api_source + 'master/sub_br', {}, function(obj) {
										$.each(obj, function(key, value) {
											global_sub_br_name[value.id] = value.name;
											global_sub_br[key] = value;
										});
							superConnect(api_source + 'master/sader_units', {}, function(obj) {
								$.each(obj, function(key, value) {
									global_saderunits_name[value.id] = value.name;
									global_saderunits[key] = value;
									global_saderunits_unit_type[value.id]=value.unit_type;
								});			
///////////////////////////// Travels SuperConnect /////////////////////////////////////////////
		superConnect(api_source + 'master/units', {}, function(obj) {
			$.each(obj, function(key, value) {
				global_units_name[value.id] = value.name;
				global_units_type[value.id] = value;
				global_units[key] = value;
			});
			superConnect(api_source + 'master/status', {}, function(obj) {
				$.each(obj, function(key, value) {
					global_status_name[value.id] = value.name;
					global_status[key] = value;
				});
				superConnect(api_source + 'eltezamat/view/ka2ed', {}, function(obj) {
					$.each(obj, function(key, value) {
						eltezamat_ka2ed.push([ ,obj[key].date,obj[key].time,obj[key].title,obj[key].place,obj[key].trans_out_num,obj[key].trans_out_date , obj[key].unit]);
					});
					superConnect(api_source + 'eltezamat/view/arkan', {}, function(obj) {
						$.each(obj, function(key, value) {
							eltezamat_arkan.push([ ,obj[key].date,obj[key].time,obj[key].title,obj[key].place,obj[key].trans_out_num,obj[key].trans_out_date , obj[key].unit]);
						});
						superConnect(api_source + 'master/savedfiles', {}, function(obj) {
							$.each(obj, function(key, value) {
								global_saved_files_name[value.code] = value.name;
								global_saved_files[key] = value;
							});
							navyAdminReady();
											});
										});
									});
								});
							});
						});
					});
				});
			});
		});
	});
				});	});});});});

	set_up_date();
}



function set_up_date(cur_day, cur_month , cur_year) {

	var day_selector="";
	var month_selector="";
	var year_selector="";
	
	for ( var day = 1; day <= 31; day++) {
		if (day == cur_day)
			day_selector += "<option selected='selected' value=" + day + ">"
					+ day + "</option>";
		else
			day_selector += "<option value=" + day + ">" + day + "</option>";
	}
	var months = [ "", "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" ];

	for ( var month = 1; month <= 12; month++) {
		if (cur_month == month)
			month_selector += "<option  selected='selected' value=" + month + ">"
					+ months[month] + "</option>";
		else
			month_selector += "<option value=" + month + ">" + months[month]
					+ "</option>";
	}

	for ( var year = 2010; year <= 2030; year++) {
		if (cur_year == year)
			year_selector += "<option selected='selected' value=" + year + ">" + year + "</option>";
		else
			year_selector += "<option value=" + year + ">" + year + "</option>";
	}

	
	
	return {days:day_selector , months:month_selector , years:year_selector };
}

function not_index() {
	return window.location.href != "http://" + host_name + ":8080/"
			&& window.location.href != "http://" + host_name
					+ ":8080/view/index.html";
}

Object.size = function(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key))
			size++;
	}
	return size;
};

/* initialization [end] */
// =========================================================================================================================
/* General purpose [start] */

function create_json(myarray) {
	var myjson = "{";
	$.each(myarray, function(key, value) {
		myjson += key + ': ' + value + ',';
	});
	myjson += "}";
	return myjson;

}

function trancate(string, length) {
	var newLength = length - 3;
	if (string.length > length)
		string = string.substring(0, newLength) + '...';
	return string;
}

function checkEmail(email) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	if (reg.test(email) == false) {
		return false;
	}
	return true;
}

function append_childs(myTagId, childs) {
	$('#' + myTagId).append(childs);
}

function append_each_childs(myTagId, childs) {
	$('#' + myTagId).each(function() {
		$(this).append(childs);
	});
}

function replace_childs(myTagId, childs) {
	$('#' + myTagId).empty().append(childs);
}

function setFaceBookImage(imageUrl) {
	$("#faceMeta").attr('content', imageUrl);
}

function favicon() {
	var link = document.createElement('link');
	link.type = 'image/x-icon';
	link.rel = 'shortcut icon';
	link.href = 'resources/img/favs/fav_' + domain_type() + '.ico';
	document.getElementsByTagName('head')[0].appendChild(link);
}

function loadFile(path, type, divSelector, func) {
	$.ajaxSetup({
		cache : true
	});
	if (type == "html") {
		$(divSelector).load(path, func);
	} else if (type == "js") {
		$.getScript(path, func);
	}
}

/* User functions [end] */
// ==========================================================================================================================
/* Cokkies functions [start] */

function read_cookie(c_name) {
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1) {
		c_start = c_value.indexOf(c_name + "=");
	}
	if (c_start == -1) {
		c_value = null;
	} else {
		c_start = c_value.indexOf("=", c_start) + 1;
		var c_end = c_value.indexOf(";", c_start);
		if (c_end == -1) {
			c_end = c_value.length;
		}
		c_value = unescape(c_value.substring(c_start, c_end));
	}
	return c_value;
}

function write_cookie(c_name, value) {
	var exdays = 5;
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}

/* Cokkies functions [start] */
// ==========================================================================================================================
/* autos Seen functions [start] */

function clear_viewed(viewed) {
	var count = 0;
	for ( var i = 0; i < viewed.length; i++) {
		if (viewed[i] === ",")
			count++;
	}
	if (count >= 200) {
		for ( var i = 0; i < 20; i++) {
			viewed = viewed.substring(viewed.indexOf(",") + 1);
		}
	}
	return viewed;
}
/* autos Seen functions [start] */
// =========================================================================================================================
/* URL functions [start] */

function get_parameters2(newURL) {
	var parameters = {};
	var query;
	(function() {
		var match, pl = /\+/g, // Regex for replacing addition symbol with a
		// space
		search = /([^&=]+)=?([^&]*)/g, decode = function(s) {
			return decodeURIComponent(s.replace(pl, " "));
		};

		query = newURL;
		while (match = search.exec(query)) {
			// if(decode(match[2]) != ""){
			if (parameters[decode(match[1])] != null)
				parameters[decode(match[1])] += ',' + decode(match[2]);
			else
				parameters[decode(match[1])] = decode(match[2]);
			// }
		}
	})();
	return parameters;
}

function parse_query(query) {
	var parameters = {};
	(function() {
		var match, pl = /\+/g, // Regex for replacing addition symbol with a
		// space
		search = /([^&=]+)=?([^&]*)/g, decode = function(s) {
			return decodeURIComponent(s.replace(pl, " "));
		};
		while (match = search.exec(query)) {
			// if(decode(match[2]) != ""){
			if (parameters[decode(match[1])] != null)
				parameters[decode(match[1])] += ',' + decode(match[2]);
			else
				parameters[decode(match[1])] = decode(match[2]);
			// }
		}
	})();
	return parameters;
}

function get_parametersWithoutDecoding(newURL) {
	var parameters = {};
	var query;
	(function() {
		var match; // Regex for replacing addition symbol with a
		// space
		search = /([^&=]+)=?([^&]*)/g;
		query = newURL;
		while (match = search.exec(query)) {
			if (parameters[match[1]] != null)
				parameters[match[1]] += ',' + match[2];
			else
				parameters[match[1]] = match[2];
		}
	})();
	return parameters;
}
function get_query() {
	return window.location.search.substring(1);
}

function get_parameters() {
	var parameters = {};
	var query;
	(function() {
		var match, pl = /\+/g, // Regex for replacing addition symbol with a
		// space
		search = /([^&=]+)=?([^&]*)/g, decode = function(s) {
			return decodeURIComponent(s.replace(pl, " "));
		};
		query = window.location.search.substring(1);
		while (match = search.exec(query)) {
			// if(decode(match[2]) != ""){
			if (parameters[decode(match[1])] != null)
				parameters[decode(match[1])] += ',' + decode(match[2]);
			else
				parameters[decode(match[1])] = decode(match[2]);
			// }
		}
	})();
	return parameters;
}

function get_data(url, myData) {
	$.get(api_source + url, myData, function(data) {
		return data;
	});
}

/* URL functions [end] */
// ==========================================================================================================================
/* Translation Part [start] */

function generate_key(key) {
	key = String(key).replace(/\n/g, '');
	key = String(key).replace(/\t/g, '');
	key = String(key).replace(/\r/g, '');
	key = String(key).replace(/ /g, '');
	return key;
}

function translate(className, obj) {
	$('.' + className).each(function() {
		var old = $(this).html();
		var key = (generate_key($(this).html())).toLowerCase();
		$(this).attr('en_lang', key);
		if (obj[key] && obj[key] != "") {
			key = (obj[key]).replace(/<br>/g, "\r\n");
			;
		} else {
			key = old;
		}
		$(this).html(key);
		$(this).show();
	});
}

function generalTranslate(className, obj) {
	$('.' + className).each(function() {
		var old = $(this).attr('en_lang');
		var key = (generate_key($(this).attr('en_lang'))).toLowerCase();
		if (obj[key] && obj[key] != "") {
			key = obj[key];
		} else {
			key = old;
		}

		$(this).html(key);
		$(this).show();
	});
}

function translate_option(key) {
	var mykey = key;
	key = (generate_key(key)).toLowerCase();

	if (translate_options && translate_options[key]
			&& translate_options[key] != "") {
		key = translate_options[key];
	} else {
		key = mykey;
	}
	return key;
}

function translate_option_withSource(key) {
	var mykey = key;
	key = (generate_key(key)).toLowerCase();

	if (translate_options && translate_options[key]
			&& translate_options[key] != "") {
		key = translate_options[key];
	} else {
		key = mykey;
	}
	return key;
}

function translate_mymobile(key) {
	var mykey = key;
	key = (generate_key(key)).toLowerCase();

	if (translate_mobile && translate_mobile[key]
			&& translate_mobile[key] != "") {
		key = translate_mobile[key];
	}

	else {
		key = mykey;
	}

	return key;
}

function translate_all(key) {
	var mykey = key;
	key = (generate_key(key)).toLowerCase();

	if (translate_options[key] && translate_options[key] != "") {
		key = translate_options[key];
	} else if (translate_mobile[key] && translate_mobile[key] != "") {
		key = translate_mobile[key];
	} else {
		key = mykey;
	}

	return key;
}

function translate_key(key, obj) {
	var mykey = key;
	key = (generate_key(key)).toLowerCase();

	if (obj[key] && obj[key] != "")
		key = obj[key];
	else
		key = mykey;

	return key;
}

/* Translation Part [end] */
// ==========================================================================================================================
/* Connection [start] */
function select_setDefault(key, defaultValue) {
	if (myprams[key] && myprams[key] != "")
		$('select[name=' + key + ']').val(myprams[key]);
	else
		$('select[name=' + key + ']').val(defaultValue);
}

function checkbox_setDefault(key) {
	if (myprams[key] && myprams[key] != "") {
		var options = myprams[key].split(",");
		for ( var i = 0; i < options.length; i++) {
			$('input[value=' + options[i] + ']').attr('checked', true);
		}
	}
}

function multi_checkbox_setDefault(key) {
	if (myprams[key] && myprams[key] != "") {
		var options = myprams[key].split(",");
		for ( var i = 0; i < options.length; i++) {
			$('select[name=' + key + '] option[value=' + options[i] + ']')
					.attr('selected', 'selected');
			// $('input[value='+ options[i]+']').attr('checked', true);
		}
	}
}

/* Connection [start] */
// ==========================================================================================================================
function superConnect(myurl, parameters, func, par) {
	$.ajaxSetup({
		cache : true
	});
	if (crossDomain) {
		if ($.browser.msie && window.XDomainRequest) {
			var data = new XDomainRequest();
			data = new XDomainRequest();
			data.open("get", myurl + '?' + generate_post_data(parameters));
			data.onload = function() {
				var obj = $.parseJSON(data.responseText);
				func(obj, par);
			};
			data.send();

		} else {
			$.getJSON(myurl, parameters, function(obj) {
				func(obj, par);
			});
		}
	} else {
		$.ajax({
			url : myurl,
			headers : {
				Accept : "application/json; charset=utf-8"
			},
			data : parameters,
			contentType : "application/json",
			success : function(data) {
				func(data, par);
			}
		});
	}
}

$.getScript = function(url, func, cache) {
	$.ajax({
		type : "GET",
		url : url,
		success : func,
		dataType : "script",
		cache : cache
	});
};

function loadFrameConnect(myurl, width, height) {
	var frame = '<iframe width="' + width + '" height="' + height
			+ '" frameborder="0" src="' + myurl + '"></iframe>';
	return frame;
}
