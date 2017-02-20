function edit() {
	loadFile("Motba/edit_form.html", "html", ".bgcontent", function() {
	});
}

function goback(){
	console.debug(stack);
//	if(Object.size(stack)==0)
//	{
//		loadFile ("Motba/search_form.html", "html", ".bgcontent", function(){});
//		return;
//	}
	
	if(Object.size(stack)!=0){
		
		document_details = stack.pop();
		loadFile("Motba/details.html", "html", ".bgcontent", function() {
		});
		
	}else if( details_back_tracker == come_from_tablet_list ){
		loadFile("FastSearchForTablet/list.html", "html", ".bgcontent", function() {});
	}else if( details_back_tracker == come_from_search_list ) {
		loadFile ("Motba/documents_list.html", "html", ".bgcontent", function(){});
	}else{
		loadFile ("Motba/search_form.html", "html", ".bgcontent", function(){});
	}
	
}

function load_doc_imgs( doc_title) {
	var images = '<ul class="row ace-thumbnails">';

	var img_html = '<li> <a href="=====" target="_blank"  title="mytitle">'
			+ ' <img  style="width: 100%; height: 70%;" src="====="> <div class="text">  </div> </a> </li>';

	var a = document_details.imgs;
	var b = document_details.allPreviousTarbet;
	for ( var i = 0; i < a.length; i++) {
		images += img_html.replace(
				/=====/g,
				a[i].replace(first_part_of_image_path, server_path)
						+ "?cache=" + Math.random()).replace("mytitle",
				doc_title);
	}
	for ( var i = 0; i < b.length; i++) {
		images += img_html.replace(
				/=====/g,
				b[i].replace(first_part_of_image_path, server_path)
						+ "?cache=" + Math.random()).replace("mytitle",
				doc_title);
	}
	
	$("#document-images").append(images);
}

function print() {
	var params = {};
	params.id = document_details.id;
	superConnect(api_source + 'documents/print', params, function(obj) {
		var win = window.open('http://'+host_name+':3000/tmp.pdf', '_blank');
		win.focus();

	});

}

function init_doc_details() {
	$('#target_id').html(global_targets_name[document_details.target_id]);

	$('#status_id').html(global_status_name[document_details.status_id]);
	
	if(document_details.unit_id!= null && document_details.unit_id > 0)
		$('#unit_id').html(global_units_name[document_details.unit_id]);

	if (document_details.doc_type == 0)
		$('#doc_type').html("داخلي");
	else
		$('#doc_type').html("خارجي");
	var motb3a="";
	if(document_details.doc_need_replay==3){
		motb3a="تم الرد";
	}else if(document_details.doc_need_replay==2){
		motb3a="فى المتابعة";
	}
	$("#motb3a").html(motb3a);
	$("#title").html(document_details.title);
	$("#doc_number").html(document_details.doc_number);
	$("#notes").html(document_details.notes);
	var str='';
	for(var x=0; x<document_details.tashira.length;x++)
		str+=global_Motb3aTashira[document_details.tashira[x]-1].name+'<br>';
	$("#tashira").html(str);
	$("#created_at").html(document_details.created_at);
	if (document_details.doc_out_number != 0)
		$("#doc_out_number").html(document_details.doc_out_number);
	$("#doc_out_date").html(document_details.doc_out_date);
	console.debug(document_details.trans_out_num);
	if (document_details.trans_out_num!= null &&document_details.trans_out_num != 0 && document_details.trans_out_num !=''){
		$("#trans_out_num").html(document_details.trans_out_num);
		$("#trans_out_date").html(document_details.trans_out_date);
	}

	var links="";
	if (document_details.parents != null && document_details.parents != '') {
		var link_to = document_details.parents.split("#");
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
	var unitsader={};
	unitsader=document_details.resp_unit;
	var color;
	var istagel;
	var istagel_arry=[];
	var resp_date="";
	var z=0;
	var str_quad='';
	for(var x=0; x<document_details.tashira_qaud.length;x++)
		str_quad+=global_Motb3aTashira[document_details.tashira_qaud[x]-1].name+'<br>';
	$("#tashira_quad").html(str_quad);
	var inner_html;
		inner_html="";
		for ( var row = 0; row < Object.size(document_details.resp_unit); row+=1){
			z=0;
			istagel_arry=[];
			if(unitsader[row].status!=1)
			{
		color="&#10004;";
			}
		else
			{
			color="&#10007;";
			}
			for ( var i = 0; i < Object.size(document_details.estagel); i++)
				{
			if(document_details.estagel[i].unit==unitsader[row].unit){
				istagel_arry[z]=document_details.estagel[i].date;
				z++;
			}	
				}
			istagel="";
			if(unitsader[row].resp_date!=null)
				resp_date=unitsader[row].resp_date;
			else
				resp_date="";
			for ( var i = 0; i < istagel_arry.length; i++)
				{
				istagel+="استعجال رقم "+(i+1)+" "+istagel_arry[i]+"<br>";
				}
			
			inner_html += "<tr style='font-size:16px;color:black;text-align:center;'>"
				+"<td style='text-align: center;'>"
//				+  "<p class='fa fa-check'></p>"
				+color
		    	+ "</td>"
				+ "<th style='text-align: center;'>"
				+  global_units_name[unitsader[row].unit]
			+ "</th>"
			+"<td style='text-align: center;'>"
			+istagel
			+"<br/>"
			+"</td>"
			+"<td style='text-align: center;'>"
			+resp_date
			+"<br/>"
			+"</td>"
			+ "</tr>" ;
		}
	
	$("#units_body").html(inner_html);
	
	$("#parents").html(links);
	//console.debug(inner_html);
	load_doc_imgs( document_details.title);
}


$(document).ready(function() {
//	$('#print').sidr();
	init_doc_details();
	var d = new Date();
	console.debug(document_details.created_at);
	var date2=new Date(document_details.created_at);
	//var date=d.getFullYear()+"-"+(d.getMonth() + 1)+"-"+d.getDate();
//	var timediff=Math.abs(d.getTime()-date2.getTime());
//	var diffDays=Math.ceil(timediff/(1000*3600*24));
//	console.debug(diffDays);

});
