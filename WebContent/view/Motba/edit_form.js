var dumb={};
var textbox1 = "";
var param={};
var istagel_arry=[];

function goback(){
		loadFile("Motba/details.html", "html", ".bgcontent", function() {});
}


function selcetDocument(id) {
	set_doc_follow(documents_tarbets[id]);
	$("#close_modal").click();
}

function save_edited_document() {
	var params = {};
	params.id = document_details.id;
	params.notes = $("#notes").val();
	params.unit=dumb[$('#units').val()];
	params.status=$('#state').val();
	params.answer=$('#answer').val();
	params.date = $("#year").val() + '-'
	+ $("#month").val() + "-" + $("#day").val();
	superConnect(api_source + 'documents/editIsatgel', params, function(obj2) {
		document_details = obj2[0];
		hidebuttom();
	});
}

function container(c) {
var container="";
var script="";
console.debug("container method"+arryPublic[c]);
console.debug("c= "+c);
if(arryPublic[c]==1){
container+='<option value="2" ">نعم</option> <option value="1" selected="selected">لا </option><br>';
	
	visbleSendbuttom();
}
else{
	container+='<option value="2" selected="selected">نعم</option> <option value="1">لا</option><br>'
	script="<div class='col-md-3'></div><div class='col-md-3'><select class='form-control'  id='year'></select></div>"
	+"<div class='col-md-4'><select class='form-control'   id='month'></select></div>"
	+"<div class='col-md-2'><select class='form-control'   id='day'></select></div></div>";
	hideSendbuttom();
}
container+="";
$('#state').html(container);
$('#date').html(script);
var d = new Date();
var cur_day = d.getDate();
var cur_month = d.getMonth() + 1;
var cur_year = d.getFullYear();
var data = set_up_date(cur_day, cur_month, cur_year);
$("#year").html(data.years);
$("#month").html(data.months);
$("#day").html(data.days);	
}
function int_istagel(x){
	var index=0;
	var z=0;
	var istagel="";
	var unitsader={};
	
	unitsader=document_details.resp_unit;
	console.debug("units:"+unitsader[x]);

	istagel_arry=[];
	for ( var i = 0; i < Object.size(document_details.estagel); i++)
	{
			if(document_details.estagel[i].unit==unitsader[x].unit){
				istagel_arry[z]=document_details.estagel[i].date;
				z++;
			}	
	}
	var html="";
	for ( var i = 0; i < istagel_arry.length; i++)
		{
		var remove_btn='<a target="_blank"   href="javascript:remove('+document_details.id+','+unitsader[x].unit+','+i+')" class="btn btn-danger" ><i class="icon-remove red"></i></a>';
		html+="<tr style='font-size:16px;background-color:white;color:black;text-align:center;'>"
			+ "<td>"
			+ remove_btn
			+ "</td>"
			+ "<td>"
			+ istagel_arry[i]
			+ "</td>"
			+"</tr>";
		}
	
//	console.debug(html);
	$('#table_content').html(html);
}
function remove(x,y,z)
{
	console.debug("remove")
	var params = {};
	params.id = x;
	params.date = istagel_arry[z];
	params.unit=y;
	console.debug(params.unit);
	superConnect(api_source + 'documents/deleteIsatgel', params, function(obj2) {
		var id_2 = $('#units').val();
		document_details = obj2[0];
		int_istagel(id_2);
	});
;		
}
function init_doc_details() {
	document.getElementById("editDoc").disabled=true;
	console.debug(document_details);
	$("#notes").html(document_details.notes);
	var motb3a_options='';
	var sader_units_options = "";
	var size = Object.size(global_targets);
	var z=0;
	var unitsader={};
	unitsader=document_details.resp_unit;
	var x=0;
	var z=0;
	var answer="";
	if(document_details.doc_need_replay==2){
		answer+='<option value="3" ">نعم</option> <option value="2" selected="selected">لا </option><br>';
	}else{
		answer+='<option value="3" selected="selected">نعم</option> <option value="2">لا </option><br>';
	}
	sader_units_options +='<option value="' + x
	+ '"selected="selected"">' + global_units_name[unitsader[0].unit]+ '</option>';
	dumb[0]=unitsader[0].unit;
	arryPublic[x]=unitsader[0].status;
	x++;
	for ( var row =1; row < Object.size(unitsader); row++){
		sader_units_options +='<option value="' + row
	+ '">' + global_units_name[unitsader[row].unit]+ '</option>';
		arryPublic[row]=unitsader[row].status;
		dumb[row]=unitsader[row].unit;
	x++;
	}
	
	$('#units').html(sader_units_options);
	$('#answer').html(answer);
var id_2 = $('#units').val();
container(id_2);
	console.debug(arryPublic);
	console.debug(id_2);
	int_istagel(id_2);
	console.debug(dumb);
}

$(document).ready(function() {
	init_doc_details();
	$('#state').change(function() {
		var id = $('#state').val();
		console.debug("state change"+arryPublic)
		var id_2 = $('#units').val();
		arryPublic[id_2]=parseInt(id);	
		visblebuttom();
		container(id_2);
	});
	$('#answer').change(function() {	
		visblebuttom();
	});
	$("#notes").on("input", function() {
		visblebuttom();
	});
	$('#units').change(function() {
		var id = $(this).find("option:selected").attr('value');
		var id_2 = $('#units').val();
		console.debug(arryPublic);
		console.debug("id"+id_2);
		hidebuttom();
		container(id_2);
		int_istagel(id_2);
	});
	loadFile("Motba/Istagel_panel.html", "html", "#istagel_panel",function() {});
});
function getSelectedOptions(element) {
    var opts = element.options;
    var selectedOptions = "";
    var check=false;
    for(var i = 0; i < opts.length; i++) 
         if(opts[i].selected) {
             selectedOptions+= opts[i].value+" ";
             check=true;
         }
    if(check)
    return selectedOptions;
    else
    	return "-1";
}
function visblebuttom(){
	document.getElementById("editDoc").disabled=false;
}
function hidebuttom(){
	document.getElementById("editDoc").disabled=true;
}
function visbleSendbuttom(){
	document.getElementById("send").disabled=false;
}
function hideSendbuttom(){
	document.getElementById("send").disabled=true;
}
function ahmed(){
    var textbox1 = "";
    saveTextBoxes();
}
    function saveTextBoxes() {
    	var params = {};
    	params.id = document_details.id;
    	params.date = $("#created_year").val() + '-'
    			+ $("#created_month").val() + "-" + $("#created_day").val();
    	params.unit=dumb[$('#units').val()];
    	console.debug(params.unit);
    	superConnect(api_source + 'documents/insertIsatgel', params, function(obj2) {
    		var id_2 = $('#units').val();
    		document_details = obj2[0];
    		int_istagel(id_2);
    	});
    }
